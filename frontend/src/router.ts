import { createRouter, createWebHashHistory, RouteParams } from 'vue-router'

import Home from './pages/Home.vue'

export type AppRouteNames =
  | 'home'
  | 'login'
  | 'register'
  | 'create-board'

export const router = createRouter({
  history: createWebHashHistory(),
  routes: [
    {
      path: '/',
      redirect: '/home'
    },
    {
      name: 'home',
      path: '/home',
      component: Home 
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
  ],
})

export async function routerPush(
  name: AppRouteNames,
  params?: RouteParams
): ReturnType<typeof router.push> {
  return await router.push({
    name,
    params,
  })
}
