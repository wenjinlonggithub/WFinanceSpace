package com.wfinance.core.model;

import com.wfinance.core.model.enums.MarketType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 交易品种模型
 * 表示一个可交易的金融工具
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Instrument {
    /**
     * 品种代码（唯一标识）
     */
    private String symbol;

    /**
     * 品种名称
     */
    private String name;

    /**
     * 市场类型
     */
    private MarketType marketType;

    /**
     * 交易所代码
     */
    private String exchange;

    /**
     * 最小价格变动单位
     */
    private BigDecimal tickSize;

    /**
     * 合约乘数（期货/期权使用）
     */
    private BigDecimal contractMultiplier;

    /**
     * 最小交易数量
     */
    private BigDecimal minQuantity;

    /**
     * 交易货币
     */
    private String currency;

    /**
     * 是否支持做空
     */
    private boolean shortable;

    /**
     * 保证金比例（期货/外汇使用）
     */
    private BigDecimal marginRate;
}
