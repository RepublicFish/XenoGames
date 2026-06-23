<template>
  <div class="game-list-page">
    <SearchBar :filters="filters" :tags="tagStore.tags" @update:filters="onFiltersChange" />

    <div v-loading="gameStore.loading" class="game-grid">
      <GameCard
        v-for="game in gameStore.games"
        :key="game.id"
        :game="game"
        @delete="onDelete"
      />
    </div>

    <el-empty
      v-if="!gameStore.loading && gameStore.games.length === 0"
      description="还没有记录游戏，快去添加吧~"
    />

    <div v-if="gameStore.total > gameStore.pageSize" class="pagination-wrapper">
      <el-pagination
        v-model:current-page="currentPage"
        :page-size="gameStore.pageSize"
        :total="gameStore.total"
        layout="prev, pager, next"
        background
        @current-change="onPageChange"
      />
    </div>

    <!-- 浮动新增按钮 -->
    <el-button
      type="primary"
      circle
      :size="60"
      class="fab"
      @click="$router.push('/games/new')"
    >
      <el-icon :size="28"><Plus /></el-icon>
    </el-button>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Plus } from '@element-plus/icons-vue'
import { useGameStore } from '@/stores/gameStore'
import { useTagStore } from '@/stores/tagStore'
import GameCard from '@/components/GameCard.vue'
import SearchBar from '@/components/SearchBar.vue'

const gameStore = useGameStore()
const tagStore = useTagStore()

const currentPage = ref(1)

const filters = ref({
  keyword: '',
  platform: '',
  status: '',
  tagId: null
})

onMounted(() => {
  gameStore.fetchGames()
  tagStore.fetchTags()
})

function onFiltersChange(newFilters) {
  filters.value = newFilters
  gameStore.filters = newFilters
  currentPage.value = 1
  gameStore.fetchGames(1)
}

function onPageChange(page) {
  currentPage.value = page
  gameStore.fetchGames(page)
  window.scrollTo({ top: 0, behavior: 'smooth' })
}

async function onDelete(game) {
  try {
    await ElMessageBox.confirm(
      `确定删除「${game.gameTitle}」吗？此操作不可撤销。`,
      '确认删除',
      { type: 'warning', confirmButtonText: '删除', cancelButtonText: '取消' }
    )
    await gameStore.removeGame(game.id)
    ElMessage.success('删除成功')
    gameStore.fetchGames(currentPage.value)
  } catch {
    // 用户取消
  }
}
</script>

<style scoped>
.game-list-page {
  position: relative;
}

.game-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 20px;
  min-height: 200px;
}

@media (max-width: 1199px) {
  .game-grid { grid-template-columns: repeat(3, 1fr); }
}
@media (max-width: 899px) {
  .game-grid { grid-template-columns: repeat(2, 1fr); }
}
@media (max-width: 599px) {
  .game-grid { grid-template-columns: 1fr; }
}

.pagination-wrapper {
  display: flex;
  justify-content: center;
  margin-top: 32px;
}

.fab {
  position: fixed;
  bottom: 40px;
  right: 40px;
  width: 60px;
  height: 60px;
  box-shadow: 0 4px 16px rgba(64, 158, 255, 0.4);
  z-index: 50;
}
</style>
