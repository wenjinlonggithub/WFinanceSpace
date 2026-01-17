package com.wfinance.core.model.enums;

/**
 * 时间周期
 */
public enum TimeFrame {
    /**
     * 1分钟
     */
    M1("1分钟", 60),

    /**
     * 5分钟
     */
    M5("5分钟", 300),

    /**
     * 15分钟
     */
    M15("15分钟", 900),

    /**
     * 30分钟
     */
    M30("30分钟", 1800),

    /**
     * 1小时
     */
    H1("1小时", 3600),

    /**
     * 4小时
     */
    H4("4小时", 14400),

    /**
     * 日线
     */
    D1("日线", 86400),

    /**
     * 周线
     */
    W1("周线", 604800),

    /**
     * 月线
     */
    MN1("月线", 2592000);

    private final String description;
    private final int seconds;

    TimeFrame(String description, int seconds) {
        this.description = description;
        this.seconds = seconds;
    }

    public String getDescription() {
        return description;
    }

    public int getSeconds() {
        return seconds;
    }
}
