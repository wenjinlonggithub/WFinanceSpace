package com.wfinance.strategies.classic;

import com.wfinance.core.model.Bar;
import com.wfinance.indicators.momentum.RSI;
import com.wfinance.strategy.engine.AbstractStrategy;
import com.wfinance.strategy.engine.StrategyContext;
import com.wfinance.strategy.engine.signal.SignalType;
import com.wfinance.strategy.engine.signal.TradingSignal;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.List;

/**
 * RSI超买超卖策略
 *
 * 策略原理：
 * - RSI < 30: 超卖，买入信号
 * - RSI > 70: 超买，卖出信号
 *
 * RSI是最常用的动量指标之一
 */
@Slf4j
public class RSIOverboughtOversoldStrategy extends AbstractStrategy {
    private final int period;
    private final BigDecimal oversoldLevel;
    private final BigDecimal overboughtLevel;
    private final RSI rsi;

    public RSIOverboughtOversoldStrategy(int period, double oversoldLevel, double overboughtLevel) {
        super(
            "RSI超买超卖策略",
            String.format("周期=%d, 超卖=%s, 超买=%s", period, oversoldLevel, overboughtLevel),
            period + 2
        );

        this.period = period;
        this.oversoldLevel = BigDecimal.valueOf(oversoldLevel);
        this.overboughtLevel = BigDecimal.valueOf(overboughtLevel);
        this.rsi = new RSI(period);
    }

    public RSIOverboughtOversoldStrategy() {
        this(14, 30.0, 70.0);
    }

    @Override
    public TradingSignal generateSignal(StrategyContext context) {
        if (!validateContext(context)) {
            return null;
        }

        List<Bar> bars = context.getBars();
        List<BigDecimal> rsiValues = rsi.calculate(bars);

        int lastIndex = bars.size() - 1;
        BigDecimal currentRSI = rsiValues.get(lastIndex);

        if (currentRSI == null) {
            return null;
        }

        Bar latestBar = context.getLatestBar();

        // 超卖信号
        if (currentRSI.compareTo(oversoldLevel) < 0) {
            log.info("检测到超卖信号: RSI={}", currentRSI);

            return TradingSignal.builder()
                    .signalType(SignalType.BUY)
                    .symbol(context.getSymbol())
                    .timestamp(latestBar.getTimestamp())
                    .price(latestBar.getClose())
                    .strength(calculateOversoldStrength(currentRSI))
                    .reason(String.format("RSI超卖: %.2f < %.2f", currentRSI, oversoldLevel))
                    .build();
        }

        // 超买信号
        if (currentRSI.compareTo(overboughtLevel) > 0) {
            log.info("检测到超买信号: RSI={}", currentRSI);

            return TradingSignal.builder()
                    .signalType(SignalType.SELL)
                    .symbol(context.getSymbol())
                    .timestamp(latestBar.getTimestamp())
                    .price(latestBar.getClose())
                    .strength(calculateOverboughtStrength(currentRSI))
                    .reason(String.format("RSI超买: %.2f > %.2f", currentRSI, overboughtLevel))
                    .build();
        }

        return null;
    }

    private double calculateOversoldStrength(BigDecimal rsi) {
        // RSI越低，信号越强
        double strength = (oversoldLevel.doubleValue() - rsi.doubleValue()) / oversoldLevel.doubleValue();
        return Math.min(1.0, Math.max(0.0, strength));
    }

    private double calculateOverboughtStrength(BigDecimal rsi) {
        // RSI越高，信号越强
        double strength = (rsi.doubleValue() - overboughtLevel.doubleValue()) /
                         (100.0 - overboughtLevel.doubleValue());
        return Math.min(1.0, Math.max(0.0, strength));
    }
}
