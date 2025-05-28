<script setup lang="ts">
import { ref, onMounted } from 'vue'

import { useNotify } from '@/shared/helpers/useNotify'
import { useFormData } from '@/shared/helpers/useFormData'

const { formatDate } = useFormData()
const { error, success } = useNotify()

const users = ref([])
const me = ref()

const getUsers = async () => {
  
  if (localStorage.getItem('token')) { 
    const response = await fetch(`${import.meta.env.VITE_API}user/all`, {
      headers: { Authorization: `Bearer ${localStorage.getItem('token')}` },
    })
    users.value = await response.json()
  } else {
    const response = await fetch(`${import.meta.env.VITE_API}user/all`, {
    })
    users.value = await response.json()
  }
  
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
  await getUsers()
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
  await getUsers()
}

onMounted(async () => {
  await getUsers()
})

const formData = ref({
  startDate: '',
  endDate: '',
  text1: '',
  text2: '',
  text3: '',
  number1: '',
  number2: '',
})

const formatDateSecond = (dateString) => {
  if (!dateString) return ''

  const date = new Date(dateString)
  const day = String(date.getDate()).padStart(2, '0')
  const month = String(date.getMonth() + 1).padStart(2, '0')
  const year = date.getFullYear()

  return `${month}.${day}.${year}`
}

const showParams = async () => {
  const registeredAfter = formatDateSecond(formData.value.endDate)
  const registeredBefore = formatDateSecond(formData.value.startDate)

  
  if (localStorage.getItem('token')) {
    const response = await fetch(
      `${import.meta.env.VITE_API}search/users?` +
        new URLSearchParams({
          registeredAfter: registeredAfter,
          registeredBefore: registeredBefore,
          username: formData.value.text1.toLowerCase(),
          email: formData.value.text2.toLowerCase(),
          minSubscribers: formData.value.number1,
          maxSubscribers: formData.value.number2,
        }),
      {
        method: 'GET',
        headers: {
          Authorization: `Bearer ${localStorage.getItem('token')}`,
        },
      },
    )
  
    const data = await response.json()
    users.value = data
  } else{
    const response = await fetch(
      `${import.meta.env.VITE_API}search/users?` +
        new URLSearchParams({
          registeredAfter: registeredAfter,
          registeredBefore: registeredBefore,
          username: formData.value.text1.toLowerCase(),
          email: formData.value.text2.toLowerCase(),
          minSubscribers: formData.value.number1,
          maxSubscribers: formData.value.number2,
        }),
      {
        method: 'GET',
      },
    )
  
    const data = await response.json()
    users.value = data
  }
  
}

const block = ref<boolean>(false)

const blockUser = async (userId: string) => {
  const response = await fetch(`${import.meta.env.VITE_API}admin/${userId}/block?block=${block.value}`, {
    method: 'PATCH',
    headers: {
      Authorization: `Bearer ${localStorage.getItem('token')}`,
    },
  })

  if (!response.ok) {
    return error('Блокировка', 'Не удалось заблокировать пользователя')
  }
  success('Блокировка', 'Пользователь успешно заблокирован')
  await getUsers()
}

const grantAdmin = async (userId: string) => {
  const response = await fetch(`${import.meta.env.VITE_API}admin/add/${userId}?role=ADMIN`, {
    method: 'PATCH',
    headers: {
      Authorization: `Bearer ${localStorage.getItem('token')}`,
    },
  })

  if (!response.ok) {
    return error('Права пользователя', 'Не удалось изменить права')
  }
  success('Права пользователя', 'Права пользователя успешно изменены')
  await getUsers()
}

const removeGrantAdmin = async (userId: string) => {
  const response = await fetch(`${import.meta.env.VITE_API}admin/remove/${userId}?role=ADMIN`, {
    method: 'PATCH',
    headers: {
      Authorization: `Bearer ${localStorage.getItem('token')}`,
    },
  })

  if (!response.ok) {
    return error('Права пользователя', 'Не удалось изменить права')
  }
  success('Права пользователя', 'Права пользователя успешно изменены')
  await getUsers()
}


