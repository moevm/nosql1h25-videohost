<script setup lang="ts">
import { ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'

import newPassword from '../api/newPassword'

const route = useRoute()
const router = useRouter()
const password = ref('')
</script>

<template>
  <div class="min-h-screen flex items-center justify-center bg-gray-100 p-4">
    <div class="w-full max-w-md bg-white rounded-xl shadow-lg p-8 space-y-6">
      <div class="space-y-6">
        <div class="text-center space-y-2">
          <svg
            class="w-12 h-12 mx-auto text-blue-500"
            fill="none"
            stroke="currentColor"
            viewBox="0 0 24 24"
            xmlns="http://www.w3.org/2000/svg"
          >
            <path
              stroke-linecap="round"
              stroke-linejoin="round"
              stroke-width="2"
              d="M15 7a2 2 0 012 2m4 0a6 6 0 01-7.743 5.743L11 17H9v2H7v2H4a1 1 0 01-1-1v-2.586a1 1 0 01.293-.707l5.964-5.964A6 6 0 1121 9z"
            ></path>
          </svg>
          <h1 class="text-3xl font-bold text-gray-800">Введите новый пароль</h1>
        </div>

        <form @submit.prevent="newPassword(password, route.params.id, router)" class="space-y-4">
          <input
            type="password"
            v-model="password"
            placeholder="Пароль"
            class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2"
          />

          <button
            type="submit"
            class="w-full bg-blue-600 hover:bg-blue-700 text-white font-medium py-3 px-4 rounded-lg transition-colors duration-300"
          >
            Изменить пароль
          </button>
        </form>
      </div>

      <div class="pt-4 border-t border-gray-200 text-center">
        <router-link
          to="/login"
          class="text-sm text-blue-600 hover:text-blue-800 transition-colors inline-flex items-center"
        >
          <svg
            class="w-4 h-4 mr-1"
            fill="none"
            stroke="currentColor"
            viewBox="0 0 24 24"
            xmlns="http://www.w3.org/2000/svg"
          >
            <path
              stroke-linecap="round"
              stroke-linejoin="round"
              stroke-width="2"
              d="M10 19l-7-7m0 0l7-7m-7 7h18"
            ></path>
          </svg>
          Вернуться к авторизации
        </router-link>
      </div>
    </div>
  </div>
</template>
