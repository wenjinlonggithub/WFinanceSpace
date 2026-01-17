package com.wfinance.strategy.engine;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * 抽象策略基类
 * 提供策略的通用实现
 */
@Slf4j
@Getter
public abstract class AbstractStrategy implements Strategy {
    protected final String name;
    protected final String description;
    protected final int minBarsRequired;

    protected AbstractStrategy(String name, String description, int minBarsRequired) {
        this.name = name;
        this.description = description;
        this.minBarsRequired = minBarsRequired;
    }

    @Override
    public void initialize() {
        log.info("策略初始化: {}", name);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public int getMinBarsRequired() {
        return minBarsRequired;
    }

    /**
     * 验证上下文数据是否充足
     */
    protected boolean validateContext(StrategyContext context) {
        if (context == null) {
            log.warn("策略上下文为空");
            return false;
        }

        if (context.getBars() == null || context.getBars().isEmpty()) {
            log.warn("K线数据为空");
            return false;
        }

        if (context.getBars().size() < minBarsRequired) {
            log.warn("K线数据不足，需要至少{}根，当前{}根",
                    minBarsRequired, context.getBars().size());
            return false;
        }

        return true;
    }
}
