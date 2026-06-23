<template>
  <el-form
    ref="formRef"
    :model="form"
    :rules="rules"
    label-width="80px"
    class="game-form"
  >
    <!-- 第一行: 名称 + 平台 + 状态 -->
    <el-row :gutter="20">
      <el-col :span="12">
        <el-form-item label="游戏名称" prop="gameTitle">
          <el-input v-model="form.gameTitle" placeholder="输入游戏名称" maxlength="255" />
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="平台" prop="platform">
          <el-select v-model="form.platform" placeholder="选择平台">
            <el-option label="PC" value="PC" />
            <el-option label="PlayStation" value="PlayStation" />
            <el-option label="Xbox" value="Xbox" />
            <el-option label="Nintendo Switch" value="Nintendo Switch" />
            <el-option label="多端互通" value="多端互通" />
            <el-option label="模拟器" value="模拟器" />
            <el-option label="Mobile" value="Mobile" />
            <el-option label="Other" value="Other" />
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="状态" prop="status">
          <el-select v-model="form.status" placeholder="选择状态">
            <el-option label="游玩中" value="PLAYING" />
            <el-option label="已通关" value="COMPLETED" />
            <el-option label="已弃坑" value="ABANDONED" />
          </el-select>
        </el-form-item>
      </el-col>
    </el-row>

    <!-- 第二行: 日期 -->
    <el-row :gutter="20">
      <el-col :span="12">
        <el-form-item label="开始日期">
          <VueDatePicker
            v-model="form.startDate"
            :enable-time-picker="false"
            placeholder="选择开始日期"
            model-type="yyyy-MM-dd"
            auto-apply
          />
        </el-form-item>
      </el-col>
      <el-col v-if="form.status === 'COMPLETED'" :span="12">
        <el-form-item label="完成日期">
          <VueDatePicker
            v-model="form.completionDate"
            :enable-time-picker="false"
            placeholder="选择完成日期"
            model-type="yyyy-MM-dd"
            auto-apply
          />
        </el-form-item>
      </el-col>
    </el-row>

    <!-- 第三行: 封面(左) + 标签(右) 并排 -->
    <el-row :gutter="20" style="margin-bottom: 24px">
      <el-col :span="12">
        <el-form-item label="封面图片">
          <div class="cover-uploader">
            <el-upload
              :auto-upload="false"
              :show-file-list="false"
              :on-change="onCoverChange"
              accept="image/jpeg,image/png,image/webp"
              drag
            >
              <img v-if="coverPreview" :src="coverPreview" class="cover-preview" />
              <div v-else class="upload-placeholder">
                <el-icon :size="28"><Plus /></el-icon>
                <span>拖拽或点击上传</span>
              </div>
            </el-upload>
            <el-button
              v-if="coverFile"
              type="primary"
              size="small"
              :loading="uploading"
              @click="uploadCover"
            >
              上传封面
            </el-button>
          </div>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="标签">
          <TagSelector v-model="form.tagIds" />
        </el-form-item>
      </el-col>
    </el-row>

    <!-- 第四行: 游玩感受 -->
    <el-form-item label="游玩感受" style="margin-top: 8px">
      <md-editor
        v-model="form.gameplayExperience"
        :toolbars="toolbars"
        style="height: 520px"
        preview-theme="default"
        language="zh-CN"
        placeholder="记录操作手感、游戏机制等..."
      />
    </el-form-item>

    <!-- 第五行: 通关感受 -->
    <el-form-item v-if="form.status === 'COMPLETED'" label="通关感受" style="margin-top: 8px">
      <md-editor
        v-model="form.storyExperience"
        :toolbars="toolbars"
        style="height: 520px"
        preview-theme="default"
        language="zh-CN"
        placeholder="记录剧情、结局感悟等..."
      />
    </el-form-item>

    <!-- 评分 -->
    <el-form-item
      v-if="form.status === 'COMPLETED'"
      label="评分"
      prop="rating"
    >
      <div class="rating-wrapper">
        <el-rate
          v-model="form.rating"
          :max="10"
          :low-threshold="3"
          :high-threshold="7"
          show-score
          score-template="{value}/10"
        />
      </div>
    </el-form-item>

    <el-form-item>
      <el-button type="primary" :loading="submitting" @click="onSubmit">
        {{ isEdit ? '保存修改' : '添加游戏' }}
      </el-button>
      <el-button @click="$router.push('/')">取消</el-button>
    </el-form-item>
  </el-form>
</template>

