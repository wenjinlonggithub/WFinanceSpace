package com.wfinance.core.model;

import com.wfinance.core.model.enums.PositionSide;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 持仓模型
 * 表示一个交易品种的持仓信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Position {
    /**
     * 持仓ID
     */
    private String positionId;

    /**
     * 交易品种代码
     */
    private String symbol;

    /**
     * 持仓方向
     */
    private PositionSide side;

    /**
     * 持仓数量
     */
    private BigDecimal quantity;

    /**
     * 平均开仓价格
     */
    private BigDecimal avgEntryPrice;

    /**
     * 当前市场价格
     */
    private BigDecimal currentPrice;

    /**
     * 开仓时间
     */
    private LocalDateTime openTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 获取持仓市值
     */
    public BigDecimal getMarketValue() {
        return currentPrice.multiply(quantity);
    }

    /**
     * 获取持仓成本
     */
    public BigDecimal getCost() {
        return avgEntryPrice.multiply(quantity);
    }

    /**
     * 获取未实现盈亏
     */
    public BigDecimal getUnrealizedPnL() {
        if (side == PositionSide.LONG) {
            return currentPrice.subtract(avgEntryPrice).multiply(quantity);
        } else if (side == PositionSide.SHORT) {
            return avgEntryPrice.subtract(currentPrice).multiply(quantity);
        }
        return BigDecimal.ZERO;
    }

    /**
     * 获取未实现盈亏百分比
     */
    public BigDecimal getUnrealizedPnLPercent() {
        BigDecimal cost = getCost();
        if (cost.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return getUnrealizedPnL().divide(cost, 4, BigDecimal.ROUND_HALF_UP)
                .multiply(new BigDecimal("100"));
    }

    /**
     * 判断是否为空仓
     */
    public boolean isFlat() {
        return side == PositionSide.FLAT ||
               quantity == null ||
               quantity.compareTo(BigDecimal.ZERO) == 0;
    }
}
