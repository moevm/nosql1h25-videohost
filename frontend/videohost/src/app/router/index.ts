import { createRouter, createWebHistory } from 'vue-router'
import { SignIn } from '@/pages/signIn/index'
import { SignUp } from '@/pages/sighUp'
import { SuccessRegister } from '@/pages/successRegister'
import { ResetPassword } from '@/pages/resetPassword'
import { NewPassword } from '@/pages/newPassword'
import { SuccessNewPassword } from '@/pages/successNewPassword'
import { Main } from '@/pages/main'
import { Video } from '@/pages/video'
import { Sidebar } from '@/widgets/sidebar'
import { Profile } from '@/pages/profile'
import { ImportExport } from '@/pages/importExport'
import { Users } from '@/pages/users'
import { HideVideo } from '@/pages/hideVideo'
import { NotFound } from '@/pages/notFound'
import { Statistic } from '@/pages/statisctic'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'main',
      component: Sidebar,
      children: [
        {
          path: '/',
          component: Main,
        },
        {
          path: '/video/:id',
          component: Video,
        },
        {
          path: '/profile/:id',
          component: Profile,
        },
        {
          path: '/importExport',
          component: ImportExport,
          meta: { requiresAuth: true },
        },
        {
          path: '/users',
          component: Users,
        },
        {
          path: '/hideVideo',
          component: HideVideo,
          meta: { requiresAuth: true },
        },
        {
          path: '/statistic',
          component: Statistic,
          meta: { requiresAuth: true },
        },
      ],
    },
    {
      path: '/login',
      name: 'signIn',
      component: SignIn,
    },
    {
      path: '/:pathMatch(.*)*',
      name: '404',
      component: NotFound,
    },
    {
      path: '/registration',
      name: 'signUp',
      component: SignUp,
    },
    {
      path: '/successRegister/:id',
      name: 'successRegister',
      component: SuccessRegister,
    },
    {
      path: '/resetPassword',
      name: 'ResetPassword',
      component: ResetPassword,
    },
    {
      path: '/successNewPassword',
      name: 'SuccessNewPassword',
      component: SuccessNewPassword,
    },
    {
      path: '/NewPassword/:id',
      name: 'NewPassword',
      component: NewPassword,
    },
  ],
})

const getMe = async () => {
  const response = await fetch(`${import.meta.env.VITE_API}user/profile`, {
    method: 'GET',
    headers: {
      Authorization: `Bearer ${localStorage.getItem('token')}`,
    },
  })

  return await response.json()
}


router.beforeEach(async (to) => {
  if (to.meta.requiresAuth) {
    const isAuthenticated = await getMe()
    console.log(1, isAuthenticated)
    if (!isAuthenticated.roles.includes('ADMIN')) {
      return {
        path: '/',
      }
    }
  }
})

export default router
