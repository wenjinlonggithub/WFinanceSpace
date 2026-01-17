package com.wfinance.philosophy.meanreversion;

import com.wfinance.core.model.Bar;
import com.wfinance.indicators.volatility.BollingerBands;
import com.wfinance.strategy.engine.AbstractStrategy;
import com.wfinance.strategy.engine.StrategyContext;
import com.wfinance.strategy.engine.signal.SignalType;
import com.wfinance.strategy.engine.signal.TradingSignal;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.List;

/**
 * 布林带均值回归哲学策略
 *
 * 核心思想：
 * - "价格围绕均值波动" - 利用布林带识别极值位置
 * - "反转交易" - 在极端位置反向操作
 * - "风险控制" - 通过标准差设置合理的买卖边界
 *
 * 策略实现：
 * - 使用布林带识别超买超卖区域
 * - 价格触及下轨时做多，触及上轨时做空
 * - 价格回到中轨附近时平仓
 */
@Slf4j
public class BollingerBandsMeanReversionStrategy extends AbstractStrategy {
    private final BollingerBands bb;

    public BollingerBandsMeanReversionStrategy(int period, double stdDevMultiplier) {
        super(
            "布林带均值回归策略",
            String.format("均值回归哲学应用：布林带周期=%d, 标准差倍数=%.1f", period, stdDevMultiplier),
            period + 2
        );
        this.bb = new BollingerBands(period, stdDevMultiplier);
    }

    public BollingerBandsMeanReversionStrategy() {
        this(20, 2.0);
    }

    @Override
    public TradingSignal generateSignal(StrategyContext context) {
        if (!validateContext(context)) {
            return null;
        }

        List<Bar> bars = context.getBars();
        List<BollingerBands.BBResult> bbResults = bb.calculate(bars);

        int lastIndex = bars.size() - 1;
        BollingerBands.BBResult current = bbResults.get(lastIndex);
        Bar latestBar = context.getLatestBar();

        if (current == null || current.getUpper() == null || current.getLower() == null) {
            return null;
        }

        BigDecimal closePrice = latestBar.getClose();

        // 价格触及下轨：均值回归做多信号
        if (closePrice.compareTo(current.getLower()) <= 0) {
            log.info("布林带均值回归策略检测到做多信号: 价格={}, 下轨={}", 
                     closePrice, current.getLower());

            return TradingSignal.builder()
                    .signalType(SignalType.BUY)
                    .symbol(context.getSymbol())
                    .timestamp(latestBar.getTimestamp())
                    .price(closePrice)
                    .strength(0.8)
                    .reason(String.format("布林带均值回归做多: 价格(%.2f)触及下轨(%.2f)", 
                                         closePrice, current.getLower()))
                    .takeProfit(current.getMiddle()) // 目标价位为中轨
                    .build();
        }

        // 价格触及上轨：均值回归做空信号
        if (closePrice.compareTo(current.getUpper()) >= 0) {
            log.info("布林带均值回归策略检测到做空信号: 价格={}, 上轨={}", 
                     closePrice, current.getUpper());

            return TradingSignal.builder()
                    .signalType(SignalType.SELL)
                    .symbol(context.getSymbol())
                    .timestamp(latestBar.getTimestamp())
                    .price(closePrice)
                    .strength(0.8)
                    .reason(String.format("布林带均值回归做空: 价格(%.2f)触及上轨(%.2f)", 
                                         closePrice, current.getUpper()))
                    .takeProfit(current.getMiddle()) // 目标价位为中轨
                    .build();
        }

        return null;
    }
}