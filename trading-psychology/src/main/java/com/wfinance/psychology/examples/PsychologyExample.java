package com.wfinance.psychology.examples;

import com.wfinance.core.model.Trade;
import com.wfinance.psychology.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 交易心理示例
 * 演示如何评估交易心理状态
 */
public class PsychologyExample {

    public static void main(String[] args) {
        TradingPsychologyEvaluator evaluator = new TradingPsychologyEvaluator();

        // 1. 评估情绪状态
        List<Trade> recentTrades = createSampleTrades();
        EmotionalState state = evaluator.assessEmotionalState(recentTrades);

        System.out.println("=== 交易心理评估 ===");
        System.out.println("\n情绪状态:");
        System.out.println("  主导情绪: " + state.getDominantEmotion());
        System.out.println("  情绪风险评分: " + String.format("%.1f", state.getEmotionalRiskScore()));
        System.out.println("  是否适合交易: " + (state.isSuitableForTrading() ? "是" : "否"));

        // 2. 检测认知偏差
        List<TradingPsychologyEvaluator.BiasDetection> biases =
            evaluator.detectBiases(recentTrades);

        if (!biases.isEmpty()) {
            System.out.println("\n检测到的认知偏差:");
            for (TradingPsychologyEvaluator.BiasDetection bias : biases) {
                System.out.println("  - " + bias.getBias().getChineseName());
                System.out.println("    置信度: " + String.format("%.0f%%", bias.getConfidence() * 100));
                System.out.println("    证据: " + bias.getEvidence());
            }
        }

        // 3. 计算纪律性评分
        double disciplineScore = evaluator.calculateDisciplineScore(recentTrades);
        System.out.println("\n纪律性评分: " + String.format("%.1f/100", disciplineScore));
    }

    private static List<Trade> createSampleTrades() {
        List<Trade> trades = new ArrayList<>();

        // 模拟一些交易记录
        for (int i = 0; i < 10; i++) {
            Trade trade = new Trade();
            trade.setSymbol("AAPL");
            trade.setEntryPrice(150.0);
            trade.setExitPrice(i % 3 == 0 ? 145.0 : 155.0); // 部分亏损
            trade.setQuantity(100.0);
            trade.setRealizedPnL((trade.getExitPrice() - trade.getEntryPrice()) * trade.getQuantity());
            trades.add(trade);
        }

        return trades;
    }
}
