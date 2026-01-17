package com.wfinance.strategies.classic;

import com.wfinance.core.model.Bar;
import com.wfinance.indicators.trend.SMA;
import com.wfinance.strategy.engine.AbstractStrategy;
import com.wfinance.strategy.engine.StrategyContext;
import com.wfinance.strategy.engine.signal.SignalType;
import com.wfinance.strategy.engine.signal.TradingSignal;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.List;

/**
 * 双均线交叉策略 (Moving Average Crossover Strategy)
 *
 * 策略原理：
 * - 金叉（买入信号）：快线上穿慢线
 * - 死叉（卖出信号）：快线下穿慢线
 *
 * 这是最经典的趋势跟踪策略之一
 */
@Slf4j
public class MovingAverageCrossoverStrategy extends AbstractStrategy {
    private final int fastPeriod;
    private final int slowPeriod;
    private final SMA fastMA;
    private final SMA slowMA;

    /**
     * 构造函数
     *
     * @param fastPeriod 快线周期
     * @param slowPeriod 慢线周期
     */
    public MovingAverageCrossoverStrategy(int fastPeriod, int slowPeriod) {
        super(
            "双均线交叉策略",
            String.format("快线周期=%d, 慢线周期=%d", fastPeriod, slowPeriod),
            slowPeriod + 1
        );

        if (fastPeriod >= slowPeriod) {
            throw new IllegalArgumentException("快线周期必须小于慢线周期");
        }

        this.fastPeriod = fastPeriod;
        this.slowPeriod = slowPeriod;
        this.fastMA = new SMA(fastPeriod);
        this.slowMA = new SMA(slowPeriod);
    }

    /**
     * 使用默认参数 (10, 20)
     */
    public MovingAverageCrossoverStrategy() {
        this(10, 20);
    }

    @Override
    public TradingSignal generateSignal(StrategyContext context) {
        if (!validateContext(context)) {
            return null;
        }

        List<Bar> bars = context.getBars();

        // 计算均线值
        List<BigDecimal> fastValues = fastMA.calculate(bars);
        List<BigDecimal> slowValues = slowMA.calculate(bars);

        int lastIndex = bars.size() - 1;
        int prevIndex = lastIndex - 1;

        // 获取当前和前一根K线的均线值
        BigDecimal currentFast = fastValues.get(lastIndex);
        BigDecimal currentSlow = slowValues.get(lastIndex);
        BigDecimal prevFast = fastValues.get(prevIndex);
        BigDecimal prevSlow = slowValues.get(prevIndex);

        if (currentFast == null || currentSlow == null ||
            prevFast == null || prevSlow == null) {
            return null;
        }

        Bar latestBar = context.getLatestBar();

        // 检测金叉：快线上穿慢线
        if (prevFast.compareTo(prevSlow) <= 0 &&
            currentFast.compareTo(currentSlow) > 0) {

            log.info("检测到金叉信号: 快线={}, 慢线={}", currentFast, currentSlow);

            return TradingSignal.builder()
                    .signalType(SignalType.BUY)
                    .symbol(context.getSymbol())
                    .timestamp(latestBar.getTimestamp())
                    .price(latestBar.getClose())
                    .strength(calculateSignalStrength(currentFast, currentSlow))
                    .reason(String.format("金叉: 快线(%.2f)上穿慢线(%.2f)",
                            currentFast, currentSlow))
                    .build();
        }

        // 检测死叉：快线下穿慢线
        if (prevFast.compareTo(prevSlow) >= 0 &&
            currentFast.compareTo(currentSlow) < 0) {

            log.info("检测到死叉信号: 快线={}, 慢线={}", currentFast, currentSlow);

            return TradingSignal.builder()
                    .signalType(SignalType.SELL)
                    .symbol(context.getSymbol())
                    .timestamp(latestBar.getTimestamp())
                    .price(latestBar.getClose())
                    .strength(calculateSignalStrength(currentSlow, currentFast))
                    .reason(String.format("死叉: 快线(%.2f)下穿慢线(%.2f)",
                            currentFast, currentSlow))
                    .build();
        }

        // 没有交叉信号
        return null;
    }

    /**
     * 计算信号强度（基于两条均线的距离）
     */
    private double calculateSignalStrength(BigDecimal line1, BigDecimal line2) {
        BigDecimal diff = line1.subtract(line2).abs();
        BigDecimal avg = line1.add(line2).divide(BigDecimal.valueOf(2), 4, BigDecimal.ROUND_HALF_UP);

        if (avg.compareTo(BigDecimal.ZERO) == 0) {
            return 0.5;
        }

        double ratio = diff.divide(avg, 4, BigDecimal.ROUND_HALF_UP).doubleValue();
        return Math.min(1.0, ratio * 10); // 归一化到0-1之间
    }
}
