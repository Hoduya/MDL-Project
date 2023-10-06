import { limit, request } from '../index'
import SearchOption from '@/utils/searchOption'
export function getBoards(page = 1): Promise<BoardsResponse> {
  const params = { limit, offset: (page - 1) * limit }
  return request.get<BoardsResponse>('/boards', { params })
}

export function getBoardss(page = 1, opt: SearchOption = SearchOption.none, keyword=""): Promise<BoardsResponse> {
  let params: { 
    limit: number; 
    offset: number; 
    searchOption?: string; 
    keyword?: string } = { limit, offset: (page - 1) * limit };

  if (opt !== SearchOption.none || keyword !== null || keyword !== "") {
    params = { ...params, searchOption: SearchOption[opt], keyword: keyword };
  }

  return request.get<BoardsResponse>('/boards', { params });
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
