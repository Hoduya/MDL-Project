import { createRouter, createWebHistory, RouteParams } from 'vue-router'

import Home from './pages/Home.vue'

export const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      name: 'global-feed',
      path: '/',
      component: Home,
    },
    {
      name: 'feed',
      path: '/',
      component: Home,
    },
    {
      name: 'login',
      path: '/login',
      component: () => import('./pages/Login.vue'),
    },
    {
      name: 'register',
      path: '/register',
      component: () => import('./pages/Register.vue'),
    },
    {
      name: 'create-board',
      path: '/board',
      component: () => import('./pages/EditBoard.vue')
    },
    {
      name: 'edit-board',
      path: '/edit-board/:slug',
      component: () => import('./pages/EditBoard.vue')
    },
    {
      name: 'profile',
      path: '/profile/:username',
      component: () => import('./pages/Profile.vue'),
    },
    {
      name: 'board',
      path: '/board/:slug',
      component: () => import('./pages/Board.vue')
    },
    {
      name: 'settings',
      path: '/settings',
      component: () => import('./pages/Settings.vue')
    }
  ],
})

export type AppRouteNames =
  | 'global-feed'
  | 'feed'
  | 'login'
  | 'register'
  | 'create-board'
  | 'edit-board'
  | 'profile'
  | 'board'
  | 'settings'

export async function routerPush(
  name: AppRouteNames,
  params?: RouteParams
): ReturnType<typeof router.push> {
  return await router.push({
    name,
    params,
  })
}
