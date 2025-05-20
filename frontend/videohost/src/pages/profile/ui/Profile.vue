<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useFormData } from '@/shared/helpers/useFormData'
import { useProfileActions } from '@/pages/profile/api/useProfile'

const { formatDate } = useFormData()
const router = useRouter()

const user = ref<any>({} as any)
const videoList = ref<any[]>([])
const followers = ref<any[]>([])
const me = ref({ userId: '' } as any)
const isMyProfile = ref(false)
const isFollowing = ref(false)

const showEditModal = ref(false)
const newUsername = ref('')
const showUploadModal = ref(false)
const uploadForm = ref({ title: '', description: '', tags: '', file: null as File | null })
const showVideoEditModal = ref(false)
const videoEditForm = ref({ id: '', title: '', description: '', tags: '' })

const {
  getUser,
  getVideo,
  getFollowers,
  getFollowings,
  updateUser,
  uploadVideo,
  changeVisibilityVideo,
  deleteVideo,
  updateVideo,
  getMe,
} = useProfileActions(user, videoList, followers, me, isMyProfile, isFollowing)

const openEditModal = () => {
  showEditModal.value = true
  newUsername.value = user.value.username
}
const closeEditModal = () => {
  showEditModal.value = false
  newUsername.value = ''
}
const saveUserEdit = async () => {
  await updateUser(newUsername.value)
  router.push("/login")
  closeEditModal()
}

const openUploadModal = () => (showUploadModal.value = true)
const closeUploadModal = () => {
  showUploadModal.value = false
  uploadForm.value = { title: '', description: '', tags: '', file: null }
}
const onFileChange = (e: Event) => {
  const input = e.target as HTMLInputElement
  uploadForm.value.file = input.files?.[0] ?? null
}
const submitUpload = async () => {
  const tagsArray = uploadForm.value.tags
    .split(',')
    .map((t) => t.trim())
    .filter((t) => t)

  await uploadVideo(
    uploadForm.value.title,
    uploadForm.value.description,
    tagsArray,
    uploadForm.value.file,
  )
  closeUploadModal()
}

const editVideo = (v: any) => {
  videoEditForm.value = {
    id: v.videoId,
    title: v.title,
    description: v.description,
    tags: v.tags.join(','),
  }
  showVideoEditModal.value = true
}
const closeVideoEditModal = () => (showVideoEditModal.value = false)
const saveVideoEdit = async () => {
  const tagsArray = videoEditForm.value.tags
    .split(',')
    .map((t) => t.trim())
    .filter((t) => t)

  await updateVideo(
    videoEditForm.value.id,
    videoEditForm.value.title,
    videoEditForm.value.description,
    tagsArray,
  )
  closeVideoEditModal()
}

const loadFollowers = () => getFollowers()
const loadFollowings = () => getFollowings()

const toggleVisibility = (id: string) => changeVisibilityVideo(id)
const removeVideo = (id: string) => deleteVideo(id)
const goToVideo = (id: string) => router.push(`/video/${id}`)
const goToProfile = (id: string) => router.push(`/profile/${id}`)

getUser()
getVideo()
getFollowers()
getFollowings()
getMe()
</script>

