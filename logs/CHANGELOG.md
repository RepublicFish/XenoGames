# XenoGames 开发日志

## 2026-06-22

### 已完成
- [x] 项目需求文档 (docs/requirements.md)
- [x] 技术规范文档 (docs/tech-spec.md)
- [x] 设计规范文档 (docs/design-spec.md)
- [x] API 接口规范文档 (docs/api-spec.md)
- [x] CLAUDE.md 指引文件
- [x] **后端项目搭建** — Maven 项目 + 实体 + Repository + DTO + 通用模块
- [x] **包名重构** — `com.gamevault` → `com.xenogames`
- [x] **后端业务逻辑** — 3 Service + 3 Controller
- [x] **前端项目搭建** — Vite + Vue 3 + Element Plus + 路由 + API 层 + Pinia
- [x] **前端页面实现** — 3 页面 + 3 组件
  - GameCard: 封面图(16:10)、状态徽章、平台、评分、标签(缓存命中)
  - SearchBar: 关键词搜索 + 平台/状态/标签下拉筛选，实时通知父组件
  - TagSelector: 多选标签、filterable、下拉时自动加载标签列表
  - GameForm: 新增/编辑共用表单(9字段)、封面拖拽上传、md-editor-v3 Markdown编辑器、状态联动(COMPLETED才显示评分/通关感受/完成日期)
  - GameList: 4列自适应网格、el-pagination分页、el-skeleton加载态、el-empty空状态、浮动+按钮
  - GameDetail: 左右双栏布局、封面大图+截图相册(el-image预览)、Markdown渲染、删除确认弹窗
  - GameFormPage: 编辑模式自动加载已有数据、提交后跳转相应页面

### 待办
- [ ] 联调测试
- [ ] Git 推送
