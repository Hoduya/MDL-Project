import request from "@/utils/request"

const fetchBoardsLimit = 10;

async function login(params: PostLoginForm): Promise<User> {
  return request({
    url: '/api/login',
    method: 'POST',
    data: params,
  }).then(({ data }) => {
    const { user, token } = data;
    user.token = token;
    return user;
  })
}

async function register(params: PostRegisterForm): Promise<void> {
  return request({
    url: '/api/users/join',
    method: 'POST',
    data: params
  })
}

async function fetchUserInfo(slug: string): Promise<User> {
  request({ 
    url: `/api/users/${slug}`,
    method: 'GET',
  }).then((res)=>{
    console.log(res)
  })
  
  return request({ 
    url: `/api/users/${slug}`,
    method: 'GET',
  })
}

async function fetchBoards(params: BoardsOption): Promise<{ boards: Board[], boardsCount: number }> {
  console.log(params)
  return request({
    url: '/api/boards',
    method:'GET',
    params: params
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

async function fetchComments(slug: string): Promise<BoardComment[]> {
  return request ({
    url: `api/boards/${slug}/comments`,
    method: 'GET'
  })
}

async function createComment(params: {
  slug: string,
  content: string
}): Promise<BoardComment> {
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

export default {
  fetchBoardsLimit,
  login,
  register,
  fetchUserInfo,
  fetchBoards,
  fetchBoard,
  createBoard,
  updateBoard,
  deleteBoard,
  fetchComments,
  createComment,
  deleteComment,
}
