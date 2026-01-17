package com.wfinance.backtest.record;

import com.wfinance.core.model.enums.OrderSide;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 交易记录
 * 记录回测过程中的每笔交易
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradeRecord {
    /**
     * 交易ID
     */
    private String tradeId;

    /**
     * 交易品种
     */
    private String symbol;

    /**
     * 交易方向
     */
    private OrderSide side;

    /**
     * 开仓时间
     */
    private LocalDateTime entryTime;

    /**
     * 开仓价格
     */
    private BigDecimal entryPrice;

    /**
     * 平仓时间
     */
    private LocalDateTime exitTime;

    /**
     * 平仓价格
     */
    private BigDecimal exitPrice;

    /**
     * 交易数量
     */
    private BigDecimal quantity;

    /**
     * 手续费
     */
    private BigDecimal commission;

    /**
     * 盈亏金额
     */
    private BigDecimal profitLoss;

    /**
     * 盈亏百分比
     */
    private BigDecimal profitLossPercent;

    /**
     * 持仓时长（秒）
     */
    private Long holdingPeriodSeconds;

    /**
     * 交易信号原因
     */
    private String entryReason;

    /**
     * 平仓原因
     */
    private String exitReason;

    /**
     * 计算盈亏
     */
    public void calculateProfitLoss() {
        if (entryPrice == null || exitPrice == null || quantity == null) {
            return;
        }

        BigDecimal priceDiff;
        if (side == OrderSide.BUY) {
            priceDiff = exitPrice.subtract(entryPrice);
        } else {
            priceDiff = entryPrice.subtract(exitPrice);
        }

        profitLoss = priceDiff.multiply(quantity).subtract(
                commission != null ? commission : BigDecimal.ZERO
        );

        BigDecimal cost = entryPrice.multiply(quantity);
        if (cost.compareTo(BigDecimal.ZERO) != 0) {
            profitLossPercent = profitLoss.divide(cost, 4, BigDecimal.ROUND_HALF_UP)
                    .multiply(new BigDecimal("100"));
        }

        if (entryTime != null && exitTime != null) {
            holdingPeriodSeconds = java.time.Duration.between(entryTime, exitTime).getSeconds();
        }
    }
}
