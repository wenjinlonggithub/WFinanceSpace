package com.wfinance.data.provider;

import com.wfinance.core.model.Bar;
import com.wfinance.core.model.Instrument;
import com.wfinance.core.model.Tick;
import com.wfinance.data.provider.query.HistoricalDataQuery;

import java.util.List;

/**
 * 数据提供者接口
 * 定义统一的市场数据访问接口
 */
public interface DataProvider {
    /**
     * 获取交易品种信息
     *
     * @param symbol 品种代码
     * @return 品种信息
     */
    Instrument getInstrument(String symbol);

    /**
     * 获取历史K线数据
     *
     * @param query 查询参数
     * @return K线数据列表
     */
    List<Bar> getHistoricalBars(HistoricalDataQuery query);

    /**
     * 获取最新Tick数据
     *
     * @param symbol 品种代码
     * @return Tick数据
     */
    Tick getLatestTick(String symbol);

    /**
     * 获取所有可交易品种列表
     *
     * @return 品种列表
     */
    List<Instrument> getAllInstruments();
}
