
/**
 * Data Model
 */
declare interface User {
  userId: string
  email: string
  name: string
  regDate: string
  token?: string
  password?: string
}

declare interface Board {
  boardId: string
  userId: string
  title: string
  content: string
  regDate: string
  updateDate: string
}

declare interface Comment {
  commentId: string
  boardId: string
  userId: string
  regDate: string
  content: string
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
}