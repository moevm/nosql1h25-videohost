<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'

import type IVideo from '@/shared/model/IVideo'
import type IUser from '@/pages/video/model/IUser'

import { useFormData } from '@/shared/helpers/useFormData'
import { useVideoActions } from '@/pages/video/api/useVideo'

const route = useRoute()
const router = useRouter()
const { formatDate } = useFormData()

const video = ref<IVideo>({
  userId: '',
  title: '',
  description: '',
  s3Key: '',
  views: 0,
  comments: [],
  reactions: { likeCount: 0, dislikeCount: 0 },
  uploadDate: '',
  tags: [],
  videoId: '',
  author: '',
  isVideoHidden: false,
  userReaction: null,
  fileName: '',
})
const user = ref<IUser>({
  username: '',
  email: '',
  registrationDate: '',
  subscriberCount: 0,
  blocked: false,
  roles: [],
  isUserSubscribe: false,
  videos: [],
})
const commentText = ref('')
const reactionType = ref<'LIKE' | 'DISLIKE'>('LIKE')
const editingCommentId = ref<string | null>(null)
const editingText = ref('')

const {
  getVideo,
  getUser,
  addComment,
  deleteComment,
  updateComment,
  addReaction,
  subscribe,
  unsubscribe,
  downloadVideo,
} = useVideoActions(video, user)

onMounted(async () => {
  await getVideo(route.params.id as string)
  await getUser()
})

const onAddComment = async () => {
  await addComment(route.params.id as string, commentText.value)
  commentText.value = ''
}
const onDeleteComment = (id: string) => deleteComment(id)
const onEditInit = (id: string, text: string) => {
  editingCommentId.value = id
  editingText.value = text
}
const onSaveEdit = () => {
  if (editingCommentId.value)
    updateComment(editingCommentId.value, editingText.value).then(
      () => (editingCommentId.value = null),
    )
}
const onCancelEdit = () => {
  editingCommentId.value = null
  editingText.value = ''
}
const onReact = (type: 'LIKE' | 'DISLIKE') => {
  reactionType.value = type
  addReaction(route.params.id as string, type)
}
const onSubscribe = () => subscribe(video.value.userId)
const onUnsubscribe = () => unsubscribe(video.value.userId)
const onDownload = () => downloadVideo(video.value.fileName)
</script>

