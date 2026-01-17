package com.wfinance.backtest.engine;

import com.wfinance.backtest.config.BacktestConfig;
import com.wfinance.backtest.metrics.PerformanceMetrics;
import com.wfinance.backtest.record.TradeRecord;
import com.wfinance.backtest.result.BacktestResult;
import com.wfinance.core.model.Account;
import com.wfinance.core.model.Bar;
import com.wfinance.core.model.Position;
import com.wfinance.core.model.enums.OrderSide;
import com.wfinance.core.model.enums.PositionSide;
import com.wfinance.data.provider.DataProvider;
import com.wfinance.data.provider.query.HistoricalDataQuery;
import com.wfinance.strategy.engine.Strategy;
import com.wfinance.strategy.engine.StrategyContext;
import com.wfinance.strategy.engine.signal.SignalType;
import com.wfinance.strategy.engine.signal.TradingSignal;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 回测引擎
 * 执行策略回测的核心类
 */
@Slf4j
public class BacktestEngine {
    private final DataProvider dataProvider;
    private final Strategy strategy;
    private final BacktestConfig config;

    private Account account;
    private Position currentPosition;
    private List<TradeRecord> tradeRecords;
    private List<BigDecimal> equityCurve;
    private TradeRecord openTrade;

    public BacktestEngine(DataProvider dataProvider, Strategy strategy, BacktestConfig config) {
        this.dataProvider = dataProvider;
        this.strategy = strategy;
        this.config = config;
    }

    /**
     * 执行回测
     */
    public BacktestResult run(String symbol) {
        log.info("开始回测: 策略={}, 品种={}", strategy.getName(), symbol);

        // 初始化
        initialize(symbol);

        // 获取历史数据
        List<Bar> bars = loadHistoricalData(symbol);
        if (bars == null || bars.isEmpty()) {
            log.error("无法获取历史数据");
            return null;
        }

        log.info("加载了{}根K线数据", bars.size());

        // 策略初始化
        strategy.initialize();

        // 遍历历史数据
        for (int i = strategy.getMinBarsRequired(); i < bars.size(); i++) {
            List<Bar> historicalBars = bars.subList(0, i + 1);
            processBar(symbol, historicalBars);
        }

        // 如果还有持仓，平仓
        if (currentPosition != null && !currentPosition.isFlat()) {
            closePosition(bars.get(bars.size() - 1), "回测结束");
        }

        // 生成回测结果
        return generateResult(symbol, bars);
    }

    private void initialize(String symbol) {
        // 初始化账户
        account = Account.builder()
                .accountId(UUID.randomUUID().toString())
                .accountName("回测账户")
                .initialCapital(config.getInitialCapital())
                .availableBalance(config.getInitialCapital())
                .frozenBalance(BigDecimal.ZERO)
                .positions(new ArrayList<>())
                .currency("CNY")
                .build();

        currentPosition = null;
        tradeRecords = new ArrayList<>();
        equityCurve = new ArrayList<>();
        openTrade = null;
    }

    private List<Bar> loadHistoricalData(String symbol) {
        HistoricalDataQuery query = HistoricalDataQuery.builder()
                .symbol(symbol)
                .startTime(config.getStartTime())
                .endTime(config.getEndTime())
                .build();

        return dataProvider.getHistoricalBars(query);
    }

    private void processBar(String symbol, List<Bar> bars) {
        Bar currentBar = bars.get(bars.size() - 1);

        // 更新持仓市值
        if (currentPosition != null && !currentPosition.isFlat()) {
            currentPosition.setCurrentPrice(currentBar.getClose());
        }

        // 记录资金曲线
        equityCurve.add(account.getTotalAssets());

        // 构建策略上下文
        StrategyContext context = StrategyContext.builder()
                .symbol(symbol)
                .bars(bars)
                .account(account)
                .currentPosition(currentPosition)
                .build();

        // 生成交易信号
        TradingSignal signal = strategy.generateSignal(context);

        if (signal != null) {
            executeSignal(signal, currentBar);
        }
    }

