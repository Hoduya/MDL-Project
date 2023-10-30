<template>
  <form @submit.prevent="onSubmit">
    <fieldset class="mt-3">
      <fieldset class="form-group mb-3">
        <input
          v-model="formData.title"
          type="text"
          class="form-control form-control-lg"
          placeholder="제목"
          data-test="title" />
      </fieldset>
      <fieldset class="form-group mb-3">
        <textarea
          v-model="formData.content"
          class="form-control"
          rows="8"
          placeholder="내용을 입력해주세요"
          data-test="content"></textarea>
      </fieldset>
    </fieldset>

    <div class="mb-3 d-flex justify-content-center">
      <button class="btn btn-lg btn-secondary me-4" type="button" @click="onCancel">
        취소
      </button>
      <button class="btn btn-lg btn-primary" type="submit">
        완료
      </button>
    </div>
  </form>
</template>
  
<script lang="ts" setup>
import { onMounted, reactive, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { routerPush } from '@/router'
import api from '@/api'
import { useToast } from 'vue-toastification'

const route = useRoute()
const router = useRouter()
const slug = computed(() => route.params.slug as string)
const toast = useToast()

const formData = reactive<BoardForm>({
  title: '',
  content: ''
})

onMounted(async () => {
  if(!slug.value) return
  const board = await api.fetchBoard(slug.value)
  formData.title = board.title
  formData.content = board.content  
})

const onSubmit = async () => {
  try {
    let board = null
    let toastMessage = ""
    if (slug.value) {
      board = await api.updateBoard({ board: formData, slug: slug.value })  
      toastMessage = "게시글 수정 완료"
    } 
    else {
      board = await api.createBoard(formData)
      toastMessage = "게시글 등록 완료"
    }
    toast.success(toastMessage, {
      timeout: 2000
    })
    if (board) await routerPush('board', { slug: board.boardId.toString() })
  } catch (error) {
    console.log(error)
  }
}

const onCancel = async () => {
  router.back()
}
</script>
  