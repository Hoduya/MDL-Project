
/**
 * Data Model
 */
declare interface User {
  userId: number
  email: string
  name: string
  deptId: number
  deptName: string
  regDate: string
  password?: string
}

declare interface Token {
  accessToken: string
  refreshToken: string
}

declare interface Profile {
  userId: number
  name: string
}

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

declare interface BoardComment {
  commentId: number
  boardId: number
  regDate: string
  content: string
  author: User
}

declare interface Department {
  deptId: number
  name: string
}

/**
 * Form Model
 */

declare interface PostLoginForm {
  email: string
  password: string
}

declare interface PostRegisterForm {
  email: string
  password: string
  name: string
  deptId: string
}

declare interface BoardForm {
  title: string,
  content: string
}