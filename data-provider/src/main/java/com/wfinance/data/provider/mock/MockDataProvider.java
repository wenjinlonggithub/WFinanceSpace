package com.wfinance.data.provider.mock;

import com.wfinance.core.model.Bar;
import com.wfinance.core.model.Instrument;
import com.wfinance.core.model.Tick;
import com.wfinance.core.model.enums.MarketType;
import com.wfinance.data.provider.DataProvider;
import com.wfinance.data.provider.query.HistoricalDataQuery;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 模拟数据提供者
 * 用于测试和演示，生成模拟的市场数据
 */
public class MockDataProvider implements DataProvider {
    private final Random random = new Random(42);

    @Override
    public Instrument getInstrument(String symbol) {
        return Instrument.builder()
                .symbol(symbol)
                .name("模拟品种-" + symbol)
                .marketType(MarketType.STOCK)
                .exchange("MOCK")
                .tickSize(new BigDecimal("0.01"))
                .contractMultiplier(BigDecimal.ONE)
                .minQuantity(BigDecimal.ONE)
                .currency("CNY")
                .shortable(true)
                .marginRate(new BigDecimal("0.1"))
                .build();
    }

    @Override
    public List<Bar> getHistoricalBars(HistoricalDataQuery query) {
        List<Bar> bars = new ArrayList<>();

        LocalDateTime currentTime = query.getStartTime();
        BigDecimal basePrice = new BigDecimal("100.0");

        // 生成模拟数据，带有趋势和波动
        while (currentTime.isBefore(query.getEndTime())) {
            BigDecimal open = basePrice;

            // 添加随机波动
            BigDecimal change = BigDecimal.valueOf((random.nextDouble() - 0.5) * 4);
            BigDecimal close = open.add(change);

            // 计算最高价和最低价
            BigDecimal high = open.max(close).add(BigDecimal.valueOf(random.nextDouble() * 2));
            BigDecimal low = open.min(close).subtract(BigDecimal.valueOf(random.nextDouble() * 2));

            // 生成成交量
            BigDecimal volume = BigDecimal.valueOf(1000000 + random.nextInt(5000000));

            Bar bar = Bar.builder()
                    .symbol(query.getSymbol())
                    .timestamp(currentTime)
                    .open(open)
                    .high(high)
                    .low(low)
                    .close(close)
                    .volume(volume)
                    .build();

            bars.add(bar);

            // 更新基准价格（添加趋势）
            basePrice = close;

            // 下一个时间点（假设日线）
            currentTime = currentTime.plusDays(1);
        }

        return bars;
    }

    @Override
    public Tick getLatestTick(String symbol) {
        return Tick.builder()
                .symbol(symbol)
                .timestamp(LocalDateTime.now())
                .lastPrice(new BigDecimal("100.0"))
                .bidPrice(new BigDecimal("99.9"))
                .askPrice(new BigDecimal("100.1"))
                .bidVolume(new BigDecimal("1000"))
                .askVolume(new BigDecimal("1000"))
                .volume(new BigDecimal("1000000"))
                .build();
    }

    @Override
    public List<Instrument> getAllInstruments() {
        List<Instrument> instruments = new ArrayList<>();
        instruments.add(getInstrument("MOCK001"));
        return instruments;
    }
}
