import request from '@/utils/request'

/**
 * 获取游戏列表（分页 + 搜索 + 筛选）
 */
export function getGames(params) {
  return request.get('/games', { params })
}

/**
 * 获取游戏详情
 */
export function getGameById(id) {
  return request.get(`/games/${id}`)
}

/**
 * 新增游戏
 */
export function createGame(data) {
  return request.post('/games', data)
}

/**
 * 更新游戏
 */
export function updateGame(id, data) {
  return request.put(`/games/${id}`, data)
}

/**
 * 删除游戏
 */
export function deleteGame(id) {
  return request.delete(`/games/${id}`)
}
