<template>
  <div class="tag-selector">
    <el-select
      v-model="selectedIds"
      multiple
      placeholder="选择标签"
      filterable
      allow-create
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
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useTagStore } from '@/stores/tagStore'

const props = defineProps({
  modelValue: { type: Array, default: () => [] }
})

defineEmits(['update:modelValue'])

const tagStore = useTagStore()
const selectedIds = ref([...props.modelValue])

const tags = ref([])

async function onVisibleChange(visible) {
  if (visible) {
    await tagStore.fetchTags()
    tags.value = tagStore.tags
  }
}
</script>

<style scoped>
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
</style>
