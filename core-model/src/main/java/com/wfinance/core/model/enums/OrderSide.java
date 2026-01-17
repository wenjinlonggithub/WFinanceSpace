package com.wfinance.core.model.enums;

/**
 * 订单方向
 */
public enum OrderSide {
    /**
     * 买入
     */
    BUY("买入"),

    /**
     * 卖出
     */
    SELL("卖出");

    private final String description;

    OrderSide(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
