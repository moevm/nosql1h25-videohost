import { useNotify } from '@/shared/helpers/useNotify'
import type { Router } from "vue-router"

const newPassword = async (password: string, token: string, router: Router) => {
  const { error, success } = useNotify()

  const response = await fetch(`${import.meta.env.VITE_API}auth/resetPassword?token=${token}`, {
    method: 'PATCH',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({
      password,
    })
  })

  if(password.length < 6) return error("Пароль", "Пароль меньше 6 символов")

  if (!response.ok) {
    return error('Обновление пароля', 'Ошибка обновления пароля')
  }

  success('Обновление пароля', 'Пароль успешно изменен!')
  router.push('/login')
}

export default newPassword
