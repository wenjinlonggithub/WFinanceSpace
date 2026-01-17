package com.wfinance.philosophy.trendfollowing;

import com.wfinance.core.model.Bar;
import com.wfinance.indicators.trend.EMA;
import com.wfinance.strategy.engine.AbstractStrategy;
import com.wfinance.strategy.engine.StrategyContext;
import com.wfinance.strategy.engine.signal.SignalType;
import com.wfinance.strategy.engine.signal.TradingSignal;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.List;

/**
 * 趋势跟踪哲学策略
 *
 * 核心思想：
 * - "截断亏损，让利润奔跑" - 跟踪趋势直到反转信号出现
 * - 在上升趋势中做多，在下降趋势中做空
 * - 趋势是你的朋友，不要逆市而为
 *
 * 策略实现：
 * - 使用EMA作为趋势判断工具
 * - 当短期均线上穿长期均线时做多
 * - 当短期均线下穿长期均线时做空
 */
@Slf4j
public class TrendFollowingStrategy extends AbstractStrategy {
    private final int shortTerm;
    private final int longTerm;
    private final EMA shortEMA;
    private final EMA longEMA;

    public TrendFollowingStrategy(int shortTerm, int longTerm) {
        super(
            "趋势跟踪策略",
            String.format("趋势跟踪哲学应用：短期EMA=%d, 长期EMA=%d", shortTerm, longTerm),
            longTerm + 2
        );

        if (shortTerm >= longTerm) {
            throw new IllegalArgumentException("短期周期必须小于长期周期");
        }

        this.shortTerm = shortTerm;
        this.longTerm = longTerm;
        this.shortEMA = new EMA(shortTerm);
        this.longEMA = new EMA(longTerm);
    }

    public TrendFollowingStrategy() {
        this(12, 26);
    }

    @Override
    public TradingSignal generateSignal(StrategyContext context) {
        if (!validateContext(context)) {
            return null;
        }

        List<Bar> bars = context.getBars();
        
        // 计算长短周期EMA
        List<BigDecimal> shortEMAs = shortEMA.calculate(bars);
        List<BigDecimal> longEMAs = longEMA.calculate(bars);

        int lastIndex = bars.size() - 1;
        int prevIndex = lastIndex - 1;

        if (lastIndex < 0 || prevIndex < 0) {
            return null;
        }

        // 获取当前和前一个周期的EMA值
        BigDecimal currentShortEMA = shortEMAs.get(lastIndex);
        BigDecimal currentLongEMA = longEMAs.get(lastIndex);
        BigDecimal prevShortEMA = shortEMAs.get(prevIndex);
        BigDecimal prevLongEMA = longEMAs.get(prevIndex);

        if (currentShortEMA == null || currentLongEMA == null ||
            prevShortEMA == null || prevLongEMA == null) {
            return null;
        }

        Bar latestBar = context.getLatestBar();

        // 黄金交叉 - 短期均线上穿长期均线，看涨信号
        if (prevShortEMA.compareTo(prevLongEMA) <= 0 &&
            currentShortEMA.compareTo(currentLongEMA) > 0) {

            log.info("趋势跟踪策略检测到黄金交叉: 短期EMA={} 上穿 长期EMA={}", 
                     currentShortEMA, currentLongEMA);

            return TradingSignal.builder()
                    .signalType(SignalType.BUY)
                    .symbol(context.getSymbol())
                    .timestamp(latestBar.getTimestamp())
                    .price(latestBar.getClose())
                    .strength(0.7)
                    .reason(String.format("趋势向上突破: EMA%d(%.2f) > EMA%d(%.2f)", 
                                         shortTerm, currentShortEMA, 
                                         longTerm, currentLongEMA))
                    .build();
        }

        // 死亡交叉 - 短期均线下穿长期均线，看跌信号
        if (prevShortEMA.compareTo(prevLongEMA) >= 0 &&
            currentShortEMA.compareTo(currentLongEMA) < 0) {

            log.info("趋势跟踪策略检测到死亡交叉: 短期EMA={} 下穿 长期EMA={}", 
                     currentShortEMA, currentLongEMA);

            return TradingSignal.builder()
                    .signalType(SignalType.SELL)
                    .symbol(context.getSymbol())
                    .timestamp(latestBar.getTimestamp())
                    .price(latestBar.getClose())
                    .strength(0.7)
                    .reason(String.format("趋势向下突破: EMA%d(%.2f) < EMA%d(%.2f)", 
                                         shortTerm, currentShortEMA, 
                                         longTerm, currentLongEMA))
                    .build();
        }

        return null;
    }
}