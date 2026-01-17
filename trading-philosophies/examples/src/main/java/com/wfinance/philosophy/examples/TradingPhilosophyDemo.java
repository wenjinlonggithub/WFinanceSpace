package com.wfinance.philosophy.examples;

import com.wfinance.backtest.config.BacktestConfig;
import com.wfinance.backtest.engine.BacktestEngine;
import com.wfinance.backtest.result.BacktestResult;
import com.wfinance.data.provider.DataProvider;
import com.wfinance.data.provider.mock.MockDataProvider;
import com.wfinance.philosophy.meanreversion.MeanReversionStrategy;
import com.wfinance.philosophy.meanreversion.BollingerBandsMeanReversionStrategy;
import com.wfinance.philosophy.position.RiskManagementStrategy;
import com.wfinance.philosophy.scalping.ScalpingStrategy;
import com.wfinance.philosophy.swing.SwingTradingStrategy;
import com.wfinance.philosophy.trendfollowing.TrendFollowingStrategy;
import com.wfinance.philosophy.trendfollowing.ElliottWaveStrategy;
import com.wfinance.strategy.engine.Strategy;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 交易哲学案例演示
 *
 * 本案例展示了五种主要的交易哲学及其应用：
 * 1. 趋势跟踪哲学 - 顺势而为
 * 2. 均值回归哲学 - 反转交易
 * 3. 风险管理哲学 - 资金保护
 * 4. 日内交易哲学 - 快进快出
 * 5. 摆动交易哲学 - 中期波动捕获
 * 6. 艾略特波浪哲学 - 结构化市场分析
 */
public class TradingPhilosophyDemo {
    public static void main(String[] args) {
        System.out.println("=== 交易哲学案例演示 ===\n");

        // 创建数据提供者
        DataProvider dataProvider = new MockDataProvider();
        System.out.println("✓ 数据提供者已创建");

        // 配置回测参数
        BacktestConfig config = BacktestConfig.builder()
                .initialCapital(new BigDecimal("100000"))  // 初始资金10万
                .startTime(LocalDateTime.now().minusMonths(6))  // 回测6个月
                .endTime(LocalDateTime.now())
                .commissionRate(new BigDecimal("0.001"))  // 手续费0.1%
                .slippage(BigDecimal.ZERO)
                .positionSizeRatio(new BigDecimal("0.95"))  // 每次使用95%资金
                .allowShort(false)
                .maxPositions(1)
                .build();
        System.out.println("✓ 回测配置已完成\n");

        String symbol = "MOCK001";

        // 1. 趋势跟踪哲学演示
        demonstrateTrendFollowingPhilosophy(dataProvider, config, symbol);

        // 2. 均值回归哲学演示
        demonstrateMeanReversionPhilosophy(dataProvider, config, symbol);

        // 3. 风险管理哲学演示
        demonstrateRiskManagementPhilosophy(dataProvider, config, symbol);

        // 4. 日内交易哲学演示
        demonstrateScalpingPhilosophy(dataProvider, config, symbol);

        // 5. 摆动交易哲学演示
        demonstrateSwingTradingPhilosophy(dataProvider, config, symbol);

        // 6. 艾略特波浪哲学演示
        demonstrateElliottWavePhilosophy(dataProvider, config, symbol);

        System.out.println("\n=== 交易哲学案例演示完成 ===");
        System.out.println("\n总结各种交易哲学的核心理念:");
        System.out.println("1. 趋势跟踪: 趋势是你的朋友，顺势而为");
        System.out.println("2. 均值回归: 物极必反，价格会回归均值");
        System.out.println("3. 风险管理: 截断亏损，让利润奔跑");
        System.out.println("4. 日内交易: 积少成多，快速进退");
        System.out.println("5. 摆动交易: 抓取中期价格波动");
        System.out.println("6. 艾略特波浪: 市场按波浪模式运行");
    }

    /**
     * 演示趋势跟踪哲学
     */
    private static void demonstrateTrendFollowingPhilosophy(DataProvider dataProvider, BacktestConfig config, String symbol) {
        System.out.println("--- 趋势跟踪哲学演示 ---");
        System.out.println("核心理念: 截断亏损，让利润奔跑；趋势是你的朋友");

        Strategy trendStrategy = new TrendFollowingStrategy(10, 20);
        BacktestEngine engine = new BacktestEngine(dataProvider, trendStrategy, config);
        BacktestResult result = engine.run(symbol);

        if (result != null) {
            System.out.println("策略名称: " + result.getStrategyName());
            System.out.printf("收益率: %.2f%%%n", 
                result.getFinalCapital().subtract(result.getInitialCapital())
                      .divide(result.getInitialCapital(), 4, BigDecimal.ROUND_HALF_UP)
                      .multiply(BigDecimal.valueOf(100)));
            System.out.println("交易次数: " + result.getTrades().size());
            System.out.println("胜率: " + String.format("%.2f%%", result.getMetrics().getWinRate() * 100));
        }
        System.out.println();
    }

