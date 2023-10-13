<template>
  <div class="container mt-5 mb-3">
    <div class="row height d-flex justify-content-center align-items-center">
      <div class="comment-card">
        <div class="p-1">
          <h4>댓글</h4>
        </div>
        <BoardComment
          :comment="comment"
          @delete="delComment(comment.commentId)"
          v-for="comment in comments"
          :key="comment.commentId" />
      </div>
    </div>
  </div>

  <div class="mb-5">
    <BoardCommentForm :boardId="slug" @add-comment="addComment" />
  </div>
</template>

<script lang="ts" setup>
import { ref } from 'vue'
import { useRoute } from 'vue-router'
import BoardCommentForm from './BoardCommentForm.vue'
import BoardComment from './BoardComment.vue'
import api from '@/api';

const route = useRoute()
const slug = route.params.slug as string

const comments = ref<BoardComment[]>(await api.fetchComments(slug))

const delComment = async (commentId: number) => {
  await api.deleteComment(slug, commentId)
  comments.value = comments.value?.filter((c) => c.commentId !== commentId)
}
const addComment = (newComment: BoardComment) => {
  comments.value?.unshift(newComment)
} 
</script>
