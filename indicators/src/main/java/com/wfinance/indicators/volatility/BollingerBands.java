package com.wfinance.indicators.volatility;

import com.wfinance.core.model.Bar;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * 布林带指标 (Bollinger Bands)
 * 由中轨（移动平均线）、上轨和下轨组成
 * 用于衡量价格波动性和超买超卖
 */
public class BollingerBands {
    private final int period;
    private final BigDecimal stdDevMultiplier;
    private final String name;

    public BollingerBands(int period, double stdDevMultiplier) {
        this.period = period;
        this.stdDevMultiplier = BigDecimal.valueOf(stdDevMultiplier);
        this.name = "BB(" + period + "," + stdDevMultiplier + ")";
    }

    /**
     * 使用默认参数 (20, 2.0)
     */
    public BollingerBands() {
        this(20, 2.0);
    }

    /**
     * 计算布林带
     *
     * @param bars K线数据
     * @return 布林带结果
     */
    public List<BBResult> calculate(List<Bar> bars) {
        List<BBResult> results = new ArrayList<>();

        if (bars == null || bars.isEmpty()) {
            return results;
        }

        for (int i = 0; i < bars.size(); i++) {
            if (i < period - 1) {
                results.add(new BBResult(null, null, null));
            } else {
                // 计算中轨（SMA）
                BigDecimal sum = BigDecimal.ZERO;
                for (int j = i - period + 1; j <= i; j++) {
                    sum = sum.add(bars.get(j).getClose());
                }
                BigDecimal middle = sum.divide(BigDecimal.valueOf(period), 4, RoundingMode.HALF_UP);

                // 计算标准差
                BigDecimal variance = BigDecimal.ZERO;
                for (int j = i - period + 1; j <= i; j++) {
                    BigDecimal diff = bars.get(j).getClose().subtract(middle);
                    variance = variance.add(diff.multiply(diff));
                }
                variance = variance.divide(BigDecimal.valueOf(period), 10, RoundingMode.HALF_UP);
                BigDecimal stdDev = BigDecimal.valueOf(Math.sqrt(variance.doubleValue()));

                // 计算上轨和下轨
                BigDecimal upper = middle.add(stdDev.multiply(stdDevMultiplier));
                BigDecimal lower = middle.subtract(stdDev.multiply(stdDevMultiplier));

                results.add(new BBResult(
                        upper.setScale(4, RoundingMode.HALF_UP),
                        middle.setScale(4, RoundingMode.HALF_UP),
                        lower.setScale(4, RoundingMode.HALF_UP)
                ));
            }
        }

        return results;
    }

    public String getName() {
        return name;
    }

    /**
     * 布林带计算结果
     */
    @Data
    @AllArgsConstructor
    public static class BBResult {
        /**
         * 上轨
         */
        private BigDecimal upper;

        /**
         * 中轨（移动平均线）
         */
        private BigDecimal middle;

        /**
         * 下轨
         */
        private BigDecimal lower;
    }
}