<template>
  <div
    v-if="showEditModal"
    class="fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-50 p-4"
  >
    <div class="bg-white rounded-xl shadow-xl w-full max-w-md p-6">
      <div class="flex justify-between items-center mb-6">
        <h3 class="text-2xl font-bold">Изменение данных</h3>
        <button @click="closeEditModal" class="text-gray-500 hover:text-gray-700">✕</button>
      </div>
      <form @submit.prevent="saveUserEdit" class="space-y-4">
        <label class="block text-sm font-medium text-gray-700">Новый логин</label>
        <input
          v-model="newUsername"
          type="text"
          required
          class="w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500"
          placeholder="Введите новый логин"
        />
        <div class="flex justify-end gap-3 pt-2">
          <button
            type="button"
            @click="closeEditModal"
            class="px-4 py-2 text-gray-700 hover:text-gray-900"
          >
            Отмена
          </button>
          <button
            type="submit"
            class="bg-blue-600 text-white px-6 py-2 rounded-lg hover:bg-blue-700"
          >
            Сохранить
          </button>
        </div>
      </form>
    </div>
  </div>

  <div
    v-if="showUploadModal"
    class="fixed inset-0 z-40 flex items-center justify-center bg-black bg-opacity-50 p-4"
  >
    <div class="bg-white rounded-xl shadow-xl w-full max-w-2xl p-6">
      <div class="flex justify-between items-center mb-6">
        <h3 class="text-2xl font-bold">Загрузка нового видео</h3>
        <button @click="closeUploadModal" class="text-gray-500 hover:text-gray-700">✕</button>
      </div>
      <form @submit.prevent="submitUpload" class="space-y-4">
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">Название видео</label>
          <input
            v-model="uploadForm.title"
            type="text"
            required
            class="w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500"
            placeholder="Введите название"
          />
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">Описание</label>
          <textarea
            v-model="uploadForm.description"
            rows="3"
            required
            class="w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500"
            placeholder="Добавьте описание видео"
          ></textarea>
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1"
            >Теги (через запятую, без пробелов)</label
          >
          <input
            v-model="uploadForm.tags"
            type="text"
            class="w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500"
            placeholder="tag1,tag2,tag3"
          />
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">Видеофайл (MP4)</label>
          <input type="file" accept="video/mp4" required @change="onFileChange" class="w-full" />
        </div>
        <div class="flex justify-end gap-3 pt-4">
          <button
            type="button"
            @click="closeUploadModal"
            class="px-4 py-2 text-gray-700 hover:text-gray-900"
          >
            Отменить
          </button>
          <button
            type="submit"
            class="bg-blue-600 text-white px-6 py-2 rounded-lg hover:bg-blue-700"
          >
            Загрузить
          </button>
        </div>
      </form>
    </div>
  </div>

  <div
    v-if="showVideoEditModal"
    class="fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-50 p-4"
  >
    <div class="bg-white rounded-xl shadow-xl w-full max-w-md p-6">
      <div class="flex justify-between items-center mb-6">
        <h3 class="text-2xl font-bold">Редактирование видео</h3>
        <button
          @click="closeVideoEditModal"
          class="cursor-pointer text-gray-500 hover:text-gray-700"
        >
          ✕
        </button>
      </div>
      <form @submit.prevent="saveVideoEdit" class="space-y-4">
        <input
          v-model="videoEditForm.title"
          placeholder="Название"
          class="w-full px-4 py-2 border rounded-lg"
        />
        <textarea
          v-model="videoEditForm.description"
          placeholder="Описание"
          rows="3"
          class="w-full px-4 py-2 border rounded-lg"
        ></textarea>
        <input
          v-model="videoEditForm.tags"
          placeholder="Теги через запятую, без пробелов"
          class="w-full px-4 py-2 border rounded-lg"
        />
        <div class="flex justify-end gap-3 pt-2">
          <button type="button" @click="closeVideoEditModal" class="cursor-pointer px-4 py-2">
            Отмена
          </button>
          <button
            type="submit"
            class="cursor-pointer bg-blue-600 text-white px-6 py-2 rounded-lg hover:bg-blue-700"
          >
            Сохранить
          </button>
        </div>
      </form>
    </div>
  </div>

  <div class="min-h-screen bg-gray-100 p-6 flex gap-6">
    <div class="w-80 flex flex-col gap-6">
      <div class="bg-white rounded-xl shadow-lg p-6 space-y-4">
        <div class="flex items-center justify-between">
          <h2 class="text-xl font-bold">Профиль</h2>
          <button
            v-if="isMyProfile"
            @click="openEditModal"
            class="text-blue-600 hover:text-blue-800"
          >
            Изменить
          </button>
        </div>
        <div class="space-y-2">
          <div class="flex items-center gap-2">
            <span class="font-medium text-blue-600">Логин:</span>
            <span class="text-gray-600">{{ user.username }}</span>
          </div>
          <div v-if="isMyProfile" class="flex items-center gap-2">
            <span class="font-medium text-blue-600">Email:</span>
            <span class="text-gray-600">{{ user.email }}</span>
          </div>
          <div class="flex items-center gap-2">
            <span class="font-medium text-blue-600">Регистрация:</span>
            <span class="text-gray-600">{{ formatDate(new Date(user.registrationDate)) }}</span>
          </div>
          <div class="flex items-center gap-2">
            <span class="font-medium text-blue-600">Подписчики:</span>
            <span class="text-gray-600">{{ user.subscriberCount }}</span>
          </div>
          <div class="flex items-center gap-2">
            <span
              v-if="isMyProfile"
              :class="user.blocked ? 'text-red-600' : 'text-green-600'"
              class="font-medium"
            >
              {{ user.blocked ? 'Заблокирован' : 'Активен' }}
            </span>
          </div>
        </div>
      </div>

      <div class="bg-white rounded-xl shadow-lg p-6 space-y-2">
        <button
          @click="loadFollowers"
          class="w-full py-3 bg-gray-50 hover:bg-gray-100 rounded-lg text-left"
        >
          Посмотреть подписчиков
        </button>
        <button
          @click="loadFollowings"
          class="w-full py-3 bg-gray-50 hover:bg-gray-100 rounded-lg text-left"
        >
          Посмотреть, на кого подписан
        </button>
      </div>

      <div v-if="isFollowing" class="space-y-3">
        <div
          v-for="f in followers"
          :key="f.userId"
          @click="goToProfile(f.userId)"
          class="flex items-center p-4 bg-white rounded-lg shadow-sm hover:shadow-md cursor-pointer"
        >
          <div class="flex-1 min-w-0">
            <p class="font-medium text-gray-800 truncate">{{ f.username }}</p>
            <p v-if="f.email" class="text-sm text-gray-500 truncate">{{ f.email }}</p>
          </div>
          <div class="text-xs text-gray-500 text-right">
            <div>Подписчиков: {{ f.subscriberCount }}</div>
            <div>Рег.: {{ formatDate(new Date(f.registrationDate)) }}</div>
          </div>
        </div>
      </div>
    </div>

    <div class="flex-1 flex flex-col gap-6">
      <div class="bg-white rounded-xl shadow-lg p-6">
        <div class="flex items-center justify-between mb-6">
          <h2 class="text-xl font-bold">{{ isMyProfile ? 'Мои видео' : 'Видео пользователя' }}</h2>
          <button
            v-if="isMyProfile"
            @click="openUploadModal"
            class="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700"
          >
            Добавить видео
          </button>
        </div>

        <div class="space-y-4">
          <div
            v-for="v in videoList"
            :key="v.videoId"
            class="group p-4 border rounded-lg hover:bg-gray-50 transition-colors"
          >
            <div class="flex items-center justify-between gap-6">
              <video
                :src="v.s3Key"
                controls
                class="w-64 h-36 rounded-xl bg-gray-100 object-cover"
              />

              <div class="flex-1 min-w-0">
                <h3 class="font-semibold text-gray-800 truncate">{{ v.title }}</h3>
                <p class="text-gray-600 text-sm mt-1 line-clamp-2">{{ v.description }}</p>
                <div class="text-xs text-gray-500 mt-2 flex flex-wrap gap-2">
                  <span>Автор: {{ v.author }}</span>
                  <span>Просмотры: {{ v.views }}</span>
                  <span>Комментарии: {{ v.comments.length }}</span>
                  <template v-for="t in v.tags" :key="t">
                    <span class="bg-gray-200 px-2 py-0.5 rounded">#{{ t }}</span>
                  </template>
                </div>
              </div>

              <div class="flex items-center gap-4 text-gray-600">
                <div class="flex items-center gap-1.5 text-green-600">
                  <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      stroke-width="2"
                      d="M14 10h4.764a2 2 0 011.789 2.894l-3.5 7A2 2 0 0115.263 21h-4.017c-.163 0-.326-.02-.485-.06L7 20m7-10V5a2 2 0 00-2-2h-.095c-.5 0-.905.405-.905.905 0 .714-.211 1.412-.608 2.006L7 11v9m7-10h-2M7 20H5a2 2 0 01-2-2v-6a2 2 0 012-2h2.5"
                    />
                  </svg>
                  <span class="font-medium">{{ v.reactions.likeCount }}</span>
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
                  <span class="font-medium">{{ v.reactions.dislikeCount }}</span>
                </div>
              </div>

              <div class="flex flex-col gap-2 items-end">
                <div class="flex gap-2">
                  <button
                    v-if="isMyProfile"
                    @click="editVideo(v)"
                    class="px-3 py-2 bg-yellow-100 text-yellow-800 rounded-md hover:bg-yellow-200"
                  >
                    Редактировать
                  </button>
                  <button
                    @click="toggleVisibility(v.videoId)"
                    v-if="isMyProfile"
                    :class="
                      v.isVideoHidden
                        ? 'bg-green-100 text-green-800 hover:bg-green-200'
                        : 'bg-blue-100 text-blue-800 hover:bg-blue-200'
                    "
                    class="px-3 py-2 rounded-md"
                  >
                    {{ v.isVideoHidden ? 'Показать' : 'Скрыть' }}
                  </button>
                  <button
                    v-if="isMyProfile"
                    @click="removeVideo(v.videoId)"
                    class="px-3 py-2 bg-red-100 text-red-800 rounded-md hover:bg-red-200"
                  >
                    Удалить
                  </button>
                </div>
                <span v-if="isMyProfile" class="text-xs text-gray-500">
                  Статус: {{ v.isVideoHidden ? 'Скрыто' : 'Видно всем' }}
                </span>
                <button
                  @click="goToVideo(v.videoId)"
                  class="cursor-pointer text-blue-600 hover:underline text-sm"
                >
                  Перейти к видео
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
video::-webkit-media-controls-play-button {
  background-color: #4f46e5;
  border-radius: 50%;
}
video::-webkit-media-controls-timeline {
  background-color: rgba(79, 70, 229, 0.2);
  border-radius: 4px;
  margin: 0 10px;
}
video::-webkit-media-controls-current-time-display,
video::-webkit-media-controls-time-remaining-display {
  font-size: 0.8em;
  color: #4f46e5;
}
</style>
