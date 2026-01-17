package com.wfinance.cases.events;

import com.wfinance.cases.CaseAnalysis;
import com.wfinance.cases.TradingCase;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * 市场事件案例
 * 研究重大市场事件及其对交易的影响
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MarketEventCase extends TradingCase {

    /**
     * 事件类型（如：金融危机、市场崩盘、泡沫破裂等）
     */
    private EventType eventType;

    /**
     * 事件触发因素
     */
    private List<String> triggers = new ArrayList<>();

    /**
     * 市场影响范围
     */
    private List<String> affectedMarkets = new ArrayList<>();

    /**
     * 价格变动幅度（百分比）
     */
    private Double priceChange;

    /**
     * 波动率变化
     */
    private Double volatilityChange;

    /**
     * 恢复时间（天数）
     */
    private Integer recoveryDays;

    /**
     * 监管响应措施
     */
    private List<String> regulatoryResponses = new ArrayList<>();

    @Override
    public CaseAnalysis analyze() {
        CaseAnalysis analysis = CaseAnalysis.builder()
            .summary(String.format("分析 %s 事件及其市场影响", caseName))
            .build();

        // 添加事件触发因素作为失败因素（如果是负面事件）
        if (priceChange != null && priceChange < 0) {
            analysis.getFailureFactors().addAll(triggers);
        }

        // 添加绩效指标
        if (priceChange != null) {
            analysis.addPerformanceMetric("价格变动", priceChange);
        }
        if (volatilityChange != null) {
            analysis.addPerformanceMetric("波动率变化", volatilityChange);
        }
        if (recoveryDays != null) {
            analysis.addPerformanceMetric("恢复天数", recoveryDays.doubleValue());
        }

        // 添加风险管理分析
        analysis.setRiskManagementAnalysis(
            String.format("该事件导致市场 %.2f%% 的变动，波动率增加 %.2f%%，" +
                "恢复时间为 %d 天。投资者应注意类似事件的风险信号。",
                priceChange != null ? priceChange : 0.0,
                volatilityChange != null ? volatilityChange : 0.0,
                recoveryDays != null ? recoveryDays : 0)
        );

        // 添加关键教训
        analysis.getRecommendations().addAll(this.keyLessons);

        return analysis;
    }

    /**
     * 事件类型枚举
     */
    public enum EventType {
        FINANCIAL_CRISIS("金融危机"),
        MARKET_CRASH("市场崩盘"),
        BUBBLE_BURST("泡沫破裂"),
        BLACK_SWAN("黑天鹅事件"),
        REGULATORY_CHANGE("监管变化"),
        GEOPOLITICAL("地缘政治"),
        PANDEMIC("疫情"),
        TECH_DISRUPTION("技术颠覆"),
        OTHER("其他");

        private final String chineseName;

        EventType(String chineseName) {
            this.chineseName = chineseName;
        }

        public String getChineseName() {
            return chineseName;
        }
    }
}
