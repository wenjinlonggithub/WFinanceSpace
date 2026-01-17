package com.wfinance.legal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 合规检查结果
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComplianceResult {

    /**
     * 是否合规
     */
    private boolean compliant;

    /**
     * 违规的法规列表
     */
    @Builder.Default
    private List<Regulation> violatedRegulations = new ArrayList<>();

    /**
     * 违规详情
     */
    @Builder.Default
    private List<String> violations = new ArrayList<>();

    /**
     * 警告信息
     */
    @Builder.Default
    private List<String> warnings = new ArrayList<>();

    /**
     * 建议措施
     */
    @Builder.Default
    private List<String> recommendations = new ArrayList<>();

    /**
     * 添加违规信息
     */
    public void addViolation(Regulation regulation, String violation) {
        this.compliant = false;
        this.violatedRegulations.add(regulation);
        this.violations.add(violation);
    }

    /**
     * 添加警告信息
     */
    public void addWarning(String warning) {
        this.warnings.add(warning);
    }

    /**
     * 添加建议
     */
    public void addRecommendation(String recommendation) {
        this.recommendations.add(recommendation);
    }

    /**
     * 获取合规性摘要
     */
    public String getSummary() {
        if (compliant) {
            return "合规检查通过";
        }
        return String.format("发现 %d 项违规，%d 项警告",
            violations.size(), warnings.size());
    }
}
