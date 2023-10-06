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
    <BoardCommentForm :bno="slug" @add-comment="addComment" />
  </div>
</template>

<script lang="ts" setup>
import { ref } from 'vue'
import { useRoute } from 'vue-router'
import { getCommentsByBno } from '@/services/comment/getComments'
import { deleteComment } from '@/services/comment/postComment'
import BoardCommentForm from './BoardCommentForm.vue'
import BoardComment from './BoardComment.vue'

const route = useRoute()
const slug = route.params.slug as string

const comments = ref<Comment[]>()

const delComment = async (commentId: string) => {
  await deleteComment(slug, commentId)
  comments.value = comments.value?.filter((c) => c.commentId !== commentId)
}

comments.value = await getCommentsByBno(slug)

const addComment = (newComment: Comment) => {
  comments.value?.unshift(newComment)
}
</script>
