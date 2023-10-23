import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import api from '@/api'

export const useUserStore = defineStore('user', () => {
  const currentUser = ref<User | null>(null)
  const token = ref<Token | null>(null)
  const isLoggedIn = computed(() => currentUser.value !== null)

  const updateUserInfo = (user: User) => {
    currentUser.value = user
  }

  const updateToken = (newToken: Token) => {
    token.value = newToken
    localStorage.setItem('access-token', newToken.accessToken)
    localStorage.setItem('refresh-token', newToken.refreshToken)
  }

  const login = async (postLoginForm: PostLoginForm) => {
    const { user, token } = await api.login(postLoginForm)
    updateUserInfo(user)
    updateToken(token)
  }

  const register = async (postRegisterForm: PostRegisterForm) => {
    await api.register(postRegisterForm)
  }

  const logout = async () => {
    currentUser.value = null
    localStorage.removeItem('access-token')
    localStorage.removeItem('refresh-token')
  }

  return { currentUser, isLoggedIn, updateUserInfo, login, register, logout }
})
