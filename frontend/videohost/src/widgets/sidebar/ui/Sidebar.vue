<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'

import getMe from '@/shared/api/getMe'
import logOut from '../api/logOut'

import type IMe from '@/shared/model/IMe'

const router = useRouter()

const me = ref<IMe>({
  userId: '',
  username: '',
  email: '',
  registrationDate: '',
  subscriberCount: 0,
  blocked: false,
  roles: [],
})

const isAuth = ref<boolean>(false)

onMounted(async () => {
  me.value = await getMe()
  isAuth.value = await getMe(true)
  
})
</script>

<template>
  <div class="min-h-screen bg-gray-100">
    <div
      class="w-64 bg-blue-600 text-white p-5 shadow-lg h-screen fixed left-0 top-0 flex flex-col justify-between"
    >
      <div>
        <h1 @click="router.push('/')" class="text-2xl font-bold mb-6 cursor-pointer">На главную</h1>
        <div v-if="isAuth && me.roles && !me.roles.includes('ADMIN')">
          <h2 class="font-semibold mb-4">Ваши возможности</h2>
          <ul class="space-y-3 text-sm">
            <li
              class="flex items-center hover:bg-blue-700 p-2 rounded-lg transition-colors cursor-pointer"
            >
              <router-link class="hover:bg-blue-700" :to="`/profile/${me.userId}`">
                Страницы настройки канала
              </router-link>
            </li>

            <li
              class="flex items-center hover:bg-blue-700 p-2 rounded-lg transition-colors cursor-pointer"
            >
              <router-link class="hover:bg-blue-700" to="/users"> Найти пользователей </router-link>
            </li>
          </ul>
        </div>

        <div v-else-if="!isAuth">
          <h2 class="font-semibold mb-4">Ваши возможности</h2>
          <ul class="space-y-3 text-sm">
            <li
              class="flex items-center hover:bg-blue-700 p-2 rounded-lg transition-colors cursor-pointer"
            >
              <router-link class="hover:bg-blue-700" to="/users"> Найти пользователей </router-link>
            </li>
          </ul>
        </div>

        <div v-if="me?.roles && me?.roles.includes('ADMIN')">
          <h2 class="font-semibold mb-4">Ваши возможности</h2>
          <ul class="space-y-3 text-sm">
            <li
              class="flex items-center hover:bg-blue-700 p-2 rounded-lg transition-colors cursor-pointer"
            >
              <router-link class="hover:bg-blue-700" to="/importExport">
                Массовый импорт/экспорт
              </router-link>
            </li>

            <li
              class="flex items-center hover:bg-blue-700 p-2 rounded-lg transition-colors cursor-pointer"
            >
              <router-link class="hover:bg-blue-700" :to="`/profile/${me.userId}`">
                Страницы настройки канала
              </router-link>
            </li>

            <li
              class="flex items-center hover:bg-blue-700 p-2 rounded-lg transition-colors cursor-pointer"
            >
              <router-link class="hover:bg-blue-700" to="/users"> Найти пользователей </router-link>
            </li>

            <li
              class="flex items-center hover:bg-blue-700 p-2 rounded-lg transition-colors cursor-pointer"
            >
              <router-link class="hover:bg-blue-700" to="/hideVideo"> Скрыть видео </router-link>
            </li>
          </ul>
        </div>
      </div>

      <div v-if="isAuth" class="border-t border-blue-500 pt-4">
        <div class="flex items-center justify-between mb-2">
          <span class="cursor-pointer" @click="router.push(`/profile/${me.userId}`)">{{
            me.username
          }}</span>
          <span>Подписчики: {{ me.subscriberCount }}</span>
        </div>
        <div class="flex justify-between items-center">
          <span v-if="me.roles && me.roles.includes('ADMIN')" class="text-blue-200 text-sm">
            Администратор
          </span>

          <span v-else class="text-blue-200 text-sm">Пользователь</span>
          <button
            @click="logOut(router)"
            class="cursor-pointer text-red-200 hover:text-red-300 text-sm"
          >
            Выход
          </button>
        </div>
      </div>

      <div v-else class="border-t border-blue-500 pt-4">
        <button
          @click="router.push('/login')"
          class="cursor-pointer text-green-200 hover:text-green-400 text-xl"
        >
          Войти в аккаунт
        </button>
      </div>
    </div>

    <div class="ml-64 h-screen">
      <router-view />
    </div>
  </div>
</template>
