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
    }else{
      return
    }
   
  }

  const getUser = async () => {
    if (localStorage.getItem('token')) {
      const res = await fetch(`${import.meta.env.VITE_API}user/${route.params.id}`, {
        headers: { Authorization: `Bearer ${localStorage.getItem('token')}` },
      })
      user.value = await res.json()
      
    }else{
      const res = await fetch(`${import.meta.env.VITE_API}user/${route.params.id}`, {
      })
      user.value = await res.json()
    }
  }

  const getVideo = async () => {
    if (localStorage.getItem('token')) {
      const res = await fetch(`${import.meta.env.VITE_API}video/user/${route.params.id}`, {
        headers: { Authorization: `Bearer ${localStorage.getItem('token')}` },
      })
      videoList.value = await res.json()
    } else{
      const res = await fetch(`${import.meta.env.VITE_API}video/user/${route.params.id}`)
      videoList.value = await res.json()
    }
    
  }

  const getFollowers = async () => {
    if (localStorage.getItem('token')) {
      const res = await fetch(
        `${import.meta.env.VITE_API}subscription/followers/${route.params.id}`,
        { headers: { Authorization: `Bearer ${localStorage.getItem('token')}` } },
      )
      followers.value = await res.json()
      isFollowing.value = true  
    } else{
      const res = await fetch(
        `${import.meta.env.VITE_API}subscription/followers/${route.params.id}`,
      )
      followers.value = await res.json()
    }
    
  }

  const getFollowings = async () => {
    if (localStorage.getItem('token')) {
      const res = await fetch(
        `${import.meta.env.VITE_API}subscription/followings/${route.params.id}`,
        { headers: { Authorization: `Bearer ${localStorage.getItem('token')}` } },
      )
      followers.value = await res.json()
      isFollowing.value = true
    } else{
      const res = await fetch(
        `${import.meta.env.VITE_API}subscription/followings/${route.params.id}`,
      )
      followers.value = await res.json()
    }
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
    
    if(!response.ok){
      return error("Обновление", "Ошибка обновления пользовательских данных")
    }
    
    success("Обновление", "Ошибка обновления пользовательских данных")
  }
  
  const uploadVideo = async (
    title: string,
    description: string,
    tags: string[],
    file: File | null,
  ) => {
    const fd = new FormData()
    fd.append('title', title)
    fd.append('description', description)
    tags.forEach((tag) => fd.append('tags', tag))
    if (file) fd.append('file', file)

    const response = await fetch(`${import.meta.env.VITE_API}video/upload/${route.params.id}`, {
      method: 'POST',
      headers: { Authorization: `Bearer ${localStorage.getItem('token')}` },
      body: fd,
    })
    
    if(!response.ok){
      return error("Обновление", "Ошибка обновления данных видео")
    }
    
    success("Обновление", "Данные для видео успешно обновлены")
    await getVideo()
  }

  const changeVisibilityVideo = async (id: string) => {
    const response = await fetch(`${import.meta.env.VITE_API}video/visibility/${id}`, {
      method: 'PATCH',
      headers: { Authorization: `Bearer ${localStorage.getItem('token')}` },
    })
    
    if(!response.ok){
      return error("Обновление", "Ошибка обновления отображения видео")
    }
    
    success("Обновление", "Отображение для видео успешно обновлено")
    await getVideo()
  }
  
  const deleteVideo = async (id: string) => {
    const response = await fetch(`${import.meta.env.VITE_API}video/delete/${id}`, {
      method: 'DELETE',
      headers: { Authorization: `Bearer ${localStorage.getItem('token')}` },
    })
    
    if(!response.ok){
      return error("Удаление", "Ошибка удаления видео")
    }
    
    success("Удаление", "Видео успешно удалено")
    await getVideo()
  }

  const updateVideo = async (id: string, title: string, description: string, tags: string[]) => {
    const response = await fetch(`${import.meta.env.VITE_API}video/update/${id}`, {
      method: 'PATCH',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${localStorage.getItem('token')}`,
      },
      body: JSON.stringify({ title, description, tags }),
    })
    
    if(!response.ok){
      return error("Обновление", "Ошибка обновления видео")
    }
    
    success("Обновление", "Видео успешно обновлено")
    await getVideo()
  }

  watch(
    () => route.params.id,
    async () => {
      await getUser()
      await getVideo()
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
    getVideo,
    getFollowers,
    getFollowings,
    updateUser,
    uploadVideo,
    changeVisibilityVideo,
    deleteVideo,
    updateVideo,
  }
}
