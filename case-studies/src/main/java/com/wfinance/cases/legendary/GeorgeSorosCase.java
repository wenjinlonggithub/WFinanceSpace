package com.wfinance.cases.legendary;

import com.wfinance.cases.CaseAnalysis;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * 乔治·索罗斯案例
 * "打垮英格兰银行的人" - 宏观对冲基金之父
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GeorgeSorosCase extends LegendaryTraderCase {

    public GeorgeSorosCase() {
        initializeCase();
    }

    private void initializeCase() {
        setCaseName("乔治·索罗斯：狙击英镑的传奇");
        setTraderName("George Soros");
        setStartDate(LocalDate.of(1969, 1, 1));
        setEndDate(LocalDate.now());
        setMarket("GLOBAL_MACRO");
        setDifficultyLevel(5);

        setBiography("乔治·索罗斯（1930-），量子基金创始人，全球宏观对冲基金之父。" +
            "1992年狙击英镑一战成名，单笔交易获利10亿美元，被称为'打垮英格兰银行的人'。" +
            "其投资哲学深受卡尔·波普尔的'反身性理论'影响。");

        setCorePhilosophy("反身性理论 + 宏观经济分析 + 逆向投资");

        setFamousTrade("1992年狙击英镑：做空100亿美元英镑，" +
            "迫使英国退出欧洲汇率机制，单日获利10亿美元");

        setFinalReturn(4000.0); // 量子基金年化30%+
        setMaxDrawdown(-32.0);  // 1987年黑色星期一

        addKeyLesson("反身性：市场参与者的偏见会影响市场，市场又反过来影响参与者");
        addKeyLesson("寻找市场的不平衡和不可持续性");
        addKeyLesson("当发现重大机会时，要敢于重仓出击");
        addKeyLesson("承认错误并快速调整 - '生存第一，赚钱第二'");
        addKeyLesson("理解政治和经济的相互作用");
        addKeyLesson("在别人恐惧时贪婪，在别人贪婪时恐惧");

        addTag("宏观对冲");
        addTag("反身性理论");
        addTag("货币投机");
        addTag("逆向投资");
    }

    @Override
    public CaseAnalysis analyze() {
        CaseAnalysis analysis = CaseAnalysis.builder()
            .summary("索罗斯是全球宏观交易的大师，他的反身性理论揭示了" +
                "市场参与者认知与市场现实之间的互动关系。")
            .build();

        // 交易哲学
        analysis.getPhilosophies().add("反身性理论：认知影响现实，现实又影响认知");
        analysis.getPhilosophies().add("寻找市场失衡：发现不可持续的趋势");
        analysis.getPhilosophies().add("宏观视角：从政治经济大局出发");

        // 战法
        analysis.getTactics().add("货币投机：利用汇率机制的脆弱性");
        analysis.getTactics().add("重仓出击：在高确定性机会上集中火力");
        analysis.getTactics().add("多空结合：同时做多和做空不同资产");

        // 成功因素
        analysis.addSuccessFactor("深刻的哲学思考和理论框架");
        analysis.addSuccessFactor("对宏观经济和政治的敏锐洞察");
        analysis.addSuccessFactor("在关键时刻的果断决策");
        analysis.addSuccessFactor("优秀的风险管理和止损纪律");
        analysis.addSuccessFactor("全球化视野和信息网络");

        // 心理因素
        analysis.getPsychologicalFactors().add("强大的自信和决断力");
        analysis.getPsychologicalFactors().add("能够承受巨大的心理压力");
        analysis.getPsychologicalFactors().add("保持开放心态，随时准备改变观点");
        analysis.getPsychologicalFactors().add("从哲学层面理解市场");

        // 风险管理
        analysis.setRiskManagementAnalysis(
            "索罗斯的风险管理哲学是'生存第一，赚钱第二'。" +
            "他强调要快速认错和止损，但在高确定性机会上敢于重仓。" +
            "这种看似矛盾的策略实际上是：小心试探，确认后重仓，错了立即撤退。"
        );

        // 关键决策
        analysis.addKeyDecision(CaseAnalysis.KeyDecision.builder()
            .timestamp("1992年9月16日")
            .decision("做空100亿美元英镑")
            .rationale("英镑汇率高估，英国经济无法支撑，政治压力巨大")
            .outcome("英国被迫退出欧洲汇率机制，获利10亿美元")
            .wasCorrect(true)
            .build());

        analysis.addKeyDecision(CaseAnalysis.KeyDecision.builder()
            .timestamp("1997年")
            .decision("做空泰铢，引发亚洲金融危机")
            .rationale("泰国经济泡沫，外汇储备不足")
            .outcome("获利巨大，但引发争议")
            .wasCorrect(true)
            .build());

        // 绩效指标
        analysis.addPerformanceMetric("量子基金年化收益", 30.0);
        analysis.addPerformanceMetric("1992年英镑交易收益", 1000000000.0);
        analysis.addPerformanceMetric("管理资产峰值", 22000000000.0);

        analysis.setReplicabilityScore(4); // 需要极高的宏观分析能力

        // 建议
        analysis.getRecommendations().add("学习反身性理论，理解市场的自我强化机制");
        analysis.getRecommendations().add("培养宏观视野，关注政治经济大局");
        analysis.getRecommendations().add("寻找市场的不平衡和不可持续性");
        analysis.getRecommendations().add("在高确定性机会上敢于重仓");
        analysis.getRecommendations().add("保持谦逊，随时准备承认错误");

        return analysis;
    }
}
