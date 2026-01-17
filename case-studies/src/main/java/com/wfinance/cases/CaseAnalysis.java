package com.wfinance.cases;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 案例分析结果
 * 包含对交易案例的详细分析
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CaseAnalysis {

    /**
     * 分析摘要
     */
    private String summary;

    /**
     * 使用的交易哲学
     */
    @Builder.Default
    private List<String> philosophies = new ArrayList<>();

    /**
     * 使用的交易战法
     */
    @Builder.Default
    private List<String> tactics = new ArrayList<>();

    /**
     * 成功因素
     */
    @Builder.Default
    private List<String> successFactors = new ArrayList<>();

    /**
     * 失败因素（如果适用）
     */
    @Builder.Default
    private List<String> failureFactors = new ArrayList<>();

    /**
     * 心理因素分析
     */
    @Builder.Default
    private List<String> psychologicalFactors = new ArrayList<>();

    /**
     * 风险管理分析
     */
    private String riskManagementAnalysis;

    /**
     * 关键决策点
     */
    @Builder.Default
    private List<KeyDecision> keyDecisions = new ArrayList<>();

    /**
     * 绩效指标
     */
    @Builder.Default
    private Map<String, Double> performanceMetrics = new HashMap<>();

    /**
     * 可复制性评分（1-10）
     */
    private Integer replicabilityScore;

    /**
     * 建议和启示
     */
    @Builder.Default
    private List<String> recommendations = new ArrayList<>();

    /**
     * 添加成功因素
     */
    public void addSuccessFactor(String factor) {
        this.successFactors.add(factor);
    }

    /**
     * 添加失败因素
     */
    public void addFailureFactor(String factor) {
        this.failureFactors.add(factor);
    }

    /**
     * 添加关键决策
     */
    public void addKeyDecision(KeyDecision decision) {
        this.keyDecisions.add(decision);
    }

    /**
     * 添加绩效指标
     */
    public void addPerformanceMetric(String name, Double value) {
        this.performanceMetrics.put(name, value);
    }

    /**
     * 关键决策点
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class KeyDecision {
        private String timestamp;
        private String decision;
        private String rationale;
        private String outcome;
        private boolean wasCorrect;
    }
}
