# XenoGames 开发日志

## 2026-06-22

### 已完成
- [x] 项目需求文档 (docs/requirements.md)
- [x] 技术规范文档 (docs/tech-spec.md)
- [x] 设计规范文档 (docs/design-spec.md)
- [x] API 接口规范文档 (docs/api-spec.md)
- [x] CLAUDE.md 指引文件
- [x] 创建开发日志文件
- [x] **后端项目搭建** — Maven 项目 + 配置 + 实体 + Repository + DTO + 通用模块 + 配置类
  - pom.xml（Spring Boot 3.3.0, JPA, MySQL, Lombok, Validation）
  - 3 实体: Game, Tag, Screenshot + GameStatus 枚举
  - 3 Repository: GameRepository(含多条件分页查询), TagRepository, ScreenshotRepository
  - 6 DTO: GameCreateDTO, GameUpdateDTO, GameQueryDTO, GameVO, TagDTO, ScreenshotVO
  - 通用模块: Result<T>, GlobalExceptionHandler, BusinessException
  - 配置: CORS, JPA Auditing, 静态资源映射

### 待办
- [ ] 后端业务逻辑（Service + Controller）
- [ ] 前端项目搭建（Vite + 依赖 + 路由 + API 层）
- [ ] 前端页面实现（列表 + 详情 + 表单）
- [ ] 联调测试
