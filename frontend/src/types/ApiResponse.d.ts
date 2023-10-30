/**
 * API 응답 모델 (에러)
 */

declare interface ApiErrorResponse {
  status: number
  name: string
  code: number
  message: string
}
