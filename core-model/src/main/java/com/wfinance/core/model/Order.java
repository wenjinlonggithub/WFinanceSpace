package com.wfinance.core.model;

import com.wfinance.core.model.enums.OrderSide;
import com.wfinance.core.model.enums.OrderStatus;
import com.wfinance.core.model.enums.OrderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单模型
 * 表示一个交易订单
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    /**
     * 订单ID（唯一标识）
     */
    private String orderId;

    /**
     * 交易品种代码
     */
    private String symbol;

    /**
     * 订单类型
     */
    private OrderType orderType;

    /**
     * 订单方向
     */
    private OrderSide orderSide;

    /**
     * 订单状态
     */
    private OrderStatus orderStatus;

    /**
     * 订单数量
     */
    private BigDecimal quantity;

    /**
     * 已成交数量
     */
    private BigDecimal filledQuantity;

    /**
     * 订单价格（限价单使用）
     */
    private BigDecimal price;

    /**
     * 止损价格（止损单使用）
     */
    private BigDecimal stopPrice;

    /**
     * 平均成交价格
     */
    private BigDecimal avgFillPrice;

    /**
     * 订单创建时间
     */
    private LocalDateTime createTime;

    /**
     * 订单更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 获取剩余未成交数量
     */
    public BigDecimal getRemainingQuantity() {
        if (filledQuantity == null) {
            return quantity;
        }
        return quantity.subtract(filledQuantity);
    }

    /**
     * 判断订单是否完全成交
     */
    public boolean isFilled() {
        return orderStatus == OrderStatus.FILLED;
    }

    /**
     * 判断订单是否活跃（可以继续成交）
     */
    public boolean isActive() {
        return orderStatus == OrderStatus.SUBMITTED ||
               orderStatus == OrderStatus.PARTIALLY_FILLED;
    }
}
