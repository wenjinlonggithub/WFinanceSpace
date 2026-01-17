package com.wfinance.cases.legendary;

import com.wfinance.cases.CaseAnalysis;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * 杰西·利弗莫尔案例
 * "投机之王" - 20世纪最伟大的交易者之一
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class JesseLivermoreCase extends LegendaryTraderCase {

    public JesseLivermoreCase() {
        initializeCase();
    }

    private void initializeCase() {
        // 基本信息
        setCaseName("杰西·利弗莫尔：投机之王的传奇人生");
        setTraderName("Jesse Livermore");
        setStartDate(LocalDate.of(1892, 1, 1));
        setEndDate(LocalDate.of(1940, 11, 28));
        setMarket("US_STOCK");
        setDifficultyLevel(5);

        // 生平简介
        setBiography("杰西·利弗莫尔（1877-1940），美国历史上最传奇的股票投机者。" +
            "14岁开始交易，一生经历4次破产和4次东山再起，" +
            "在1929年大萧条中做空获利1亿美元（相当于今天的数十亿美元）。");

        // 核心交易哲学
        setCorePhilosophy("趋势跟踪 + 价格行为分析 + 严格的资金管理");

        // 著名交易
        setFamousTrade("1929年大萧条做空：在市场崩盘前建立大量空头头寸，" +
            "获利1亿美元，成为'最伟大的空头'");

        // 绩效指标
        setFinalReturn(10000.0); // 从5美元到1亿美元
        setMaxDrawdown(-100.0);  // 4次破产

        // 关键教训
        addKeyLesson("永远不要与趋势作对 - 趋势是你的朋友");
        addKeyLesson("及时止损，让利润奔跑 - 截断亏损，让利润奔跑");
        addKeyLesson("市场永远是对的 - 当市场证明你错了，立即认错");
        addKeyLesson("情绪是交易的最大敌人 - 保持冷静和客观");
        addKeyLesson("耐心等待最佳时机 - 不要频繁交易");
        addKeyLesson("资金管理至关重要 - 永远不要满仓");
        addKeyLesson("研究市场心理 - 理解群众的恐惧和贪婪");

        // 标签
        addTag("趋势跟踪");
        addTag("价格行为");
        addTag("投机");
        addTag("做空大师");
        addTag("资金管理");
    }

    @Override
    public CaseAnalysis analyze() {
        CaseAnalysis analysis = CaseAnalysis.builder()
            .summary("杰西·利弗莫尔是趋势跟踪和价格行为分析的先驱，" +
                "他的交易哲学影响了几代交易者。")
            .build();

        // 交易哲学
        analysis.getPhilosophies().add("趋势跟踪：顺势而为，不逆势操作");
        analysis.getPhilosophies().add("价格行为：只看价格，不听消息");
        analysis.getPhilosophies().add("关键点理论：在关键价格点位进场");

        // 战法
        analysis.getTactics().add("突破交易：等待价格突破关键阻力位");
        analysis.getTactics().add("金字塔加仓：盈利后逐步加仓");
        analysis.getTactics().add("快速止损：错了立即离场");

        // 成功因素
        analysis.addSuccessFactor("卓越的市场洞察力和时机把握");
        analysis.addSuccessFactor("严格的交易纪律和止损执行");
        analysis.addSuccessFactor("深刻理解市场心理和群众行为");
        analysis.addSuccessFactor("耐心等待最佳交易机会");
        analysis.addSuccessFactor("敢于在关键时刻重仓出击");

        // 失败因素
        analysis.addFailureFactor("过度自信导致的风险管理失控");
        analysis.addFailureFactor("个人生活问题影响交易决策");
        analysis.addFailureFactor("在非最佳时机进行交易");
        analysis.addFailureFactor("未能坚持自己的交易原则");

        // 心理因素
        analysis.getPsychologicalFactors().add("极强的心理承受能力");
        analysis.getPsychologicalFactors().add("能够在巨大压力下保持冷静");
        analysis.getPsychologicalFactors().add("从失败中快速恢复的能力");
        analysis.getPsychologicalFactors().add("孤独的交易者心态");

        // 风险管理分析
        analysis.setRiskManagementAnalysis(
            "利弗莫尔的风险管理是矛盾的：他强调止损和资金管理的重要性，" +
            "但在实际操作中有时会因过度自信而违反自己的原则。" +
            "他的4次破产都与风险管理失控有关。这告诉我们：" +
            "即使是最伟大的交易者，也必须始终遵守风险管理原则。"
        );

        // 关键决策
        analysis.addKeyDecision(CaseAnalysis.KeyDecision.builder()
            .timestamp("1907年")
            .decision("在1907年恐慌中做空")
            .rationale("看到市场出现系统性风险信号")
            .outcome("获利300万美元")
            .wasCorrect(true)
            .build());

        analysis.addKeyDecision(CaseAnalysis.KeyDecision.builder()
            .timestamp("1929年10月")
            .decision("在大崩盘前建立巨额空头")
            .rationale("识别出市场泡沫和崩盘信号")
            .outcome("获利1亿美元，成为传奇")
            .wasCorrect(true)
            .build());

        // 绩效指标
        analysis.addPerformanceMetric("生涯最高收益", 100000000.0);
        analysis.addPerformanceMetric("破产次数", 4.0);
        analysis.addPerformanceMetric("东山再起次数", 4.0);

        // 可复制性评分
        analysis.setReplicabilityScore(7);

        // 建议和启示
        analysis.getRecommendations().add("学习他的趋势跟踪方法，但要更严格地执行风险管理");
        analysis.getRecommendations().add("培养耐心，等待最佳交易机会");
        analysis.getRecommendations().add("研究价格行为，而不是依赖消息");
        analysis.getRecommendations().add("保持情绪稳定，不要让贪婪和恐惧控制决策");
        analysis.getRecommendations().add("从失败中学习，但不要重复同样的错误");

        return analysis;
    }
}
