import { ref, watch, computed } from 'vue'
import { useRoute } from 'vue-router'
import api from '@/api'
import { useUserStore } from '../store/user'

export function useBoards() {
  const userStore = useUserStore()

  const boards = ref<Board[]>([])
  const boardsCount = ref(0)

  async function fetchBoards(): Promise<void> {
    let responsePromise: null | Promise<{ boards: Board[], boardsCount: number}> = null
    const baseOption: BoardsOption = {
        limit: api.fetchBoardsLimit,
        offset: (page.value - 1) * api.fetchBoardsLimit
    }

    if (routeName.value === 'global-feed') {
      responsePromise = api.fetchBoards(baseOption)
    }

    if (routeName.value === 'feed' && userStore.currentUser) {
      responsePromise = api.fetchBoards(baseOption)    }

    if (routeName.value === 'profile' && userId.value) {
      responsePromise = api.fetchBoards(baseOption)    }

    if (responsePromise !== null) {
      const response = await responsePromise
      console.log(response)
      boards.value = response.boards
      boardsCount.value = response.boardsCount
    }
  }

  const page = ref(1)
  async function changePage(value: number) {
    page.value = value
  }
  watch(page, fetchBoards)

  const { userId, routeName } = useMetaChange(fetchBoards)

  return {
    page,
    userId,
    changePage,
    fetchBoards,
    boards,
    boardsCount,
  }
}

function useMetaChange(callbackFunc: () => void) {
  const route = useRoute()

  const routeName = computed(() => route.name)
  const userId = computed(() =>
    typeof route.params.userId === 'string' ? route.params.userId : ''
  )

  watch([routeName, userId], () => {
    callbackFunc()
  })

  return { routeName, userId }
}
