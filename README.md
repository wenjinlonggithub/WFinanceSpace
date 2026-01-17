# Trading Philosophy Lab (交易哲学实验室)

> 用代码探索交易的本质 - 哲学、心理、战法与合规的统一框架

## ⚠️ 重要声明

**本项目仅供教育和研究目的使用。**

- 金融市场交易存在重大风险，可能导致全部资金损失
- 历史表现不代表未来结果
- 本项目不构成任何投资建议
- 使用本项目进行实盘交易需自行承担所有风险
- 作者不对任何交易损失负责

## 项目简介

Trading Philosophy Lab 是一个专注于**交易哲学、策略和心理**的教育研究框架。本项目使用代码模拟和实现各种交易哲学、战法、心理因素和法律合规要求，帮助交易者理解交易的本质。

### 核心理念

- 🧠 **哲学优先**: 关注交易背后的思想和哲学，而非纯技术实现
- 💭 **心理分析**: 深入研究交易心理、情绪控制和认知偏差
- 📚 **案例学习**: 通过真实案例学习成功和失败的经验
- ⚖️ **合规意识**: 强调法律法规和交易伦理的重要性
- 🎓 **教育导向**: 面向学习和研究，而非商业交易系统

## 核心模块

### 🧠 交易哲学 (trading-philosophies)
实现各种交易哲学的代码化表达：
- **趋势跟踪**: 顺势而为，让利润奔跑
- **均值回归**: 价格终将回归均值
- **波段交易**: 捕捉中期价格波动
- **剥头皮**: 快进快出，积少成多

### 📚 案例研究 (case-studies)
通过真实案例学习交易智慧：
- **传奇交易者**: 杰西·利弗莫尔、乔治·索罗斯、沃伦·巴菲特等
- **市场事件**: 1987黑色星期一、2008金融危机、2020疫情崩盘等
- **策略回测**: 成功策略的历史表现分析

### 💭 交易心理 (trading-psychology)
量化和评估交易心理因素：
- **情绪控制**: 评估恐惧、贪婪、焦虑等情绪状态
- **认知偏差**: 检测过度自信、确认偏差、损失厌恶等
- **纪律性**: 评估策略执行的一致性

### ⚖️ 法律合规 (legal-compliance)
提供交易法律法规知识：
- **监管法规**: 中国证监会、SEC、CFTC等监管要求
- **市场规则**: 交易时间、持仓限制、保证金要求
- **合规检查**: 自动检查交易是否符合监管要求

## 项目架构

```
Trading Philosophy Lab
├── 核心基础层 (Foundation)
│   ├── core-model              # 核心数据模型
│   └── core-common             # 通用工具类
│
├── 哲学层 (Philosophy)
│   └── trading-philosophies    # 交易哲学实现
│
├── 战法层 (Tactics)
│   ├── strategy-engine         # 策略引擎
│   ├── indicators              # 技术指标
│   └── strategies-classic      # 经典战法
│
├── 案例层 (Cases)
│   └── case-studies            # 案例研究
│
├── 心理层 (Psychology)
│   └── trading-psychology      # 交易心理学
│
├── 法律层 (Legal)
│   └── legal-compliance        # 法律合规
│
└── 模拟层 (Simulation)
    ├── backtest-engine         # 回测引擎
    └── data-provider           # 数据提供者
```

## 快速开始

### 环境要求

- Java 17 或更高版本
- Maven 3.6+

### 构建项目

```bash
# 克隆项目
git clone https://github.com/yourusername/wfinance-trading-strategies.git
cd wfinance-trading-strategies

# 编译项目
mvn clean install
```

### 使用示例

#### 1. 评估交易心理

```java
import com.wfinance.psychology.*;

TradingPsychologyEvaluator evaluator = new TradingPsychologyEvaluator();

// 评估情绪状态
EmotionalState state = evaluator.assessEmotionalState(recentTrades);
if (!state.isSuitableForTrading()) {
    System.out.println("当前情绪不适合交易: " + state.getDominantEmotion());
    System.out.println("情绪风险评分: " + state.getEmotionalRiskScore());
}

// 检测认知偏差
List<BiasDetection> biases = evaluator.detectBiases(tradingHistory);
for (BiasDetection bias : biases) {
    System.out.println("检测到偏差: " + bias.getBias().getChineseName());
    System.out.println("证据: " + bias.getEvidence());
}
```

