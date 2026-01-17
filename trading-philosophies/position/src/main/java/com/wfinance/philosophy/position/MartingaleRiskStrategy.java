package com.wfinance.philosophy.position;

import com.wfinance.core.model.Bar;
import com.wfinance.strategy.engine.AbstractStrategy;
import com.wfinance.strategy.engine.StrategyContext;
import com.wfinance.strategy.engine.signal.SignalType;
import com.wfinance.strategy.engine.signal.TradingSignal;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.List;

/**
 * 风险递增哲学策略 (马丁格尔策略变种)
 *
 * 核心思想：
 * - "在亏损后加倍下注" - 通过增加投入来摊薄成本
 * - "最终会回归" - 相信市场最终会向有利方向移动
 * - "资金管理" - 通过调整头寸大小管理风险和回报
 *
 * 风险警告：
 * - 这是一种高风险策略，可能导致重大损失
 * - 需要充足的资本和风险控制
 * - 仅用于教育目的，实际使用需谨慎
 *
 * 策略实现：
 * - 初始小仓位进入市场
 * - 亏损后增加仓位大小
 * - 盈利后恢复初始仓位大小
 */
@Slf4j
public class MartingaleRiskStrategy extends AbstractStrategy {
    private final BigDecimal initialRiskPercentage; // 初始风险百分比
    private final BigDecimal multiplier; // 递增倍数
    private int consecutiveLosses; // 连续亏损次数
    private BigDecimal lastTradeResult; // 上次交易结果

    public MartingaleRiskStrategy(double initialRiskPercentage, double multiplier) {
        super(
            "风险递增策略",
            String.format("风险递增哲学应用：初始风险=%.2f%%, 递增倍数=%.1fx", 
                         initialRiskPercentage*100, multiplier),
            1 // 不需要额外的数据
        );

        this.initialRiskPercentage = BigDecimal.valueOf(initialRiskPercentage);
        this.multiplier = BigDecimal.valueOf(multiplier);
        this.consecutiveLosses = 0;
        this.lastTradeResult = BigDecimal.ZERO;
    }

    public MartingaleRiskStrategy() {
        this(0.01, 2.0); // 默认：1%初始风险，2倍递增
    }

    @Override
    public TradingSignal generateSignal(StrategyContext context) {
        if (!validateContext(context)) {
            return null;
        }

        // 根据上次交易结果调整连续亏损计数
        if (lastTradeResult != null && lastTradeResult.compareTo(BigDecimal.ZERO) < 0) {
            consecutiveLosses++;
        } else if (lastTradeResult != null && lastTradeResult.compareTo(BigDecimal.ZERO) > 0) {
            consecutiveLosses = 0; // 重置连续亏损计数
        }

        // 这里我们简单地在价格下跌时做多，上涨时做空
        // 实际马丁格尔策略通常结合其他信号使用
        List<Bar> bars = context.getBars();
        
        if (bars.size() < 2) {
            return null;
        }

        Bar currentBar = bars.get(bars.size() - 1);
        Bar previousBar = bars.get(bars.size() - 2);
        Bar latestBar = context.getLatestBar();

        // 简单的方向判断
        BigDecimal currentClose = currentBar.getClose();
        BigDecimal previousClose = previousBar.getClose();
        
        SignalType signalType;
        if (currentClose.compareTo(previousClose) < 0) {
            // 价格下跌，考虑做多（均值回归思路）
            signalType = SignalType.BUY;
        } else {
            // 价格上涨，考虑做空
            signalType = SignalType.SELL;
        }

        log.info("风险递增策略检测到信号: 方向={}, 连续亏损次数={}", 
                 signalType, consecutiveLosses);

        // 计算当前应使用的风险资金比例
        BigDecimal riskMultiplier = multiplier.pow(consecutiveLosses);
        BigDecimal currentRiskPercentage = initialRiskPercentage.multiply(riskMultiplier);
        
        // 限制最大风险倍数，防止过度风险
        BigDecimal maxRiskMultiplier = BigDecimal.valueOf(8); // 最大8倍
        if (riskMultiplier.compareTo(maxRiskMultiplier) > 0) {
            currentRiskPercentage = initialRiskPercentage.multiply(maxRiskMultiplier);
            log.warn("风险倍数达到上限，使用最大风险倍数: {}", maxRiskMultiplier);
        }

        return TradingSignal.builder()
                .signalType(signalType)
                .symbol(context.getSymbol())
                .timestamp(latestBar.getTimestamp())
                .price(latestBar.getClose())
                .strength(0.5) // 强度适中
                .reason(String.format("风险递增策略: 方向%s, 风险倍数%.1fx", 
                                     signalType == SignalType.BUY ? "做多" : "做空", 
                                     riskMultiplier))
                .riskAdjustment(currentRiskPercentage)
                .build();
    }

    /**
     * 更新上次交易结果（此方法通常由外部调用）
     */
    public void updateLastTradeResult(BigDecimal result) {
        this.lastTradeResult = result;
    }
}