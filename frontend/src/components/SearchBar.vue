<template>
  <div class="search-bar">
    <el-input
      v-model="localFilters.keyword"
      placeholder="搜索游戏名称..."
      clearable
      :prefix-icon="Search"
      class="search-input"
      @input="onChange"
    />
    <el-select
      v-model="localFilters.platform"
      placeholder="平台"
      clearable
      class="filter-select"
      @change="onChange"
    >
      <el-option label="PC" value="PC" />
      <el-option label="PS5" value="PS5" />
      <el-option label="PS4" value="PS4" />
      <el-option label="Switch" value="Switch" />
      <el-option label="Xbox Series X" value="Xbox Series X" />
      <el-option label="Mobile" value="Mobile" />
      <el-option label="Other" value="Other" />
    </el-select>
    <el-select
      v-model="localFilters.status"
      placeholder="状态"
      clearable
      class="filter-select"
      @change="onChange"
    >
      <el-option label="游玩中" value="PLAYING" />
      <el-option label="已通关" value="COMPLETED" />
      <el-option label="已弃坑" value="ABANDONED" />
    </el-select>
    <el-select
      v-model="localFilters.tagId"
      placeholder="标签"
      clearable
      class="filter-select"
      @change="onChange"
    >
      <el-option
        v-for="tag in tags"
        :key="tag.id"
        :label="tag.name"
        :value="tag.id"
      />
    </el-select>
  </div>
</template>

<script setup>
import { reactive, watch } from 'vue'
import { Search } from '@element-plus/icons-vue'

const props = defineProps({
  filters: { type: Object, required: true },
  tags: { type: Array, default: () => [] }
})

const emit = defineEmits(['update:filters'])

const localFilters = reactive({ ...props.filters })

watch(() => props.filters, (val) => {
  Object.assign(localFilters, val)
})

function onChange() {
  emit('update:filters', { ...localFilters })
}
</script>

<style scoped>
.search-bar {
  display: flex;
  gap: 12px;
  margin-bottom: 20px;
  flex-wrap: wrap;
}
.search-input {
  width: 240px;
  flex-shrink: 0;
}
.filter-select {
  width: 140px;
  flex-shrink: 0;
}
</style>
