package com.wfinance.core.model;

import com.wfinance.core.model.enums.OrderSide;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 成交记录模型
 * 表示一笔实际的交易成交
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Trade {
    /**
     * 成交ID（唯一标识）
     */
    private String tradeId;

    /**
     * 关联的订单ID
     */
    private String orderId;

    /**
     * 交易品种代码
     */
    private String symbol;

    /**
     * 交易方向
     */
    private OrderSide side;

    /**
     * 成交价格
     */
    private BigDecimal price;

    /**
     * 成交数量
     */
    private BigDecimal quantity;

    /**
     * 手续费
     */
    private BigDecimal commission;

    /**
     * 成交时间
     */
    private LocalDateTime tradeTime;

    /**
     * 获取成交金额
     */
    public BigDecimal getAmount() {
        return price.multiply(quantity);
    }

    /**
     * 获取净成交金额（扣除手续费）
     */
    public BigDecimal getNetAmount() {
        return getAmount().subtract(commission != null ? commission : BigDecimal.ZERO);
    }
}
