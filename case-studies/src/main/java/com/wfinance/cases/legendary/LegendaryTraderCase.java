package com.wfinance.cases.legendary;

import com.wfinance.cases.CaseAnalysis;
import com.wfinance.cases.TradingCase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 传奇交易者案例
 * 研究历史上著名交易者的交易策略和哲学
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LegendaryTraderCase extends TradingCase {

    /**
     * 交易者姓名
     */
    private String traderName;

    /**
     * 交易者生平简介
     */
    private String biography;

    /**
     * 核心交易哲学
     */
    private String corePhilosophy;

    /**
     * 著名交易案例
     */
    private String famousTrade;

    /**
     * 最终收益率
     */
    private Double finalReturn;

    /**
     * 最大回撤
     */
    private Double maxDrawdown;

    @Override
    public CaseAnalysis analyze() {
        CaseAnalysis analysis = CaseAnalysis.builder()
            .summary(String.format("分析 %s 的交易生涯和策略", traderName))
            .build();

        // 添加交易哲学
        if (corePhilosophy != null) {
            analysis.getPhilosophies().add(corePhilosophy);
        }

        // 添加绩效指标
        if (finalReturn != null) {
            analysis.addPerformanceMetric("总收益率", finalReturn);
        }
        if (maxDrawdown != null) {
            analysis.addPerformanceMetric("最大回撤", maxDrawdown);
        }

        // 添加关键教训作为建议
        analysis.getRecommendations().addAll(this.keyLessons);

        return analysis;
    }
}
