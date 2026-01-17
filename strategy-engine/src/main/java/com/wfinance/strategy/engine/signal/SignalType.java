package com.wfinance.strategy.engine.signal;

/**
 * 交易信号类型
 */
public enum SignalType {
    /**
     * 买入信号
     */
    BUY("买入"),

    /**
     * 卖出信号
     */
    SELL("卖出"),

    /**
     * 持有信号（不操作）
     */
    HOLD("持有"),

    /**
     * 平仓信号
     */
    CLOSE("平仓");

    private final String description;

    SignalType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
