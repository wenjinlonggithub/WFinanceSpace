package com.wfinance.cases.legendary;

import com.wfinance.cases.CaseAnalysis;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * 雷·达里奥案例
 * 桥水基金创始人 - 全天候策略之父
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RayDalioCase extends LegendaryTraderCase {

    public RayDalioCase() {
        initializeCase();
    }

    private void initializeCase() {
        setCaseName("雷·达里奥：原则与全天候策略");
        setTraderName("Ray Dalio");
        setStartDate(LocalDate.of(1975, 1, 1));
        setEndDate(LocalDate.now());
        setMarket("GLOBAL_MACRO");
        setDifficultyLevel(5);

        setBiography("雷·达里奥（1949-），桥水基金创始人，全球最大对冲基金掌门人。" +
            "创立'全天候策略'和'纯阿尔法策略'，著有《原则》一书。" +
            "强调系统化投资和风险平价。");

        setCorePhilosophy("全天候策略 + 风险平价 + 系统化投资");

        setFamousTrade("2008年金融危机：通过风险平价策略，" +
            "在市场暴跌时仍获得正收益，验证了全天候策略的有效性");

        setFinalReturn(3000.0);
        setMaxDrawdown(-12.0);

        addKeyLesson("分散投资是唯一的免费午餐");
        addKeyLesson("理解经济机器如何运转");
        addKeyLesson("建立系统化的决策流程");
        addKeyLesson("从失败中学习，建立原则");
        addKeyLesson("风险平价：平衡不同资产的风险贡献");
        addKeyLesson("极度透明和极度求真");

        addTag("全天候策略");
        addTag("风险平价");
        addTag("系统化投资");
        addTag("宏观对冲");
    }

    @Override
    public CaseAnalysis analyze() {
        CaseAnalysis analysis = CaseAnalysis.builder()
            .summary("达里奥创立的全天候策略和风险平价理念，" +
                "为投资者提供了在各种市场环境下都能稳定收益的方法。")
            .build();

        analysis.getPhilosophies().add("全天候策略：在任何经济环境下都能表现良好");
        analysis.getPhilosophies().add("风险平价：平衡不同资产的风险而非金额");
        analysis.getPhilosophies().add("系统化：将投资决策流程化、规则化");

        analysis.getTactics().add("资产配置：股票、债券、商品、黄金等多元化");
        analysis.getTactics().add("风险平衡：确保各资产风险贡献相等");
        analysis.getTactics().add("动态调整：根据经济周期调整配置");

        analysis.addSuccessFactor("深刻理解经济周期和资产相关性");
        analysis.addSuccessFactor("严格的系统化投资流程");
        analysis.addSuccessFactor("优秀的风险管理框架");
        analysis.addSuccessFactor("持续学习和改进的文化");

        analysis.getPsychologicalFactors().add("极度理性和系统化思维");
        analysis.getPsychologicalFactors().add("从失败中学习的能力");
        analysis.getPsychologicalFactors().add("开放心态和求真精神");

        analysis.setRiskManagementAnalysis(
            "达里奥的风险管理核心是'风险平价'：" +
            "不是简单地分散投资金额，而是平衡各资产的风险贡献。" +
            "通过这种方式，组合在各种市场环境下都能保持稳定。"
        );

        analysis.addKeyDecision(CaseAnalysis.KeyDecision.builder()
            .timestamp("1996年")
            .decision("创立全天候策略")
            .rationale("寻找在任何经济环境下都能表现的投资组合")
            .outcome("成为最成功的资产配置策略之一")
            .wasCorrect(true)
            .build());

        analysis.addPerformanceMetric("桥水基金管理规模", 150000000000.0);
        analysis.addPerformanceMetric("全天候策略年化收益", 7.0);
        analysis.addPerformanceMetric("最大回撤", -12.0);

        analysis.setReplicabilityScore(9); // 策略清晰，可复制性强

        analysis.getRecommendations().add("学习全天候策略，构建多元化投资组合");
        analysis.getRecommendations().add("理解不同资产在经济周期中的表现");
        analysis.getRecommendations().add("建立系统化的投资流程");
        analysis.getRecommendations().add("从失败中总结原则");

        return analysis;
    }
}