const getMe = async () => {
  if (localStorage.getItem('token')) {
    const response = await fetch(`${import.meta.env.VITE_API}user/profile`, {
      method: 'GET',
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`,
      },
    })
  
    return await response.json()  
  } else {
    return
  }
  
}

onMounted(async () => {
  me.value = await getMe()
})

</script>

<template>
  <div class="bg-white rounded-xl shadow-lg p-6 mb-6">
    <div class="space-y-4">
      <div class="flex items-center justify-center gap-4">
        <span class="text-gray-500">до</span>
        <input
          type="date"
          v-model="formData.startDate"
          class="px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 transition-colors"
        />
        <span class="text-gray-500">после</span>
        <input
          type="date"
          v-model="formData.endDate"
          class="px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 transition-colors"
        />
      </div>

      <input
        type="text"
        v-model="formData.text1"
        placeholder="Никнейм"
        class="px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 transition-colors w-full"
      />
      <input
        type="text"
        v-model="formData.text2"
        placeholder="Почта"
        class="px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 transition-colors w-full"
      />
      <input
        type="number"
        v-model="formData.number1"
        placeholder="Минимальное количество подписчиков"
        class="px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 transition-colors w-full"
      />
      <input
        type="number"
        v-model="formData.number2"
        placeholder="Максимальное количество подписчиков"
        class="px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 transition-colors w-full"
      />

      <button
        @click="showParams"
        class="w-full bg-blue-500 hover:bg-blue-600 text-white font-medium py-2 px-4 rounded-lg transition-colors"
      >
        Показать параметры
      </button>
    </div>
  </div>

  <div class="container mx-auto px-4 py-8">
    <h1 class="text-3xl font-bold mb-6 text-gray-800">Список пользователей</h1>

    <div class="bg-white shadow-md rounded-lg overflow-x-auto">
      <table class="min-w-full divide-y divide-gray-200">
        <thead class="bg-gray-50">
          <tr>
            <th
              class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
            >
              Имя пользователя
            </th>
            <th
              class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
            >
              Email
            </th>
            <th
              class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
            >
              Дата регистрации
            </th>
            <th
              class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
            >
              Подписчики
            </th>
            <th
              class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
            >
              Роль
            </th>
            <th
              class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
            >
              Подписка
            </th>
            <th
              class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
            >
              Действия
            </th>
          </tr>
        </thead>
        <tbody class="bg-white divide-y divide-gray-200">
          <tr v-for="user in users" :key="user.userId">
            <td class="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">
              {{ user.username }}
            </td>
            <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{{ user.email }}</td>
            <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
              {{ formatDate(new Date(user.registrationDate)) }}
            </td>
            <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
              {{ user.subscriberCount }}
            </td>
            <td class="px-6 py-4 whitespace-nowrap">
              <span
                v-if="user.roles && user.roles.includes('ADMIN')"
                class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full"
              >
                Администратор
              </span>

              <span v-else class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full">
                Пользователь
              </span>
            </td>
            <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
              <span v-if="user.isUserSubscribe" class="text-green-600">✓ Подписан</span>
              <span v-else class="text-gray-400">Не подписан</span>
            </td>

            <td v-if="user.isUserSubscribe" class="px-6 py-4 whitespace-nowrap text-sm font-medium">
              <button
                @click="unsubscribe(user.userId)"
                class="px-3 cursor-pointer py-1 rounded-md text-sm font-medium transition-colors bg-red-100 text-red-700 hover:bg-red-200"
              >
                Отписаться
              </button>
            </td>

            <td v-else class="px-6 py-4 whitespace-nowrap text-sm font-medium">
              <button
                @click="subscribe(user.userId)"
                class="px-3 cursor-pointer py-1 rounded-md text-sm font-medium transition-colors bg-green-100 text-green-700 hover:bg-green-200"
              >
                Подписаться
              </button>
            </td>

            <td v-if="me?.roles && me?.roles?.includes('ADMIN') && !user.blocked && me.username !== user.username" class="px-6 py-4 whitespace-nowrap text-sm font-medium">
              <button
                @click="block=true; blockUser(user.userId)"
                class="px-3 py-1 rounded-md text-sm font-medium transition-colors bg-gray-100 text-gray-700 hover:bg-gray-200"
              >
                Заблокировать
              </button>
            </td>
            
            <td v-else-if="me?.roles && me?.roles.includes('ADMIN') && user.blocked && me.username !== user.username" class="px-6 py-4 whitespace-nowrap text-sm font-medium">
              <button
                @click="block=false; blockUser(user.userId)"
                class="px-3 py-1 rounded-md text-sm font-medium transition-colors bg-gray-100 text-gray-700 hover:bg-gray-200"
              >
                Разблокировать
              </button>
            </td>

            <td v-if="user.roles && !user.roles.includes('ADMIN') && me.username !== user.username" class="px-6 py-4 whitespace-nowrap text-sm font-medium">
              <button
                @click="grantAdmin(user.userId)"
                v-if="!user.roles.includes('ADMIN')"
                class="px-3 py-1 rounded-md text-sm font-medium transition-colors bg-blue-100 text-blue-700 hover:bg-blue-200 w-fit"
              >
                Сделать админом
              </button>
            </td>
            
            <td v-else class="px-6 py-4 whitespace-nowrap text-sm font-medium">
              <button
                @click="removeGrantAdmin(user.userId)"
                v-if="user.roles.includes('ADMIN') && me.username !== user.username"
                class="px-3 py-1 rounded-md text-sm font-medium transition-colors bg-blue-100 text-blue-700 hover:bg-blue-200 w-fit"
              >
                Лишить прав
              </button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>
