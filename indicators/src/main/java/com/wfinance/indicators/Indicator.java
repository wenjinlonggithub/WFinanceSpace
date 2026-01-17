package com.wfinance.indicators;

import com.wfinance.core.model.Bar;

import java.math.BigDecimal;
import java.util.List;

/**
 * 技术指标接口
 * 所有技术指标的基础接口
 */
public interface Indicator {
    /**
     * 计算指标值
     *
     * @param bars K线数据列表
     * @return 指标值列表（与输入K线数量对应）
     */
    List<BigDecimal> calculate(List<Bar> bars);

    /**
     * 计算单个指标值（基于最新的N根K线）
     *
     * @param bars K线数据列表
     * @return 最新的指标值
     */
    BigDecimal calculateLast(List<Bar> bars);

    /**
     * 获取指标名称
     *
     * @return 指标名称
     */
    String getName();
}
