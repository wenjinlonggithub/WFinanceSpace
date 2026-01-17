package com.wfinance.backtest.metrics;

import com.wfinance.backtest.record.TradeRecord;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * 绩效统计
 * 计算回测的各项绩效指标
 */
@Data
public class PerformanceMetrics {
    /**
     * 总收益率
     */
    private BigDecimal totalReturn;

    /**
     * 年化收益率
     */
    private BigDecimal annualizedReturn;

    /**
     * 最大回撤
     */
    private BigDecimal maxDrawdown;

    /**
     * 夏普比率
     */
    private BigDecimal sharpeRatio;

    /**
     * 总交易次数
     */
    private int totalTrades;

    /**
     * 盈利交易次数
     */
    private int winningTrades;

    /**
     * 亏损交易次数
     */
    private int losingTrades;

    /**
     * 胜率
     */
    private BigDecimal winRate;

    /**
     * 平均盈利
     */
    private BigDecimal avgProfit;

    /**
     * 平均亏损
     */
    private BigDecimal avgLoss;

    /**
     * 盈亏比
     */
    private BigDecimal profitFactor;

    /**
     * 计算绩效指标
     */
    public static PerformanceMetrics calculate(
            BigDecimal initialCapital,
            BigDecimal finalCapital,
            List<TradeRecord> trades,
            List<BigDecimal> equityCurve,
            int tradingDays) {

        PerformanceMetrics metrics = new PerformanceMetrics();

        // 计算总收益率
        if (initialCapital.compareTo(BigDecimal.ZERO) > 0) {
            metrics.totalReturn = finalCapital.subtract(initialCapital)
                    .divide(initialCapital, 4, RoundingMode.HALF_UP)
                    .multiply(new BigDecimal("100"));
        }

        // 计算年化收益率
        if (tradingDays > 0) {
            double years = tradingDays / 252.0;
            if (years > 0 && metrics.totalReturn != null) {
                double totalReturnRatio = metrics.totalReturn.divide(
                        new BigDecimal("100"), 10, RoundingMode.HALF_UP
                ).doubleValue();
                double annualizedReturnRatio = Math.pow(1 + totalReturnRatio, 1.0 / years) - 1;
                metrics.annualizedReturn = BigDecimal.valueOf(annualizedReturnRatio * 100)
                        .setScale(2, RoundingMode.HALF_UP);
            }
        }

        // 计算最大回撤
        metrics.maxDrawdown = calculateMaxDrawdown(equityCurve);

        // 计算交易统计
        metrics.totalTrades = trades.size();
        metrics.winningTrades = 0;
        metrics.losingTrades = 0;
        BigDecimal totalProfit = BigDecimal.ZERO;
        BigDecimal totalLoss = BigDecimal.ZERO;

        for (TradeRecord trade : trades) {
            if (trade.getProfitLoss() != null) {
                if (trade.getProfitLoss().compareTo(BigDecimal.ZERO) > 0) {
                    metrics.winningTrades++;
                    totalProfit = totalProfit.add(trade.getProfitLoss());
                } else if (trade.getProfitLoss().compareTo(BigDecimal.ZERO) < 0) {
                    metrics.losingTrades++;
                    totalLoss = totalLoss.add(trade.getProfitLoss().abs());
                }
            }
        }

        // 计算胜率
        if (metrics.totalTrades > 0) {
            metrics.winRate = BigDecimal.valueOf(metrics.winningTrades)
                    .divide(BigDecimal.valueOf(metrics.totalTrades), 4, RoundingMode.HALF_UP)
                    .multiply(new BigDecimal("100"));
        }

        // 计算平均盈利和亏损
        if (metrics.winningTrades > 0) {
            metrics.avgProfit = totalProfit.divide(
                    BigDecimal.valueOf(metrics.winningTrades), 2, RoundingMode.HALF_UP
            );
        }

        if (metrics.losingTrades > 0) {
            metrics.avgLoss = totalLoss.divide(
                    BigDecimal.valueOf(metrics.losingTrades), 2, RoundingMode.HALF_UP
            );
        }

        // 计算盈亏比
        if (totalLoss.compareTo(BigDecimal.ZERO) > 0) {
            metrics.profitFactor = totalProfit.divide(totalLoss, 2, RoundingMode.HALF_UP);
        }

        return metrics;
    }

    private static BigDecimal calculateMaxDrawdown(List<BigDecimal> equityCurve) {
        if (equityCurve == null || equityCurve.isEmpty()) {
            return BigDecimal.ZERO;
        }

        BigDecimal maxDrawdown = BigDecimal.ZERO;
        BigDecimal peak = equityCurve.get(0);

        for (BigDecimal equity : equityCurve) {
            if (equity.compareTo(peak) > 0) {
                peak = equity;
            }

            BigDecimal drawdown = peak.subtract(equity).divide(peak, 4, RoundingMode.HALF_UP);
            if (drawdown.compareTo(maxDrawdown) > 0) {
                maxDrawdown = drawdown;
            }
        }

        return maxDrawdown.multiply(new BigDecimal("100"));
    }
}
