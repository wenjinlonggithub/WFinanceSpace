package com.wfinance.core.model.enums;

/**
 * 市场类型
 */
public enum MarketType {
    /**
     * 股票市场
     */
    STOCK("股票"),

    /**
     * 期货市场
     */
    FUTURES("期货"),

    /**
     * 外汇市场
     */
    FOREX("外汇"),

    /**
     * 加密货币市场
     */
    CRYPTO("加密货币"),

    /**
     * 期权市场
     */
    OPTIONS("期权");

    private final String description;

    MarketType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
