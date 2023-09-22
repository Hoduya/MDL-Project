import { createRouter, createWebHashHistory, RouteParams } from 'vue-router'

import Home from './pages/Home.vue'

export type AppRouteNames =
  | 'login'
  | 'register'

export const router = createRouter({
  history: createWebHashHistory(),
  routes: [
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
