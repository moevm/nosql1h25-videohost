<script setup lang="ts">
import { ref, onMounted } from 'vue'

import { useNotify } from '@/shared/helpers/useNotify'
import { useFormData } from '@/shared/helpers/useFormData'

const { formatDate } = useFormData()
const { error, success } = useNotify()

const videos = ref([])

const getVideos = async () => {
  const response = await fetch(`${import.meta.env.VITE_API}video/all`, {
    method: 'GET',
    headers: {
      Authorization: `Bearer ${localStorage.getItem('token')}`,
    },
  })

  videos.value = await response.json()
}

const toggleVisibility = async (id: string) => {
  const response = await fetch(`${import.meta.env.VITE_API}admin/hidden/${id}`, {
    method: 'PATCH',
    headers: {
      Authorization: `Bearer ${localStorage.getItem('token')}`,
    },
  })

  if (!response.ok) {
    return error('Видео', 'Не удалось заблокировать видео')
  }
  success('Видео', 'Вы успешно заблокировали видео')
  await getVideos()
}

onMounted(async () => {
  await getVideos()
})
</script>

<template>
  <div class="bg-white rounded-xl shadow-lg p-6 h-[700px] overflow-y-auto">
    <div class="space-y-4">
      <div
        v-for="(video, index) in videos"
        :key="index"
        class="p-4 flex gap-6 border-2 rounded-xl cursor-pointer hover:border-blue-300 transition-colors bg-gray-50 hover:bg-white"
      >
        <video :src="video.s3Key" class="w-64 h-36 rounded-lg object-cover shadow-sm"></video>

        <div class="flex-1 flex flex-col justify-between">
          <div class="space-y-2">
            <div class="space-y-1">
              <h3 class="text-2xl font-bold text-gray-800">
                {{ video.title }}
              </h3>
              <p class="text-lg text-blue-600 font-medium">Автор: {{ video.author }}</p>
            </div>

            <p class="text-gray-600 text-base pr-4">
              {{ video.description }}
            </p>

            <div class="flex flex-wrap gap-2">
              <span
                v-for="(tag, tagIndex) in video.tags"
                :key="tagIndex"
                class="px-3 py-1 bg-gray-200 text-gray-700 rounded-full text-sm"
              >
                #{{ tag }}
              </span>
            </div>
          </div>

          <div class="flex flex-wrap gap-4 items-center mt-4">
            <div class="text-sm text-gray-500">
              Опубликовано: {{ formatDate(new Date(video.uploadDate)) }}
            </div>
          </div>

          <button
            @click="toggleVisibility(video.videoId)"
            class="cursor-pointer px-4 py-2 bg-blue-500 hover:bg-blue-600 text-white rounded-lg transition-colors"
          >
            Скрыть видео
          </button>
        </div>
      </div>
    </div>
  </div>
</template>
