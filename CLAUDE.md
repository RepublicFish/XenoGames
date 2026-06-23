# CLAUDE.md - XenoGames 项目 AI 助手指引

## 项目概述

XenoGames 是一个个人单用户游戏记录应用。基于 **Spring Boot 3 + Vue 3 + MySQL** 全栈架构，用于记录游戏通关感受、评分及截图。

---

## 标准文件索引

在开始任何开发工作前，请先阅读对应的标准文件：

| 文件 | 内容 | 何时阅读 |
|------|------|----------|
| [docs/requirements.md](docs/requirements.md) | 需求文档 - 功能需求、用户故事 | 了解"要做什么" |
| [docs/tech-spec.md](docs/tech-spec.md) | 技术规范 - 技术栈、架构、数据库设计 | 后端/前端编码前 |
| [docs/design-spec.md](docs/design-spec.md) | 设计规范 - 色彩、布局、交互规范 | 前端页面/组件开发前 |
| [docs/api-spec.md](docs/api-spec.md) | API 接口规范 - 全部接口定义 | 后端 Controller 或前端 API 层开发前 |
| [logs/CHANGELOG.md](logs/CHANGELOG.md) | 开发日志 - 已完成 + 待办事项 | 每次会话开始时查看进度 |

---

## 工作流程

### 每次开发会话

1. **查看进度**: 先读 [logs/CHANGELOG.md](logs/CHANGELOG.md) 了解当前进度和待办事项
2. **阅读规范**: 根据当前任务类型，阅读对应的标准文件（技术/设计/API）
3. **编码实现**: 严格遵循规范文档中的约定
4. **更新日志**: 会话结束前，在 [logs/CHANGELOG.md](logs/CHANGELOG.md) 中记录本次完成内容和新的待办事项

### 编码原则

- 后端严格遵循 Controller → Service → Repository 三层架构
- 前端使用 Vue 3 Composition API (`<script setup>`)
- 接口统一返回 `Result<T>` 格式
- 异常由全局异常处理器统一拦截，Service 层抛出 `BusinessException`
- 前端状态管理统一使用 Pinia

---

## 关键约定

### 后端

- 包名: `com.xenogames`
- Entity 使用 Lombok (`@Data`, `@NoArgsConstructor`, `@AllArgsConstructor`)
- DTO 使用 `@Valid` 进行参数校验
- JPA 审计: `@CreatedDate` / `@LastModifiedDate`
- 文件上传存储在 `./uploads/` 目录

### 前端

- 统一 Axios 实例 `/src/api/request.js`，baseURL 指向 `http://localhost:18080/api`
- 页面组件放在 `views/`，可复用组件放在 `components/`
- 表单新增和编辑共用同一个组件，通过 props 区分模式

### 数据库

- MySQL 数据库名: `xenogames`
- 字符集: utf8mb4
- JPA 策略: `ddl-auto=update`（开发阶段）

---

## 技术栈速查

| 层级 | 技术 | 关键版本 |
|------|------|----------|
| 后端 | Spring Boot 3 + JPA | Java 17 |
| 前端 | Vue 3 + Vite | Composition API |
| UI | Element Plus | 2.x |
| 状态 | Pinia | 2.x |
| MD编辑器 | md-editor-v3 | 4.x |
| 日期 | @vuepic/vue-datepicker | 8.x |
| DB | MySQL | 8.x |
