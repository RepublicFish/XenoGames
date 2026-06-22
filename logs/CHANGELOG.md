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
- [x] **包名重构** — 全部 `com.gamevault` → `com.xenogames`，`GameVaultApplication` → `XenoGamesApplication`
- [x] **后端业务逻辑** — 3 Service + 3 Controller
  - GameService: 分页搜索筛选、CRUD、Entity↔DTO 转换
  - TagService: 标签 CRUD、重名校验
  - FileService: 文件上传（UUID 命名、格式校验、分目录存储）
  - GameController: GET/POST/PUT/DELETE `/api/games`
  - TagController: GET/POST/DELETE `/api/tags`
  - FileController: POST `/api/files/upload`

### 待办
- [ ] Git 提交 + 推送本次变更
- [ ] 前端项目搭建（Vite + 依赖 + 路由 + API 层）
- [ ] 前端页面实现（列表 + 详情 + 表单）
- [ ] 联调测试
