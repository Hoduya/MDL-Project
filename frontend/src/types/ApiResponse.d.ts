/**
 * API 응답 모델 (성공 / 에러)
 */

declare interface ApiResponse<T> {
  data: T
}

declare interface ApiErrorResponse {
  status: number
  name: string
  code: number
  message: string
}
