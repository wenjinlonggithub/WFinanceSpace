package com.wfinance.backtest.result;

import com.wfinance.backtest.config.BacktestConfig;
import com.wfinance.backtest.metrics.PerformanceMetrics;
import com.wfinance.backtest.record.TradeRecord;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 回测结果
 * 包含回测的完整结果信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BacktestResult {
    /**
     * 回测配置
     */
    private BacktestConfig config;

    /**
     * 策略名称
     */
    private String strategyName;

    /**
     * 交易品种
     */
    private String symbol;

    /**
     * 初始资金
     */
    private BigDecimal initialCapital;

    /**
     * 最终资金
     */
    private BigDecimal finalCapital;

    /**
     * 回测开始时间
     */
    private LocalDateTime startTime;

    /**
     * 回测结束时间
     */
    private LocalDateTime endTime;

    /**
     * 交易记录列表
     */
    @Builder.Default
    private List<TradeRecord> trades = new ArrayList<>();

    /**
     * 资金曲线
     */
    @Builder.Default
    private List<BigDecimal> equityCurve = new ArrayList<>();

    /**
     * 绩效指标
     */
    private PerformanceMetrics metrics;

    /**
     * 打印回测报告
     */
    public String generateReport() {
        StringBuilder report = new StringBuilder();
        report.append("\n========== 回测报告 ==========\n");
        report.append(String.format("策略名称: %s\n", strategyName));
        report.append(String.format("交易品种: %s\n", symbol));
        report.append(String.format("回测周期: %s 至 %s\n", startTime, endTime));
        report.append("\n--- 资金情况 ---\n");
        report.append(String.format("初始资金: %.2f\n", initialCapital));
        report.append(String.format("最终资金: %.2f\n", finalCapital));
        report.append(String.format("总收益: %.2f (%.2f%%)\n",
                finalCapital.subtract(initialCapital), metrics.getTotalReturn()));

        report.append("\n--- 绩效指标 ---\n");
        report.append(String.format("年化收益率: %.2f%%\n", metrics.getAnnualizedReturn()));
        report.append(String.format("最大回撤: %.2f%%\n", metrics.getMaxDrawdown()));
        report.append(String.format("总交易次数: %d\n", metrics.getTotalTrades()));
        report.append(String.format("盈利次数: %d\n", metrics.getWinningTrades()));
        report.append(String.format("亏损次数: %d\n", metrics.getLosingTrades()));
        report.append(String.format("胜率: %.2f%%\n", metrics.getWinRate()));

        if (metrics.getAvgProfit() != null) {
            report.append(String.format("平均盈利: %.2f\n", metrics.getAvgProfit()));
        }
        if (metrics.getAvgLoss() != null) {
            report.append(String.format("平均亏损: %.2f\n", metrics.getAvgLoss()));
        }
        if (metrics.getProfitFactor() != null) {
            report.append(String.format("盈亏比: %.2f\n", metrics.getProfitFactor()));
        }

        report.append("\n=============================\n");
        return report.toString();
    }
}
