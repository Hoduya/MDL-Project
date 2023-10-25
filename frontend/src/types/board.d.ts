/**
 * 게시판
 */

declare interface Board {
  boardId: number
  title: string
  content: string
  regDate: string
  updateDate: string
  author: User
}

declare interface BoardsOption {
  tag?: string
  authorId?: string
  limit: number
  offset: number
  searchOption?: SearchOption
}

declare interface SearchOption {
  searchFilter: number
  searchText: string
}

declare interface UpdateBoard {
  title: string
  content: string
  updateDate: string
}

/**
 * Form Model
 */

declare interface BoardForm {
  title: string,
  content: string
}