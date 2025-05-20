<script setup lang="ts">
import { ref } from 'vue'

import resetPassword from '../api/resetPassword'

const email = ref('')
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
              d="M12 15v2m-6 4h12a2 2 0 002-2v-6a2 2 0 00-2-2H6a2 2 0 00-2 2v6a2 2 0 002 2zm10-10V7a4 4 0 00-8 0v4h8z"
            ></path>
          </svg>
          <h1 class="text-3xl font-bold text-gray-800">Восстановление пароля</h1>
        </div>

        <p class="text-gray-600 text-center">
          Для восстановления пароля введите свою электронную почту или логин. На почту, связанную с
          аккаунтом, будет выслана ссылка для восстановления пароля.
        </p>

        <form @submit.prevent="resetPassword(email)" class="space-y-4">
          <input
            v-model="email"
            type="email"
            placeholder="Введите электронную почту или логин"
            class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 transition-colors;"
          />

          <button
            type="submit"
            class="w-full bg-blue-600 hover:bg-blue-700 text-white font-medium py-3 px-4 rounded-lg transition-colors duration-300"
          >
            Прислать ссылку
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
