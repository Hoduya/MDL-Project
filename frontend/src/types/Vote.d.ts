/**
 * 투표
 */

declare interface Component {
  componentId: number;
  userName: string;
  userId: number;
  deptId: number;
  coordX: number;
  coordY: number;

}

declare interface UpdateComponent {
  componentId: number;
  coordX: number;
  coordY: number;
}