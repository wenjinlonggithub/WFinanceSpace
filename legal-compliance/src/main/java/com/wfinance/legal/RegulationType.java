package com.wfinance.legal;

/**
 * 法规类型
 */
public enum RegulationType {

    /**
     * 交易规则
     */
    TRADING_RULES("交易规则"),

    /**
     * 持仓限制
     */
    POSITION_LIMITS("持仓限制"),

    /**
     * 保证金要求
     */
    MARGIN_REQUIREMENTS("保证金要求"),

    /**
     * 信息披露
     */
    DISCLOSURE("信息披露"),

    /**
     * 内幕交易禁止
     */
    INSIDER_TRADING("内幕交易禁止"),

    /**
     * 市场操纵禁止
     */
    MARKET_MANIPULATION("市场操纵禁止"),

    /**
     * 风险管理
     */
    RISK_MANAGEMENT("风险管理"),

    /**
     * 投资者保护
     */
    INVESTOR_PROTECTION("投资者保护"),

    /**
     * 反洗钱
     */
    AML("反洗钱"),

    /**
     * 税务合规
     */
    TAX_COMPLIANCE("税务合规"),

    /**
     * 交易时间限制
     */
    TRADING_HOURS("交易时间限制"),

    /**
     * 其他
     */
    OTHER("其他");

    private final String chineseName;

    RegulationType(String chineseName) {
        this.chineseName = chineseName;
    }

    public String getChineseName() {
        return chineseName;
    }
}
