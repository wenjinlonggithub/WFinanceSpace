package com.wfinance.strategy.engine.signal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 交易信号
 * 表示策略生成的交易信号
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradingSignal {
    /**
     * 信号类型
     */
    private SignalType signalType;

    /**
     * 交易品种代码
     */
    private String symbol;

    /**
     * 信号生成时间
     */
    private LocalDateTime timestamp;

    /**
     * 建议价格
     */
    private BigDecimal price;

    /**
     * 建议数量
     */
    private BigDecimal quantity;

    /**
     * 信号强度（0-1之间，1表示最强）
     */
    private Double strength;

    /**
     * 信号描述/原因
     */
    private String reason;

    /**
     * 止损价格（可选）
     */
    private BigDecimal stopLoss;

    /**
     * 止盈价格（可选）
     */
    private BigDecimal takeProfit;
}
