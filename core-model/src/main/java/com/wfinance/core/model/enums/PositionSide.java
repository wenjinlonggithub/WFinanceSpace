package com.wfinance.core.model.enums;

/**
 * 持仓方向
 */
public enum PositionSide {
    /**
     * 多头持仓
     */
    LONG("多头"),

    /**
     * 空头持仓
     */
    SHORT("空头"),

    /**
     * 无持仓
     */
    FLAT("平仓");

    private final String description;

    PositionSide(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
