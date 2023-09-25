import { ref, watch, computed } from 'vue'
import { useRoute } from 'vue-router'
import {
  getBoards,
  getBoardsByFeed,
  getBoardsByAuthor,
} from '../services/board/getBoards'
import { userStore } from '../store/user'

export function useBoards() {
  const store = userStore()

  const boards = ref<Board[]>([])
  const boardsCount = ref(0)

  async function fetchBoards(): Promise<void> {
    let responsePromise: null | Promise<BoardsResponse> = null

    if (routeName.value === 'global-feed') {
      responsePromise = getBoards(page.value)
    }

    if (routeName.value === 'feed' && store.user) {
      responsePromise = getBoardsByFeed(page.value)
    }

    if (routeName.value === 'profile' && username.value) {
      responsePromise = getBoardsByAuthor(username.value, page.value)
    }

    if (responsePromise !== null) {
      const response = await responsePromise
      console.log(response.boards);
      boards.value = response.boards
      boardsCount.value = response.boardsCount
    }
  }

  const page = ref(1)
  async function changePage(value: number) {
    page.value = value
  }
  watch(page, fetchBoards)

  const updateBoard = (index: number, board: Board): void => {
    boards.value[index] = board
  }

  const { username, routeName } = useMetaChange(fetchBoards)

  return {
    page,
    username,
    changePage,
    fetchBoards,
    boards,
    boardsCount,
    updateBoard,
  }
}

function useMetaChange(callbackFunc: () => void) {
  const route = useRoute()

  const routeName = computed(() => route.name)
  const username = computed(() =>
    typeof route.params.username === 'string' ? route.params.username : ''
  )

  watch([routeName, username], () => {
    callbackFunc()
  })

  return { routeName, username }
}
