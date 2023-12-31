<template>
  <header class="p-3 text-bg-dark">
    <div class="container">
      <div class="row align-items-center">
        <div class="col-auto">
          <AppLink class="navbar-brand fw-bold" style="font-size: 1.5rem" name="global-feed"> 기업IT개발1팀 </AppLink>
        </div>
        <div class="col-auto">
          <ul class="nav mb-2 justify-content-center mb-md-0">
            <li class="nav-item" v-for="link in enabledNavLinks" :key="link.name">
              <AppLink
                class="nav-link px-2 text-white"
                active-class="active"
                :name="link.name"
                :params="link.params"
                style="font-weight: 300;">
                <i v-if="link.icon" :class="link.icon" /> {{ link.title }}
              </AppLink>
            </li>
          </ul>
        </div>
        <div class="col"></div>
        <div class="col-auto text-end">

          <template v-if="displayStatus === 'GUEST'">
            <AppLink :name="loginLink.name" class="btn btn-outline-light me-2">
              {{ loginLink.title }}
            </AppLink>

            <AppLink :name="registerLink.name" class="btn btn-warning">
              {{ registerLink.title }}
            </AppLink>
          </template>

          <template v-else>
            <div class="dropdown text-end">
              <a href="#" class="d-block link-light text-decoration-none dropdown-toggle"
                data-bs-toggle="dropdown" aria-expanded="false">
                <img v-bind:src="userProfileUrl" alt="mdo" width="32" height="32" class="rounded-circle me-2">
                <span class="fw-semibold">{{ username }}</span>
              </a>
              <ul class="dropdown-menu dropdown-menu-dark text-small" data-bs-theme="dark">
                <li>
                  <AppLink :name="profileLink.name" :params="profileLink.params" class="dropdown-item">프로필</AppLink>
                </li>
                <li>
                  <AppLink :name="settingsLink.name" class="dropdown-item">설정</AppLink>
                </li>
                <hr class="dropdown-divider">
                <li>
                  <button class="dropdown-item text-danger fw-bold" @click="onLogout">로그아웃</button>
                </li>
              </ul>
            </div>
          </template>
        </div>
      </div>
    </div>
  </header>
</template>


<script lang="ts" setup>
import { computed, onMounted } from 'vue'
import AppLink from './AppLink.vue'
import { AppRouteNames, routerPush } from '../router'
import { RouteParams } from 'vue-router'
import { useUserStore } from '../store/user'

interface NavLink {
  name: AppRouteNames
  params?: RouteParams
  title?: string
  icon?: string
  display: 'GUEST' | 'USER' | 'ADMIN'
}

const userStore = useUserStore()

const role = computed(() => userStore.currentUser?.role)
const username = computed(() => userStore.currentUser?.name)
const userId = computed(() => userStore.currentUser?.userId)
const userProfileUrl = computed(() => require("/src/assets/images/defaultProfile.png"))
const displayStatus = computed(() => role.value ?  role.value : 'GUEST')

const navLinks = computed<NavLink[]>(() => [
  {
    name: 'create-board',
    title: '글쓰기',
    display: 'USER',
  },
  {
    name: 'Vote',
    title: '투표',
    display: 'USER',
  },
  {
    name: 'VoteSettings',
    title: '투표관리',
    display: 'ADMIN',
  }
])

const loginLink: NavLink = {
  name: 'login',
  title: '로그인',
  display: 'GUEST'
}

const registerLink: NavLink = {
  name: 'register',
  title: '회원가입',
  display: 'GUEST'
}

const profileLink = computed<NavLink>(() => {
  return {
    name: 'profile',
    display: 'USER',
    params: { slug: userId.value?.toString() || 'as' },
  }
})

const settingsLink = computed<NavLink>(() => {
  return {
    name: 'settings',
    display: 'USER'
  }
})

const enabledNavLinks = computed(() =>
  navLinks.value.filter(
    (link) => link.display === displayStatus.value
  )
)

onMounted(()=> {
  console.log(role.value)
  console.log(displayStatus.value)
})

const onLogout = async () => {
  await userStore.logout()
  await routerPush("global-feed")
}

</script>
