package com.wfinance.cases.examples;

import com.wfinance.cases.CaseAnalysis;
import com.wfinance.cases.legendary.LegendaryTraderCase;

import java.time.LocalDate;

/**
 * 案例研究示例
 * 演示如何创建和分析传奇交易者案例
 */
public class CaseStudyExample {

    public static void main(String[] args) {
        // 创建杰西·利弗莫尔案例
        LegendaryTraderCase jesseCase = createJesseLivermoreCase();

        // 分析案例
        CaseAnalysis analysis = jesseCase.analyze();

        // 输出分析结果
        System.out.println("=== 案例分析：" + jesseCase.getTraderName() + " ===");
        System.out.println("\n核心哲学:");
        for (String philosophy : analysis.getPhilosophies()) {
            System.out.println("  - " + philosophy);
        }

        System.out.println("\n关键教训:");
        for (String lesson : analysis.getRecommendations()) {
            System.out.println("  - " + lesson);
        }

        System.out.println("\n绩效指标:");
        analysis.getPerformanceMetrics().forEach((key, value) ->
            System.out.println("  " + key + ": " + value + "%")
        );
    }

    private static LegendaryTraderCase createJesseLivermoreCase() {
        LegendaryTraderCase jesseCase = new LegendaryTraderCase();

        jesseCase.setCaseName("杰西·利弗莫尔的交易生涯");
        jesseCase.setTraderName("Jesse Livermore");
        jesseCase.setStartDate(LocalDate.of(1892, 1, 1));
        jesseCase.setEndDate(LocalDate.of(1940, 11, 28));
        jesseCase.setMarket("US_STOCK");

        jesseCase.setCorePhilosophy("趋势跟踪，顺势而为");
        jesseCase.setBiography("20世纪最伟大的投机者之一");
        jesseCase.setFamousTrade("1929年大萧条做空获利1亿美元");

        jesseCase.addKeyLesson("永远不要与趋势作对");
        jesseCase.addKeyLesson("及时止损，让利润奔跑");
        jesseCase.addKeyLesson("市场永远是对的，错的是自己");
        jesseCase.addKeyLesson("情绪是交易的最大敌人");

        jesseCase.setFinalReturn(10000.0);
        jesseCase.setMaxDrawdown(-100.0);

        return jesseCase;
    }
}
