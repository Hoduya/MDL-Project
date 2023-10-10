import request from "@/utils/request"

async function login(params: PostLoginForm): Promise<User> {
  return request({
    url: '/api/users/login',
    method: 'post',
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
    method: 'post',
    data: params
  })
}

export default {
  login,
  register
}
