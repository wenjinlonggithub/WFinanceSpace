package com.wfinance.indicators.trend;

import com.wfinance.core.model.Bar;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * MACD指标 (Moving Average Convergence Divergence)
 * 由快线(DIF)、慢线(DEA)和柱状图(MACD)组成
 */
public class MACD {
    private final int fastPeriod;
    private final int slowPeriod;
    private final int signalPeriod;
    private final String name;

    public MACD(int fastPeriod, int slowPeriod, int signalPeriod) {
        this.fastPeriod = fastPeriod;
        this.slowPeriod = slowPeriod;
        this.signalPeriod = signalPeriod;
        this.name = "MACD(" + fastPeriod + "," + slowPeriod + "," + signalPeriod + ")";
    }

    /**
     * 使用默认参数 (12, 26, 9)
     */
    public MACD() {
        this(12, 26, 9);
    }

    /**
     * 计算MACD指标
     *
     * @param bars K线数据
     * @return MACD结果
     */
    public List<MACDResult> calculate(List<Bar> bars) {
        List<MACDResult> results = new ArrayList<>();

        if (bars == null || bars.isEmpty()) {
            return results;
        }

        // 计算快线EMA和慢线EMA
        EMA fastEMA = new EMA(fastPeriod);
        EMA slowEMA = new EMA(slowPeriod);

        List<BigDecimal> fastValues = fastEMA.calculate(bars);
        List<BigDecimal> slowValues = slowEMA.calculate(bars);

        // 计算DIF (快线 - 慢线)
        List<BigDecimal> difValues = new ArrayList<>();
        for (int i = 0; i < bars.size(); i++) {
            if (fastValues.get(i) == null || slowValues.get(i) == null) {
                difValues.add(null);
            } else {
                difValues.add(fastValues.get(i).subtract(slowValues.get(i)));
            }
        }

        // 计算DEA (DIF的EMA)
        List<BigDecimal> deaValues = calculateDEA(difValues);

        // 计算MACD柱状图 (DIF - DEA) * 2
        for (int i = 0; i < bars.size(); i++) {
            BigDecimal dif = difValues.get(i);
            BigDecimal dea = deaValues.get(i);
            BigDecimal macd = null;

            if (dif != null && dea != null) {
                macd = dif.subtract(dea).multiply(BigDecimal.valueOf(2));
            }

            results.add(new MACDResult(dif, dea, macd));
        }

        return results;
    }

    private List<BigDecimal> calculateDEA(List<BigDecimal> difValues) {
        List<BigDecimal> deaValues = new ArrayList<>();
        BigDecimal dea = null;
        BigDecimal multiplier = BigDecimal.valueOf(2)
                .divide(BigDecimal.valueOf(signalPeriod + 1), 10, java.math.RoundingMode.HALF_UP);

        int validCount = 0;
        BigDecimal sum = BigDecimal.ZERO;

        for (BigDecimal dif : difValues) {
            if (dif == null) {
                deaValues.add(null);
            } else {
                if (validCount < signalPeriod) {
                    sum = sum.add(dif);
                    validCount++;
                    if (validCount == signalPeriod) {
                        dea = sum.divide(BigDecimal.valueOf(signalPeriod), 4, java.math.RoundingMode.HALF_UP);
                        deaValues.add(dea);
                    } else {
                        deaValues.add(null);
                    }
                } else {
                    dea = dif.subtract(dea).multiply(multiplier).add(dea);
                    deaValues.add(dea.setScale(4, java.math.RoundingMode.HALF_UP));
                }
            }
        }

        return deaValues;
    }

    public String getName() {
        return name;
    }

    /**
     * MACD计算结果
     */
    @Data
    @AllArgsConstructor
    public static class MACDResult {
        /**
         * DIF值 (快线 - 慢线)
         */
        private BigDecimal dif;

        /**
         * DEA值 (DIF的EMA)
         */
        private BigDecimal dea;

        /**
         * MACD柱状图 (DIF - DEA) * 2
         */
        private BigDecimal macd;
    }
}
