import axios from 'axios'
import { CONFIG } from '@/config'

const instance = axios.create({
  timeout: 10000,
  baseURL: CONFIG.API_HOST
})

instance.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('jwt-token')

    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }

    return config
  },
  error => Promise.reject(error)
)

instance.interceptors.response.use(
  ({ data }) => data,
  ({ message, response }) => Promise.reject(response ? response.data : message)
) 

export default instance

/**
 * 200외 에러 message 모두 묶어서. return
 * api 사용부분에서 catch 되면 alert 표시.
 */
