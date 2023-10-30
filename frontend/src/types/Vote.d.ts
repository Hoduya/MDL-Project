/**
 * 투표
 * 
 * 0: 미투표, 
 * 1: 구내식당,
 * 2: 외식
 */
type voteState = 0 | 1 | 2 

declare interface Component {
  componentId: number;
  userName: string;
  userId: number;
  deptId: number;
  coordX: number;
  coordY: number;
  voteState: voteState;
}

declare interface UpdateComponent {
  componentId: number;
  coordX: number;
  coordY: number;
  voteState: voteState;
}