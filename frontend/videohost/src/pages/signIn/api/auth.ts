import { useNotify } from '@/shared/helpers/useNotify'
import type { Router } from 'vue-router'
import type ILoginData from '../model'

const auth = async (loginData: ILoginData, router: Router) => {
  const { error, success } = useNotify()

  const response = await fetch(`${import.meta.env.VITE_API}auth/login`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(loginData),
  })

  if (!response.ok) {
    return error('Авторизация', 'Пользователь не найден')
  }

  const data = await response.json()

  localStorage.setItem('token', data.token)

  success('Авторизация', 'Вы успешно вошли в аккаунт!')
  router.push('/')
}

export default auth
