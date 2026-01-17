package com.wfinance.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * K线数据模型 (OHLCV)
 * 表示一个时间周期内的价格数据
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bar {
    /**
     * 交易品种代码
     */
    private String symbol;

    /**
     * 时间戳
     */
    private LocalDateTime timestamp;

    /**
     * 开盘价
     */
    private BigDecimal open;

    /**
     * 最高价
     */
    private BigDecimal high;

    /**
     * 最低价
     */
    private BigDecimal low;

    /**
     * 收盘价
     */
    private BigDecimal close;

    /**
     * 成交量
     */
    private BigDecimal volume;

    /**
     * 成交额（可选）
     */
    private BigDecimal amount;

    /**
     * 获取价格范围（最高价 - 最低价）
     */
    public BigDecimal getRange() {
        return high.subtract(low);
    }

    /**
     * 获取涨跌幅（收盘价 - 开盘价）
     */
    public BigDecimal getChange() {
        return close.subtract(open);
    }

    /**
     * 获取涨跌幅百分比
     */
    public BigDecimal getChangePercent() {
        if (open.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return getChange().divide(open, 4, BigDecimal.ROUND_HALF_UP)
                .multiply(new BigDecimal("100"));
    }

    /**
     * 判断是否为阳线
     */
    public boolean isBullish() {
        return close.compareTo(open) > 0;
    }

    /**
     * 判断是否为阴线
     */
    public boolean isBearish() {
        return close.compareTo(open) < 0;
    }
}