<template>
  <div class="bg-white rounded-xl shadow-lg p-6">
    <div class="space-y-6">
      <div class="p-4 border rounded-lg hover:border-blue-200 transition-colors">
        <video
          controls
          :src="video.s3Key"
          class="w-full object-cover rounded-lg mb-4 aspect-video"
        ></video>

        <div class="mb-4 space-y-2">
          <div
            @click="router.push(`/profile/${video.userId}`)"
            class="flex items-center gap-3 cursor-pointer"
          >
            <div>
              <div class="text-lg font-semibold text-blue-600 hover:underline">
                {{ video.author }}
              </div>
              <div class="text-sm text-gray-500">
                {{ user.subscriberCount.toLocaleString() }} подписчиков
              </div>
            </div>
          </div>

          <h2 class="text-2xl font-bold text-gray-900">{{ video.title }}</h2>

          <div class="text-gray-700 space-y-1">
            <p class="leading-relaxed">{{ video.description }}</p>
            <div class="flex flex-wrap gap-2">
              <span
                v-for="tag in video.tags"
                :key="tag"
                class="text-blue-600 bg-blue-100 px-2 py-0.5 rounded-full text-sm hover:bg-blue-200 cursor-pointer"
              >
                #{{ tag }}
              </span>
            </div>
          </div>

          <div class="flex items-center gap-2 text-sm text-gray-500">
            <svg
              class="w-5 h-5 text-gray-400"
              fill="none"
              stroke="currentColor"
              viewBox="0 0 24 24"
            >
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
            <span class="font-medium">{{ video.views.toLocaleString() }} просмотров</span>
            <span>•</span>
            <span>{{ formatDate(new Date(video.uploadDate)) }}</span>
          </div>
        </div>

        <div class="flex items-center justify-between mb-4">
          <div class="flex items-center gap-6">
            <button
              @click="
                () => {
                  reactionType = 'LIKE'
                  onReact('LIKE')
                }
              "
            >
              <div class="cursor-pointer flex items-center gap-1.5 text-green-600">
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
            </button>
            <button
              @click="
                () => {
                  reactionType = 'DISLIKE'
                  onReact('DISLIKE')
                }
              "
            >
              <div class="cursor-pointer flex items-center gap-1.5 text-red-600">
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
            </button>
          </div>

          <button
            v-if="user.isUserSubscribe"
            @click="() => onUnsubscribe()"
            class="cursor-pointer px-4 py-2 rounded-full text-sm font-medium bg-gray-200 hover:bg-gray-300 text-gray-700 transition-colors"
          >
            Отписаться
          </button>
          <button
            v-else
            @click="() => onSubscribe()"
            class="cursor-pointer px-4 py-2 rounded-full text-sm font-medium bg-blue-600 hover:bg-blue-700 text-white transition-colors"
          >
            Подписаться
          </button>
        </div>

        <button
          @click="() => onDownload()"
          class="cursor-pointer inline-flex items-center bg-blue-600 hover:bg-blue-700 text-white px-4 py-2.5 rounded-lg transition-colors"
        >
          Скачать
        </button>
      </div>

      <div class="pt-6 border-t border-gray-200 space-y-5">
        <form @submit.prevent="onAddComment" class="mb-4">
          <textarea
            v-model="commentText"
            class="w-full px-4 py-3 border rounded-lg focus:ring-2 focus:ring-blue-500 outline-none transition"
            placeholder="Напишите комментарий..."
            rows="3"
          ></textarea>
          <button
            type="submit"
            class="cursor-pointer mt-2 bg-blue-600 hover:bg-blue-700 text-white px-5 py-2.5 rounded-lg transition-colors"
          >
            Опубликовать
          </button>
        </form>

        <div v-for="item in video.comments" :key="item.commentId">
          <div
            class="p-4 bg-white rounded-lg shadow-sm border border-gray-100 hover:shadow-md transition-shadow"
          >
            <div class="flex justify-between">
              <div class="flex items-start gap-3">
                <div class="w-8 h-8 rounded-full bg-blue-100 flex items-center justify-center">
                  <span class="text-sm font-medium text-blue-600">{{ item.author }}</span>
                </div>
                <div>
                  <div class="text-sm font-medium text-gray-900">{{ item.author }}</div>
                  <div class="text-xs text-gray-500">
                    {{ formatDate(new Date(item.creationDate)) }}
                  </div>
                </div>
              </div>

              <div class="flex items-center gap-2">
                <template v-if="item.isUserComment">
                  <template v-if="editingCommentId === item.commentId">
                    <button
                      @click="() => onSaveEdit()"
                      class="cursor-pointer px-2 py-1 text-sm text-white bg-green-600 rounded hover:bg-green-700"
                    >
                      Сохранить
                    </button>
                    <button
                      @click="onCancelEdit()"
                      class="cursor-pointer px-2 py-1 text-sm text-gray-700 bg-gray-200 rounded hover:bg-gray-300"
                    >
                      Отмена
                    </button>
                  </template>
                  <template v-else>
                    <button
                      @click="
                        () => {
                          onEditInit(item.commentId, editingText)
                        }
                      "
                      class="cursor-pointer p-1.5 rounded-full hover:bg-gray-100 text-gray-600 hover:text-blue-600"
                    >
                      ✏️
                    </button>
                    <button
                      @click="() => onDeleteComment(item.commentId)"
                      class="cursor-pointer p-1.5 rounded-full hover:bg-gray-100 text-gray-600 hover:text-red-600"
                    >
                      🗑️
                    </button>
                  </template>
                </template>
              </div>
            </div>

            <div class="mt-2">
              <textarea
                v-if="editingCommentId === item.commentId"
                v-model="editingText"
                class="w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500"
                rows="3"
              ></textarea>
              <p v-else class="text-gray-700 whitespace-pre-wrap">{{ item.text }}</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
