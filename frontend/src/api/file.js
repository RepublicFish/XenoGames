import axios from 'axios'

/**
 * 上传文件（封面/截图）
 */
export function uploadFile(file, type) {
  const formData = new FormData()
  formData.append('file', file)
  formData.append('type', type)

  return axios.post('/api/files/upload', formData, {
    headers: { 'Content-Type': 'multipart/form-data' },
    timeout: 30000
  }).then(res => res.data)
}