<script setup>
import { ref, reactive, watch } from 'vue'
import { ElMessage } from 'element-plus'
import { Plus } from '@element-plus/icons-vue'
import { MdEditor } from 'md-editor-v3'
import 'md-editor-v3/lib/style.css'
import { uploadFile } from '@/api/file'
import TagSelector from './TagSelector.vue'

const props = defineProps({
  initialData: { type: Object, default: () => ({}) },
  isEdit: { type: Boolean, default: false }
})

const emit = defineEmits(['submit'])

const formRef = ref(null)
const submitting = ref(false)
const uploading = ref(false)
const coverFile = ref(null)
const coverPreview = ref('')

const toolbars = ['bold', 'italic', 'underline', 'strikeThrough', 'title', 'sub', 'sup', 'quote', 'unorderedList', 'orderedList', 'codeRow', 'code', 'link', 'image', 'table', 'preview']

const form = reactive({
  gameTitle: '',
  platform: '',
  status: 'PLAYING',
  gameplayExperience: '',
  storyExperience: '',
  rating: null,
  coverImageUrl: '',
  tagIds: [],
  startDate: null,
  completionDate: null
})

const rules = {
  gameTitle: [{ required: true, message: '请输入游戏名称', trigger: 'blur' }],
  platform: [{ required: true, message: '请选择平台', trigger: 'change' }],
  status: [{ required: true, message: '请选择状态', trigger: 'change' }],
  rating: [
    {
      validator: (rule, value, callback) => {
        if (form.status === 'COMPLETED' && !value) {
          callback(new Error('请为已通关的游戏评分'))
        } else if (value && (value < 1 || value > 10)) {
          callback(new Error('评分范围 1-10'))
        } else {
          callback()
        }
      },
      trigger: 'change'
    }
  ]
}

// 监听 initialData 变化（编辑模式下异步加载）
watch(() => props.initialData, (data) => {
  if (!data || !data.gameTitle) return
  Object.assign(form, {
    gameTitle: data.gameTitle || '',
    platform: data.platform || '',
    status: data.status || 'PLAYING',
    gameplayExperience: data.gameplayExperience || '',
    storyExperience: data.storyExperience || '',
    rating: data.rating || null,
    coverImageUrl: data.coverImageUrl || '',
    tagIds: data.tags?.map(t => t.id) || [],
    startDate: data.startDate || null,
    completionDate: data.completionDate || null
  })
  coverFile.value = null
  if (data.coverImageUrl) {
    coverPreview.value = data.coverImageUrl
  }
}, { deep: true, immediate: true })

watch(() => form.status, () => {
  if (form.status !== 'COMPLETED') {
    form.rating = null
    form.completionDate = null
    form.storyExperience = ''
  }
})

function onCoverChange(file) {
  coverFile.value = file.raw
  coverPreview.value = URL.createObjectURL(file.raw)
}

async function uploadCover() {
  if (!coverFile.value) return
  uploading.value = true
  try {
    const res = await uploadFile(coverFile.value, 'cover')
    form.coverImageUrl = res.data.url
    ElMessage.success('封面上传成功')
  } catch {
    // 错误已在拦截器处理
  } finally {
    uploading.value = false
  }
}

async function onSubmit() {
  const valid = await formRef.value.validate().catch(() => false)
  if (!valid) return

  submitting.value = true
  try {
    const payload = {
      gameTitle: form.gameTitle,
      platform: form.platform,
      status: form.status,
      gameplayExperience: form.gameplayExperience || null,
      storyExperience: form.storyExperience || null,
      rating: form.rating || null,
      coverImageUrl: form.coverImageUrl || null,
      tagIds: form.tagIds,
      startDate: form.startDate || null,
      completionDate: form.completionDate || null
    }
    emit('submit', payload)
  } finally {
    submitting.value = false
  }
}
</script>

<style scoped>
.game-form {
  max-width: 960px;
}

/* 封面上传 */
.cover-uploader :deep(.el-upload) {
  border: 1px dashed #d9d9d9;
  border-radius: 8px;
  cursor: pointer;
  width: 200px;
  height: 120px;
}
.cover-uploader :deep(.el-upload:hover) {
  border-color: #409EFF;
}

.cover-preview {
  width: 200px;
  height: 120px;
  object-fit: cover;
  border-radius: 8px;
}

.upload-placeholder {
  width: 200px;
  height: 120px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  color: #c0c4cc;
  font-size: 12px;
  gap: 6px;
}

.rating-wrapper {
  padding-top: 6px;
}
</style>
