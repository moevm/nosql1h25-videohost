import { useNotify } from '@/shared/helpers/useNotify'

import type { Router } from 'vue-router'

const { error, success } = useNotify()

const logOut = async (router: Router) => {
  const response = await fetch(`${import.meta.env.VITE_API}auth/logout`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${localStorage.getItem('token')}`,
    },
  })
  
  if(!response.ok){
    return error("Акканут", "Не удалось выйти из аккаунта")
  }
  
  success("Акканут", "Вы успешно вышли из аккаунта")
  localStorage.removeItem('token')

  router.push('/login')
}

export default logOut
