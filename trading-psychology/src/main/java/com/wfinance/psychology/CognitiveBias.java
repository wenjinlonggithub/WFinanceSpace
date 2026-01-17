package com.wfinance.psychology;

/**
 * 认知偏差
 * 交易中常见的认知偏差类型
 */
public enum CognitiveBias {

    /**
     * 过度自信偏差
     * 高估自己的判断能力和预测准确性
     */
    OVERCONFIDENCE("过度自信",
        "高估自己的判断能力，导致过度交易或承担过大风险"),

    /**
     * 确认偏差
     * 只关注支持自己观点的信息，忽略相反证据
     */
    CONFIRMATION_BIAS("确认偏差",
        "只看到支持自己观点的信息，忽略反面证据"),

    /**
     * 锚定效应
     * 过度依赖最初获得的信息
     */
    ANCHORING("锚定效应",
        "过度依赖初始价格或信息，难以调整判断"),

    /**
     * 损失厌恶
     * 对损失的痛苦感受强于同等收益的快乐
     */
    LOSS_AVERSION("损失厌恶",
        "对亏损的恐惧超过对盈利的渴望，导致过早止盈或死扛亏损"),

    /**
     * 处置效应
     * 倾向于过早卖出盈利头寸，持有亏损头寸
     */
    DISPOSITION_EFFECT("处置效应",
        "急于兑现盈利，却不愿意承认亏损"),

    /**
     * 后见之明偏差
     * 事后认为结果是可预测的
     */
    HINDSIGHT_BIAS("后见之明",
        "事后觉得结果显而易见，高估自己的预测能力"),

    /**
     * 赌徒谬误
     * 认为随机事件会受到之前结果的影响
     */
    GAMBLERS_FALLACY("赌徒谬误",
        "认为连续亏损后必然会盈利，或连续盈利后必然会亏损"),

    /**
     * 羊群效应
     * 盲目跟随大众的决策
     */
    HERDING("羊群效应",
        "盲目跟随市场大众，缺乏独立判断"),

    /**
     * 近因偏差
     * 过度重视最近的信息和经验
     */
    RECENCY_BIAS("近因偏差",
        "过度重视最近的交易结果，忽视长期表现"),

    /**
     * 自我归因偏差
     * 将成功归因于自己，将失败归因于外部因素
     */
    SELF_ATTRIBUTION("自我归因",
        "成功时认为是自己能力强，失败时怪罪市场或运气"),

    /**
     * 心理账户
     * 对不同来源的钱采用不同的态度
     */
    MENTAL_ACCOUNTING("心理账户",
        "对本金和盈利采用不同的风险态度"),

    /**
     * 框架效应
     * 决策受到信息呈现方式的影响
     */
    FRAMING_EFFECT("框架效应",
        "同样的信息以不同方式呈现会导致不同决策");

    private final String chineseName;
    private final String description;

    CognitiveBias(String chineseName, String description) {
        this.chineseName = chineseName;
        this.description = description;
    }

    public String getChineseName() {
        return chineseName;
    }

    public String getDescription() {
        return description;
    }
}
