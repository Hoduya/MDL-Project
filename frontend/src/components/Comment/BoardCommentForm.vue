<template>
  <form v-if="userStore.currentUser" class="card comment-form">
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
import AppLink from '@/components/AppLink.vue'
import { ref, defineProps, defineEmits } from 'vue'
import { useUserStore } from '@/store/user'
import api from '@/api';

interface Props {
  boardId: string
}

interface Emits {
  (e: 'add-Comment', comment: BoardComment): void
}

const props = defineProps<Props>()
const emit = defineEmits<Emits>()

const userStore = useUserStore()

const comment = ref('')

const addComment = async () => {
  const newComment = await api.createComment({ 
    slug: props.boardId, 
    content: comment.value })

  emit('add-Comment', newComment)
  comment.value = ''
}

</script>
