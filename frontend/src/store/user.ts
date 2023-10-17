import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import api from '@/api'

export const useUserStore = defineStore('user', () => {
  const currentUser = ref<User | null>(null)
  const isLoggedIn = computed(() => currentUser.value !== null)

  const updateUserInfo = (user: User) => {
    currentUser.value = user
  }

  const updateUserAuth = (user: User) => {
    currentUser.value = user
    localStorage.setItem('jwt-token', user.token!)
  }

  const login = async (postLoginForm: PostLoginForm) => {
    const user = await api.login(postLoginForm)
    updateUserAuth(user)
  }

  const register = async (postRegisterForm: PostRegisterForm) => {
    await api.register(postRegisterForm)
  }

  const logout = async () => {
    currentUser.value = null
    localStorage.removeItem('jwt-token')
  }

  return { currentUser, isLoggedIn, updateUserInfo, login, register, logout }
})
