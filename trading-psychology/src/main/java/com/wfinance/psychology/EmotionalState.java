package com.wfinance.psychology;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 情绪状态
 * 表示交易者在特定时刻的情绪状态
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmotionalState {

    /**
     * 时间戳
     */
    private LocalDateTime timestamp;

    /**
     * 恐惧指数 (0-10)
     * 0 = 无恐惧, 10 = 极度恐惧
     */
    private double fearLevel;

    /**
     * 贪婪指数 (0-10)
     * 0 = 无贪婪, 10 = 极度贪婪
     */
    private double greedLevel;

    /**
     * 自信指数 (0-10)
     * 0 = 无自信, 10 = 过度自信
     */
    private double confidenceLevel;

    /**
     * 焦虑指数 (0-10)
     * 0 = 无焦虑, 10 = 极度焦虑
     */
    private double anxietyLevel;

    /**
     * 冲动指数 (0-10)
     * 0 = 无冲动, 10 = 极度冲动
     */
    private double impulsivityLevel;

    /**
     * 整体情绪稳定性 (0-10)
     * 0 = 极不稳定, 10 = 极度稳定
     */
    private double emotionalStability;

    /**
     * 触发因素
     */
    private String trigger;

    /**
     * 评估情绪是否适合交易
     * 当情绪过于极端时，不适合交易
     */
    public boolean isSuitableForTrading() {
        // 恐惧或贪婪过高
        if (fearLevel > 7.0 || greedLevel > 7.0) {
            return false;
        }

        // 过度自信
        if (confidenceLevel > 8.0) {
            return false;
        }

        // 焦虑或冲动过高
        if (anxietyLevel > 7.0 || impulsivityLevel > 7.0) {
            return false;
        }

        // 情绪稳定性过低
        if (emotionalStability < 4.0) {
            return false;
        }

        return true;
    }

    /**
     * 获取主导情绪
     */
    public String getDominantEmotion() {
        double maxLevel = Math.max(fearLevel,
                         Math.max(greedLevel,
                         Math.max(anxietyLevel, impulsivityLevel)));

        if (maxLevel == fearLevel) return "恐惧";
        if (maxLevel == greedLevel) return "贪婪";
        if (maxLevel == anxietyLevel) return "焦虑";
        if (maxLevel == impulsivityLevel) return "冲动";

        return "平静";
    }

    /**
     * 获取情绪风险评分 (0-100)
     * 分数越高，情绪风险越大
     */
    public double getEmotionalRiskScore() {
        double riskScore = 0.0;

        // 极端情绪增加风险
        riskScore += Math.max(0, fearLevel - 5) * 10;
        riskScore += Math.max(0, greedLevel - 5) * 10;
        riskScore += Math.max(0, confidenceLevel - 7) * 15;
        riskScore += Math.max(0, anxietyLevel - 5) * 10;
        riskScore += Math.max(0, impulsivityLevel - 5) * 15;

        // 情绪不稳定增加风险
        riskScore += Math.max(0, 5 - emotionalStability) * 10;

        return Math.min(100, riskScore);
    }
}
