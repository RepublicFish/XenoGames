# XenoGames 技术规范

## 技术栈版本

| 层级 | 技术 | 版本 |
|------|------|------|
| 后端语言 | Java | 17 |
| 后端框架 | Spring Boot | 3.x (最新稳定版) |
| ORM | Spring Data JPA (Hibernate) | - |
| 数据库 | MySQL | 8.x |
| 构建工具 | Maven | 3.8+ |
| 前端语言 | JavaScript (ES6+) | - |
| 前端框架 | Vue 3 (Composition API) | 3.4+ |
| 构建工具 | Vite | 5.x |
| UI 组件库 | Element Plus | 2.x |
| 状态管理 | Pinia | 2.x |
| 路由 | Vue Router | 4.x |
| HTTP 客户端 | Axios | 1.x |
| Markdown 编辑器 | md-editor-v3 | 4.x |
| 日期选择器 | @vuepic/vue-datepicker | 8.x |

## 后端架构规范

### 包结构

```
com.xenogames
├── XenoGamesApplication.java          # 启动类
├── common/                             # 通用模块
│   ├── Result.java                     # 统一响应体
│   ├── GlobalExceptionHandler.java     # 全局异常处理器
│   └── exception/
│       └── BusinessException.java      # 业务异常
├── config/                             # 配置类
│   ├── CorsConfig.java                 # 跨域配置
│   ├── JpaAuditingConfig.java          # JPA 审计配置
│   └── WebMvcConfig.java               # 静态资源配置
├── entity/                             # 实体层
│   ├── Game.java
│   ├── Tag.java
│   └── Screenshot.java
├── dto/                                # 数据传输对象
│   ├── GameCreateDTO.java
│   ├── GameUpdateDTO.java
│   ├── GameQueryDTO.java
│   └── GameVO.java
├── repository/                         # 数据访问层
│   ├── GameRepository.java
│   ├── TagRepository.java
│   └── ScreenshotRepository.java
├── service/                            # 业务逻辑层
│   ├── GameService.java
│   ├── TagService.java
│   └── FileService.java
└── controller/                         # 控制层
    ├── GameController.java
    ├── TagController.java
    └── FileController.java
```

### 命名规范

| 类型 | 规范 | 示例 |
|------|------|------|
| Entity | 单数名词 | Game, Tag, Screenshot |
| DTO | 名词 + 操作后缀 | GameCreateDTO, GameVO |
| Repository | Entity + Repository | GameRepository |
| Service | Entity + Service | GameService |
| Controller | Entity + Controller | GameController |
| 方法 | 动词开头，camelCase | findGamesByStatus() |

### 三层职责

- **Controller**: 接收 HTTP 请求，参数校验（@Valid），调用 Service，返回 Result<T>
- **Service**: 业务逻辑处理，事务管理（@Transactional），DTO ↔ Entity 转换
- **Repository**: 数据持久化，继承 JpaRepository，自定义查询方法

### 数据库设计

#### game 表
| 列名 | 类型 | 约束 | 说明 |
|------|------|------|------|
| id | BIGINT | PK, AUTO_INCREMENT | 主键 |
| game_title | VARCHAR(255) | NOT NULL | 游戏名称 |
| platform | VARCHAR(50) | NOT NULL | 平台 |
| status | VARCHAR(20) | NOT NULL | PLAYING/COMPLETED/ABANDONED |
| gameplay_experience | TEXT | - | 游玩感受(Markdown) |
| story_experience | TEXT | - | 通关感受(Markdown) |
| rating | INT | 1-10 | 评分(仅COMPLETED) |
| cover_image_url | VARCHAR(500) | - | 封面图路径 |
| start_date | DATE | - | 开始时间 |
| completion_date | DATE | - | 完成时间 |
| created_at | DATETIME | NOT NULL | 创建时间 |
| updated_at | DATETIME | NOT NULL | 更新时间 |

#### tag 表
| 列名 | 类型 | 约束 | 说明 |
|------|------|------|------|
| id | BIGINT | PK, AUTO_INCREMENT | 主键 |
| name | VARCHAR(50) | NOT NULL, UNIQUE | 标签名 |
| color | VARCHAR(7) | - | 颜色(#RRGGBB) |

#### game_tag 表（多对多关联）
| 列名 | 类型 | 约束 | 说明 |
|------|------|------|------|
| game_id | BIGINT | FK → game.id | 游戏ID |
| tag_id | BIGINT | FK → tag.id | 标签ID |

#### screenshot 表
| 列名 | 类型 | 约束 | 说明 |
|------|------|------|------|
| id | BIGINT | PK, AUTO_INCREMENT | 主键 |
| image_url | VARCHAR(500) | NOT NULL | 截图路径 |
| caption | VARCHAR(200) | - | 截图说明 |
| sort_order | INT | DEFAULT 0 | 排序 |
| game_id | BIGINT | FK → game.id | 所属游戏 |

### 文件存储规范

- 上传目录: `./uploads/`
- 子目录: `covers/` (封面), `screenshots/` (截图)
- 命名规则: `{uuid}.{原始扩展名}` 防止冲突
- 访问方式: 通过 `/uploads/**` 静态资源映射

## 前端架构规范

### 目录结构

```
src/
├── api/                  # API 请求层
│   ├── request.js        # Axios 实例(基础URL、拦截器)
│   ├── game.js           # 游戏相关接口
│   ├── tag.js            # 标签相关接口
│   └── file.js           # 文件上传接口
├── components/           # 可复用组件
│   ├── GameCard.vue      # 游戏卡片
│   ├── GameForm.vue      # 游戏表单(新增/编辑共用)
│   ├── ScreenshotUploader.vue  # 截图上传组件
│   ├── TagSelector.vue   # 标签选择器
│   └── SearchBar.vue     # 搜索筛选栏
├── views/                # 页面视图
│   ├── GameList.vue      # 首页-游戏列表
│   ├── GameDetail.vue    # 游戏详情
│   └── GameFormPage.vue  # 新增/编辑游戏页面
├── stores/               # Pinia 状态管理
│   ├── gameStore.js      # 游戏状态
│   └── tagStore.js       # 标签状态
├── router/               # 路由配置
│   └── index.js
├── utils/                # 工具函数
│   └── index.js
├── App.vue               # 根组件
└── main.js               # 入口文件
```

### 组件设计原则

- 页面 (views/) 负责布局和数据获取，通过 Pinia store 管理状态
- 组件 (components/) 纯展示 + 事件抛出，父组件处理业务逻辑
- 表单组件复用：新增和编辑共用同一个 GameForm 组件

### 状态管理 (Pinia)

- **gameStore**: 游戏列表、当前游戏、分页信息、搜索筛选条件
- **tagStore**: 标签列表（全局缓存）

### 路由设计

| 路径 | 视图 | 说明 |
|------|------|------|
| / | GameList | 首页 |
| /games/:id | GameDetail | 详情 |
| /games/new | GameFormPage | 新增 |
| /games/:id/edit | GameFormPage | 编辑 |
