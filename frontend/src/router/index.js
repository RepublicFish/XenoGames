import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    name: 'GameList',
    component: () => import('@/views/GameList.vue')
  },
  {
    path: '/games/new',
    name: 'GameCreate',
    component: () => import('@/views/GameFormPage.vue')
  },
  {
    path: '/games/:id',
    name: 'GameDetail',
    component: () => import('@/views/GameDetail.vue')
  },
  {
    path: '/games/:id/edit',
    name: 'GameEdit',
    component: () => import('@/views/GameFormPage.vue')
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
