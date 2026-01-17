package com.wfinance.indicators.trend;

import com.wfinance.core.model.Bar;
import com.wfinance.indicators.Indicator;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * 简单移动平均线 (Simple Moving Average)
 * 计算指定周期内的价格平均值
 */
@Getter
public class SMA implements Indicator {
    private final int period;
    private final String name;

    public SMA(int period) {
        this.period = period;
        this.name = "SMA(" + period + ")";
    }

    @Override
    public List<BigDecimal> calculate(List<Bar> bars) {
        List<BigDecimal> result = new ArrayList<>();

        if (bars == null || bars.isEmpty()) {
            return result;
        }

        for (int i = 0; i < bars.size(); i++) {
            if (i < period - 1) {
                // 数据不足，返回null
                result.add(null);
            } else {
                // 计算最近period个周期的平均值
                BigDecimal sum = BigDecimal.ZERO;
                for (int j = i - period + 1; j <= i; j++) {
                    sum = sum.add(bars.get(j).getClose());
                }
                BigDecimal sma = sum.divide(BigDecimal.valueOf(period), 4, RoundingMode.HALF_UP);
                result.add(sma);
            }
        }

        return result;
    }

    @Override
    public BigDecimal calculateLast(List<Bar> bars) {
        if (bars == null || bars.size() < period) {
            return null;
        }

        BigDecimal sum = BigDecimal.ZERO;
        for (int i = bars.size() - period; i < bars.size(); i++) {
            sum = sum.add(bars.get(i).getClose());
        }

        return sum.divide(BigDecimal.valueOf(period), 4, RoundingMode.HALF_UP);
    }
}
