import { useNotify } from '@/shared/helpers/useNotify'
import type ILoginData from '../model'

const registration = async (loginData: ILoginData) => {
  const { error, success } = useNotify()

  const response = await fetch(`${import.meta.env.VITE_API}auth/register`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(loginData),
  })

  if (!response.ok) {
    const errorText = await response.text()

    const parser = new DOMParser()
    const xmlDoc = parser.parseFromString(errorText, "text/xml")
    const message = xmlDoc.getElementsByTagName("message")[0]?.textContent
    
    return error('Регистрация', message || 'Ошибка регистрации')
  }

  success('Регистрация', 'Проверьте вашу почту, на нее пришло сообщение')
}

export default registration
