<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'

import registration from '../api/registration'
import type IRegisterData from '../model'

const router = useRouter()

const username = ref('')
const password = ref('')
const email = ref('')

const registerData = ref<IRegisterData>({
  username: username.value,
  email: email.value,
  password: password.value,
})
</script>

<template>
  <div class="min-h-screen flex items-center justify-center bg-gray-100 p-4">
    <div class="w-full max-w-md bg-white rounded-xl shadow-lg p-8 space-y-6">
      <span class="cursor-pointer" @click="router.go(-1)">
        <svg
          width="24"
          height="24"
          viewBox="0 0 24 24"
          fill="none"
          xmlns="http://www.w3.org/2000/svg"
        >
          <path
            d="M15 18L9 12L15 6"
            stroke="currentColor"
            stroke-width="2"
            stroke-linecap="round"
            stroke-linejoin="round"
          />
        </svg>
      </span>
      <form @submit.prevent="registration(registerData, router)" class="space-y-6">
        <h1 class="text-3xl font-bold text-gray-800 text-center">Регистрация</h1>

        <div class="space-y-4">
          <input
            v-model="registerData.username"
            type="text"
            placeholder="Логин"
            class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 transition-colors"
          />

          <input
            v-model="registerData.email"
            type="email"
            placeholder="Email"
            class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 transition-colors"
          />

          <input
            v-model="registerData.password"
            type="password"
            placeholder="Пароль"
            class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 transition-colors"
          />

          <button
            type="submit"
            class="w-full bg-blue-600 hover:bg-blue-700 text-white font-medium py-3 px-4 rounded-lg transition-colors duration-300"
          >
            Зарегистрироваться
          </button>
        </div>
      </form>

      <div class="flex flex-col items-center space-y-3 pt-4 border-t border-gray-200">
        <router-link
          to="/login"
          class="text-sm text-blue-600 hover:text-blue-800 transition-colors"
        >
          Уже есть аккаунт? Войти
        </router-link>

        <router-link
          to="/resetPassword"
          class="text-sm text-blue-600 hover:text-blue-800 transition-colors"
        >
          Забыли пароль?
        </router-link>
      </div>
    </div>
  </div>
</template>
