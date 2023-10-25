export class ApiErrorBase<T extends number> extends Error {
  status: number
  message: string
  code: T

  constructor({ 
    status, 
    message, 
    code 
  }: {
    status: number
    message: string
    code: T
  }) {
    super()
    this.status = status
    this.message = message
    this.code = code
  }
}