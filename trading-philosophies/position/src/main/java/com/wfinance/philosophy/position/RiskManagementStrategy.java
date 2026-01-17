package com.wfinance.philosophy.position;

import com.wfinance.core.model.Bar;
import com.wfinance.indicators.trend.SMA;
import com.wfinance.strategy.engine.AbstractStrategy;
import com.wfinance.strategy.engine.StrategyContext;
import com.wfinance.strategy.engine.signal.SignalType;
import com.wfinance.strategy.engine.signal.TradingSignal;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.List;

/**
 * 风险管理哲学策略
 *
 * 核心思想：
 * - "截断亏损，让利润奔跑" - 控制下行风险，允许上行收益增长
 * - "分散投资" - 不要把所有鸡蛋放在一个篮子里
 * - "轻仓操作" - 保持足够的安全边际
 * - "资金管理" - 合理分配每笔交易的风险敞口
 *
 * 策略实现：
 * - 使用移动平均线作为支撑阻力参考
 * - 根据风险承受能力动态调整头寸大小
 * - 设置止损止盈机制
 */
@Slf4j
public class RiskManagementStrategy extends AbstractStrategy {
    private final int maPeriod;
    private final SMA ma;
    private final BigDecimal maxRiskPerTrade; // 单笔交易最大风险比例
    private final BigDecimal stopLossPercentage; // 止损百分比
    private final BigDecimal takeProfitPercentage; // 止盈百分比

    public RiskManagementStrategy(int maPeriod, double maxRiskPerTrade, 
                                double stopLossPercentage, double takeProfitPercentage) {
        super(
            "风险管理策略",
            String.format("风险管理哲学应用：均线周期=%d, 单笔风险=%.2f%%, 止损=%.2f%%, 止盈=%.2f%%", 
                         maPeriod, maxRiskPerTrade*100, stopLossPercentage*100, takeProfitPercentage*100),
            maPeriod + 2
        );

        this.maPeriod = maPeriod;
        this.ma = new SMA(maPeriod);
        this.maxRiskPerTrade = BigDecimal.valueOf(maxRiskPerTrade);
        this.stopLossPercentage = BigDecimal.valueOf(stopLossPercentage);
        this.takeProfitPercentage = BigDecimal.valueOf(takeProfitPercentage);
    }

    public RiskManagementStrategy() {
        this(20, 0.02, 0.05, 0.10); // 默认：2%单笔风险，5%止损，10%止盈
    }

    @Override
    public TradingSignal generateSignal(StrategyContext context) {
        if (!validateContext(context)) {
            return null;
        }

        List<Bar> bars = context.getBars();
        
        // 计算移动平均线
        List<BigDecimal> maValues = ma.calculate(bars);

        int lastIndex = bars.size() - 1;

        if (lastIndex < 0) {
            return null;
        }

        BigDecimal currentMA = maValues.get(lastIndex);
        Bar latestBar = context.getLatestBar();
        BigDecimal currentPrice = latestBar.getClose();

        if (currentMA == null) {
            return null;
        }

        // 计算价格与均线的关系
        BigDecimal priceDiff = currentPrice.subtract(currentMA);
        BigDecimal pctDiff = priceDiff.divide(currentMA, 4, BigDecimal.ROUND_HALF_UP);

        // 当价格偏离均线一定幅度时产生信号
        BigDecimal threshold = BigDecimal.valueOf(0.02); // 2%阈值

        if (pctDiff.abs().compareTo(threshold) >= 0) {
            SignalType signalType = pctDiff.compareTo(BigDecimal.ZERO) > 0 ? 
                                   SignalType.SELL : SignalType.BUY;

            log.info("风险管理策略检测到信号: 价格{}偏离均线超过阈值", 
                     pctDiff.compareTo(BigDecimal.ZERO) > 0 ? "向上" : "向下");

            TradingSignal.Builder signalBuilder = TradingSignal.builder()
                    .signalType(signalType)
                    .symbol(context.getSymbol())
                    .timestamp(latestBar.getTimestamp())
                    .price(currentPrice)
                    .strength(0.6)
                    .reason(String.format("价格偏离均线: 当前价格(%.2f) vs 均线(%.2f), 偏离%.2f%%", 
                                         currentPrice, currentMA, 
                                         pctDiff.multiply(BigDecimal.valueOf(100))))
                    .stopLoss(calculateStopLoss(currentPrice, signalType))
                    .takeProfit(calculateTakeProfit(currentPrice, signalType));

            return signalBuilder.build();
        }

        return null;
    }

    /**
     * 计算止损价
     */
    private BigDecimal calculateStopLoss(BigDecimal currentPrice, SignalType signalType) {
        if (signalType == SignalType.BUY) {
            // 做多时止损在下方
            return currentPrice.multiply(BigDecimal.ONE.subtract(stopLossPercentage));
        } else {
            // 做空时止损在上方
            return currentPrice.multiply(BigDecimal.ONE.add(stopLossPercentage));
        }
    }

    /**
     * 计算止盈价
     */
    private BigDecimal calculateTakeProfit(BigDecimal currentPrice, SignalType signalType) {
        if (signalType == SignalType.BUY) {
            // 做多时止盈在上方
            return currentPrice.multiply(BigDecimal.ONE.add(takeProfitPercentage));
        } else {
            // 做空时止盈在下方
            return currentPrice.multiply(BigDecimal.ONE.subtract(takeProfitPercentage));
        }
    }
}