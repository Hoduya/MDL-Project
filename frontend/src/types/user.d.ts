/**
 * 유저 관련
 */
declare interface User {
  userId: number
  email: string
  name: string
  deptId: number
  deptName: string
  regDate: string
  password?: string
}

declare interface Profile {
  userId: number
  name: string
}

declare interface Token {
  accessToken: string
  refreshToken: string
}

declare interface Department {
  deptId: number
  name: string
}

/**
 * 계정 관련 폼
 */

declare interface PostLoginForm {
  email: string
  password: string
}

declare interface PostRegisterForm {
  email: string
  password: string
  name: string
  deptId: string
}

