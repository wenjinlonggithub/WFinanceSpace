package com.wfinance.core.model.enums;

/**
 * 订单状态
 */
public enum OrderStatus {
    /**
     * 待提交
     */
    PENDING("待提交"),

    /**
     * 已提交
     */
    SUBMITTED("已提交"),

    /**
     * 部分成交
     */
    PARTIALLY_FILLED("部分成交"),

    /**
     * 完全成交
     */
    FILLED("完全成交"),

    /**
     * 已取消
     */
    CANCELLED("已取消"),

    /**
     * 已拒绝
     */
    REJECTED("已拒绝");

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
