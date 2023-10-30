import request from "@/utils/request"

const fetchBoardsLimit = 10;

// 로그인

async function login(params: PostLoginForm): Promise<{user: User, token: Token}> {
  return request({
    url: '/api/login',
    method: 'POST',
    data: params,
  })
}

async function logout(): Promise<void> {
  return request({
    url: '/api/logout',
    method: 'PUT'
  })
}

async function register(params: PostRegisterForm): Promise<void> {
  return request({
    url: '/api/join',
    method: 'POST',
    data: params
  })
}

// 유저

async function fetchUser(slug: string): Promise<User> {
  return request({ 
    url: `/api/users/${slug}`,
    method: 'GET',
  })
}

async function fetchProfilesFromDept(deptId: number): Promise<Profile[]> {
  return request({
    url: `/api/profiles/${deptId}`,
    method: 'GET',
  })
}

async function updateUser(user: User): Promise<User> {
  return request({
    url: `/api/users`,
    method: 'PUT',
    data: user
  })
}

async function deleteCurrentUser(): Promise<void> {
  return request({
    url: `/api/users`,
    method: 'DELETE',
  })
}

// 부서

async function fetchDepartments(): Promise<Department[]> {
  return request({
    url: '/api/departments',
    method: 'GET',
  })
}

async function fetchDepartmentName(deptId: number): Promise<string> {
  return request({
    url: `api/deaprtments/${deptId}`,
    method:'GET'
  })
}

// 게시글

async function fetchBoards(params: BoardsOption, data?: SearchOption): Promise<{ boards: Board[], boardsCount: number }> {
  return request({
    url: '/api/boards',
    method:'GET',
    params: params,
    data: data
  })
}

async function fetchBoard(slug: string): Promise<Board> {
  return request({
    url: `/api/boards/${slug}`,
    method: 'GET',
  })
}

async function createBoard(params: BoardForm): Promise<Board> {
  return request({
    url: '/api/boards',
    method: 'POST',
    data: params
  })
}

async function updateBoard(params: {
  board: BoardForm
  slug: string
}): Promise<Board> {
  return request ({
    url: `api/boards/${params.slug}`,
    method: 'PUT',
    data: params.board
  })
}

async function deleteBoard(slug: string): Promise<void> {
  return request ({ 
    url: `api/boards/${slug}`,
    method: 'DELETE',
  })
}

// 댓글

async function fetchComments(slug: string): Promise<BoardComment[]> {
  return request ({
    url: `api/boards/${slug}/comments`,
    method: 'GET'
  })
}

async function createComment(params: { slug: string, content: string}): Promise<BoardComment> {
  return request({
    url: `/api/boards/${params.slug}/comments`,
    method: 'post',
    data: params
  })
}

async function deleteComment(slug: string, id: number): Promise<void> {
  return request({ 
    url: `/api/boards/${slug}/comments/${id}`,
    method: 'DELETE'
  })
}

// 투표

async function fetchComponents(slug: number): Promise<Component[]>{
  return request({
    url: `/api/components/${slug}`,
    method: 'GET'
  })
}

async function updateUserComponent(component: UpdateComponent): Promise<void> {
  return request({
    url: '/api/components',
    method: 'PUT',
    data: component
  })
}

// 투표 관리

async function uploadMenuImage(formData: FormData, slug: string): Promise<void> {
  return request({
    headers: {
      "Content-Type": "multipart/form-data",
    },
    url: `/api/menuImage/${slug}`,
    method: 'POST',
    data: formData
  })
}

async function fetchMenuImage(slug: string): Promise<string> {
  return request({
    url: `/api/menuImage/${slug}`,
    method: 'GET',
  })
}

export default {
  fetchBoardsLimit,
  login,
  logout,
  register,
  fetchUser,
  fetchProfilesFromDept,
  updateUser,
  deleteCurrentUser,
  fetchDepartments,
  fetchDepartmentName,
  fetchBoards,
  fetchBoard,
  createBoard,
  updateBoard,
  deleteBoard,
  fetchComments,
  createComment,
  deleteComment,
  fetchComponents,
  updateUserComponent,
  uploadMenuImage,
  fetchMenuImage,
}
