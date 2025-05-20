import { useNotify } from '@/shared/helpers/useNotify'
import type { Router } from 'vue-router'
import type ILoginData from '../model'

const registration = async (loginData: ILoginData, router: Router) => {
  const { error, success } = useNotify()

  const response = await fetch(`${import.meta.env.VITE_API}auth/register`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(loginData),
  })

  if (!response.ok) {
    return error('Регистрация', 'Ошибка регистрации')
  }

  success('Регистрация', 'Проверьте вашу почту, на нее пришло сообщение')
}

export default registration
