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
 * 艾略特波浪哲学策略
 *
 * 核心思想：
 * - "市场按波浪模式运行" - 识别5浪推动和3浪调整
 * - "分形结构" - 大小周期都呈现相似的波浪形态
 * - "趋势识别" - 识别当前处于哪个浪中
 *
 * 实际应用：
 * - 使用多种时间周期的EMA识别不同级别的趋势
 * - 寻找趋势一致性的入场时机
 * - 顺应主要趋势方向交易
 *
 * 注意：艾略特波浪理论主观性较强，这里仅提供一个算法化的近似实现
 */
@Slf4j
public class ElliottWaveStrategy extends AbstractStrategy {
    private final EMA shortEMA;  // 短期趋势
    private final EMA mediumEMA; // 中期趋势
    private final EMA longEMA;   // 长期趋势
    
    private BigDecimal prevShortEMA;
    private BigDecimal prevMediumEMA;
    private BigDecimal prevLongEMA;

    public ElliottWaveStrategy(int shortPeriod, int mediumPeriod, int longPeriod) {
        super(
            "艾略特波浪策略",
            String.format("艾略特波浪哲学应用：短期EMA=%d, 中期EMA=%d, 长期EMA=%d", 
                         shortPeriod, mediumPeriod, longPeriod),
            longPeriod + 2
        );

        this.shortEMA = new EMA(shortPeriod);
        this.mediumEMA = new EMA(mediumPeriod);
        this.longEMA = new EMA(longPeriod);
    }

    public ElliottWaveStrategy() {
        this(5, 20, 50); // 短中长期周期
    }

    @Override
    public TradingSignal generateSignal(StrategyContext context) {
        if (!validateContext(context)) {
            return null;
        }

        List<Bar> bars = context.getBars();
        
        // 计算三个不同周期的EMA
        List<BigDecimal> shortEMAs = shortEMA.calculate(bars);
        List<BigDecimal> mediumEMAs = mediumEMA.calculate(bars);
        List<BigDecimal> longEMAs = longEMA.calculate(bars);

        int lastIndex = bars.size() - 1;

        if (lastIndex < 1) {
            return null;
        }

        // 获取当前EMA值
        BigDecimal currentShortEMA = shortEMAs.get(lastIndex);
        BigDecimal currentMediumEMA = mediumEMAs.get(lastIndex);
        BigDecimal currentLongEMA = longEMAs.get(lastIndex);

        // 获取前一个周期的EMA值用于趋势判断
        BigDecimal prevShortEMA = shortEMAs.get(lastIndex - 1);
        BigDecimal prevMediumEMA = mediumEMAs.get(lastIndex - 1);
        BigDecimal prevLongEMA = longEMAs.get(lastIndex - 1);

        if (currentShortEMA == null || currentMediumEMA == null || currentLongEMA == null ||
            prevShortEMA == null || prevMediumEMA == null || prevLongEMA == null) {
            return null;
        }

        Bar latestBar = context.getLatestBar();

        // 艾略特波浪理论：趋势一致性做多信号
        // 长期、中期、短期都在上升趋势，且排列正确
        boolean longTermBullish = currentLongEMA.compareTo(prevLongEMA) > 0;
        boolean midTermBullish = currentMediumEMA.compareTo(prevMediumEMA) > 0;
        boolean shortTermBullish = currentShortEMA.compareTo(prevShortEMA) > 0;
        
        boolean alignmentBullish = currentShortEMA.compareTo(currentMediumEMA) > 0 && 
                                  currentMediumEMA.compareTo(currentLongEMA) > 0;

        if (longTermBullish && midTermBullish && shortTermBullish && alignmentBullish) {
            log.info("艾略特波浪策略检测到做多信号: 三重趋势向上对齐");

            return TradingSignal.builder()
                    .signalType(SignalType.BUY)
                    .symbol(context.getSymbol())
                    .timestamp(latestBar.getTimestamp())
                    .price(latestBar.getClose())
                    .strength(0.9) // 趋势一致性强度很高
                    .reason(String.format("艾略特波浪做多: 三重趋势向上(短:%.2f, 中:%.2f, 长:%.2f)", 
                                         currentShortEMA, currentMediumEMA, currentLongEMA))
                    .build();
        }

        // 艾略特波浪理论：趋势一致性做空信号
        // 长期、中期、短期都在下降趋势，且排列正确
        boolean longTermBearish = currentLongEMA.compareTo(prevLongEMA) < 0;
        boolean midTermBearish = currentMediumEMA.compareTo(prevMediumEMA) < 0;
        boolean shortTermBearish = currentShortEMA.compareTo(prevShortEMA) < 0;
        
        boolean alignmentBearish = currentShortEMA.compareTo(currentMediumEMA) < 0 && 
                                  currentMediumEMA.compareTo(currentLongEMA) < 0;

        if (longTermBearish && midTermBearish && shortTermBearish && alignmentBearish) {
            log.info("艾略特波浪策略检测到做空信号: 三重趋势向下对齐");

            return TradingSignal.builder()
                    .signalType(SignalType.SELL)
                    .symbol(context.getSymbol())
                    .timestamp(latestBar.getTimestamp())
                    .price(latestBar.getClose())
                    .strength(0.9) // 趋势一致性强度很高
                    .reason(String.format("艾略特波浪做空: 三重趋势向下(短:%.2f, 中:%.2f, 长:%.2f)", 
                                         currentShortEMA, currentMediumEMA, currentLongEMA))
                    .build();
        }

        return null;
    }
}