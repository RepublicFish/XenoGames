import request from '@/utils/request'

/**
 * 获取所有标签
 */
export function getTags() {
  return request.get('/tags')
}

/**
 * 新增标签
 */
export function createTag(data) {
  return request.post('/tags', data)
}

/**
 * 删除标签
 */
export function deleteTag(id) {
  return request.delete(`/tags/${id}`)
}
