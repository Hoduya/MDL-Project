<template>
  <nav class="navbar navbar-expand-lg navbar-light bg-transparent border-bottom border-primary mb-4">
    <ul class="nav">
      <li class="nav-item" v-for="link in links" :key="link.routeName">
        <AppLink
          class="nav-link"
          active-class="active"
          :name="link.routeName"
          :params="link.routeParams">
          <i v-if="link.icon" :class="link.icon" /> {{ link.title }}
        </AppLink>
      </li>
    </ul>
  </nav>
</template>


<script lang="ts" setup>
import { computed, withDefaults, defineProps } from 'vue'
import { AppRouteNames } from '../router'
import { RouteParams } from 'vue-router'
import { useUserStore } from '../store/user'

import AppLink from './AppLink.vue'

interface Props {
  useGlobalFeed?: boolean
  useMyFeed?: boolean
  useTagFeed?: boolean
  useUserFeed?: boolean
  useUserFavorited?: boolean
}

const props = withDefaults(defineProps<Props>(), {
  useGlobalFeed: false,
  useMyFeed: false,
  useTagFeed: false,
  useUserFavorited: false,
  useUserFeed: false,
})

const userStore = useUserStore()

interface NavLink {
  routeName: AppRouteNames
  routeParams?: RouteParams
  title: string
  show: true | false
  icon?: string
}

const allLinks = computed<NavLink[]>(() => [
  {
    routeName: 'global-feed',
    title: '전체글',
    show: props.useGlobalFeed,
  },
  {
    routeName: 'user-feed',
    title: '나의 게시글',
    routeParams: {
      slug: userStore.currentUser?.userId.toString() || ''
    },
    show: props.useMyFeed && userStore.currentUser ? true : false,
  },
])
const links = computed(() => allLinks.value.filter((link) => link.show))
</script>