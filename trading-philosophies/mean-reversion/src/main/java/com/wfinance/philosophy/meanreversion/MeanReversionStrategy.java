package com.wfinance.philosophy.meanreversion;

import com.wfinance.core.model.Bar;
import com.wfinance.indicators.momentum.RSI;
import com.wfinance.indicators.trend.SMA;
import com.wfinance.strategy.engine.AbstractStrategy;
import com.wfinance.strategy.engine.StrategyContext;
import com.wfinance.strategy.engine.signal.SignalType;
import com.wfinance.strategy.engine.signal.TradingSignal;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.List;

/**
 * 均值回归哲学策略
 *
 * 核心思想：
 * - "价格总是围绕价值中枢波动" - 高估时卖出，低估时买入
 * - "物极必反" - 极端情况后往往会回归正常水平
 * - "低买高卖" - 在相对低位买入，在相对高位卖出
 *
 * 策略实现：
 * - 使用RSI指标识别超买超卖状态
 * - 超卖时做多，超买时做空
 * - 结合价格相对于均值的位置确认信号
 */
@Slf4j
public class MeanReversionStrategy extends AbstractStrategy {
    private final int rsiPeriod;
    private final int maPeriod;
    private final RSI rsi;
    private final SMA ma;
    
    private final BigDecimal oversoldLevel;
    private final BigDecimal overboughtLevel;

    public MeanReversionStrategy(int rsiPeriod, int maPeriod, double oversoldLevel, double overboughtLevel) {
        super(
            "均值回归策略",
            String.format("均值回归哲学应用：RSI周期=%d, 均线周期=%d, 超卖=%.1f, 超买=%.1f", 
                         rsiPeriod, maPeriod, oversoldLevel, overboughtLevel),
            Math.max(rsiPeriod, maPeriod) + 2
        );

        this.rsiPeriod = rsiPeriod;
        this.maPeriod = maPeriod;
        this.oversoldLevel = BigDecimal.valueOf(oversoldLevel);
        this.overboughtLevel = BigDecimal.valueOf(overboughtLevel);
        this.rsi = new RSI(rsiPeriod);
        this.ma = new SMA(maPeriod);
    }

    public MeanReversionStrategy() {
        this(14, 20, 30.0, 70.0);
    }

    @Override
    public TradingSignal generateSignal(StrategyContext context) {
        if (!validateContext(context)) {
            return null;
        }

        List<Bar> bars = context.getBars();
        
        // 计算RSI和移动平均线
        List<BigDecimal> rsiValues = rsi.calculate(bars);
        List<BigDecimal> maValues = ma.calculate(bars);

        int lastIndex = bars.size() - 1;

        if (lastIndex < 0) {
            return null;
        }

        BigDecimal currentRSI = rsiValues.get(lastIndex);
        BigDecimal currentMA = maValues.get(lastIndex);
        Bar latestBar = context.getLatestBar();
        BigDecimal currentPrice = latestBar.getClose();

        if (currentRSI == null || currentMA == null) {
            return null;
        }

        // 均值回归做多信号：RSI超卖 + 价格低于均值
        if (currentRSI.compareTo(oversoldLevel) < 0 && 
            currentPrice.compareTo(currentMA) < 0) {

            log.info("均值回归策略检测到做多信号: RSI={}, 价格={}, 均值={}", 
                     currentRSI, currentPrice, currentMA);

            return TradingSignal.builder()
                    .signalType(SignalType.BUY)
                    .symbol(context.getSymbol())
                    .timestamp(latestBar.getTimestamp())
                    .price(currentPrice)
                    .strength(calculateReversionStrength(currentRSI, oversoldLevel, SignalType.BUY))
                    .reason(String.format("均值回归做多: RSI(%.2f)<%.1f 且 价格(%.2f)<均线(%.2f)", 
                                         currentRSI, oversoldLevel, 
                                         currentPrice, currentMA))
                    .build();
        }

        // 均值回归做空信号：RSI超买 + 价格高于均值
        if (currentRSI.compareTo(overboughtLevel) > 0 && 
            currentPrice.compareTo(currentMA) > 0) {

            log.info("均值回归策略检测到做空信号: RSI={}, 价格={}, 均值={}", 
                     currentRSI, currentPrice, currentMA);

            return TradingSignal.builder()
                    .signalType(SignalType.SELL)
                    .symbol(context.getSymbol())
                    .timestamp(latestBar.getTimestamp())
                    .price(currentPrice)
                    .strength(calculateReversionStrength(currentRSI, overboughtLevel, SignalType.SELL))
                    .reason(String.format("均值回归做空: RSI(%.2f)>%.1f 且 价格(%.2f)>均线(%.2f)", 
                                         currentRSI, overboughtLevel, 
                                         currentPrice, currentMA))
                    .build();
        }

        return null;
    }

    /**
     * 计算回归信号强度
     */
    private double calculateReversionStrength(BigDecimal indicatorValue, BigDecimal threshold, SignalType signalType) {
        if (signalType == SignalType.BUY) {
            // 对于做多信号，RSI越低强度越大
            double diff = threshold.doubleValue() - indicatorValue.doubleValue();
            return Math.min(1.0, Math.max(0.0, diff / 10)); // 假设最大差异是40点（30-70）
        } else {
            // 对于做空信号，RSI越高强度越大
            double diff = indicatorValue.doubleValue() - threshold.doubleValue();
            return Math.min(1.0, Math.max(0.0, diff / 30)); // 假设最大差异是30点（70-100）
        }
    }
}