// import { request } from '../index'

// export function postComment(boardId: string, content: string): Promise<Comment> {
//   return request
//     .post<Comment>(`/boards/${boardId}/comments`, {
//       content: content
//     })
// }

// export function deleteComment(boardId: string, commentId: string): Promise<void> {
//   return request.delete(`/boards/${boardId}/comments/${commentId}`)
// }