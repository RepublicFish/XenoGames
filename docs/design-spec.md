# XenoGames 设计规范

## UI 组件库

使用 **Element Plus** 作为首要 UI 组件库，遵循其设计语言和约定。

## 色彩体系

### 主色调

| 用途 | 色值 | 说明 |
|------|------|------|
| 品牌主色 | #409EFF | Element Plus 默认蓝 |
| 成功色 | #67C23A | 已通关状态 |
| 警告色 | #E6A23C | 游玩中状态 |
| 危险色 | #F56C6C | 弃坑状态、删除操作 |
| 信息色 | #909399 | 次要信息 |

### 状态色映射

| 状态 | 颜色 | Element 组件类型 |
|------|------|------------------|
| COMPLETED | #67C23A | el-tag type="success" |
| PLAYING | #E6A23C | el-tag type="warning" |
| ABANDONED | #F56C6C | el-tag type="danger" |

## 布局规范

### 整体布局

- 顶部导航栏（应用名称 + 全局搜索）
- 主内容区（最大宽度 1200px，居中）
- 无侧边栏（单页面应用，通过路由切换）

### 游戏列表 - 卡片网格

- 卡片宽度: 280px
- 间距: 20px
- 使用 CSS Grid 自适应列数
- 断点:
  - ≥1200px: 4 列
  - 900-1199px: 3 列
  - 600-899px: 2 列
  - <600px: 1 列

### 游戏卡片内容

```
┌────────────────────┐
│   封面图片(16:10)   │
├────────────────────┤
│ 游戏名称            │
│ 平台标签 · 状态标签  │
│ ★★★★☆ 8/10        │
└────────────────────┘
```

### 游戏详情布局

- 左侧: 封面大图 + 截图相册
- 右侧: 游戏信息 + 标签 + 感受 + 评分
- 移动端: 上下堆叠

## 表单交互规范

### 游戏表单

- 游戏名称: 必填，文本输入框（el-input）
- 平台: 必填，下拉选择（el-select），选项: PC, PS5, PS4, Switch, Xbox Series X, Xbox One, Mobile, Other
- 游玩状态: 必填，单选按钮组（el-radio-group）
- 游玩感受: 非必填，Markdown 编辑器（md-editor-v3）
- 通关感受: 非必填，Markdown 编辑器，仅 COMPLETED 状态时联动显示
- 评分: 仅 COMPLETED 时必填，评分组件（el-rate），范围 1-10
- 开始日期: 日期选择器（@vuepic/vue-datepicker）
- 完成日期: 日期选择器，仅 COMPLETED 时显示
- 封面图: 上传组件（el-upload），拖拽上传
- 标签: 多选标签（TagSelector 自定义组件）

### 表单验证规则

| 字段 | 规则 |
|------|------|
| gameTitle | 必填，1-255 字符 |
| platform | 必填 |
| status | 必填 |
| rating | 仅 COMPLETED 时必填，1-10 |
| startDate | 非必填 |
| completionDate | 仅 COMPLETED 时非必填 |

### 操作反馈

- 保存成功: el-message "保存成功" + 返回列表
- 删除确认: el-message-box confirm "确定删除？此操作不可撤销"
- 上传中: el-upload 自带进度条
- 网络错误: el-message "网络异常，请重试"

## 响应式设计

### 断点

| 断点 | 宽度 | 适配策略 |
|------|------|----------|
| 大屏 | ≥1200px | 4 列卡片网格 |
| 中屏 | 768-1199px | 2-3 列卡片网格，详情页左右布局 |
| 小屏 | <768px | 1 列卡片，详情页上下堆叠，表单全宽 |

### 移动端适配

- 卡片占满宽度
- 表单标签在上、输入框在下
- 底部固定操作按钮
- 抽屉式筛选面板

## 图标使用

使用 **Element Plus Icons** 内置图标集：

| 场景 | 图标 |
|------|------|
| 新增 | Plus |
| 编辑 | Edit |
| 删除 | Delete |
| 搜索 | Search |
| 返回 | ArrowLeft |
| 上传 | Upload |
| 评分 | Star / StarFilled |
| 平台-PC | Monitor |
| 平台-PS5 | (自定义 SVG) |
| 平台-Switch | (自定义 SVG) |

## 空状态与加载

- 列表为空: el-empty 组件，提示"还没有记录游戏，快去添加吧~"
- 加载中: el-skeleton 骨架屏（卡片列表）/ v-loading 指令（详情页）
- 搜索无结果: el-empty "没有找到匹配的游戏"
