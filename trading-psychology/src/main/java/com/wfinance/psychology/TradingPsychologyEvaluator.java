package com.wfinance.psychology;

import com.wfinance.core.model.Trade;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 交易心理评估器
 * 评估交易者的心理状态和认知偏差
 */
@Data
public class TradingPsychologyEvaluator {

    /**
     * 评估交易者的情绪状态
     * 基于最近的交易记录
     */
    public EmotionalState assessEmotionalState(List<Trade> recentTrades) {
        if (recentTrades == null || recentTrades.isEmpty()) {
            return EmotionalState.builder()
                .emotionalStability(7.0)
                .build();
        }

        // 计算最近交易的盈亏情况
        int winCount = 0;
        int lossCount = 0;
        double totalPnL = 0.0;

        for (Trade trade : recentTrades) {
            double pnl = trade.getRealizedPnL();
            totalPnL += pnl;
            if (pnl > 0) winCount++;
            else if (pnl < 0) lossCount++;
        }

        // 基于交易结果推断情绪状态
        EmotionalState.EmotionalStateBuilder builder = EmotionalState.builder();

        // 连续亏损增加恐惧和焦虑
        if (lossCount > winCount) {
            builder.fearLevel(Math.min(10, 5 + lossCount * 0.5));
            builder.anxietyLevel(Math.min(10, 4 + lossCount * 0.6));
            builder.confidenceLevel(Math.max(0, 5 - lossCount * 0.4));
        }

        // 连续盈利可能导致过度自信和贪婪
        if (winCount > lossCount && winCount >= 3) {
            builder.confidenceLevel(Math.min(10, 5 + winCount * 0.5));
            builder.greedLevel(Math.min(10, 4 + winCount * 0.4));
            builder.fearLevel(Math.max(0, 3 - winCount * 0.3));
        }

        // 情绪稳定性与交易结果的波动性相关
        double stability = 7.0 - Math.abs(winCount - lossCount) * 0.3;
        builder.emotionalStability(Math.max(0, Math.min(10, stability)));

        return builder.build();
    }

    /**
     * 检测认知偏差
     * 基于交易历史识别可能存在的认知偏差
     */
    public List<BiasDetection> detectBiases(List<Trade> tradingHistory) {
        List<BiasDetection> detectedBiases = new ArrayList<>();

        if (tradingHistory == null || tradingHistory.size() < 5) {
            return detectedBiases;
        }

        // 检测处置效应：盈利头寸持有时间短，亏损头寸持有时间长
        detectDispositionEffect(tradingHistory, detectedBiases);

        // 检测过度自信：交易频率过高
        detectOverconfidence(tradingHistory, detectedBiases);

        // 检测损失厌恶：止损不及时
        detectLossAversion(tradingHistory, detectedBiases);

        // 检测赌徒谬误：亏损后加大仓位
        detectGamblersFallacy(tradingHistory, detectedBiases);

        return detectedBiases;
    }

    /**
     * 计算纪律性评分
     * 评估策略执行的一致性
     */
    public double calculateDisciplineScore(List<Trade> trades) {
        if (trades == null || trades.isEmpty()) {
            return 0.0;
        }

        double score = 100.0;

        // 检查是否有违反风险管理规则的交易
        // 这里简化处理，实际应该根据具体策略规则
        int violationCount = 0;

        for (Trade trade : trades) {
            // 检查是否有异常大的亏损（可能是没有止损）
            if (trade.getRealizedPnL() < 0) {
                double lossPercent = Math.abs(trade.getRealizedPnL() / trade.getEntryPrice());
                if (lossPercent > 0.05) { // 单笔亏损超过5%
                    violationCount++;
                }
            }
        }

        // 每次违规扣10分
        score -= violationCount * 10;

        return Math.max(0, Math.min(100, score));
    }

    // 私有辅助方法

    private void detectDispositionEffect(List<Trade> trades, List<BiasDetection> detections) {
        double avgWinHoldTime = 0;
        double avgLossHoldTime = 0;
        int winCount = 0;
        int lossCount = 0;

        for (Trade trade : trades) {
            // 这里简化处理，实际需要计算持仓时间
            if (trade.getRealizedPnL() > 0) {
                winCount++;
            } else if (trade.getRealizedPnL() < 0) {
                lossCount++;
            }
        }

        // 如果亏损交易数量明显多于盈利交易，可能存在处置效应
        if (lossCount > winCount * 1.5) {
            detections.add(new BiasDetection(
                CognitiveBias.DISPOSITION_EFFECT,
                0.7,
                "亏损交易数量明显多于盈利交易，可能存在急于止盈、死扛亏损的倾向"
            ));
        }
    }

    private void detectOverconfidence(List<Trade> trades, List<BiasDetection> detections) {
        // 如果交易频率过高（这里简化为交易数量）
        if (trades.size() > 50) {
            detections.add(new BiasDetection(
                CognitiveBias.OVERCONFIDENCE,
                0.6,
                "交易频率过高，可能存在过度自信，认为能够把握每一次机会"
            ));
        }
    }

    private void detectLossAversion(List<Trade> trades, List<BiasDetection> detections) {
        int largeLossCount = 0;

        for (Trade trade : trades) {
            if (trade.getRealizedPnL() < 0) {
                double lossPercent = Math.abs(trade.getRealizedPnL() / trade.getEntryPrice());
                if (lossPercent > 0.1) { // 单笔亏损超过10%
                    largeLossCount++;
                }
            }
        }

        if (largeLossCount > 0) {
            detections.add(new BiasDetection(
                CognitiveBias.LOSS_AVERSION,
                0.8,
                String.format("存在 %d 笔大额亏损，可能因损失厌恶而未能及时止损", largeLossCount)
            ));
        }
    }

    private void detectGamblersFallacy(List<Trade> trades, List<BiasDetection> detections) {
        // 检查是否在连续亏损后加大仓位
        // 这里简化处理，实际需要分析仓位变化
        int consecutiveLosses = 0;
        int maxConsecutiveLosses = 0;

        for (Trade trade : trades) {
            if (trade.getRealizedPnL() < 0) {
                consecutiveLosses++;
                maxConsecutiveLosses = Math.max(maxConsecutiveLosses, consecutiveLosses);
            } else {
                consecutiveLosses = 0;
            }
        }

        if (maxConsecutiveLosses >= 5) {
            detections.add(new BiasDetection(
                CognitiveBias.GAMBLERS_FALLACY,
                0.6,
                String.format("存在连续 %d 次亏损，需警惕赌徒谬误心理", maxConsecutiveLosses)
            ));
        }
    }

    /**
     * 偏差检测结果
     */
    @Data
    public static class BiasDetection {
        private final CognitiveBias bias;
        private final double confidence; // 0-1
        private final String evidence;

        public BiasDetection(CognitiveBias bias, double confidence, String evidence) {
            this.bias = bias;
            this.confidence = confidence;
            this.evidence = evidence;
        }
    }
}
