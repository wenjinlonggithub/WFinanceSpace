package com.wfinance.cases.events;

import com.wfinance.cases.CaseAnalysis;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * 2008年金融危机
 * 次贷危机引发的全球金融海啸
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FinancialCrisis2008Case extends MarketEventCase {

    public FinancialCrisis2008Case() {
        initializeCase();
    }

    private void initializeCase() {
        setCaseName("2008年全球金融危机");
        setDescription("次贷危机引发的全球金融海啸，雷曼兄弟破产");
        setStartDate(LocalDate.of(2007, 8, 1));
        setEndDate(LocalDate.of(2009, 3, 9));
        setMarket("GLOBAL");
        setEventType(EventType.FINANCIAL_CRISIS);
        setDifficultyLevel(5);

        getTriggers().add("次级抵押贷款泡沫破裂");
        getTriggers().add("金融衍生品过度杠杆");
        getTriggers().add("雷曼兄弟破产引发连锁反应");
        getTriggers().add("信用市场冻结");
        getTriggers().add("全球流动性危机");

        getAffectedMarkets().add("全球股市");
        getAffectedMarkets().add("房地产市场");
        getAffectedMarkets().add("信用市场");
        getAffectedMarkets().add("商品市场");

        setPriceChange(-57.0); // 标普500从高点跌幅
        setVolatilityChange(400.0);
        setRecoveryDays(1800);

        getRegulatoryResponses().add("大规模救市计划（TARP）");
        getRegulatoryResponses().add("量化宽松政策");
        getRegulatoryResponses().add("多德-弗兰克法案");
        getRegulatoryResponses().add("巴塞尔III协议");

        addKeyLesson("过度杠杆和金融创新的风险");
        addKeyLesson("系统性风险的传染性");
        addKeyLesson("流动性是金融系统的生命线");
        addKeyLesson("危机中现金为王");
        addKeyLesson("政府干预的必要性和局限性");

        addTag("金融危机");
        addTag("次贷危机");
        addTag("系统性风险");
        addTag("流动性危机");
    }

    @Override
    public CaseAnalysis analyze() {
        CaseAnalysis analysis = CaseAnalysis.builder()
            .summary("2008年金融危机是大萧条以来最严重的金融危机，" +
                "揭示了金融系统的脆弱性和系统性风险的破坏力。")
            .build();

        analysis.addFailureFactor("房地产泡沫和次级贷款的过度扩张");
        analysis.addFailureFactor("金融机构过度杠杆和风险管理失败");
        analysis.addFailureFactor("复杂金融衍生品的不透明性");
        analysis.addFailureFactor("监管缺失和道德风险");

        analysis.getPsychologicalFactors().add("极度恐慌和信心崩溃");
        analysis.getPsychologicalFactors().add("对金融系统的不信任");
        analysis.getPsychologicalFactors().add("羊群效应和恐慌性抛售");

        analysis.setRiskManagementAnalysis(
            "2008年危机暴露了金融系统风险管理的系统性失败：" +
            "过度依赖模型、忽视尾部风险、杠杆失控。" +
            "教训：1) 保持充足的流动性储备；" +
            "2) 避免过度杠杆；3) 分散投资；4) 理解系统性风险。"
        );

        analysis.addKeyDecision(CaseAnalysis.KeyDecision.builder()
            .timestamp("2008年9月15日")
            .decision("让雷曼兄弟破产")
            .rationale("避免道德风险")
            .outcome("引发全球金融恐慌")
            .wasCorrect(false)
            .build());

        analysis.addPerformanceMetric("标普500最大跌幅", -57.0);
        analysis.addPerformanceMetric("全球财富损失（万亿美元）", 50.0);
        analysis.addPerformanceMetric("失业率峰值", 10.0);

        analysis.setReplicabilityScore(4);

        analysis.getRecommendations().add("警惕资产泡沫和过度杠杆");
        analysis.getRecommendations().add("保持充足的现金储备");
        analysis.getRecommendations().add("危机是长期投资者的机会");
        analysis.getRecommendations().add("理解系统性风险的传染性");

        return analysis;
    }
}
