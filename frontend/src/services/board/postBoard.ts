import { request } from '../index'

interface PostBoardForm {
  title: string
  content: string
}

export async function putBoard(
  slug: string,
  form: PostBoardForm
): Promise<Board> {
  return request
    .put<Board>(`/boards/${slug}`, form)
}

export async function postBoard(form: PostBoardForm): Promise<Board> {
  return request
    .post<Board>(`/boards`, form)
}
