package com.wfinance.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 账户模型
 * 表示一个交易账户的资金和持仓信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    /**
     * 账户ID
     */
    private String accountId;

    /**
     * 账户名称
     */
    private String accountName;

    /**
     * 初始资金
     */
    private BigDecimal initialCapital;

    /**
     * 可用资金
     */
    private BigDecimal availableBalance;

    /**
     * 冻结资金（未成交订单占用）
     */
    private BigDecimal frozenBalance;

    /**
     * 持仓列表
     */
    @Builder.Default
    private List<Position> positions = new ArrayList<>();

    /**
     * 账户货币
     */
    private String currency;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 获取总资金（可用 + 冻结）
     */
    public BigDecimal getTotalBalance() {
        return availableBalance.add(frozenBalance != null ? frozenBalance : BigDecimal.ZERO);
    }

    /**
     * 获取持仓市值
     */
    public BigDecimal getPositionValue() {
        return positions.stream()
                .map(Position::getMarketValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * 获取账户总资产（资金 + 持仓市值）
     */
    public BigDecimal getTotalAssets() {
        return getTotalBalance().add(getPositionValue());
    }

    /**
     * 获取总盈亏
     */
    public BigDecimal getTotalPnL() {
        return getTotalAssets().subtract(initialCapital);
    }

    /**
     * 获取总盈亏百分比
     */
    public BigDecimal getTotalPnLPercent() {
        if (initialCapital.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return getTotalPnL().divide(initialCapital, 4, BigDecimal.ROUND_HALF_UP)
                .multiply(new BigDecimal("100"));
    }
}
