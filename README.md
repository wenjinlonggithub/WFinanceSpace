# WFinance Trading Strategies

> 开源金融交易策略框架 - 涵盖所有市场和策略的完整交易系统

## ⚠️ 重要风险提示

**本项目仅供教育和研究目的使用。**

- 金融市场交易存在重大风险，可能导致全部资金损失
- 历史表现不代表未来结果
- 大多数零售交易者长期亏损
- 使用本项目进行实盘交易需自行承担所有风险
- 作者不对任何交易损失负责

## 项目简介

WFinance Trading Strategies 是一个全面的、模块化的金融交易策略框架，使用 Java 17 开发。本项目旨在：

- 📚 提供金融交易策略的教育资源
- 🔬 支持量化交易研究和回测
- 🛠️ 构建可扩展的交易系统架构
- 🌍 覆盖多个市场：股票、期货、外汇、加密货币
- 📊 实现经典和现代交易策略

## 核心特性

### 多市场支持
- ✅ 股票市场（A股、港股、美股）
- ✅ 期货市场（商品期货、股指期货）
- ✅ 外汇市场（主要货币对）
- ✅ 加密货币市场

### 技术指标库
- 趋势指标：SMA、EMA、MACD
- 动量指标：RSI、Stochastic
- 波动率指标：Bollinger Bands、ATR
- 成交量指标：OBV、VWAP

### 策略类型
- 经典技术分析策略
- 量化交易策略
- 基本面分析策略
- 多因子策略

### 回测引擎
- 完整的历史数据回测
- 真实的交易成本模拟（手续费、滑点）
- 详细的绩效分析报告
- 风险管理和仓位控制

## 项目架构

```
wfinance-trading-strategies/
├── core-model/              # 核心数据模型
├── core-common/             # 通用工具类
├── data-provider/           # 数据提供者接口
│   ├── data-provider-stock/     # 股票数据
│   ├── data-provider-futures/   # 期货数据
│   ├── data-provider-forex/     # 外汇数据
│   └── data-provider-crypto/    # 加密货币数据
├── strategy-engine/         # 策略执行引擎
├── indicators/              # 技术指标库
├── strategies-classic/      # 经典策略
├── strategies-quantitative/ # 量化策略
├── strategies-fundamental/  # 基本面策略
├── backtest-engine/         # 回测引擎
├── risk-management/         # 风险管理
├── order-execution/         # 订单执行
├── performance-analysis/    # 绩效分析
└── visualization/           # 数据可视化
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

#### 1. 计算技术指标

```java
import com.wfinance.core.model.Bar;
import com.wfinance.indicators.trend.SMA;
import com.wfinance.indicators.momentum.RSI;

// 创建指标
SMA sma20 = new SMA(20);
RSI rsi14 = new RSI(14);

// 计算指标值
List<Bar> bars = // 获取K线数据
List<BigDecimal> smaValues = sma20.calculate(bars);
List<BigDecimal> rsiValues = rsi14.calculate(bars);
```

#### 2. 实现简单策略

```java
// 示例：双均线交叉策略
SMA fastMA = new SMA(10);
SMA slowMA = new SMA(20);

List<BigDecimal> fastValues = fastMA.calculate(bars);
List<BigDecimal> slowValues = slowMA.calculate(bars);

// 金叉信号：快线上穿慢线
if (fastValues.get(i) > slowValues.get(i) &&
    fastValues.get(i-1) <= slowValues.get(i-1)) {
    // 买入信号
}
```

## 已实现的技术指标

### 趋势指标
- **SMA** (Simple Moving Average) - 简单移动平均线
- **EMA** (Exponential Moving Average) - 指数移动平均线
- **MACD** (Moving Average Convergence Divergence) - 移动平均收敛散度

### 动量指标
- **RSI** (Relative Strength Index) - 相对强弱指数

### 波动率指标
- **Bollinger Bands** - 布林带

## 开发路线图

- [x] 核心数据模型
- [x] 技术指标库（基础指标）
- [ ] 策略执行引擎
- [ ] 回测引擎
- [ ] 经典策略实现
- [ ] 量化策略实现
- [ ] 风险管理模块
- [ ] 绩效分析报告
- [ ] 数据可视化
- [ ] 实时交易接口

## 贡献指南

欢迎贡献代码、报告问题或提出建议！

1. Fork 本项目
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 开启 Pull Request

## 许可证

本项目采用 MIT 许可证 - 详见 [LICENSE](LICENSE) 文件

## 联系方式

- 项目主页: https://github.com/yourusername/wfinance-trading-strategies
- 问题反馈: https://github.com/yourusername/wfinance-trading-strategies/issues

## 致谢

感谢所有为量化交易和开源社区做出贡献的开发者。

---

**再次提醒：交易有风险，投资需谨慎。本项目仅供学习研究使用。**