    private void executeSignal(TradingSignal signal, Bar bar) {
        log.debug("收到交易信号: {}", signal);

        if (signal.getSignalType() == SignalType.BUY) {
            if (currentPosition == null || currentPosition.isFlat()) {
                openPosition(signal, bar, OrderSide.BUY);
            }
        } else if (signal.getSignalType() == SignalType.SELL) {
            if (currentPosition != null && !currentPosition.isFlat()) {
                closePosition(bar, signal.getReason());
            }
        }
    }

    private void openPosition(TradingSignal signal, Bar bar, OrderSide side) {
        // 计算交易数量
        BigDecimal availableFunds = account.getAvailableBalance()
                .multiply(config.getPositionSizeRatio());
        BigDecimal price = bar.getClose();
        BigDecimal quantity = availableFunds.divide(price, 0, BigDecimal.ROUND_DOWN);

        if (quantity.compareTo(BigDecimal.ZERO) <= 0) {
            log.warn("可用资金不足，无法开仓");
            return;
        }

        // 计算手续费
        BigDecimal cost = price.multiply(quantity);
        BigDecimal commission = cost.multiply(config.getCommissionRate());

        // 更新账户
        account.setAvailableBalance(account.getAvailableBalance().subtract(cost).subtract(commission));

        // 创建持仓
        currentPosition = Position.builder()
                .positionId(UUID.randomUUID().toString())
                .symbol(signal.getSymbol())
                .side(side == OrderSide.BUY ? PositionSide.LONG : PositionSide.SHORT)
                .quantity(quantity)
                .avgEntryPrice(price)
                .currentPrice(price)
                .openTime(bar.getTimestamp())
                .build();

        // 创建交易记录
        openTrade = TradeRecord.builder()
                .tradeId(UUID.randomUUID().toString())
                .symbol(signal.getSymbol())
                .side(side)
                .entryTime(bar.getTimestamp())
                .entryPrice(price)
                .quantity(quantity)
                .commission(commission)
                .entryReason(signal.getReason())
                .build();

        log.info("开仓: {} {} @ {}, 数量={}", signal.getSymbol(), side, price, quantity);
    }

    private void closePosition(Bar bar, String reason) {
        if (currentPosition == null || currentPosition.isFlat()) {
            return;
        }

        BigDecimal exitPrice = bar.getClose();
        BigDecimal quantity = currentPosition.getQuantity();

        // 计算手续费
        BigDecimal proceeds = exitPrice.multiply(quantity);
        BigDecimal commission = proceeds.multiply(config.getCommissionRate());

        // 更新账户
        account.setAvailableBalance(account.getAvailableBalance().add(proceeds).subtract(commission));

        // 完成交易记录
        openTrade.setExitTime(bar.getTimestamp());
        openTrade.setExitPrice(exitPrice);
        openTrade.setCommission(openTrade.getCommission().add(commission));
        openTrade.setExitReason(reason);
        openTrade.calculateProfitLoss();

        tradeRecords.add(openTrade);

        log.info("平仓: {} @ {}, 盈亏={}", exitPrice, openTrade.getProfitLoss());

        // 清空持仓
        currentPosition = null;
        openTrade = null;
    }

    private BacktestResult generateResult(String symbol, List<Bar> bars) {
        int tradingDays = (int) Duration.between(
                config.getStartTime(),
                config.getEndTime()
        ).toDays();

        PerformanceMetrics metrics = PerformanceMetrics.calculate(
                config.getInitialCapital(),
                account.getTotalAssets(),
                tradeRecords,
                equityCurve,
                tradingDays
        );

        return BacktestResult.builder()
                .config(config)
                .strategyName(strategy.getName())
                .symbol(symbol)
                .initialCapital(config.getInitialCapital())
                .finalCapital(account.getTotalAssets())
                .startTime(config.getStartTime())
                .endTime(config.getEndTime())
                .trades(tradeRecords)
                .equityCurve(equityCurve)
                .metrics(metrics)
                .build();
    }
}
