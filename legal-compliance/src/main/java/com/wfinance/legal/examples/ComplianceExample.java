package com.wfinance.legal.examples;

import com.wfinance.core.model.Order;
import com.wfinance.core.model.OrderSide;
import com.wfinance.legal.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 法律合规示例
 * 演示如何使用合规检查器
 */
public class ComplianceExample {

    public static void main(String[] args) {
        // 1. 创建监管法规
        List<Regulation> regulations = createSampleRegulations();

        // 2. 创建合规检查器
        ComplianceChecker checker = new ComplianceChecker(regulations);

        // 3. 检查订单合规性
        Order order = createSampleOrder();
        ComplianceResult result = checker.checkOrder(order, "CN_STOCK");

        // 4. 输出检查结果
        System.out.println("=== 合规检查结果 ===");
        System.out.println("是否合规: " + (result.isCompliant() ? "是" : "否"));
        System.out.println("摘要: " + result.getSummary());

        if (!result.isCompliant()) {
            System.out.println("\n违规项:");
            for (String violation : result.getViolations()) {
                System.out.println("  - " + violation);
            }
        }

        if (!result.getWarnings().isEmpty()) {
            System.out.println("\n警告:");
            for (String warning : result.getWarnings()) {
                System.out.println("  - " + warning);
            }
        }
    }

    private static List<Regulation> createSampleRegulations() {
        List<Regulation> regulations = new ArrayList<>();

        // 中国A股持仓限制
        Regulation cnStockLimit = Regulation.builder()
            .regulationId("CSRC-2023-001")
            .name("A股持仓限制规定")
            .description("限制单一账户持仓比例")
            .authority(RegulatoryAuthority.CSRC)
            .applicableMarkets(List.of("CN_STOCK"))
            .effectiveDate(LocalDate.of(2023, 1, 1))
            .type(RegulationType.POSITION_LIMITS)
            .positionLimit(PositionLimit.builder()
                .maxPosition(1000000.0)
                .maxPositionPercentage(0.05)
                .shortSellingAllowed(false)
                .build())
            .build();

        regulations.add(cnStockLimit);
        return regulations;
    }

    private static Order createSampleOrder() {
        Order order = new Order();
        order.setSymbol("600000.SH");
        order.setSide(OrderSide.BUY);
        order.setQuantity(100.0);
        order.setPrice(10.0);
        return order;
    }
}
