<template>
  <div class="tag-selector">
    <div class="tag-selector-row">
      <el-select
        v-model="selectedIds"
        multiple
        placeholder="选择标签"
        filterable
        style="flex: 1; min-width: 200px"
        @change="$emit('update:modelValue', selectedIds)"
        @visible-change="onVisibleChange"
      >
        <el-option
          v-for="tag in tags"
          :key="tag.id"
          :label="tag.name"
          :value="tag.id"
        >
          <span class="tag-option">
            <span class="tag-dot" :style="{ background: tag.color }"></span>
            {{ tag.name }}
          </span>
        </el-option>
      </el-select>
      <el-popover
        v-model:visible="popVisible"
        :width="220"
        trigger="click"
        placement="bottom"
      >
        <template #reference>
          <el-button size="small" circle>
            <el-icon><Plus /></el-icon>
          </el-button>
        </template>
        <div class="new-tag-form">
          <div class="new-tag-title">创建新标签</div>
          <el-input
            v-model="newTagName"
            placeholder="标签名称"
            size="small"
            maxlength="20"
          />
          <div class="new-tag-colors">
            <span
              v-for="c in presetColors"
              :key="c"
              class="color-dot"
              :class="{ active: newTagColor === c }"
              :style="{ background: c }"
              @click="newTagColor = c"
            />
          </div>
          <el-button
            type="primary"
            size="small"
            :disabled="!newTagName.trim()"
            :loading="creating"
            style="width: 100%; margin-top: 8px"
            @click="onCreateTag"
          >
            创建
          </el-button>
        </div>
      </el-popover>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
import { Plus } from '@element-plus/icons-vue'
import { useTagStore } from '@/stores/tagStore'

const props = defineProps({
  modelValue: { type: Array, default: () => [] }
})

const emit = defineEmits(['update:modelValue'])

const tagStore = useTagStore()
const selectedIds = ref([...props.modelValue])
const tags = ref([])
const popVisible = ref(false)
const newTagName = ref('')
const newTagColor = ref('#409EFF')
const creating = ref(false)

const presetColors = [
  '#409EFF', '#67C23A', '#E6A23C', '#F56C6C',
  '#909399', '#B37FEB', '#FF85C0', '#36CFC9',
  '#FA8C16', '#A0D911', '#2F54EB', '#722ED1'
]

async function onVisibleChange(visible) {
  if (visible) {
    await tagStore.fetchTags()
    tags.value = tagStore.tags
  }
}

async function onCreateTag() {
  const name = newTagName.value.trim()
  if (!name) return
  creating.value = true
  try {
    const res = await tagStore.addTag({ name, color: newTagColor.value })
    // find the newly created tag ID from refreshed list
    await tagStore.fetchTags()
    tags.value = tagStore.tags
    const newTag = tagStore.tags.find(t => t.name === name)
    if (newTag) {
      selectedIds.value = [...selectedIds.value, newTag.id]
      emit('update:modelValue', selectedIds.value)
    }
    newTagName.value = ''
    popVisible.value = false
    ElMessage.success('标签创建成功')
  } catch {
    // error handled by interceptor
  } finally {
    creating.value = false
  }
}
</script>

<style scoped>
.tag-selector-row {
  display: flex;
  gap: 8px;
  align-items: center;
}

.tag-dot {
  display: inline-block;
  width: 10px;
  height: 10px;
  border-radius: 50%;
  margin-right: 6px;
}
.tag-option {
  display: flex;
  align-items: center;
}

.new-tag-form {
  display: flex;
  flex-direction: column;
  gap: 8px;
}
.new-tag-title {
  font-size: 14px;
  font-weight: 600;
  color: #303133;
}
.new-tag-colors {
  display: flex;
  flex-wrap: wrap;
  gap: 6px;
}
.color-dot {
  width: 24px;
  height: 24px;
  border-radius: 50%;
  cursor: pointer;
  border: 2px solid transparent;
  transition: border-color 0.2s;
}
.color-dot.active {
  border-color: #303133;
}
</style>