    /**
     * 演示均值回归哲学
     */
    private static void demonstrateMeanReversionPhilosophy(DataProvider dataProvider, BacktestConfig config, String symbol) {
        System.out.println("--- 均值回归哲学演示 ---");
        System.out.println("核心理念: 物极必反，价格围绕价值中枢波动");

        Strategy meanRevStrategy = new MeanReversionStrategy(14, 20, 30.0, 70.0);
        BacktestEngine engine = new BacktestEngine(dataProvider, meanRevStrategy, config);
        BacktestResult result = engine.run(symbol);

        if (result != null) {
            System.out.println("策略名称: " + result.getStrategyName());
            System.out.printf("收益率: %.2f%%%n", 
                result.getFinalCapital().subtract(result.getInitialCapital())
                      .divide(result.getInitialCapital(), 4, BigDecimal.ROUND_HALF_UP)
                      .multiply(BigDecimal.valueOf(100)));
            System.out.println("交易次数: " + result.getTrades().size());
            System.out.println("胜率: " + String.format("%.2f%%", result.getMetrics().getWinRate() * 100));
        }
        System.out.println();
    }

    /**
     * 演示风险管理哲学
     */
    private static void demonstrateRiskManagementPhilosophy(DataProvider dataProvider, BacktestConfig config, String symbol) {
        System.out.println("--- 风险管理哲学演示 ---");
        System.out.println("核心理念: 保护资本，合理分配风险");

        Strategy riskMgmtStrategy = new RiskManagementStrategy(20, 0.02, 0.05, 0.10);
        BacktestEngine engine = new BacktestEngine(dataProvider, riskMgmtStrategy, config);
        BacktestResult result = engine.run(symbol);

        if (result != null) {
            System.out.println("策略名称: " + result.getStrategyName());
            System.out.printf("收益率: %.2f%%%n", 
                result.getFinalCapital().subtract(result.getInitialCapital())
                      .divide(result.getInitialCapital(), 4, BigDecimal.ROUND_HALF_UP)
                      .multiply(BigDecimal.valueOf(100)));
            System.out.println("交易次数: " + result.getTrades().size());
            System.out.println("胜率: " + String.format("%.2f%%", result.getMetrics().getWinRate() * 100));
        }
        System.out.println();
    }

    /**
     * 演示日内交易哲学
     */
    private static void demonstrateScalpingPhilosophy(DataProvider dataProvider, BacktestConfig config, String symbol) {
        System.out.println("--- 日内交易哲学演示 ---");
        System.out.println("核心理念: 积少成多，快速进退");

        Strategy scalpStrategy = new ScalpingStrategy(5, 8, 13, 20.0, 80.0);
        BacktestEngine engine = new BacktestEngine(dataProvider, scalpStrategy, config);
        BacktestResult result = engine.run(symbol);

        if (result != null) {
            System.out.println("策略名称: " + result.getStrategyName());
            System.out.printf("收益率: %.2f%%%n", 
                result.getFinalCapital().subtract(result.getInitialCapital())
                      .divide(result.getInitialCapital(), 4, BigDecimal.ROUND_HALF_UP)
                      .multiply(BigDecimal.valueOf(100)));
            System.out.println("交易次数: " + result.getTrades().size());
            System.out.println("胜率: " + String.format("%.2f%%", result.getMetrics().getWinRate() * 100));
        }
        System.out.println();
    }

    /**
     * 演示摆动交易哲学
     */
    private static void demonstrateSwingTradingPhilosophy(DataProvider dataProvider, BacktestConfig config, String symbol) {
        System.out.println("--- 摆动交易哲学演示 ---");
        System.out.println("核心理念: 抓取中期价格波动，平衡风险收益");

        Strategy swingStrategy = new SwingTradingStrategy(14, 12, 26, 9, 30.0, 70.0);
        BacktestEngine engine = new BacktestEngine(dataProvider, swingStrategy, config);
        BacktestResult result = engine.run(symbol);

        if (result != null) {
            System.out.println("策略名称: " + result.getStrategyName());
            System.out.printf("收益率: %.2f%%%n", 
                result.getFinalCapital().subtract(result.getInitialCapital())
                      .divide(result.getInitialCapital(), 4, BigDecimal.ROUND_HALF_UP)
                      .multiply(BigDecimal.valueOf(100)));
            System.out.println("交易次数: " + result.getTrades().size());
            System.out.println("胜率: " + String.format("%.2f%%", result.getMetrics().getWinRate() * 100));
        }
        System.out.println();
    }

    /**
     * 演示艾略特波浪哲学
     */
    private static void demonstrateElliottWavePhilosophy(DataProvider dataProvider, BacktestConfig config, String symbol) {
        System.out.println("--- 艾略特波浪哲学演示 ---");
        System.out.println("核心理念: 市场按波浪模式运行，具有分形结构");

        Strategy elliottStrategy = new ElliottWaveStrategy(5, 20, 50);
        BacktestEngine engine = new BacktestEngine(dataProvider, elliottStrategy, config);
        BacktestResult result = engine.run(symbol);

        if (result != null) {
            System.out.println("策略名称: " + result.getStrategyName());
            System.out.printf("收益率: %.2f%%%n", 
                result.getFinalCapital().subtract(result.getInitialCapital())
                      .divide(result.getInitialCapital(), 4, BigDecimal.ROUND_HALF_UP)
                      .multiply(BigDecimal.valueOf(100)));
            System.out.println("交易次数: " + result.getTrades().size());
            System.out.println("胜率: " + String.format("%.2f%%", result.getMetrics().getWinRate() * 100));
        }
        System.out.println();
    }
}