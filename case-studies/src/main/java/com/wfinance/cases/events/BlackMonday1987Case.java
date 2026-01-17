package com.wfinance.cases.events;

import com.wfinance.cases.CaseAnalysis;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * 1987年黑色星期一
 * 历史上最大的单日股市崩盘
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BlackMonday1987Case extends MarketEventCase {

    public BlackMonday1987Case() {
        initializeCase();
    }

    private void initializeCase() {
        setCaseName("1987年黑色星期一");
        setDescription("美国股市单日暴跌22.6%，全球股市连锁崩盘");
        setStartDate(LocalDate.of(1987, 10, 19));
        setEndDate(LocalDate.of(1987, 10, 19));
        setMarket("US_STOCK");
        setEventType(EventType.MARKET_CRASH);
        setDifficultyLevel(5);

        // 触发因素
        getTriggers().add("程序化交易和投资组合保险策略");
        getTriggers().add("美元贬值和贸易赤字担忧");
        getTriggers().add("利率上升压力");
        getTriggers().add("市场过度投机和高估值");
        getTriggers().add("流动性枯竭和恐慌性抛售");

        // 影响范围
        getAffectedMarkets().add("美国股市");
        getAffectedMarkets().add("全球股市");
        getAffectedMarkets().add("期货市场");

        // 市场数据
        setPriceChange(-22.6);
        setVolatilityChange(300.0);
        setRecoveryDays(600);

        // 监管响应
        getRegulatoryResponses().add("引入熔断机制");
        getRegulatoryResponses().add("限制程序化交易");
        getRegulatoryResponses().add("加强市场监管");

        // 关键教训
        addKeyLesson("程序化交易可能放大市场波动");
        addKeyLesson("流动性在危机时刻至关重要");
        addKeyLesson("市场恐慌会自我强化");
        addKeyLesson("分散投资无法完全避免系统性风险");
        addKeyLesson("需要建立市场稳定机制（熔断）");

        addTag("市场崩盘");
        addTag("程序化交易");
        addTag("系统性风险");
        addTag("恐慌性抛售");
    }

    @Override
    public CaseAnalysis analyze() {
        CaseAnalysis analysis = CaseAnalysis.builder()
            .summary("1987年黑色星期一是历史上最大的单日股市崩盘，" +
                "揭示了程序化交易和市场结构性问题的风险。")
            .build();

        // 失败因素（导致崩盘的原因）
        analysis.addFailureFactor("程序化交易和投资组合保险策略的连锁反应");
        analysis.addFailureFactor("市场流动性突然枯竭");
        analysis.addFailureFactor("恐慌情绪的自我强化");
        analysis.addFailureFactor("缺乏市场稳定机制");

        // 心理因素
        analysis.getPsychologicalFactors().add("群体恐慌和羊群效应");
        analysis.getPsychologicalFactors().add("损失厌恶导致的恐慌性抛售");
        analysis.getPsychologicalFactors().add("市场信心的瞬间崩溃");

        // 风险管理分析
        analysis.setRiskManagementAnalysis(
            "黑色星期一暴露了当时风险管理的严重缺陷：" +
            "1) 投资组合保险策略在极端情况下失效；" +
            "2) 程序化交易放大了市场波动；" +
            "3) 缺乏熔断机制导致崩盘失控。" +
            "这次事件促使监管机构引入熔断机制和加强风险管理。"
        );

        // 关键决策点
        analysis.addKeyDecision(CaseAnalysis.KeyDecision.builder()
            .timestamp("1987年10月19日上午")
            .decision("大量程序化卖单涌入市场")
            .rationale("投资组合保险策略触发自动止损")
            .outcome("引发连锁反应，市场暴跌22.6%")
            .wasCorrect(false)
            .build());

        analysis.addKeyDecision(CaseAnalysis.KeyDecision.builder()
            .timestamp("1987年10月20日")
            .decision("美联储承诺提供流动性支持")
            .rationale("防止金融系统崩溃")
            .outcome("市场逐步稳定")
            .wasCorrect(true)
            .build());

        // 绩效指标
        analysis.addPerformanceMetric("道琼斯单日跌幅", -22.6);
        analysis.addPerformanceMetric("全球股市损失（万亿美元）", 1.7);
        analysis.addPerformanceMetric("恢复到崩盘前水平天数", 600.0);

        // 可复制性（预测类似事件的难度）
        analysis.setReplicabilityScore(3);

        // 建议和启示
        analysis.getRecommendations().add("警惕程序化交易和算法交易的系统性风险");
        analysis.getRecommendations().add("在市场极端波动时保持冷静，不要恐慌性抛售");
        analysis.getRecommendations().add("理解熔断机制的作用和局限性");
        analysis.getRecommendations().add("分散投资无法完全避免系统性风险");
        analysis.getRecommendations().add("危机往往是长期投资的机会");

        return analysis;
    }
}
