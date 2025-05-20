import { useNotify } from '@/shared/helpers/useNotify'
import type { IresetPassword } from '../model'

const resetPassword = async (loginData: IresetPassword) => {
  const { error, success } = useNotify()

  const response = await fetch(`${import.meta.env.VITE_API}auth/update`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(loginData),
  })

  if (!response.ok) {
    return error('Обновление пароля', 'Ошибка обновления пароля')
  }

  success('Обновление пароля', 'Ссылка успешно отправлена на почту!')
}

export default resetPassword
