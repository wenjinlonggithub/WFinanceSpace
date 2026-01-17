package com.wfinance.strategy.engine;

import com.wfinance.core.model.Account;
import com.wfinance.core.model.Bar;
import com.wfinance.core.model.Position;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 策略上下文
 * 包含策略执行所需的所有信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StrategyContext {
    /**
     * 交易品种代码
     */
    private String symbol;

    /**
     * 历史K线数据
     */
    private List<Bar> bars;

    /**
     * 当前账户信息
     */
    private Account account;

    /**
     * 当前持仓信息
     */
    private Position currentPosition;

    /**
     * 获取最新的K线
     */
    public Bar getLatestBar() {
        if (bars == null || bars.isEmpty()) {
            return null;
        }
        return bars.get(bars.size() - 1);
    }

    /**
     * 获取指定索引的K线（从后往前，0表示最新）
     */
    public Bar getBar(int indexFromEnd) {
        if (bars == null || bars.isEmpty() || indexFromEnd >= bars.size()) {
            return null;
        }
        return bars.get(bars.size() - 1 - indexFromEnd);
    }

    /**
     * 判断是否有持仓
     */
    public boolean hasPosition() {
        return currentPosition != null && !currentPosition.isFlat();
    }
}
