package com.wfinance.backtest.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 回测配置
 * 定义回测的各项参数
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BacktestConfig {
    /**
     * 初始资金
     */
    private BigDecimal initialCapital;

    /**
     * 回测开始时间
     */
    private LocalDateTime startTime;

    /**
     * 回测结束时间
     */
    private LocalDateTime endTime;

    /**
     * 手续费率（例如：0.001 表示 0.1%）
     */
    @Builder.Default
    private BigDecimal commissionRate = new BigDecimal("0.001");

    /**
     * 滑点（点数）
     */
    @Builder.Default
    private BigDecimal slippage = BigDecimal.ZERO;

    /**
     * 每次交易的资金比例（0-1之间）
     */
    @Builder.Default
    private BigDecimal positionSizeRatio = new BigDecimal("1.0");

    /**
     * 是否允许做空
     */
    @Builder.Default
    private boolean allowShort = false;

    /**
     * 最大持仓数量
     */
    @Builder.Default
    private int maxPositions = 1;
}
