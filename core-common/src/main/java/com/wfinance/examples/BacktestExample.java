package com.wfinance.examples;

import com.wfinance.backtest.config.BacktestConfig;
import com.wfinance.backtest.engine.BacktestEngine;
import com.wfinance.backtest.result.BacktestResult;
import com.wfinance.data.provider.DataProvider;
import com.wfinance.data.provider.mock.MockDataProvider;
import com.wfinance.strategies.classic.MovingAverageCrossoverStrategy;
import com.wfinance.strategy.engine.Strategy;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 回测示例
 * 演示如何使用框架进行策略回测
 */
public class BacktestExample {
    public static void main(String[] args) {
        System.out.println("=== WFinance 交易策略回测示例 ===\n");

        // 1. 创建数据提供者
        DataProvider dataProvider = new MockDataProvider();
        System.out.println("✓ 数据提供者已创建");

        // 2. 创建交易策略（双均线交叉策略）
        Strategy strategy = new MovingAverageCrossoverStrategy(10, 20);
        System.out.println("✓ 策略已创建: " + strategy.getName());
        System.out.println("  描述: " + strategy.getDescription());

        // 3. 配置回测参数
        BacktestConfig config = BacktestConfig.builder()
                .initialCapital(new BigDecimal("100000"))  // 初始资金10万
                .startTime(LocalDateTime.now().minusYears(1))  // 回测1年
                .endTime(LocalDateTime.now())
                .commissionRate(new BigDecimal("0.001"))  // 手续费0.1%
                .slippage(BigDecimal.ZERO)
                .positionSizeRatio(new BigDecimal("0.95"))  // 每次使用95%资金
                .allowShort(false)
                .maxPositions(1)
                .build();
        System.out.println("✓ 回测配置已完成");
        System.out.println("  初始资金: " + config.getInitialCapital());
        System.out.println("  回测周期: " + config.getStartTime() + " 至 " + config.getEndTime());

        // 4. 创建回测引擎
        BacktestEngine engine = new BacktestEngine(dataProvider, strategy, config);
        System.out.println("✓ 回测引擎已创建\n");

        // 5. 执行回测
        System.out.println("开始执行回测...\n");
        String symbol = "MOCK001";
        BacktestResult result = engine.run(symbol);

        // 6. 输出回测结果
        if (result != null) {
            System.out.println(result.generateReport());

            // 输出部分交易记录
            System.out.println("--- 交易记录（前5笔）---");
            int count = Math.min(5, result.getTrades().size());
            for (int i = 0; i < count; i++) {
                var trade = result.getTrades().get(i);
                System.out.println(String.format(
                        "%d. %s %s @ %.2f -> %.2f, 盈亏: %.2f (%.2f%%)",
                        i + 1,
                        trade.getSymbol(),
                        trade.getSide(),
                        trade.getEntryPrice(),
                        trade.getExitPrice(),
                        trade.getProfitLoss(),
                        trade.getProfitLossPercent()
                ));
            }
        } else {
            System.out.println("回测失败");
        }
    }
}
