package com.wfinance.cases;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 交易案例基类
 * 所有案例研究的抽象基类
 */
@Data
public abstract class TradingCase {

    /**
     * 案例名称
     */
    protected String caseName;

    /**
     * 案例描述
     */
    protected String description;

    /**
     * 开始日期
     */
    protected LocalDate startDate;

    /**
     * 结束日期
     */
    protected LocalDate endDate;

    /**
     * 涉及的市场
     */
    protected String market;

    /**
     * 涉及的交易品种
     */
    protected List<String> instruments = new ArrayList<>();

    /**
     * 关键教训
     */
    protected List<String> keyLessons = new ArrayList<>();

    /**
     * 案例标签（如：趋势跟踪、风险管理、心理学等）
     */
    protected List<String> tags = new ArrayList<>();

    /**
     * 案例难度等级（1-5）
     */
    protected int difficultyLevel;

    /**
     * 分析案例
     * 子类必须实现此方法来提供具体的案例分析
     */
    public abstract CaseAnalysis analyze();

    /**
     * 获取案例摘要
     */
    public String getSummary() {
        return String.format("%s (%s 至 %s): %s",
            caseName,
            startDate != null ? startDate.toString() : "未知",
            endDate != null ? endDate.toString() : "未知",
            description);
    }

    /**
     * 添加关键教训
     */
    public void addKeyLesson(String lesson) {
        this.keyLessons.add(lesson);
    }

    /**
     * 添加标签
     */
    public void addTag(String tag) {
        this.tags.add(tag);
    }
}
