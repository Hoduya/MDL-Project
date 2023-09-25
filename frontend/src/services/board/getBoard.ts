import { request } from '../index'

export async function getBoardBySlug(slug: string): Promise<Board> {
  return request
    .get<Board>(`/boards/${slug}`)
}
