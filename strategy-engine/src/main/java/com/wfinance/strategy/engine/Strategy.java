package com.wfinance.strategy.engine;

import com.wfinance.core.model.Bar;
import com.wfinance.strategy.engine.signal.TradingSignal;

import java.util.List;

/**
 * 交易策略接口
 * 所有交易策略的基础接口
 */
public interface Strategy {
    /**
     * 策略初始化
     */
    void initialize();

    /**
     * 生成交易信号
     *
     * @param context 策略上下文
     * @return 交易信号，如果没有信号则返回null
     */
    TradingSignal generateSignal(StrategyContext context);

    /**
     * 策略名称
     *
     * @return 策略名称
     */
    String getName();

    /**
     * 策略描述
     *
     * @return 策略描述
     */
    String getDescription();

    /**
     * 获取策略所需的最小K线数量
     *
     * @return 最小K线数量
     */
    int getMinBarsRequired();
}
