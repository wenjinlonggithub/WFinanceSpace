package com.wfinance.core.model.enums;

/**
 * 订单类型
 */
public enum OrderType {
    /**
     * 市价单 - 以当前市场价格立即执行
     */
    MARKET("市价单"),

    /**
     * 限价单 - 以指定价格或更好的价格执行
     */
    LIMIT("限价单"),

    /**
     * 止损单 - 当价格达到止损价时转为市价单
     */
    STOP("止损单"),

    /**
     * 止损限价单 - 当价格达到止损价时转为限价单
     */
    STOP_LIMIT("止损限价单");

    private final String description;

    OrderType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
