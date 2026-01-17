package com.wfinance.indicators.momentum;

import com.wfinance.core.model.Bar;
import com.wfinance.indicators.Indicator;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * 相对强弱指数 (Relative Strength Index)
 * 衡量价格变动的速度和幅度，范围0-100
 * RSI > 70 通常认为超买，RSI < 30 通常认为超卖
 */
@Getter
public class RSI implements Indicator {
    private final int period;
    private final String name;

    public RSI(int period) {
        this.period = period;
        this.name = "RSI(" + period + ")";
    }

    @Override
    public List<BigDecimal> calculate(List<Bar> bars) {
        List<BigDecimal> result = new ArrayList<>();

        if (bars == null || bars.size() < period + 1) {
            return result;
        }

        // 计算价格变化
        List<BigDecimal> gains = new ArrayList<>();
        List<BigDecimal> losses = new ArrayList<>();

        for (int i = 1; i < bars.size(); i++) {
            BigDecimal change = bars.get(i).getClose().subtract(bars.get(i - 1).getClose());
            gains.add(change.compareTo(BigDecimal.ZERO) > 0 ? change : BigDecimal.ZERO);
            losses.add(change.compareTo(BigDecimal.ZERO) < 0 ? change.abs() : BigDecimal.ZERO);
        }

        // 第一个RSI值使用简单平均
        BigDecimal avgGain = BigDecimal.ZERO;
        BigDecimal avgLoss = BigDecimal.ZERO;

        for (int i = 0; i < period; i++) {
            avgGain = avgGain.add(gains.get(i));
            avgLoss = avgLoss.add(losses.get(i));
        }

        avgGain = avgGain.divide(BigDecimal.valueOf(period), 10, RoundingMode.HALF_UP);
        avgLoss = avgLoss.divide(BigDecimal.valueOf(period), 10, RoundingMode.HALF_UP);

        // 添加前面的null值
        for (int i = 0; i <= period; i++) {
            result.add(null);
        }

        // 计算第一个RSI
        BigDecimal rsi = calculateRSI(avgGain, avgLoss);
        result.add(rsi);

        // 使用平滑移动平均计算后续RSI
        for (int i = period; i < gains.size(); i++) {
            avgGain = avgGain.multiply(BigDecimal.valueOf(period - 1))
                    .add(gains.get(i))
                    .divide(BigDecimal.valueOf(period), 10, RoundingMode.HALF_UP);

            avgLoss = avgLoss.multiply(BigDecimal.valueOf(period - 1))
                    .add(losses.get(i))
                    .divide(BigDecimal.valueOf(period), 10, RoundingMode.HALF_UP);

            rsi = calculateRSI(avgGain, avgLoss);
            result.add(rsi);
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

    private BigDecimal calculateRSI(BigDecimal avgGain, BigDecimal avgLoss) {
        if (avgLoss.compareTo(BigDecimal.ZERO) == 0) {
            return new BigDecimal("100");
        }

        BigDecimal rs = avgGain.divide(avgLoss, 10, RoundingMode.HALF_UP);
        BigDecimal rsi = new BigDecimal("100").subtract(
                new BigDecimal("100").divide(
                        BigDecimal.ONE.add(rs), 4, RoundingMode.HALF_UP
                )
        );

        return rsi.setScale(2, RoundingMode.HALF_UP);
    }
}
