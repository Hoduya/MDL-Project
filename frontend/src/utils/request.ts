import axios from 'axios'
import { CONFIG } from '@/config'
import { useUserStore } from '@/store/user'

const instance = axios.create({
  timeout: 10000,
  baseURL: CONFIG.API_HOST
})

const userStore = useUserStore()

instance.interceptors.request.use(
  (config) => {
    
    if(userStore.isLoggedIn) {
      const token = localStorage.getItem('access-token')

      if (token) {
        config.headers.Authorization = `Bearer ${token}`
      }
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