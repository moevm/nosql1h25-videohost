<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'

import { useFormData } from '@/shared/helpers/useFormData'
import getMe from '@/shared/api/getMe'
import getVideos from '../api/getVideos'

import type IMe from '@/shared/model/IMe'
import type IVideo from '@/shared/model/IVideo'

const router = useRouter()
const { formatDate } = useFormData()

const videos = ref<IVideo[]>([])
const showAllVideos = ref(true)
const isAuth = ref<boolean>(false)

const me = ref<IMe>({
  userId: '',
  username: '',
  email: '',
  registrationDate: '',
  subscriberCount: 0,
  blocked: false,
  roles: [],
})

const toggleVideos = async () => {
  showAllVideos.value = !showAllVideos.value
  videos.value = await getVideos(me.value.userId, showAllVideos)
}

onMounted(async () => {
  me.value = await getMe()
  videos.value = await getVideos(me.value?.userId, showAllVideos)

  isAuth.value = await getMe(true)
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
      `${import.meta.env.VITE_API}search/videos?` +
        new URLSearchParams({
          uploadedAfter: registeredAfter,
          uploadedBefore: registeredBefore,
          title: formData.value.text1.toLowerCase(),
          description: formData.value.text2.toLowerCase(),
          minViews: formData.value.number1,
          maxViews: formData.value.number2,
        }),
      {
        method: 'GET',
        headers: {
          Authorization: `Bearer ${localStorage.getItem('token')}`,
        },
      },
    )
  
    const data = await response.json()
    videos.value = data
  }else{
    const response = await fetch(
      `${import.meta.env.VITE_API}search/videos?` +
        new URLSearchParams({
          uploadedAfter: registeredAfter,
          uploadedBefore: registeredBefore,
          title: formData.value.text1.toLowerCase(),
          description: formData.value.text2.toLowerCase(),
          minViews: formData.value.number1,
          maxViews: formData.value.number2,
        }),
      {
        method: 'GET',
      },
    )
  
    const data = await response.json()
    videos.value = data
  }

}
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
        placeholder="Название"
        class="px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 transition-colors w-full"
      />
      <input
        type="text"
        v-model="formData.text2"
        placeholder="Описание"
        class="px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 transition-colors w-full"
      />
      <!-- <input
      type="text"
      v-model="formData.text3"
      placeholder="Хештеги"
      class="px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 transition-colors w-full"
    /> -->

      <!-- Числовые поля -->
      <input
        type="number"
        v-model="formData.number1"
        placeholder="Минимальное количество просмотров"
        class="px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 transition-colors w-full"
      />
      <input
        type="number"
        v-model="formData.number2"
        placeholder="Максимальное количество просмотров"
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

  <button
    @click="toggleVideos"
    v-if="isAuth"
    class="w-full cursor-pointer bg-gradient-to-r from-blue-500 to-purple-600 text-white py-3.5 rounded-lg hover:from-blue-600 hover:to-purple-700 transition-all mb-6 shadow-lg font-semibold text-lg flex items-center justify-center gap-2 group"
  >
    <svg
      class="w-5 h-5 transition-transform group-hover:translate-x-1"
      fill="none"
      stroke="currentColor"
      viewBox="0 0 24 24"
    >
      <path
        stroke-linecap="round"
        stroke-linejoin="round"
        stroke-width="2"
        d="M8 7h12m0 0l-4-4m4 4l-4 4m0 6H4m0 0l4 4m-4-4l4-4"
      />
    </svg>
    {{ showAllVideos ? 'Подписки' : 'Все видео' }}
  </button>

  <div class="bg-white rounded-xl shadow-lg p-6 h-[700px] overflow-y-auto">
    <div class="space-y-4">
      <div
        v-for="(video, index) in videos"
        :key="index"
        @click="router.push(`/video/${video.videoId}`)"
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

            <div class="flex items-center gap-4 text-sm">
              <div class="flex items-center gap-1.5 text-blue-600">
                <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                    d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"
                  />
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                    d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z"
                  />
                </svg>
                <span class="font-medium">{{ video.views }}</span>
              </div>

              <div class="flex items-center gap-1.5 text-green-600">
                <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                    d="M14 10h4.764a2 2 0 011.789 2.894l-3.5 7A2 2 0 0115.263 21h-4.017c-.163 0-.326-.02-.485-.06L7 20m7-10V5a2 2 0 00-2-2h-.095c-.5 0-.905.405-.905.905 0 .714-.211 1.412-.608 2.006L7 11v9m7-10h-2M7 20H5a2 2 0 01-2-2v-6a2 2 0 012-2h2.5"
                  />
                </svg>
                <span class="font-medium">{{ video.reactions.likeCount }}</span>
              </div>

              <div class="flex items-center gap-1.5 text-red-600">
                <div class="rotate-180">
                  <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      stroke-width="2"
                      d="M14 10h4.764a2 2 0 011.789 2.894l-3.5 7A2 2 0 0115.263 21h-4.017c-.163 0-.326-.02-.485-.06L7 20m7-10V5a2 2 0 00-2-2h-.095c-.5 0-.905.405-.905.905 0 .714-.211 1.412-.608 2.006L7 11v9m7-10h-2M7 20H5a2 2 0 01-2-2v-6a2 2 0 012-2h2.5"
                    />
                  </svg>
                </div>
                <span class="font-medium">{{ video.reactions.dislikeCount }}</span>
              </div>

              <div class="flex items-center gap-1.5 text-purple-600">
                <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                    d="M8 12h.01M12 12h.01M16 12h.01M21 12c0 4.418-4.03 8-9 8a9.863 9.863 0 01-4.255-.949L3 20l1.395-3.72C3.512 15.042 3 13.574 3 12c0-4.418 4.03-8 9-8s9 3.582 9 8z"
                  />
                </svg>
                <span class="font-medium">{{ video.comments.length }}</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
