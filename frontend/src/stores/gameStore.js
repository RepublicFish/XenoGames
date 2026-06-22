import { defineStore } from 'pinia'
import { getGames, getGameById, createGame, updateGame, deleteGame } from '@/api/game'

export const useGameStore = defineStore('game', {
  state: () => ({
    // 游戏列表
    games: [],
    total: 0,
    currentPage: 1,
    pageSize: 20,
    loading: false,

    // 当前查看的游戏详情
    currentGame: null,

    // 搜索筛选条件
    filters: {
      keyword: '',
      platform: '',
      status: '',
      tagId: null
    }
  }),

  actions: {
    /**
     * 加载游戏列表
     */
    async fetchGames(page = 1) {
      this.loading = true
      this.currentPage = page
      try {
        const res = await getGames({
          page,
          size: this.pageSize,
          keyword: this.filters.keyword || undefined,
          platform: this.filters.platform || undefined,
          status: this.filters.status || undefined,
          tagId: this.filters.tagId || undefined
        })
        const data = res.data
        this.games = data.content
        this.total = data.totalElements
      } finally {
        this.loading = false
      }
    },

    /**
     * 加载游戏详情
     */
    async fetchGameById(id) {
      const res = await getGameById(id)
      this.currentGame = res.data
      return this.currentGame
    },

    /**
     * 新增游戏
     */
    async addGame(data) {
      await createGame(data)
    },

    /**
     * 更新游戏
     */
    async editGame(id, data) {
      await updateGame(id, data)
    },

    /**
     * 删除游戏
     */
    async removeGame(id) {
      await deleteGame(id)
    },

    /**
     * 设置筛选条件并重新查询
     */
    setFilters(filters) {
      Object.assign(this.filters, filters)
      this.fetchGames(1)
    },

    /**
     * 重置筛选条件
     */
    resetFilters() {
      this.filters = {
        keyword: '',
        platform: '',
        status: '',
        tagId: null
      }
      this.fetchGames(1)
    }
  }
})
