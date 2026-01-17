package com.wfinance.strategies.classic;

import com.wfinance.core.model.Bar;
import com.wfinance.indicators.trend.MACD;
import com.wfinance.strategy.engine.AbstractStrategy;
import com.wfinance.strategy.engine.StrategyContext;
import com.wfinance.strategy.engine.signal.SignalType;
import com.wfinance.strategy.engine.signal.TradingSignal;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.List;

/**
 * MACD策略
 *
 * 策略原理：
 * - DIF上穿DEA（金叉）：买入信号
 * - DIF下穿DEA（死叉）：卖出信号
 * - MACD柱状图由负转正：买入确认
 * - MACD柱状图由正转负：卖出确认
 */
@Slf4j
public class MACDStrategy extends AbstractStrategy {
    private final MACD macd;

    public MACDStrategy(int fastPeriod, int slowPeriod, int signalPeriod) {
        super(
            "MACD策略",
            String.format("快线=%d, 慢线=%d, 信号线=%d", fastPeriod, slowPeriod, signalPeriod),
            slowPeriod + signalPeriod
        );
        this.macd = new MACD(fastPeriod, slowPeriod, signalPeriod);
    }

    public MACDStrategy() {
        this(12, 26, 9);
    }

    @Override
    public TradingSignal generateSignal(StrategyContext context) {
        if (!validateContext(context)) {
            return null;
        }

        List<Bar> bars = context.getBars();
        List<MACD.MACDResult> macdResults = macd.calculate(bars);

        int lastIndex = bars.size() - 1;
        int prevIndex = lastIndex - 1;

        MACD.MACDResult current = macdResults.get(lastIndex);
        MACD.MACDResult prev = macdResults.get(prevIndex);

        if (current.getDif() == null || current.getDea() == null ||
            prev.getDif() == null || prev.getDea() == null) {
            return null;
        }

        Bar latestBar = context.getLatestBar();

        // 检测金叉：DIF上穿DEA
        if (prev.getDif().compareTo(prev.getDea()) <= 0 &&
            current.getDif().compareTo(current.getDea()) > 0) {

            log.info("检测到MACD金叉: DIF={}, DEA={}", current.getDif(), current.getDea());

            return TradingSignal.builder()
                    .signalType(SignalType.BUY)
                    .symbol(context.getSymbol())
                    .timestamp(latestBar.getTimestamp())
                    .price(latestBar.getClose())
                    .strength(calculateSignalStrength(current))
                    .reason(String.format("MACD金叉: DIF(%.4f)上穿DEA(%.4f)",
                            current.getDif(), current.getDea()))
                    .build();
        }

        // 检测死叉：DIF下穿DEA
        if (prev.getDif().compareTo(prev.getDea()) >= 0 &&
            current.getDif().compareTo(current.getDea()) < 0) {

            log.info("检测到MACD死叉: DIF={}, DEA={}", current.getDif(), current.getDea());

            return TradingSignal.builder()
                    .signalType(SignalType.SELL)
                    .symbol(context.getSymbol())
                    .timestamp(latestBar.getTimestamp())
                    .price(latestBar.getClose())
                    .strength(calculateSignalStrength(current))
                    .reason(String.format("MACD死叉: DIF(%.4f)下穿DEA(%.4f)",
                            current.getDif(), current.getDea()))
                    .build();
        }

        return null;
    }

    private double calculateSignalStrength(MACD.MACDResult result) {
        if (result.getMacd() == null) {
            return 0.5;
        }
        // 基于MACD柱状图的绝对值计算信号强度
        double macdValue = Math.abs(result.getMacd().doubleValue());
        return Math.min(1.0, macdValue * 10);
    }
}
