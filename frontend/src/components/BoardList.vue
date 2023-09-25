<template>
  <BoardPreview
    :board="board"
    v-for="(board, index) in boards"
    :key="index"
    @update="(newBoard) => updateBoard(index, newBoard)" />
  <div class="article-preview" v-if="boards.length === 0">
    게시글이 없습니다.
  </div>
  <BoardPagination
    :page="page"
    :count="boardsCount"
    @page-change="changePage" />
</template>

<script lang="ts">
export default {
  name: 'PopularTags',
}
</script>

<script lang="ts" setup>
import BoardPreview from './BoardPreview.vue'
import BoardPagination from './BoardPagination.vue'

import { useBoards } from '../composable/useBoards'

const {
  page,
  boardsCount,
  changePage,
  fetchBoards,
  boards,
  updateBoard,
} = useBoards()

await fetchBoards()
</script>
