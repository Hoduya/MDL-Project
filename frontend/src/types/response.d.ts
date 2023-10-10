  /**
  * .d.ts 파일
  *   기존 JavaScript로 만들어진 서드파티 모듈들을 
  *   TypeScript 환경에서도 사용할 수 있도록 따로 타입만 정리해서 넣어둔 파일
  *   해당 파일이 없다면 JS 기반의 라이브러리들은 TS환경에서 타입이 지정되지 않았기 때문에
  *   타입체킹이 제대로 되지 않아서 정상적으로 인식되지 못하는 문제가 발생하게 된다.
  */

  /**
  * declare 
  *   변수, 상수, 함수, 또는 클래스가 어딘가에 이미 선언되어 있음을 알린다.
  *   즉, JS 코드로는 컴파일 되지 않고, TypeScript 컴파일러에게 타입 정보를 알리기만 한다. 
  * 
  * => 해당 인터페이스는 JS 
  */

// declare interface CurrentUserResponse {
//   data: {
//     token: string
//     user: User
//   }
// }

// declare interface BoardsResponse {
//   boards: Board[]
//   boardsCount: number
// }

// declare interface UserResponse {
//   success: string,
//   message: string,
//   data: User
// }

