import axios from 'axios'
import { CONFIG } from '@/config'
import { useUserStore } from '@/store/user'
import { useToast } from 'vue-toastification'
import { routerPush } from '@/router'

const instance = axios.create({
  timeout: 10000,
  baseURL: CONFIG.API_HOST
})

const handleInvalidTokenState = async () => {
  const userStore = useUserStore()
  const toast = useToast()
  userStore.forceLogout()
  toast.info("로그인 시간 만료\n다시 로그인해주세요", { timeout: 3000 })
  routerPush('login')
}


instance.interceptors.request.use(
  (config) => {
    const accessToken = localStorage.getItem('access-token')
    if(accessToken) {
      config.headers.Authorization = `Bearer ${accessToken}`
    } else {
      config.headers.Authorization = null
    }

    return config
  },
  error => { 
    Promise.reject(error)
  }
)

instance.interceptors.response.use(
  ( response ) => {
    return response.data
  },
  async (error) => {
    const { config: originRequest } = error
    const apiError: ApiErrorResponse = error.response.data
    
    // 105 : 엑세스 토큰 만료 -> 재발급
    if (apiError.code === 105) {
      
      const accessToken = localStorage.getItem("access-token")
      const refreshToken = localStorage.getItem("refresh-token")
      
      if(!accessToken || !refreshToken) {
        handleInvalidTokenState()
        return
      }
      
      const token: Token = {
        accessToken,
        refreshToken
      }

      // 토큰 재발급 요청
      try {
        const response = await axios.post<Token>(
          'http://localhost:8081/api/reissue',
          token,
          {}
        ) 

        // 재발급 성공 -> localStorage 저장
        const newToken = response.data 
        localStorage.setItem('access-token', newToken.accessToken)
        localStorage.setItem('refresh-token', newToken.refreshToken)
        
        // 동일한 요청 전송 (새로운 Access 토큰으로)
        originRequest.headers.authorization = `Bearer ${newToken.accessToken}`
        return axios(originRequest).then(response => response.data)

      } catch (e) {
        // 재발급 불가능한 경우 로그아웃 처리
        handleInvalidTokenState()
        return Promise.reject(apiError)
      }
    }
    return Promise.reject(apiError)
  }
) 

export default instance