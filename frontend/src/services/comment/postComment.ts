import { request } from '../index'

export function postComment(bno: string, content: string): Promise<Comment> {
  return request
    .post<Comment>(`/boards/${bno}/comments`, {
      content: content
    })
}

export function deleteComment(bno: string, commentId: string): Promise<void> {
  return request.delete(`/boards/${bno}/comments/${commentId}`)
}