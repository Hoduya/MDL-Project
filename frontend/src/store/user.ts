import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import api from '@/api'

export const useUserStore = defineStore('user', () => {
  const currentUser = ref<User | null>(null)
  const isLoggedIn = computed(() => currentUser.value !== null)

  const updateUser = (user: User) => {
    currentUser.value = user
  }

  const updateToken = (newToken: Token) => {
    localStorage.setItem('access-token', newToken.accessToken)
    localStorage.setItem('refresh-token', newToken.refreshToken)
  }

  const logout = async () => {
    await api.logout()
    currentUser.value = null
    localStorage.removeItem('access-token')
    localStorage.removeItem('refresh-token')
  }

  return { currentUser, isLoggedIn, updateUser, updateToken, logout }
})
