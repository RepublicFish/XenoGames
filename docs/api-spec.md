# XenoGames API 接口规范

## 通用约定

### 基础路径

```
http://localhost:8080/api
```

### 统一响应格式 Result<T>

```json
{
  "code": 200,
  "msg": "success",
  "data": {}
}
```

### 错误码表

| code | 说明 |
|------|------|
| 200 | 成功 |
| 400 | 请求参数错误 |
| 404 | 资源不存在 |
| 500 | 服务器内部错误 |

### 分页响应格式

```json
{
  "code": 200,
  "msg": "success",
  "data": {
    "content": [],
    "totalElements": 100,
    "totalPages": 5,
    "currentPage": 1,
    "pageSize": 20
  }
}
```

---

## 游戏接口 /api/games

### 1. 获取游戏列表（分页+搜索+筛选）

```
GET /api/games
```

**Query 参数**

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| page | int | 否 | 页码，默认 1 |
| size | int | 否 | 每页条数，默认 20 |
| keyword | String | 否 | 搜索关键词（匹配游戏名称） |
| platform | String | 否 | 平台筛选 |
| status | String | 否 | 状态筛选 |
| tagId | Long | 否 | 标签ID筛选 |
| sort | String | 否 | 排序字段，默认 updatedAt |
| direction | String | 否 | 排序方向 ASC/DESC，默认 DESC |

**响应示例**

```json
{
  "code": 200,
  "msg": "success",
  "data": {
    "content": [
      {
        "id": 1,
        "gameTitle": "Elden Ring",
        "platform": "PC",
        "status": "COMPLETED",
        "rating": 10,
        "coverImageUrl": "/uploads/covers/abc.jpg",
        "tags": [
          { "id": 1, "name": "魂系", "color": "#E6A23C" }
        ],
        "startDate": "2024-01-15",
        "completionDate": "2024-03-20",
        "createdAt": "2024-01-15T10:30:00",
        "updatedAt": "2024-03-20T22:00:00"
      }
    ],
    "totalElements": 1,
    "totalPages": 1,
    "currentPage": 1,
    "pageSize": 20
  }
}
```

### 2. 获取游戏详情

```
GET /api/games/{id}
```

**响应示例**

```json
{
  "code": 200,
  "msg": "success",
  "data": {
    "id": 1,
    "gameTitle": "Elden Ring",
    "platform": "PC",
    "status": "COMPLETED",
    "gameplayExperience": "## 操作手感\n\n打击感极佳...",
    "storyExperience": "## 结局感悟\n\n黄金律法的终结...",
    "rating": 10,
    "coverImageUrl": "/uploads/covers/abc.jpg",
    "tags": [
      { "id": 1, "name": "魂系", "color": "#E6A23C" },
      { "id": 2, "name": "开放世界", "color": "#409EFF" }
    ],
    "screenshots": [
      {
        "id": 1,
        "imageUrl": "/uploads/screenshots/def.jpg",
        "caption": "最终BOSS战",
        "sortOrder": 0
      }
    ],
    "startDate": "2024-01-15",
    "completionDate": "2024-03-20",
    "createdAt": "2024-01-15T10:30:00",
    "updatedAt": "2024-03-20T22:00:00"
  }
}
```

### 3. 新增游戏

```
POST /api/games
Content-Type: application/json
```

**请求体**

```json
{
  "gameTitle": "Elden Ring",
  "platform": "PC",
  "status": "PLAYING",
  "gameplayExperience": "## 操作手感\n\n初见大树守卫...",
  "storyExperience": null,
  "rating": null,
  "coverImageUrl": "/uploads/covers/abc.jpg",
  "tagIds": [1, 2],
  "startDate": "2024-01-15"
}
```

### 4. 更新游戏

```
PUT /api/games/{id}
Content-Type: application/json
```

**请求体** (同新增，全量更新)

```json
{
  "gameTitle": "Elden Ring",
  "platform": "PC",
  "status": "COMPLETED",
  "gameplayExperience": "## 操作手感\n\n打击感极佳...",
  "storyExperience": "## 结局感悟\n\n黄金律法的终结...",
  "rating": 10,
  "coverImageUrl": "/uploads/covers/abc.jpg",
  "tagIds": [1, 2],
  "startDate": "2024-01-15",
  "completionDate": "2024-03-20"
}
```

### 5. 删除游戏

```
DELETE /api/games/{id}
```

**响应**

```json
{
  "code": 200,
  "msg": "删除成功",
  "data": null
}
```

---

## 标签接口 /api/tags

### 6. 获取标签列表

```
GET /api/tags
```

### 7. 新增标签

```
POST /api/tags
Content-Type: application/json
```

```json
{
  "name": "魂系",
  "color": "#E6A23C"
}
```

### 8. 删除标签

```
DELETE /api/tags/{id}
```

---

## 文件上传接口 /api/files

### 9. 上传文件

```
POST /api/files/upload
Content-Type: multipart/form-data
```

**表单参数**

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| file | File | 是 | 图片文件 |
| type | String | 是 | cover / screenshot |

**限制**
- 格式: jpg, jpeg, png, webp
- 大小: 单文件 ≤ 10MB

**响应**

```json
{
  "code": 200,
  "msg": "上传成功",
  "data": {
    "url": "/uploads/covers/a1b2c3d4.jpg"
  }
}
```

---

## 截图接口 /api/screenshots

### 10. 新增截图记录

```
POST /api/games/{gameId}/screenshots
Content-Type: application/json
```

```json
{
  "imageUrl": "/uploads/screenshots/def.jpg",
  "caption": "最终BOSS战"
}
```

### 11. 删除截图

```
DELETE /api/screenshots/{id}
```
