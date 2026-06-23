<template>
  <div class="game-form-page">
    <el-page-header :title="'返回列表'" @back="$router.push('/')" />

    <h1 class="page-title">{{ isEdit ? '编辑游戏' : '添加新游戏' }}</h1>

    <GameForm
      :initial-data="initialData"
      :is-edit="isEdit"
      @submit="onSubmit"
    />
  </div>
</template>

<script setup>
import { computed, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { useGameStore } from '@/stores/gameStore'
import GameForm from '@/components/GameForm.vue'

const route = useRoute()
const router = useRouter()
const gameStore = useGameStore()

const isEdit = computed(() => route.params.id !== undefined)

const initialData = ref({})

// 编辑模式: 加载已有数据
if (isEdit.value) {
  gameStore.fetchGameById(route.params.id).then(game => {
    initialData.value = game
  })
}

async function onSubmit(formData) {
  try {
    if (isEdit.value) {
      await gameStore.editGame(route.params.id, formData)
      ElMessage.success('修改成功')
      router.push(`/games/${route.params.id}`)
    } else {
      await gameStore.addGame(formData)
      ElMessage.success('添加成功')
      router.push('/')
    }
  } catch {
    // 错误已在拦截器处理
  }
}
</script>

<style scoped>
.game-form-page {
  max-width: 960px;
}

.page-title {
  font-size: 24px;
  font-weight: 700;
  color: #303133;
  margin: 20px 0 32px;
}
</style>
