import { ref, watch, computed } from 'vue'
import { useRoute } from 'vue-router'
import {
  getBoardss,
  getBoardsByFeed,
  getBoardsByAuthor,
} from '../services/board/getBoards'
import { userStore } from '../store/user'
import SearchOption from '@/utils/searchOption'

export function useBoards() {
  const store = userStore()

  const boards = ref<Board[]>([])
  const boardsCount = ref(0)

  async function fetchBoards(): Promise<void> {
    let responsePromise: null | Promise<BoardsResponse> = null

    if (routeName.value === 'global-feed') {
      responsePromise = getBoardss(page.value)
    }

    if (routeName.value === 'feed' && store.user) {
      responsePromise = getBoardsByAuthor(userId.value)
    }

    if (routeName.value === 'profile' && userId.value) {
      responsePromise = getBoardsByAuthor(userId.value, page.value)
    }

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
