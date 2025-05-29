<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'

import auth from '../api/auth'
import type ILoginData from '../model'

const router = useRouter()

const username = ref('')
const password = ref('')

const loginData = ref<ILoginData>({
  username: username.value,
  password: password.value,
})
</script>

<template>
  <div class="min-h-screen flex items-center justify-center bg-gray-100 p-4">
    <div class="w-full max-w-md bg-white rounded-xl shadow-lg p-8 space-y-6">
      <form @submit.prevent="auth(loginData, router)" class="space-y-6">
        <h1 class="text-3xl font-bold text-gray-800 text-center">Вход</h1>

        <div class="space-y-4">
          <input
            v-model="loginData.username"
            placeholder="Логин"
            class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 transition-colors"
          />

          <input
            v-model="loginData.password"
            type="password"
            placeholder="Пароль"
            class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 transition-colors"
          />

          <button
            type="submit"
            class="w-full bg-blue-600 hover:bg-blue-700 text-white font-medium py-3 px-4 rounded-lg transition-colors duration-300 cursor-pointer"
          >
            Войти
          </button>
        </div>
      </form>

      <div class="flex flex-col items-center space-y-3 pt-4 border-t border-gray-200">
        <router-link
          to="/resetPassword"
          class="text-sm text-blue-600 hover:text-blue-800 transition-colors"
        >
          Забыли пароль?
        </router-link>

        <router-link
          to="/registration"
          class="text-sm text-blue-600 hover:text-blue-800 transition-colors"
        >
          Создать аккаунт
        </router-link>
      </div>
    </div>
  </div>
</template>
