package com.wfinance.cases.legendary;

import com.wfinance.cases.CaseAnalysis;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * 沃伦·巴菲特案例
 * "奥马哈的先知" - 价值投资之神
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WarrenBuffettCase extends LegendaryTraderCase {

    public WarrenBuffettCase() {
        initializeCase();
    }

    private void initializeCase() {
        setCaseName("沃伦·巴菲特：价值投资的力量");
        setTraderName("Warren Buffett");
        setStartDate(LocalDate.of(1956, 1, 1));
        setEndDate(LocalDate.now());
        setMarket("US_STOCK");
        setDifficultyLevel(4);

        setBiography("沃伦·巴菲特（1930-），伯克希尔·哈撒韦公司CEO，" +
            "被誉为'股神'和'奥马哈的先知'。师从本杰明·格雷厄姆，" +
            "将价值投资发扬光大。从100美元起步，建立千亿美元财富帝国。");

        setCorePhilosophy("价值投资 + 长期持有 + 护城河理论");

        setFamousTrade("1988年买入可口可乐：投资10亿美元，" +
            "持有至今增值超过20倍，成为经典价值投资案例");

        setFinalReturn(2000000.0); // 从100美元到900亿美元
        setMaxDrawdown(-50.0);     // 2008年金融危机

        addKeyLesson("价格是你付出的，价值是你得到的");
        addKeyLesson("在别人恐惧时贪婪，在别人贪婪时恐惧");
        addKeyLesson("寻找具有护城河的优秀企业");
        addKeyLesson("长期持有，让复利发挥魔力");
        addKeyLesson("投资你理解的企业");
        addKeyLesson("管理层的诚信比能力更重要");
        addKeyLesson("不要预测市场，专注于企业价值");

        addTag("价值投资");
        addTag("长期持有");
        addTag("护城河");
        addTag("复利");
    }

    @Override
    public CaseAnalysis analyze() {
        CaseAnalysis analysis = CaseAnalysis.builder()
            .summary("巴菲特证明了价值投资和长期持有的威力，" +
                "他的成功源于对企业价值的深刻理解和超凡的耐心。")
            .build();

        // 交易哲学
        analysis.getPhilosophies().add("价值投资：以低于内在价值的价格买入优质企业");
        analysis.getPhilosophies().add("长期主义：持有优秀企业数十年");
        analysis.getPhilosophies().add("护城河理论：寻找具有竞争优势的企业");
        analysis.getPhilosophies().add("能力圈：只投资自己理解的领域");

        // 战法
        analysis.getTactics().add("集中投资：重仓持有少数优质股票");
        analysis.getTactics().add("逆向投资：在市场恐慌时买入");
        analysis.getTactics().add("买入持有：长期持有优质资产");

        // 成功因素
        analysis.addSuccessFactor("深刻的企业分析能力");
        analysis.addSuccessFactor("超凡的耐心和定力");
        analysis.addSuccessFactor("简单而有效的投资原则");
        analysis.addSuccessFactor("优秀的资本配置能力");
        analysis.addSuccessFactor("复利的长期积累");

        // 心理因素
        analysis.getPsychologicalFactors().add("极强的情绪控制能力");
        analysis.getPsychologicalFactors().add("不受市场波动影响的定力");
        analysis.getPsychologicalFactors().add("长期思维和耐心");
        analysis.getPsychologicalFactors().add("独立思考，不随波逐流");

        // 风险管理
        analysis.setRiskManagementAnalysis(
            "巴菲特的风险管理哲学是：'第一条规则是不要亏损，第二条规则是永远记住第一条'。" +
            "他通过深入研究企业、买入安全边际、长期持有来控制风险。" +
            "他不使用杠杆，不做空，不投机，专注于企业的内在价值。"
        );

        // 关键决策
        analysis.addKeyDecision(CaseAnalysis.KeyDecision.builder()
            .timestamp("1988年")
            .decision("重仓买入可口可乐")
            .rationale("强大的品牌护城河，稳定的现金流，合理的估值")
            .outcome("持有至今增值20倍以上")
            .wasCorrect(true)
            .build());

        analysis.addKeyDecision(CaseAnalysis.KeyDecision.builder()
            .timestamp("2008年金融危机")
            .decision("在市场恐慌时买入高盛、通用电气等")
            .rationale("优质企业被错杀，提供巨大安全边际")
            .outcome("获得丰厚回报")
            .wasCorrect(true)
            .build());

        // 绩效指标
        analysis.addPerformanceMetric("伯克希尔年化收益", 20.0);
        analysis.addPerformanceMetric("跑赢标普500指数", 10.0);
        analysis.addPerformanceMetric("个人财富", 90000000000.0);

        analysis.setReplicabilityScore(8); // 原则简单但需要极强的耐心

        // 建议
        analysis.getRecommendations().add("学习企业分析，理解商业模式和竞争优势");
        analysis.getRecommendations().add("培养长期思维，不被短期波动影响");
        analysis.getRecommendations().add("寻找具有护城河的优质企业");
        analysis.getRecommendations().add("在市场恐慌时保持冷静，寻找机会");
        analysis.getRecommendations().add("让复利发挥作用，耐心等待");

        return analysis;
    }
}
