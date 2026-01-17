package com.wfinance.indicators.volatility;

import com.wfinance.core.model.Bar;
import com.wfinance.indicators.Indicator;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * 平均真实波幅 (Average True Range)
 * 衡量市场波动性的指标
 */
@Getter
public class ATR implements Indicator {
    private final int period;
    private final String name;

    public ATR(int period) {
        this.period = period;
        this.name = "ATR(" + period + ")";
    }

    @Override
    public List<BigDecimal> calculate(List<Bar> bars) {
        List<BigDecimal> result = new ArrayList<>();

        if (bars == null || bars.isEmpty()) {
            return result;
        }

        // 第一根K线没有TR
        result.add(null);

        // 计算TR (True Range)
        List<BigDecimal> trValues = new ArrayList<>();
        trValues.add(null);

        for (int i = 1; i < bars.size(); i++) {
            Bar current = bars.get(i);
            Bar previous = bars.get(i - 1);

            BigDecimal tr = calculateTrueRange(current, previous);
            trValues.add(tr);
        }

        // 计算ATR（使用指数移动平均）
        BigDecimal atr = null;
        int validCount = 0;
        BigDecimal sum = BigDecimal.ZERO;

        for (int i = 1; i < bars.size(); i++) {
            BigDecimal tr = trValues.get(i);

            if (tr == null) {
                result.add(null);
                continue;
            }

            if (validCount < period) {
                sum = sum.add(tr);
                validCount++;

                if (validCount == period) {
                    atr = sum.divide(BigDecimal.valueOf(period), 4, RoundingMode.HALF_UP);
                    result.add(atr);
                } else {
                    result.add(null);
                }
            } else {
                // ATR = (前一日ATR × (n-1) + 当日TR) / n
                atr = atr.multiply(BigDecimal.valueOf(period - 1))
                        .add(tr)
                        .divide(BigDecimal.valueOf(period), 4, RoundingMode.HALF_UP);
                result.add(atr);
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

    /**
     * 计算真实波幅
     * TR = max(high - low, abs(high - 前收), abs(low - 前收))
     */
    private BigDecimal calculateTrueRange(Bar current, Bar previous) {
        BigDecimal hl = current.getHigh().subtract(current.getLow());
        BigDecimal hc = current.getHigh().subtract(previous.getClose()).abs();
        BigDecimal lc = current.getLow().subtract(previous.getClose()).abs();

        return hl.max(hc).max(lc);
    }
}
