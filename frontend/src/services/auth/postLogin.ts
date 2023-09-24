import { ValidationError } from '../../types/errors'
import { request } from '../index'

export interface PostLoginForm {
  id: string
  password: string
}

export type PostLoginErrors = Partial<Record<keyof PostLoginForm, string[]>>

export async function postLogin(form: PostLoginForm): Promise<User | any> {
  return request
    .post<UserResponse>('/login', form)
    .then((res) => {
      const user = res.data.user
      user.token = res.data.token
      return user
    })
    .catch(async (error) => {
      if (error instanceof ValidationError) {
        throw await error.getErrors()
      }
    })
}
