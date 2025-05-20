<template>
  <div class="min-h-screen bg-gray-100 flex flex-col justify-center items-center px-4">
    <div class="max-w-lg w-full text-center space-y-8">
      <h1 class="text-9xl font-bold text-indigo-600 animate-pulse">
        404
      </h1>

      <div class="space-y-4">
        <h2 class="text-3xl font-semibold text-gray-800">
          Страница не найдена
        </h2>
        <p class="text-gray-600 text-lg">
          Похоже, мы не можем найти нужную вам страницу. Возможно, она была перемещена или удалена.
        </p>
      </div>

      <router-link
        to="/"
        class="inline-block px-6 py-3 bg-indigo-600 text-white rounded-md 
               hover:bg-indigo-700 transition-colors duration-200
               text-lg font-medium"
      >
        Вернуться на главную
      </router-link>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'

const router = useRouter()
</script>

<style>
@keyframes pulse {
  0%, 100% {
    opacity: 1;
  }
  50% {
    opacity: 0.5;
  }
}
.animate-pulse {
  animation: pulse 2s cubic-bezier(0.4, 0, 0.6, 1) infinite;
}
</style>
