import { ref, watch } from 'vue'
import { useRoute } from 'vue-router'

import { useNotify } from '@/shared/helpers/useNotify'

import type { Ref } from 'vue'
import type IUser from '@/shared/model/IUser'

interface IMe {
  userId: string
  username: string
  email: string
  registrationDate: string
  subscriberCount: number
  blocked: boolean
  roles: string[]
}

interface IFollowers {
  userId: string
  username: string
  email: string
  registrationDate: string
  subscriberCount: number
  blocked: boolean
  roles: ('ADMIN' | 'USER')[]
  isUserSubscribe: boolean
}

export function useProfileActions(
  user: Ref<IUser>,
  videoList: Ref<any[]>,
  followers: Ref<IFollowers[]>,
  me: Ref<IMe>,
  isMyProfile: Ref<boolean>,
  isFollowing: Ref<boolean>,
) {
  const route = useRoute()
  const { error, success } = useNotify()

  const getMe = async () => {
    if (localStorage.getItem('token')) { 
      const res = await fetch(`${import.meta.env.VITE_API}user/profile`, {
        headers: { Authorization: `Bearer ${localStorage.getItem('token')}` },
      })
      me.value = await res.json()   
    }
  }

  const getUser = async () => {
    const res = await fetch(`${import.meta.env.VITE_API}user/${route.params.id}`, {
      headers: localStorage.getItem('token')
        ? { Authorization: `Bearer ${localStorage.getItem('token')}` }
        : undefined,
    })
    user.value = await res.json()
  }

  const getFollowers = async () => {
    const res = await fetch(
      `${import.meta.env.VITE_API}subscription/followers/${route.params.id}`,
      localStorage.getItem('token')
        ? { headers: { Authorization: `Bearer ${localStorage.getItem('token')}` } }
        : undefined,
    )
    followers.value = await res.json()
    if (localStorage.getItem('token')) isFollowing.value = true
  }

  const getFollowings = async () => {
    const res = await fetch(
      `${import.meta.env.VITE_API}subscription/followings/${route.params.id}`,
      localStorage.getItem('token')
        ? { headers: { Authorization: `Bearer ${localStorage.getItem('token')}` } }
        : undefined,
    )
    followers.value = await res.json()
    if (localStorage.getItem('token')) isFollowing.value = true
  }

  const updateUser = async (newUsername: string) => {
    const response = await fetch(`${import.meta.env.VITE_API}user/update/${route.params.id}`, {
      method: 'PATCH',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${localStorage.getItem('token')}`,
      },
      body: JSON.stringify({ username: newUsername }),
    })
    
    if (!response.ok) {
      return error("Обновление", "Ошибка обновления пользовательских данных")
    }
    
    success("Обновление", "Ошибка обновления пользовательских данных")
  }

  watch(
    () => route.params.id,
    async () => {
      await getUser()
      await getMe()
      isMyProfile.value = me.value.userId === user.value.userId
      await getFollowings()
      await getFollowers()
      isFollowing.value = false
    },
    { immediate: true },
  )

  return {
    getMe,
    getUser,
    getFollowers,
    getFollowings,
    updateUser,
  }
}
