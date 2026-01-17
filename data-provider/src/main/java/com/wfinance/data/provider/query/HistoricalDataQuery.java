package com.wfinance.data.provider.query;

import com.wfinance.core.model.enums.TimeFrame;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 历史数据查询参数
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoricalDataQuery {
    /**
     * 交易品种代码
     */
    private String symbol;

    /**
     * 时间周期
     */
    private TimeFrame timeFrame;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 最大返回数量（可选）
     */
    private Integer limit;
}
