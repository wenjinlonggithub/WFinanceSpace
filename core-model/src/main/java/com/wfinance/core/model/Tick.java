package com.wfinance.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Tick数据模型
 * 表示最细粒度的市场报价数据
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tick {
    /**
     * 交易品种代码
     */
    private String symbol;

    /**
     * 时间戳
     */
    private LocalDateTime timestamp;

    /**
     * 最新价
     */
    private BigDecimal lastPrice;

    /**
     * 买一价
     */
    private BigDecimal bidPrice;

    /**
     * 卖一价
     */
    private BigDecimal askPrice;

    /**
     * 买一量
     */
    private BigDecimal bidVolume;

    /**
     * 卖一量
     */
    private BigDecimal askVolume;

    /**
     * 成交量
     */
    private BigDecimal volume;

    /**
     * 获取买卖价差
     */
    public BigDecimal getSpread() {
        if (askPrice == null || bidPrice == null) {
            return BigDecimal.ZERO;
        }
        return askPrice.subtract(bidPrice);
    }

    /**
     * 获取中间价
     */
    public BigDecimal getMidPrice() {
        if (askPrice == null || bidPrice == null) {
            return lastPrice;
        }
        return askPrice.add(bidPrice).divide(new BigDecimal("2"), 4, BigDecimal.ROUND_HALF_UP);
    }
}
