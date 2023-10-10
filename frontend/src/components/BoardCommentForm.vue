<template>
  <form v-if="store.user" class="card comment-form">
    <div class="card-block">
      <textarea
        v-model="comment"
        class="form-control"
        placeholder="댓글 입력..."
        rows="3" />
    </div>
    <div class="card-footer d-flex justify-content-end">
      <button class="btn btn-primary" @click.prevent="addComment" v-bind:disabled="comment === ''">
        등록
      </button>
    </div>
  </form>
  <p v-else>
    <AppLink name="login">로그인</AppLink> 또는
    <AppLink name="register">회원가입</AppLink> 후에 댓글 작성이 가능합니다.
  </p>
</template>
<script lang="ts" setup>
import { ref, defineProps, defineEmits } from 'vue'
import { postComment } from '@/services/comment/postComment';
import { userStore } from '../store/user'
import AppLink from './AppLink.vue'

interface Props {
  boardId: string
}

interface Emits {
  (e: 'add-Comment', comment: Comment): void
}

const props = defineProps<Props>()
const emit = defineEmits<Emits>()

const store = userStore()

const comment = ref('')

const addComment = async () => {
  const newComment = await postComment(props.boardId, comment.value)
  emit('add-Comment', newComment)
  comment.value = ''
}

</script>
