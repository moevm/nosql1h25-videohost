import { useNotify } from '@/shared/helpers/useNotify'
import type { Router } from 'vue-router'
import type { INewPassword } from '../model'

const newPassword = async (newPassword: INewPassword, router: Router) => {
  const { error, success } = useNotify()

  const response = await fetch(`${import.meta.env.VITE_API}auth/update`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(newPassword),
  })

  if (!response.ok) {
    return error('Обновление пароля', 'Ошибка обновления пароля')
  }

  success('Обновление пароля', 'Пароль успешно изменен!')
  router.push('/successNewPassword')
}

export default newPassword
