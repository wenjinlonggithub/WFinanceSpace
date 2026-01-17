package com.wfinance.philosophy.swing;

import com.wfinance.core.model.Bar;
import com.wfinance.indicators.momentum.RSI;
import com.wfinance.indicators.trend.MACD;
import com.wfinance.strategy.engine.AbstractStrategy;
import com.wfinance.strategy.engine.StrategyContext;
import com.wfinance.strategy.engine.signal.SignalType;
import com.wfinance.strategy.engine.signal.TradingSignal;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.List;

/**
 * 摆动交易哲学策略 (Swing Trading Philosophy)
 *
 * 核心思想：
 * - "抓取价格摆动" - 捕获中期价格波动的利润
 * - "顺势而为" - 在趋势中的回调和反弹中寻找机会
 * - "平衡风险收益" - 比日内交易有更大的潜在收益，比长线交易有更高的频率
 * - "耐心等待" - 等待明确的入场和出场信号
 *
 * 策略实现：
 * - 使用MACD识别中期趋势
 * - 使用RSI识别局部超买超卖
 * - 结合两种指标过滤假信号
 */
@Slf4j
public class SwingTradingStrategy extends AbstractStrategy {
    private final RSI rsi;
    private final MACD macd;
    
    private final BigDecimal oversoldLevel;
    private final BigDecimal overboughtLevel;

    public SwingTradingStrategy(int rsiPeriod, int fastPeriod, int slowPeriod, int signalPeriod,
                              double oversoldLevel, double overboughtLevel) {
        super(
            "摆动交易策略",
            String.format("摆动交易哲学应用：RSI周期=%d, MACD(%d,%d,%d)", 
                         rsiPeriod, fastPeriod, slowPeriod, signalPeriod),
            Math.max(rsiPeriod, slowPeriod + signalPeriod) + 2
        );

        this.rsi = new RSI(rsiPeriod);
        this.macd = new MACD(fastPeriod, slowPeriod, signalPeriod);
        this.oversoldLevel = BigDecimal.valueOf(oversoldLevel);
        this.overboughtLevel = BigDecimal.valueOf(overboughtLevel);
    }

    public SwingTradingStrategy() {
        this(14, 12, 26, 9, 30.0, 70.0); // 标准MACD和RSI参数
    }

    @Override
    public TradingSignal generateSignal(StrategyContext context) {
        if (!validateContext(context)) {
            return null;
        }

        List<Bar> bars = context.getBars();
        
        // 计算RSI和MACD
        List<BigDecimal> rsiValues = rsi.calculate(bars);
        List<MACD.MACDResult> macdResults = macd.calculate(bars);

        int lastIndex = bars.size() - 1;
        int prevIndex = lastIndex - 1;

        if (lastIndex < 1) {
            return null;
        }

        BigDecimal currentRSI = rsiValues.get(lastIndex);
        MACD.MACDResult currentMACD = macdResults.get(lastIndex);
        MACD.MACDResult prevMACD = macdResults.get(prevIndex);
        Bar latestBar = context.getLatestBar();
        BigDecimal currentPrice = latestBar.getClose();

        if (currentRSI == null || currentMACD == null || prevMACD == null) {
            return null;
        }

        // 摆动做多信号：MACD金叉 + RSI未超买
        if (prevMACD.getMacd().compareTo(prevMACD.getSignal()) <= 0 &&
            currentMACD.getMacd().compareTo(currentMACD.getSignal()) > 0 &&
            currentRSI.compareTo(overboughtLevel) < 0) {

            log.info("摆动交易策略检测到做多信号: MACD金叉={}, RSI={}", 
                     currentMACD.getMacd().subtract(currentMACD.getSignal()), currentRSI);

            return TradingSignal.builder()
                    .signalType(SignalType.BUY)
                    .symbol(context.getSymbol())
                    .timestamp(latestBar.getTimestamp())
                    .price(currentPrice)
                    .strength(0.7)
                    .reason(String.format("摆动做多: MACD金叉 且 RSI(%.2f)<%.1f", 
                                         currentRSI, overboughtLevel))
                    .build();
        }

        // 摆动做空信号：MACD死叉 + RSI未超卖
        if (prevMACD.getMacd().compareTo(prevMACD.getSignal()) >= 0 &&
            currentMACD.getMacd().compareTo(currentMACD.getSignal()) < 0 &&
            currentRSI.compareTo(oversoldLevel) > 0) {

            log.info("摆动交易策略检测到做空信号: MACD死叉={}, RSI={}", 
                     currentMACD.getMacd().subtract(currentMACD.getSignal()), currentRSI);

            return TradingSignal.builder()
                    .signalType(SignalType.SELL)
                    .symbol(context.getSymbol())
                    .timestamp(latestBar.getTimestamp())
                    .price(currentPrice)
                    .strength(0.7)
                    .reason(String.format("摆动做空: MACD死叉 且 RSI(%.2f)>%.1f", 
                                         currentRSI, oversoldLevel))
                    .build();
        }

        return null;
    }
}