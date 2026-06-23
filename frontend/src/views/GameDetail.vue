<template>
  <div v-loading="loading" class="game-detail-page">
    <template v-if="game">
      <el-page-header @back="$router.push('/')" :title="'返回列表'" />

      <div class="detail-layout">
        <!-- 左侧: 封面 + 截图 -->
        <div class="detail-left">
          <div class="cover-section">
            <img
              v-if="game.coverImageUrl"
              :src="game.coverImageUrl"
              :alt="game.gameTitle"
              class="cover-large"
            />
            <div v-else class="cover-large-placeholder">
              <el-icon :size="64"><PictureFilled /></el-icon>
            </div>
          </div>

          <div v-if="game.screenshots?.length" class="screenshots-section">
            <h3 class="section-title">游戏截图</h3>
            <div class="screenshot-grid">
              <el-image
                v-for="ss in game.screenshots"
                :key="ss.id"
                :src="ss.imageUrl"
                :preview-src-list="[ss.imageUrl]"
                fit="cover"
                class="screenshot-thumb"
              />
            </div>
          </div>
        </div>

        <!-- 右侧: 信息 -->
        <div class="detail-right">
          <div class="info-header">
            <h1 class="game-title">{{ game.gameTitle }}</h1>
            <div class="info-badges">
              <el-tag :type="statusType" size="large">{{ statusLabel }}</el-tag>
              <el-tag type="info" size="large">{{ game.platform }}</el-tag>
            </div>
          </div>

          <div v-if="game.tags?.length" class="info-tags">
            <el-tag
              v-for="tag in game.tags"
              :key="tag.id"
              :color="tag.color"
              effect="plain"
              class="tag-chip"
            >
              {{ tag.name }}
            </el-tag>
          </div>

          <div v-if="game.rating" class="info-rating">
            <el-rate :model-value="game.rating" :max="10" disabled show-score />
          </div>

          <div class="info-dates">
            <span v-if="game.startDate">
              <el-icon><Calendar /></el-icon> 开始: {{ game.startDate }}
            </span>
            <span v-if="game.completionDate" style="margin-left: 20px">
              <el-icon><Check /></el-icon> 通关: {{ game.completionDate }}
            </span>
          </div>

          <div v-if="game.gameplayExperience" class="info-section">
            <h3 class="section-title">游玩感受</h3>
            <div class="md-body" v-html="renderedGameplay"></div>
          </div>

          <div v-if="game.storyExperience" class="info-section">
            <h3 class="section-title">通关感受</h3>
            <div class="md-body" v-html="renderedStory"></div>
          </div>

          <div class="action-buttons">
            <el-button type="primary" @click="$router.push(`/games/${game.id}/edit`)">
              <el-icon><Edit /></el-icon> 编辑
            </el-button>
            <el-button type="danger" @click="onDelete">
              <el-icon><Delete /></el-icon> 删除
            </el-button>
          </div>
        </div>
      </div>
    </template>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessageBox, ElMessage } from 'element-plus'
import { Edit, Delete, Calendar, Check } from '@element-plus/icons-vue'
import { useGameStore } from '@/stores/gameStore'

const route = useRoute()
const router = useRouter()
const gameStore = useGameStore()

const game = computed(() => gameStore.currentGame)
const loading = ref(false)

const statusLabel = computed(() => {
  const map = { PLAYING: '游玩中', COMPLETED: '已通关', ABANDONED: '已弃坑' }
  return map[game.value?.status] || ''
})
const statusType = computed(() => {
  const map = { PLAYING: 'warning', COMPLETED: 'success', ABANDONED: 'danger' }
  return map[game.value?.status] || 'info'
})

// 简单 Markdown 渲染（后续可替换为专业渲染库）
const renderedGameplay = computed(() => {
  return game.value?.gameplayExperience?.replace(/\n/g, '<br>') || ''
})
const renderedStory = computed(() => {
  return game.value?.storyExperience?.replace(/\n/g, '<br>') || ''
})

onMounted(async () => {
  loading.value = true
  try {
    await gameStore.fetchGameById(route.params.id)
  } finally {
    loading.value = false
  }
})

async function onDelete() {
  try {
    await ElMessageBox.confirm('确定删除这款游戏吗？此操作不可撤销。', '确认删除', {
      type: 'warning',
      confirmButtonText: '删除',
      cancelButtonText: '取消'
    })
    await gameStore.removeGame(game.value.id)
    ElMessage.success('删除成功')
    router.push('/')
  } catch {
    // 用户取消
  }
}
</script>

<style scoped>
.game-detail-page {
  max-width: 1100px;
}

.detail-layout {
  display: flex;
  gap: 32px;
  margin-top: 20px;
}

.detail-left {
  width: 420px;
  flex-shrink: 0;
}
.detail-right {
  flex: 1;
  min-width: 0;
}

.cover-large {
  width: 100%;
  border-radius: 12px;
  object-fit: cover;
  max-height: 320px;
}
.cover-large-placeholder {
  width: 100%;
  height: 260px;
  background: #f0f2f5;
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #c0c4cc;
}

.screenshots-section {
  margin-top: 24px;
}
.screenshot-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 8px;
}
.screenshot-thumb {
  border-radius: 8px;
  height: 100px;
  cursor: pointer;
}

.section-title {
  font-size: 16px;
  font-weight: 600;
  margin-bottom: 12px;
  color: #303133;
}

.info-header {
  margin-bottom: 16px;
}
.game-title {
  font-size: 28px;
  font-weight: 700;
  margin-bottom: 12px;
}
.info-badges {
  display: flex;
  gap: 8px;
}
.info-tags {
  display: flex;
  flex-wrap: wrap;
  gap: 6px;
  margin-bottom: 16px;
}
.tag-chip {
  border-radius: 4px;
}
.info-rating {
  margin-bottom: 16px;
}
.info-dates {
  color: #909399;
  font-size: 14px;
  margin-bottom: 24px;
  display: flex;
  align-items: center;
}
.info-dates .el-icon {
  vertical-align: -2px;
}

.info-section {
  margin-bottom: 24px;
}
.md-body {
  line-height: 1.8;
  color: #606266;
}

.action-buttons {
  display: flex;
  gap: 12px;
  margin-top: 32px;
  padding-top: 24px;
  border-top: 1px solid #ebeef5;
}

@media (max-width: 768px) {
  .detail-layout {
    flex-direction: column;
  }
  .detail-left {
    width: 100%;
  }
}
</style>
