import { useNotify } from '@/shared/helpers/useNotify'

const resetPassword = async (username: string) => {
  const { error, success } = useNotify()

  const response = await fetch(`${import.meta.env.VITE_API}auth/reset?usernameOrEmail=${username}`, {
    method: 'PATCH',
    headers: {
      'Content-Type': 'application/json',
    },
  })

  if (!response.ok) {
    return error('Обновление пароля', 'Ошибка обновления пароля')
  }

  success('Обновление пароля', 'Ссылка успешно отправлена на почту!')
}

export default resetPassword
