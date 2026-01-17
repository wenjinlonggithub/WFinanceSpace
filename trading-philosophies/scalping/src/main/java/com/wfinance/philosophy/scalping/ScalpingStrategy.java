package com.wfinance.philosophy.scalping;

import com.wfinance.core.model.Bar;
import com.wfinance.indicators.momentum.RSI;
import com.wfinance.indicators.trend.EMA;
import com.wfinance.strategy.engine.AbstractStrategy;
import com.wfinance.strategy.engine.StrategyContext;
import com.wfinance.strategy.engine.signal.SignalType;
import com.wfinance.strategy.engine.signal.TradingSignal;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.List;

/**
 * 日内交易哲学策略 (Scalping Philosophy)
 *
 * 核心思想：
 * - "积少成多" - 通过大量小额盈利累积收益
 * - "快速进退" - 不持有过夜，避免隔夜风险
 * - "严格纪律" - 遵循既定规则，不受情绪影响
 * - "高频交易" - 利用市场的微小波动获利
 *
 * 策略实现：
 * - 使用短周期RSI识别短期超买超卖
 * - 使用短周期EMA捕捉即时趋势
 * - 快进快出，追求小而频繁的利润
 */
@Slf4j
public class ScalpingStrategy extends AbstractStrategy {
    private final int rsiPeriod;
    private final int emaFast;
    private final int emaSlow;
    private final RSI rsi;
    private final EMA fastEMA;
    private final EMA slowEMA;

    private final BigDecimal oversoldLevel;
    private final BigDecimal overboughtLevel;

    public ScalpingStrategy(int rsiPeriod, int emaFast, int emaSlow, 
                           double oversoldLevel, double overboughtLevel) {
        super(
            "日内交易策略",
            String.format("日内交易哲学应用：RSI周期=%d, 快线EMA=%d, 慢线EMA=%d", 
                         rsiPeriod, emaFast, emaSlow),
            Math.max(Math.max(rsiPeriod, emaFast), emaSlow) + 2
        );

        this.rsiPeriod = rsiPeriod;
        this.emaFast = emaFast;
        this.emaSlow = emaSlow;
        this.oversoldLevel = BigDecimal.valueOf(oversoldLevel);
        this.overboughtLevel = BigDecimal.valueOf(overboughtLevel);
        this.rsi = new RSI(rsiPeriod);
        this.fastEMA = new EMA(emaFast);
        this.slowEMA = new EMA(emaSlow);
    }

    public ScalpingStrategy() {
        this(5, 8, 13, 20.0, 80.0); // 使用非常短的周期适合日内交易
    }

    @Override
    public TradingSignal generateSignal(StrategyContext context) {
        if (!validateContext(context)) {
            return null;
        }

        List<Bar> bars = context.getBars();
        
        // 计算RSI和EMA
        List<BigDecimal> rsiValues = rsi.calculate(bars);
        List<BigDecimal> fastEMAValues = fastEMA.calculate(bars);
        List<BigDecimal> slowEMAValues = slowEMA.calculate(bars);

        int lastIndex = bars.size() - 1;
        int prevIndex = lastIndex - 1;

        if (lastIndex < 1) { // 至少需要两根K线来比较
            return null;
        }

        BigDecimal currentRSI = rsiValues.get(lastIndex);
        BigDecimal currentFastEMA = fastEMAValues.get(lastIndex);
        BigDecimal currentSlowEMA = slowEMAValues.get(lastIndex);
        BigDecimal prevFastEMA = fastEMAValues.get(prevIndex);
        BigDecimal prevSlowEMA = slowEMAValues.get(prevIndex);
        Bar latestBar = context.getLatestBar();
        BigDecimal currentPrice = latestBar.getClose();

        if (currentRSI == null || currentFastEMA == null || currentSlowEMA == null ||
            prevFastEMA == null || prevSlowEMA == null) {
            return null;
        }

        // 日内做多信号：RSI超卖 + 快线上穿慢线
        if (currentRSI.compareTo(oversoldLevel) < 0 &&
            prevFastEMA.compareTo(prevSlowEMA) <= 0 &&
            currentFastEMA.compareTo(currentSlowEMA) > 0) {

            log.info("日内交易策略检测到做多信号: RSI={}, 快线EMA上穿慢线", currentRSI);

            return TradingSignal.builder()
                    .signalType(SignalType.BUY)
                    .symbol(context.getSymbol())
                    .timestamp(latestBar.getTimestamp())
                    .price(currentPrice)
                    .strength(0.8) // 日内交易通常信号强度较高
                    .reason(String.format("日内做多: RSI(%.2f)<%.1f 且 EMA%d上穿EMA%d", 
                                         currentRSI, oversoldLevel, emaFast, emaSlow))
                    .build();
        }

        // 日内做空信号：RSI超买 + 快线下穿慢线
        if (currentRSI.compareTo(overboughtLevel) > 0 &&
            prevFastEMA.compareTo(prevSlowEMA) >= 0 &&
            currentFastEMA.compareTo(currentSlowEMA) < 0) {

            log.info("日内交易策略检测到做空信号: RSI={}, 快线EMA下穿慢线", currentRSI);

            return TradingSignal.builder()
                    .signalType(SignalType.SELL)
                    .symbol(context.getSymbol())
                    .timestamp(latestBar.getTimestamp())
                    .price(currentPrice)
                    .strength(0.8) // 日内交易通常信号强度较高
                    .reason(String.format("日内做空: RSI(%.2f)>%.1f 且 EMA%d下穿EMA%d", 
                                         currentRSI, overboughtLevel, emaFast, emaSlow))
                    .build();
        }

        return null;
    }
}