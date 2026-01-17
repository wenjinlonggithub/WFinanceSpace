package com.wfinance.cases.legendary;

import com.wfinance.cases.CaseAnalysis;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * 保罗·都铎·琼斯案例
 * 预测1987年黑色星期一的传奇交易者
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PaulTudorJonesCase extends LegendaryTraderCase {

    public PaulTudorJonesCase() {
        initializeCase();
    }

    private void initializeCase() {
        setCaseName("保罗·都铎·琼斯：预测黑色星期一");
        setTraderName("Paul Tudor Jones");
        setStartDate(LocalDate.of(1980, 1, 1));
        setEndDate(LocalDate.now());
        setMarket("US_STOCK");
        setDifficultyLevel(5);

        setBiography("保罗·都铎·琼斯（1954-），都铎投资公司创始人。" +
            "1987年成功预测并做空股市崩盘，当月获利62%，一战成名。" +
            "擅长技术分析和宏观交易，强调风险控制。");

        setCorePhilosophy("技术分析 + 宏观判断 + 严格风控");

        setFamousTrade("1987年10月黑色星期一：提前做空股市，" +
            "在市场单日暴跌22.6%时获利62%，资产翻三倍");

        setFinalReturn(5000.0);
        setMaxDrawdown(-18.0);

        addKeyLesson("防守比进攻更重要 - 先保护资本");
        addKeyLesson("每天都要问自己：如果我错了怎么办？");
        addKeyLesson("技术分析揭示市场情绪和趋势");
        addKeyLesson("风险回报比至少要1:5才值得交易");
        addKeyLesson("永远不要让一笔交易毁掉你");
        addKeyLesson("市场会告诉你何时错了，要倾听");

        addTag("技术分析");
        addTag("宏观交易");
        addTag("风险控制");
        addTag("做空大师");
    }

    @Override
    public CaseAnalysis analyze() {
        CaseAnalysis analysis = CaseAnalysis.builder()
            .summary("保罗·都铎·琼斯是技术分析和风险管理的典范，" +
                "他在1987年的成功预测展示了技术分析的威力。")
            .build();

        analysis.getPhilosophies().add("技术分析：通过图表识别市场模式");
        analysis.getPhilosophies().add("防守第一：保护资本是首要任务");
        analysis.getPhilosophies().add("风险回报：只做高赔率交易");

        analysis.getTactics().add("艾略特波浪理论：识别市场周期");
        analysis.getTactics().add("严格止损：每笔交易设定明确止损");
        analysis.getTactics().add("分散投资：不把鸡蛋放在一个篮子里");

        analysis.addSuccessFactor("卓越的技术分析能力");
        analysis.addSuccessFactor("严格的风险管理纪律");
        analysis.addSuccessFactor("对市场情绪的敏锐感知");
        analysis.addSuccessFactor("快速决策和执行能力");

        analysis.getPsychologicalFactors().add("极度重视风险控制");
        analysis.getPsychologicalFactors().add("保持谦逊，随时准备认错");
        analysis.getPsychologicalFactors().add("不追求完美，追求生存");

        analysis.setRiskManagementAnalysis(
            "琼斯的风险管理哲学是'防守赢得冠军'。" +
            "他每天都问自己：如果我错了怎么办？" +
            "他要求风险回报比至少1:5，并严格执行止损。" +
            "这种保守的风险管理使他能够长期稳定盈利。"
        );

        analysis.addKeyDecision(CaseAnalysis.KeyDecision.builder()
            .timestamp("1987年10月")
            .decision("大量做空股指期货")
            .rationale("技术图表显示1929年崩盘前的相似模式")
            .outcome("市场暴跌22.6%，当月获利62%")
            .wasCorrect(true)
            .build());

        analysis.addPerformanceMetric("1987年10月收益", 62.0);
        analysis.addPerformanceMetric("基金年化收益", 20.0);
        analysis.addPerformanceMetric("最大回撤", -18.0);

        analysis.setReplicabilityScore(6);

        analysis.getRecommendations().add("学习技术分析，识别市场模式");
        analysis.getRecommendations().add("建立严格的风险管理体系");
        analysis.getRecommendations().add("只做高赔率交易");
        analysis.getRecommendations().add("保持谦逊，随时准备认错");

        return analysis;
    }
}
