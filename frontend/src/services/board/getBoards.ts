import { limit, request } from '../index'
export function getBoards(page = 1): Promise<BoardsResponse> {
  const params = { limit, offset: (page - 1) * limit }
  return request.get<BoardsResponse>('/boards', { params })
}

export function getBoardsByFeed(page = 1): Promise<BoardsResponse> {
  const params = { limit, offset: (page - 1) * limit }
  return request.get<BoardsResponse>('/boards/feed', { params })
}

export function getBoardsByAuthor(
  authorName: string,
  page = 1
): Promise<BoardsResponse> {
  const params = { author: authorName, limit, offset: (page - 1) * limit }
  return request.get<BoardsResponse>('/boards', { params })
}
