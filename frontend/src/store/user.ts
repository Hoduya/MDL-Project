import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import api from '@/api'

export const useUserStore = defineStore('user', () => {
  const currentUser = ref<User | null>(null)
  const isLoggedIn = computed(() => currentUser.value !== null)

  const updateUser = (user: User, token: Token) => {
    currentUser.value = user
    writeToken(token)
  }

  // 정상 로그아웃 -> 리프레시 토큰 만료
  const logout = async () => {
    await api.logout()
    currentUser.value = null
    removeToken()
  }

  // 리프레시 토큰 만료 -> 강제 로그아웃
  const forceLogout = () => {
    currentUser.value = null
    removeToken()
  }

  const writeToken = (token: Token) => {
      localStorage.setItem('access-token', token.accessToken)
      localStorage.setItem('refresh-token', token.refreshToken)
  }

  const removeToken = () => {
    localStorage.removeItem('access-token')
    localStorage.removeItem('refresh-token')
  }

  return { currentUser, isLoggedIn, updateUser, logout, forceLogout }
}, 
{ persist: {
    enabled: true,
    strategies: [
      { key: "pinia", storage: localStorage },
    ]
  }
}
)
