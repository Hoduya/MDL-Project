import axios from 'axios'
import { CONFIG } from '@/config'
import { useUserStore } from '@/store/user'

const instance = axios.create({
  timeout: 10000,
  baseURL: CONFIG.API_HOST
})

const doLogout = async () => {
  const userStore = useUserStore()
  userStore.logout()
}

instance.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('access-token')
    console.log("request intercept!")
    if(token) {
      config.headers.Authorization = `Bearer ${token}`
    } else {
      config.headers.Authorization = null
    }

    return config
  },
  error => Promise.reject(error)
)

instance.interceptors.response.use(
  ( response ) => {
    console.log(response)
    return response
  },
  async (error) => {
    const {
      config,
      response: { 
        status, 
        data: {
          code: errorCode
        }
      }
    } = error
    
    // TODO - 상태 코드로 만료 시 재발급, 토큰 서명 오류 & 불일치 시 로그아웃
    if (errorCode === 105) {
      
      const originRequest = config;
      const accessToken = localStorage.getItem("access-token")
      const refreshToken = localStorage.getItem("refresh-token")

      if (!accessToken || !refreshToken) {
        await doLogout()
        return
      }
      
      const token: Token = { 'accessToken': accessToken, 'refreshToken': refreshToken }

      // 토큰 재발급 요청
      try {
        const response = await axios.post<Token>(
          'http://localhost:8081/api/reissue',
          token,
          {}
        ) 

        // 재발급 성공
        const newToken = response.data
        localStorage.setItem("access-token", newToken.accessToken)
        localStorage.setItem("refresh-token", newToken.refreshToken)
        
        // 동일한 요청 전송 (새로운 Access 토큰으로)
        originRequest.headers.authorization = `Bearer ${newToken.accessToken}`
        return axios(originRequest)
      } catch (error) {
        return Promise.reject(error)
      }
    }
    // ({ message, response }) => Promise.reject(response ? response.data : message)
    Promise.reject(error)
  }
) 

export default instance