<template>
  <div class="banner">
    <div class="container">
      <h1>{{ board?.title }}</h1>
    </div>
  </div>

  <div class="container page">
    <div class="row article-content">
      <BoardContent :board="board" />
    </div>

    <hr />

    <div class="row">
      <div class="col-xs-12 col-md-8 offset-md-2">
        <suspense>
          <template #default>
            <ArticleComments />
          </template>
          <template #fallback>
            <div class="card">
              <div class="card-block">Loading comments...</div>
            </div>
          </template>
        </suspense>
      </div>
    </div>
  </div>
</template>
<script lang="ts" setup>
import { ref } from 'vue'
import { useRoute } from 'vue-router'
import { getBoardBySlug } from '@/services/board/getBoard'
import BoardContent from './BoardContent.vue'

interface Board {
  bno: string
  id: string
  writerName: string
  title: string
  content: string
  regDate: string
  updateDate: string
}

const route = useRoute()
const slug = route.params.slug as string

const board = ref<Board>(await getBoardBySlug(slug))

const updateBoard = (newBoard: Board) => {
  board.value = newBoard
}
</script>
