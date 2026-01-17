package com.wfinance.indicators.trend;

import com.wfinance.core.model.Bar;
import com.wfinance.indicators.Indicator;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * 指数移动平均线 (Exponential Moving Average)
 * 对近期价格赋予更高权重的移动平均线
 */
@Getter
public class EMA implements Indicator {
    private final int period;
    private final String name;
    private final BigDecimal multiplier;

    public EMA(int period) {
        this.period = period;
        this.name = "EMA(" + period + ")";
        // EMA平滑系数 = 2 / (period + 1)
        this.multiplier = BigDecimal.valueOf(2)
                .divide(BigDecimal.valueOf(period + 1), 10, RoundingMode.HALF_UP);
    }

    @Override
    public List<BigDecimal> calculate(List<Bar> bars) {
        List<BigDecimal> result = new ArrayList<>();

        if (bars == null || bars.isEmpty()) {
            return result;
        }

        BigDecimal ema = null;

        for (int i = 0; i < bars.size(); i++) {
            if (i < period - 1) {
                // 数据不足，返回null
                result.add(null);
            } else if (i == period - 1) {
                // 第一个EMA值使用SMA
                BigDecimal sum = BigDecimal.ZERO;
                for (int j = 0; j <= i; j++) {
                    sum = sum.add(bars.get(j).getClose());
                }
                ema = sum.divide(BigDecimal.valueOf(period), 4, RoundingMode.HALF_UP);
                result.add(ema);
            } else {
                // EMA = (当前价格 - 昨日EMA) × 平滑系数 + 昨日EMA
                BigDecimal close = bars.get(i).getClose();
                ema = close.subtract(ema).multiply(multiplier).add(ema);
                result.add(ema.setScale(4, RoundingMode.HALF_UP));
            }
        }

        return result;
    }

    @Override
    public BigDecimal calculateLast(List<Bar> bars) {
        List<BigDecimal> allValues = calculate(bars);
        if (allValues.isEmpty()) {
            return null;
        }
        return allValues.get(allValues.size() - 1);
    }
}