#### 2. 合规检查

```java
import com.wfinance.legal.*;

ComplianceChecker checker = new ComplianceChecker(regulations);

// 检查订单是否合规
ComplianceResult result = checker.checkOrder(order, "CN_STOCK");
if (!result.isCompliant()) {
    System.out.println("订单不合规: " + result.getSummary());
    for (String violation : result.getViolations()) {
        System.out.println("- " + violation);
    }
}
```

#### 3. 案例分析

```java
import com.wfinance.cases.legendary.*;

LegendaryTraderCase jesseCase = new LegendaryTraderCase();
jesseCase.setTraderName("Jesse Livermore");
jesseCase.setCorePhilosophy("趋势跟踪，顺势而为");
jesseCase.addKeyLesson("永远不要与趋势作对");
jesseCase.addKeyLesson("及时止损，让利润奔跑");

CaseAnalysis analysis = jesseCase.analyze();
System.out.println("核心哲学: " + analysis.getPhilosophies());
System.out.println("关键教训: " + analysis.getRecommendations());
```

## 设计原则

1. **哲学驱动**: 每个策略都基于明确的交易哲学
2. **心理感知**: 考虑人性和心理因素的影响
3. **案例导向**: 通过真实案例验证理论
4. **合规优先**: 始终遵守法律法规和交易伦理
5. **教育为本**: 代码清晰易懂，注重教学价值

## 项目特色

### 与传统量化框架的区别

| 维度 | 传统量化框架 | Trading Philosophy Lab |
|------|-------------|------------------------|
| 关注点 | 技术实现、性能优化 | 交易哲学、心理、伦理 |
| 目标 | 构建交易系统 | 教育和研究 |
| 内容 | 技术指标、回测引擎 | 哲学、心理、案例、法律 |
| 用户 | 量化交易员 | 交易学习者、研究者 |

## 已实现功能

### ✅ 核心模块
- [x] 交易哲学框架（趋势跟踪、均值回归等）
- [x] 交易心理评估（情绪状态、认知偏差）
- [x] 法律合规检查（监管法规、合规验证）
- [x] 案例研究框架（传奇交易者、市场事件）
- [x] 技术指标库（SMA、EMA、MACD、RSI等）
- [x] 回测引擎（历史数据模拟）

### 🚧 待完善
- [ ] 更多传奇交易者案例
- [ ] 重大市场事件详细分析
- [ ] 交易心理训练模块
- [ ] 各国法规数据库完善
- [ ] 交易哲学文档和教程

## 贡献指南

欢迎贡献以下内容：

- 📖 **新的交易哲学实现**: 分享你理解的交易哲学
- 📚 **历史案例研究**: 添加传奇交易者或市场事件案例
- 🧠 **心理学模型改进**: 完善交易心理评估算法
- ⚖️ **法规数据更新**: 补充各国监管法规信息
- 📝 **文档完善**: 改进教程和说明文档

### 贡献流程

1. Fork 本项目
2. 创建特性分支 (`git checkout -b feature/NewPhilosophy`)
3. 提交更改 (`git commit -m 'Add new trading philosophy'`)
4. 推送到分支 (`git push origin feature/NewPhilosophy`)
5. 开启 Pull Request

## 学习资源

### 推荐书籍
- 《股票作手回忆录》- 杰西·利弗莫尔
- 《金融炼金术》- 乔治·索罗斯
- 《海龟交易法则》- 柯蒂斯·费思
- 《交易心理分析》- 马克·道格拉斯

### 相关项目
- 详见 [REFACTORING_PLAN.md](REFACTORING_PLAN.md) 了解重构详情

## 许可证

本项目仅用于教育和研究目的。

## 联系方式

- 问题反馈: 通过 GitHub Issues 提交

---

**Trading Philosophy Lab** - 用代码探索交易的本质

⚠️ **再次提醒：交易有风险，投资需谨慎。本项目仅供学习研究使用，不构成任何投资建议。**
