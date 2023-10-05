<template>
  <!-- <BoardCommentForm :bno="slug" @add-comment="addComment" />

  <BoardComment
    :comment="comment"
    @delete="delComment(comment.commentId)"
    v-for="comment in comments"
    :key="comment.commentId" /> -->


  <div class="container mt-5 mb-5">
    <div class="row height d-flex justify-content-center align-items-center">
      <div class="comment-card">
        <div class="p-1">
          <h4>댓글</h4>
        </div>
        <div class="mt-2">
          <div class="d-flex flex-row p-1">
            <div class="w-10">
              <div class="d-flex justify-content-between align-items-center">
                <div class="d-flex flex-row align-items-center">
                  <span class="fs-3">Brian selter</span>
                </div>
                <small>12h ago</small>
              </div>
              <p class="text-justify comment-text mb-2">Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do
                eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>

  <div class="mb-5">
    <BoardCommentForm :bno="slug" @add-comment="addComment" />
  </div>
</template>

<script lang="ts">
export default {
  name: 'ArticleComments',
}
</script>

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

<style scoped>
body {
  background-color: #eee;
}

.comment-card {
  border-radius: 3rem;
  border-bottom: #495057;
  border-width: 20px;
}

.form-color {

  background-color: #1ab1aa;

}

.form-control {
  height: 48px;
  border-radius: 25px;
}

.form-control:focus {
  color: #495057;
  background-color: #fff;
  border-color: #35b69f;
  outline: 0;
  box-shadow: none;
  text-indent: 10px;
}

.c-badge {
  background-color: #35b69f;
  color: white;
  height: 20px;
  font-size: 11px;
  width: 92px;
  border-radius: 5px;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 2px;
}

.comment-text {
  font-size: 18px;
}

.wish {

  color: #35b69f;
}


.user-feed {

  font-size: 14px;
  margin-top: 12px;
}
</style>