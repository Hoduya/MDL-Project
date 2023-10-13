<template>
  <div class="d-flex justify-content-center">
    <BoardSearchBar @text-search="searchEvent" />
  </div>
  <div class="mt-4">
  <BoardPreview
    :board="board"
    v-for="(board, index) in boards"
    :key="index" />
  </div>
  <div v-if="boards.length === 0">
    게시글이 없습니다.
  </div>
  <BoardPagination
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
import BoardSearchBar from './BoardSearchBar.vue'
import { ref, computed, watch } from 'vue'
import { useRoute } from 'vue-router'
import api from '@/api'

const boards = ref<Board[]>([])
const boardsCount = ref(0)
const route = useRoute()

let searchOption: SearchOption | undefined

const userId = computed(() => {
  console.log(route.params.slug)
  return typeof route.params.slug === 'string' ? route.params.slug : ''
})

const baseOption: BoardsOption = {
  limit: 10,
  offset: 0,
}

const fetchBoards = async () => {
  const params: BoardsOption = {
    ...baseOption,
    ...searchOption
  }

  if (userId.value) {
    params.authorId = userId.value
  }

  const response = await api.fetchBoards(params, searchOption)
  boards.value = response.boards
  boardsCount.value = response.boardsCount
}

await fetchBoards()

const changePage = async (index: number) => { 
  baseOption.offset = (index - 1) * 10
  await fetchBoards()
}

const searchEvent = async (option?: SearchOption) => {
  searchOption = option
  await fetchBoards()
}

watch(userId, fetchBoards)

</script>
