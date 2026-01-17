package com.wfinance.legal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 持仓限制
 * 定义对特定工具或市场的持仓限制规则
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PositionLimit {

    /**
     * 最大持仓数量（手/张/股）
     */
    private Double maxPosition;

    /**
     * 最大持仓价值
     */
    private Double maxPositionValue;

    /**
     * 最大持仓占比（相对于总资产）
     */
    private Double maxPositionPercentage;

    /**
     * 单日最大交易量
     */
    private Double maxDailyVolume;

    /**
     * 是否允许做空
     */
    private Boolean shortSellingAllowed;

    /**
     * 最大杠杆倍数
     */
    private Double maxLeverage;

    /**
     * 检查持仓是否超过限制
     */
    public boolean isWithinLimit(double currentPosition, double accountValue) {
        if (maxPosition != null && Math.abs(currentPosition) > maxPosition) {
            return false;
        }

        if (maxPositionPercentage != null && accountValue > 0) {
            double positionPercentage = Math.abs(currentPosition) / accountValue;
            if (positionPercentage > maxPositionPercentage) {
                return false;
            }
        }

        if (!Boolean.TRUE.equals(shortSellingAllowed) && currentPosition < 0) {
            return false;
        }

        return true;
    }
}
