# XenoGames 🎮

个人游戏通关记录应用 —— 记录游戏通关感受、评分及截图。

## 技术栈

| 层级 | 技术 |
|------|------|
| 后端 | Spring Boot 3 + JPA + MySQL |
| 前端 | Vue 3 + Vite + Element Plus |
| 状态管理 | Pinia |
| Markdown | md-editor-v3 |

## 项目结构

```
├── docs/                # 开发规范文档
│   ├── requirements.md  # 需求文档
│   ├── tech-spec.md     # 技术规范
│   ├── design-spec.md   # 设计规范
│   └── api-spec.md      # API 接口规范
├── logs/                # 开发日志
│   └── CHANGELOG.md
├── backend/             # Spring Boot 后端
└── frontend/            # Vue 3 前端（待创建）
```

## 快速开始

### 后端
```bash
cd backend
# 确保 MySQL 已启动，数据库 xenogames 已创建
./mvnw spring-boot:run
```

### 前端
```bash
cd frontend
npm install
npm run dev
```
