import { request } from '../index'

export function getCommentsByBoardId(boardId: string): Promise<Comment[]> {
  return request
    .get<Comment[]>(`/boards/${boardId}/comments`);
}