import { defineStore } from 'pinia'
import { getTags, createTag, deleteTag } from '@/api/tag'

export const useTagStore = defineStore('tag', {
  state: () => ({
    tags: [],
    loading: false
  }),

  actions: {
    /**
     * 加载所有标签
     */
    async fetchTags() {
      this.loading = true
      try {
        const res = await getTags()
        this.tags = res.data
      } finally {
        this.loading = false
      }
    },

    /**
     * 新增标签
     */
    async addTag(data) {
      await createTag(data)
      await this.fetchTags()
    },

    /**
     * 删除标签
     */
    async removeTag(id) {
      await deleteTag(id)
      await this.fetchTags()
    }
  }
})
