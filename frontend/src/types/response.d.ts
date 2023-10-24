
declare interface User {
  userId: number
  email: string
  name: string
  deptId: number
  deptName: string
  regDate: string
  password?: string
}


declare interface Response<T> {
  data: T
}

declare interface ErrorResponse {
  status: number
  name: number
  code: number
  message: string
}