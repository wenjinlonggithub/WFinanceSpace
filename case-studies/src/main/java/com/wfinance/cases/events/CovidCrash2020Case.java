package com.wfinance.cases.events;

import com.wfinance.cases.CaseAnalysis;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * 2020年新冠疫情崩盘
 * 疫情引发的全球市场暴跌和V型反转
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CovidCrash2020Case extends MarketEventCase {

    public CovidCrash2020Case() {
        initializeCase();
    }

    private void initializeCase() {
        setCaseName("2020年新冠疫情市场崩盘");
        setDescription("新冠疫情引发的全球市场暴跌，随后V型反转");
        setStartDate(LocalDate.of(2020, 2, 19));
        setEndDate(LocalDate.of(2020, 3, 23));
        setMarket("GLOBAL");
        setEventType(EventType.PANDEMIC);
        setDifficultyLevel(5);

        getTriggers().add("新冠疫情全球爆发");
        getTriggers().add("经济活动突然停摆");
        getTriggers().add("不确定性和恐慌情绪");
        getTriggers().add("流动性危机");

        getAffectedMarkets().add("全球股市");
        getAffectedMarkets().add("原油市场");
        getAffectedMarkets().add("债券市场");
        getAffectedMarkets().add("加密货币");

        setPriceChange(-34.0); // 标普500最大跌幅
        setVolatilityChange(500.0);
        setRecoveryDays(150); // V型反转

        getRegulatoryResponses().add("无限量化宽松");
        getRegulatoryResponses().add("大规模财政刺激");
        getRegulatoryResponses().add("暂停熔断机制调整");

        addKeyLesson("黑天鹅事件的不可预测性");
        addKeyLesson("央行干预的威力");
        addKeyLesson("危机中的快速反转可能性");
        addKeyLesson("现金和流动性的重要性");
        addKeyLesson("科技股的抗风险能力");

        addTag("疫情");
        addTag("黑天鹅");
        addTag("V型反转");
        addTag("量化宽松");
    }

    @Override
    public CaseAnalysis analyze() {
        CaseAnalysis analysis = CaseAnalysis.builder()
            .summary("2020年疫情崩盘展示了黑天鹅事件的破坏力，" +
                "以及央行大规模干预下的快速反转。")
            .build();

        analysis.addFailureFactor("突发公共卫生事件");
        analysis.addFailureFactor("全球经济活动骤停");
        analysis.addFailureFactor("市场流动性枯竭");

        analysis.addSuccessFactor("央行快速而大规模的干预");
        analysis.addSuccessFactor("财政刺激政策");
        analysis.addSuccessFactor("科技股引领反弹");

        analysis.getPsychologicalFactors().add("极度恐慌和不确定性");
        analysis.getPsychologicalFactors().add("FOMO（害怕错过）驱动反弹");

        analysis.setRiskManagementAnalysis(
            "疫情崩盘的教训：1) 保持现金储备应对突发事件；" +
            "2) 不要在恐慌中抛售优质资产；" +
            "3) 理解央行政策的影响；4) V型反转可能很快到来。"
        );

        analysis.addKeyDecision(CaseAnalysis.KeyDecision.builder()
            .timestamp("2020年3月23日")
            .decision("美联储宣布无限量QE")
            .rationale("防止金融系统崩溃")
            .outcome("市场触底反弹，开启牛市")
            .wasCorrect(true)
            .build());

        analysis.addPerformanceMetric("标普500最大跌幅", -34.0);
        analysis.addPerformanceMetric("触底到新高天数", 150.0);
        analysis.addPerformanceMetric("VIX峰值", 82.0);

        analysis.setReplicabilityScore(2);

        analysis.getRecommendations().add("黑天鹅事件不可预测，但要做好准备");
        analysis.getRecommendations().add("危机中保持冷静，不要恐慌性抛售");
        analysis.getRecommendations().add("理解央行政策对市场的影响");
        analysis.getRecommendations().add("优质资产在危机后会快速恢复");

        return analysis;
    }
}
