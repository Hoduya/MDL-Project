<template>
  <nav class="navbar bg-body-tertiary">
    <div class="container">
      <app-link class="navbar-brand" name="global-feed"> 기업IT개발1팀 </app-link>
      <ul class="nav navbar-nav pull-xs-right">
        <li class="nav-item" v-for="link in navLinks" :key="link.name">
          <app-link
            class="nav-link"
            active-class="active"
            :name="link.name"
            :params="link.params">
            <i v-if="link.icon" :class="link.icon" /> {{ link.title }}
          </app-link>
        </li>
      </ul>
    </div>
  </nav>
</template>

<script lang="ts" setup>
import { computed } from 'vue'
import AppLink from './AppLink.vue'
import { AppRouteNames } from '../router'
import { RouteParams } from 'vue-router'
import { userStore } from '../store/user'

interface NavLink {
  name: AppRouteNames
  params?: RouteParams
  title: string
  icon?: string
  display: 'all' | 'guest' | 'authorized'
}

const store = userStore()

const username = computed(() => store.user?.name)
const displayStatus = computed(() => (username.value ? 'authorized' : 'guest'))

const allLinks = computed<NavLink[]>(() => [
  {
    name: 'login',
    title: '로그인',
    display: 'guest',
  },
  {
    name: 'register',
    title: '회원가입',
    display: 'guest'
  },
  {
    name: 'create-board',
    title: '글쓰기',
    display: 'authorized',
    icon: '',
  },
])

const navLinks = computed(() =>
  allLinks.value.filter(
    (l) => l.display === 'all' || l.display === displayStatus.value
  )
)
</script>
