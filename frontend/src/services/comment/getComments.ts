import { request } from '../index'

export function getCommentsByBno(bno: string): Promise<Comment[]> {
  return request
    .get<Comment[]>(`/boards/${bno}/comments`);
}