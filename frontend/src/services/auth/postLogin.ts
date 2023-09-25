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
    .then(({ data }) => {
      const { user, token } = data;
      user.token = token;
      return user;
    })
    .catch(async (error) => {
      if (error instanceof ValidationError) {
        throw await error.getErrors()
      }
    })
}
