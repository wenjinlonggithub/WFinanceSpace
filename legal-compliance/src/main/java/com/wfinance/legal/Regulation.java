package com.wfinance.legal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * 监管法规
 * 代表一个具体的交易监管规则或法律条款
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Regulation {

    /**
     * 法规ID
     */
    private String regulationId;

    /**
     * 法规名称
     */
    private String name;

    /**
     * 法规描述
     */
    private String description;

    /**
     * 监管机构
     */
    private RegulatoryAuthority authority;

    /**
     * 适用市场
     */
    private List<String> applicableMarkets;

    /**
     * 生效日期
     */
    private LocalDate effectiveDate;

    /**
     * 失效日期（如果已废止）
     */
    private LocalDate expiryDate;

    /**
     * 法规类型
     */
    private RegulationType type;

    /**
     * 持仓限制（如果适用）
     */
    private PositionLimit positionLimit;

    /**
     * 保证金要求（如果适用）
     */
    private Double marginRequirement;

    /**
     * 法规全文链接
     */
    private String fullTextUrl;

    /**
     * 检查该法规是否当前有效
     */
    public boolean isActive() {
        LocalDate now = LocalDate.now();
        return (effectiveDate == null || !now.isBefore(effectiveDate)) &&
               (expiryDate == null || now.isBefore(expiryDate));
    }

    /**
     * 检查该法规是否适用于指定市场
     */
    public boolean isApplicableTo(String market) {
        return applicableMarkets == null || applicableMarkets.contains(market);
    }
}
