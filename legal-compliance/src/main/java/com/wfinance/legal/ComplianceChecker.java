package com.wfinance.legal;

import com.wfinance.core.model.Order;
import com.wfinance.core.model.Position;
import com.wfinance.core.model.Trade;

import java.util.ArrayList;
import java.util.List;

/**
 * 合规检查器
 * 检查交易、持仓等是否符合监管要求
 */
public class ComplianceChecker {

    private final List<Regulation> regulations;

    public ComplianceChecker(List<Regulation> regulations) {
        this.regulations = regulations != null ? regulations : new ArrayList<>();
    }

    /**
     * 检查订单是否合规
     */
    public ComplianceResult checkOrder(Order order, String market) {
        ComplianceResult result = ComplianceResult.builder()
            .compliant(true)
            .build();

        for (Regulation regulation : regulations) {
            if (!regulation.isActive() || !regulation.isApplicableTo(market)) {
                continue;
            }

            // 检查持仓限制
            if (regulation.getPositionLimit() != null) {
                checkOrderAgainstPositionLimit(order, regulation, result);
            }

            // 检查保证金要求
            if (regulation.getMarginRequirement() != null) {
                checkMarginRequirement(order, regulation, result);
            }
        }

        return result;
    }

    /**
     * 检查持仓是否合规
     */
    public ComplianceResult checkPosition(Position position, String market, double accountValue) {
        ComplianceResult result = ComplianceResult.builder()
            .compliant(true)
            .build();

        for (Regulation regulation : regulations) {
            if (!regulation.isActive() || !regulation.isApplicableTo(market)) {
                continue;
            }

            PositionLimit limit = regulation.getPositionLimit();
            if (limit != null) {
                if (!limit.isWithinLimit(position.getQuantity(), accountValue)) {
                    result.addViolation(regulation,
                        String.format("持仓 %.2f 超过限制", position.getQuantity()));
                }
            }
        }

        return result;
    }

    /**
     * 检查交易是否合规
     */
    public ComplianceResult checkTrade(Trade trade, String market) {
        ComplianceResult result = ComplianceResult.builder()
            .compliant(true)
            .build();

        for (Regulation regulation : regulations) {
            if (!regulation.isActive() || !regulation.isApplicableTo(market)) {
                continue;
            }

            // 可以添加更多交易相关的合规检查
            if (regulation.getType() == RegulationType.INSIDER_TRADING) {
                result.addWarning("请确保不存在内幕交易行为");
            }

            if (regulation.getType() == RegulationType.MARKET_MANIPULATION) {
                result.addWarning("请确保不存在市场操纵行为");
            }
        }

        return result;
    }

    /**
     * 检查订单是否违反持仓限制
     */
    private void checkOrderAgainstPositionLimit(Order order, Regulation regulation, ComplianceResult result) {
        PositionLimit limit = regulation.getPositionLimit();

        // 检查是否允许做空
        if (!Boolean.TRUE.equals(limit.getShortSellingAllowed()) && order.getQuantity() < 0) {
            result.addViolation(regulation, "该市场不允许做空");
        }

        // 检查单笔订单数量
        if (limit.getMaxDailyVolume() != null && Math.abs(order.getQuantity()) > limit.getMaxDailyVolume()) {
            result.addViolation(regulation,
                String.format("订单数量 %.2f 超过单日最大交易量 %.2f",
                    Math.abs(order.getQuantity()), limit.getMaxDailyVolume()));
        }
    }

    /**
     * 检查保证金要求
     */
    private void checkMarginRequirement(Order order, Regulation regulation, ComplianceResult result) {
        Double marginReq = regulation.getMarginRequirement();
        if (marginReq != null && marginReq > 0) {
            result.addWarning(String.format("该交易需要 %.1f%% 的保证金", marginReq * 100));
        }
    }

    /**
     * 获取适用于指定市场的所有法规
     */
    public List<Regulation> getApplicableRegulations(String market) {
        List<Regulation> applicable = new ArrayList<>();
        for (Regulation regulation : regulations) {
            if (regulation.isActive() && regulation.isApplicableTo(market)) {
                applicable.add(regulation);
            }
        }
        return applicable;
    }
}
