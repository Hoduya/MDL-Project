import { ValidationError } from '../../types/errors'
import { request } from '../index'

export interface PostRegisterForm {
  id: string
  password: string
  name: string
}

// 에러 타입
// key: PostRegisterForm의 프로퍼티들의 키값 (email, password, username)
// value: string 배열 타입
export type PostRegisterErrors = Partial<Record<keyof PostRegisterForm, string[]>>

export async function postRegister(form: PostRegisterForm): Promise<User | any> {
  return request
    .post<UserResponse>('/join', form)
    .then((res) => res.data.user)
    .catch(async (error) => {
      if (error instanceof ValidationError) {
        throw await error.getErrors()
      }
    })
}