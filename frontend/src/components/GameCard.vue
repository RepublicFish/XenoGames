<template>
  <div class="game-card" @click="$router.push(`/games/${game.id}`)">
    <div class="card-cover">
      <img
        v-if="game.coverImageUrl"
        :src="game.coverImageUrl"
        :alt="game.gameTitle"
        class="cover-image"
      />
      <div v-else class="cover-placeholder">
        <el-icon :size="48"><PictureFilled /></el-icon>
      </div>
      <el-tag
        class="status-badge"
        :type="statusType"
        size="small"
        effect="dark"
      >
        {{ statusLabel }}
      </el-tag>
      <div class="delete-btn" @click.stop="$emit('delete', game)">
        <el-icon :size="16"><Close /></el-icon>
      </div>
    </div>
    <div class="card-body">
      <h3 class="card-title">{{ game.gameTitle }}</h3>
      <div class="card-meta">
        <span class="platform">{{ game.platform }}</span>
        <span v-if="game.rating" class="rating">
          <el-icon color="#f7ba2a"><StarFilled /></el-icon>
          {{ game.rating }}/10
        </span>
      </div>
      <div v-if="game.tags?.length" class="card-tags">
        <el-tag
          v-for="tag in game.tags"
          :key="tag.id"
          :color="tag.color"
          size="small"
          effect="plain"
          class="tag-chip"
        >
          {{ tag.name }}
        </el-tag>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { Close } from '@element-plus/icons-vue'

const props = defineProps({
  game: { type: Object, required: true }
})

defineEmits(['delete'])

const statusLabel = computed(() => {
  const map = { PLAYING: '游玩中', COMPLETED: '已通关', ABANDONED: '已弃坑' }
  return map[props.game.status] || props.game.status
})

const statusType = computed(() => {
  const map = { PLAYING: 'warning', COMPLETED: 'success', ABANDONED: 'danger' }
  return map[props.game.status] || 'info'
})
</script>

<style scoped>
.game-card {
  background: #fff;
  border-radius: 12px;
  overflow: hidden;
  cursor: pointer;
  transition: transform 0.2s, box-shadow 0.2s;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
}
.game-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.12);
}

.card-cover {
  position: relative;
  width: 100%;
  height: 180px;
  overflow: hidden;
  background: #f0f2f5;
}
.cover-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
}
.cover-placeholder {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #c0c4cc;
}

.status-badge {
  position: absolute;
  top: 8px;
  right: 8px;
}

.delete-btn {
  position: absolute;
  top: 8px;
  left: 8px;
  width: 28px;
  height: 28px;
  border-radius: 50%;
  background: rgba(0, 0, 0, 0.45);
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
  cursor: pointer;
  opacity: 0;
  transition: opacity 0.2s;
}
.game-card:hover .delete-btn {
  opacity: 1;
}
.delete-btn:hover {
  background: #f56c6c;
}

.card-body {
  padding: 14px 16px;
}
.card-title {
  font-size: 16px;
  font-weight: 600;
  color: #303133;
  margin-bottom: 8px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
.card-meta {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 13px;
  color: #909399;
  margin-bottom: 8px;
}
.rating {
  display: flex;
  align-items: center;
  gap: 4px;
  color: #e6a23c;
  font-weight: 500;
}
.card-tags {
  display: flex;
  flex-wrap: wrap;
  gap: 4px;
}
.tag-chip {
  border-radius: 4px;
}
</style>
