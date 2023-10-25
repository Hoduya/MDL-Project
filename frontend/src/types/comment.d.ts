/**
 * 댓글
 */
declare interface BoardComment {
  commentId: number
  boardId: number
  regDate: string
  content: string
  author: User
}