import type { Ref } from 'vue'
import { useNotify } from '@/shared/helpers/useNotify'
import type IVideo from '@/shared/model/IVideo'
import type IUser from '@/shared/model/IUser'

export const useVideoActions = (video: Ref<IVideo>, user: Ref<IUser>) => {
  const { error, success } = useNotify()

  const getVideo = async (id: string) => {
    if (localStorage.getItem('token')) {
      const res = await fetch(`${import.meta.env.VITE_API}video/${id}`, {
        headers: { Authorization: `Bearer ${localStorage.getItem('token')}` },
      })
      video.value = await res.json()
    } else{
      const res = await fetch(`${import.meta.env.VITE_API}video/${id}`, {
      })
      video.value = await res.json()
    }
  }

  const getUser = async () => {
    if (localStorage.getItem('token')) { 
      const res = await fetch(`${import.meta.env.VITE_API}user/${video.value.userId}`, {
        headers: { Authorization: `Bearer ${localStorage.getItem('token')}` },
      })
      user.value = await res.json()
    } else{
      const res = await fetch(`${import.meta.env.VITE_API}user/${video.value.userId}`, {
      })
      user.value = await res.json()
    }
  }

  const addComment = async (videoId: string, text: string) => {
    const response = await fetch(`${import.meta.env.VITE_API}comment/add/${videoId}`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${localStorage.getItem('token')}`,
      },
      body: JSON.stringify({ text }),
    })

    if (!response.ok) {
      return error('Комменатрий', 'Не удалось добавить комментарий')
    }

    success('Комменатрий', 'Комментарий добавлен')
    await getVideo(videoId)
  }

  const deleteComment = async (commentId: string) => {
    const response = await fetch(`${import.meta.env.VITE_API}comment/delete/${commentId}`, {
      method: 'DELETE',
      headers: { Authorization: `Bearer ${localStorage.getItem('token')}` },
    })

    if (!response.ok) {
      return error('Комменатрий', 'Не удалось удалить комментарий')
    }

    success('Комменатрий', 'Комментарий удалён')
    await getVideo(video.value.videoId)
  }

  const updateComment = async (commentId: string, text: string) => {
    const response = await fetch(`${import.meta.env.VITE_API}comment/update/${commentId}`, {
      method: 'PATCH',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${localStorage.getItem('token')}`,
      },
      body: JSON.stringify({ text }),
    })

    if (!response.ok) {
      return error('Комменатрий', 'Не удалось обновить комментарий')
    }

    success('Комменатрий', 'Комментарий обновлён')
    await getVideo(video.value.videoId)
  }

  const addReaction = async (videoId: string, type: 'LIKE' | 'DISLIKE') => {
    const response = await fetch(`${import.meta.env.VITE_API}reaction/add/${videoId}`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${localStorage.getItem('token')}`,
      },
      body: JSON.stringify({ type }),
    })

    if (!response.ok) {
      return error('Реакция', 'Не удалось отправить реакцию')
    }

    success('Реакция', 'Реакция успешно добавлена')
    await getVideo(videoId)
  }

  const subscribe = async (userId: string) => {
    const response = await fetch(`${import.meta.env.VITE_API}subscription/subscribe/${userId}`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${localStorage.getItem('token')}`,
      },
    })

    if (!response.ok) {
      return error('Подписка', 'Не удалось подписаться')
    }
    success('Подписка', 'Вы успешно подписались')
    await getUser()
  }

  const unsubscribe = async (userId: string) => {
    const reponse = await fetch(`${import.meta.env.VITE_API}subscription/unsubscribe/${userId}`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${localStorage.getItem('token')}`,
      },
    })

    if (!reponse.ok) {
      return error('Отписка', 'Не удалось отписаться')
    }

    success('Отписка', 'Вы успешно отписались')
    await getUser()
  }

  const downloadVideo = async (fileName: string) => {
    const res = await fetch(`${import.meta.env.VITE_API}video/download/${fileName}`, {
      headers: { Authorization: `Bearer ${localStorage.getItem('token')}` },
    })
    const blob = await res.blob()
    const url = URL.createObjectURL(blob)
    const a = document.createElement('a')
    a.href = url
    a.download = fileName
    document.body.appendChild(a)
    a.click()
    URL.revokeObjectURL(url)
    document.body.removeChild(a)

    if (!res.ok) {
      return error('Скачивание', 'Не удалось скачать видео')
    }

    success('Скачивание', 'Видео успешно скачалось')
  }

  return {
    getVideo,
    getUser,
    addComment,
    deleteComment,
    updateComment,
    addReaction,
    subscribe,
    unsubscribe,
    downloadVideo,
  }
}
