<template>
  <BoardPreview
    class="mt-5"
    :board="board"
    v-for="(board, index) in boards"
    :key="index" />
  <div v-if="boards.length === 0">
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
import { ref } from 'vue'
import api from '@/api'

const boards = ref<Board[]>([])
const boardsCount = ref(0)
const page = ref(1)

interface Props{ 
  searchOption: SearchOption | null
}

const props = defineProps<Props>()

const baseOption: BoardsOption = {
  limit: 10,
  offset: (page.value - 1) * 10
}

const fetchBoards = async () => {
  const params: BoardsOption = {
    ...baseOption,
  }

  const response = await api.fetchBoards(params)
  boards.value = response.boards
  boardsCount.value = response.boardsCount
}

const changePage = (index: number) => { page.value = index }

await fetchBoards()

// const {
//   page,
//   boardsCount,
//   changePage,
//   fetchBoards,
//   boards,
// } = useBoards()


</script>
