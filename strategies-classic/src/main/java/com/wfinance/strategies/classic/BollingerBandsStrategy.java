package com.wfinance.strategies.classic;

import com.wfinance.core.model.Bar;
import com.wfinance.indicators.volatility.BollingerBands;
import com.wfinance.strategy.engine.AbstractStrategy;
import com.wfinance.strategy.engine.StrategyContext;
import com.wfinance.strategy.engine.signal.SignalType;
import com.wfinance.strategy.engine.signal.TradingSignal;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.List;

/**
 * 布林带突破策略
 *
 * 策略原理：
 * - 价格突破下轨：超卖，买入信号
 * - 价格突破上轨：超买，卖出信号
 * - 价格回归中轨：平仓信号
 */
@Slf4j
public class BollingerBandsStrategy extends AbstractStrategy {
    private final BollingerBands bb;

    public BollingerBandsStrategy(int period, double stdDevMultiplier) {
        super(
            "布林带突破策略",
            String.format("周期=%d, 标准差倍数=%.1f", period, stdDevMultiplier),
            period
        );
        this.bb = new BollingerBands(period, stdDevMultiplier);
    }

    public BollingerBandsStrategy() {
        this(20, 2.0);
    }

    @Override
    public TradingSignal generateSignal(StrategyContext context) {
        if (!validateContext(context)) {
            return null;
        }

        List<Bar> bars = context.getBars();
        List<BollingerBands.BBResult> bbResults = bb.calculate(bars);

        int lastIndex = bars.size() - 1;
        BollingerBands.BBResult current = bbResults.get(lastIndex);
        Bar latestBar = context.getLatestBar();

        if (current.getUpper() == null || current.getLower() == null) {
            return null;
        }

        BigDecimal closePrice = latestBar.getClose();

        // 价格突破下轨：买入信号
        if (closePrice.compareTo(current.getLower()) < 0) {
            log.info("价格突破布林带下轨: 价格={}, 下轨={}", closePrice, current.getLower());

            return TradingSignal.builder()
                    .signalType(SignalType.BUY)
                    .symbol(context.getSymbol())
                    .timestamp(latestBar.getTimestamp())
                    .price(closePrice)
                    .strength(calculateLowerBandStrength(closePrice, current))
                    .reason(String.format("突破下轨: 价格(%.2f) < 下轨(%.2f)",
                            closePrice, current.getLower()))
                    .takeProfit(current.getMiddle())
                    .build();
        }

        // 价格突破上轨：卖出信号
        if (closePrice.compareTo(current.getUpper()) > 0) {
            log.info("价格突破布林带上轨: 价格={}, 上轨={}", closePrice, current.getUpper());

            return TradingSignal.builder()
                    .signalType(SignalType.SELL)
                    .symbol(context.getSymbol())
                    .timestamp(latestBar.getTimestamp())
                    .price(closePrice)
                    .strength(calculateUpperBandStrength(closePrice, current))
                    .reason(String.format("突破上轨: 价格(%.2f) > 上轨(%.2f)",
                            closePrice, current.getUpper()))
                    .takeProfit(current.getMiddle())
                    .build();
        }

        return null;
    }

    private double calculateLowerBandStrength(BigDecimal price, BollingerBands.BBResult bb) {
        BigDecimal distance = bb.getLower().subtract(price);
        BigDecimal bandwidth = bb.getUpper().subtract(bb.getLower());

        if (bandwidth.compareTo(BigDecimal.ZERO) == 0) {
            return 0.5;
        }

        double ratio = distance.divide(bandwidth, 4, BigDecimal.ROUND_HALF_UP).doubleValue();
        return Math.min(1.0, Math.max(0.0, ratio * 2));
    }

    private double calculateUpperBandStrength(BigDecimal price, BollingerBands.BBResult bb) {
        BigDecimal distance = price.subtract(bb.getUpper());
        BigDecimal bandwidth = bb.getUpper().subtract(bb.getLower());

        if (bandwidth.compareTo(BigDecimal.ZERO) == 0) {
            return 0.5;
        }

        double ratio = distance.divide(bandwidth, 4, BigDecimal.ROUND_HALF_UP).doubleValue();
        return Math.min(1.0, Math.max(0.0, ratio * 2));
    }
}
