<script setup lang="ts">
import { ref } from 'vue'

import { useNotify } from '@/shared/helpers/useNotify'

const showImportOptions = ref(false)
const showExportOptions = ref(false)
const importFormat = ref('JSON')
const exportFormat = ref('JSON')
const { error, success } = useNotify()

const toggleImportOptions = () => {
  showImportOptions.value = !showImportOptions.value
  showExportOptions.value = false
}

const toggleExportOptions = () => {
  showExportOptions.value = !showExportOptions.value
  showImportOptions.value = false
}

const selectedFile = ref<File | null>(null)

const handleFileUpload = (event: Event) => {
  const input = event.target as HTMLInputElement
  if (input.files?.length) {
    const file = input.files[0]

    selectedFile.value = file
  }
}

const handleImport = async () => {
  if (!selectedFile.value) {
    alert('Пожалуйста, выберите файл')
    return
  }

  const formData = new FormData()
  formData.append('file', selectedFile.value)
  formData.append('format', importFormat.value)

  const response = await fetch(`${import.meta.env.VITE_API}transfer/import`, {
    method: 'POST',
    headers: {
      Authorization: `Bearer ${localStorage.getItem('token')}`,
    },
    body: formData,
  })

  if (!response.ok) {
    return error("Импорт", 'Ошибка импорта')
  }

  success("Импорт", 'Файл успешно импортирован!')
}

const handleExport = async () => {
  const response = await fetch(
    `${import.meta.env.VITE_API}transfer/export?format=${exportFormat.value}`,
    {
      method: 'GET',
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`,
      },
    },
  )

  const blob = await response.blob()
  const filename =
    response.headers.get('content-disposition')?.split('filename=')[1] ||
    `export.${exportFormat.value.toLowerCase()}`

  const url = window.URL.createObjectURL(blob)
  const a = document.createElement('a')

  a.href = url
  a.download = filename
  document.body.appendChild(a)
  a.click()
  document.body.removeChild(a)
  window.URL.revokeObjectURL(url)
}
</script>

<template>
  <div class="p-4 max-w-md mx-auto">
    <div class="flex gap-4 mb-6">
      <button
        @click="toggleImportOptions"
        class="cursor-pointer px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600 transition"
      >
        Импорт
      </button>

      <button
        @click="toggleExportOptions"
        class="cursor-pointer px-4 py-2 bg-green-500 text-white rounded hover:bg-green-600 transition"
      >
        Экспорт
      </button>
    </div>

    <div v-if="showImportOptions" class="p-4 bg-gray-50 rounded-lg shadow mb-4">
      <div class="mb-4">
        <label class="block mb-2 font-medium">Формат импорта:</label>
        <div class="flex gap-4">
          <label class="flex items-center">
            <input type="radio" v-model="importFormat" value="JSON" class="mr-2" />
            JSON
          </label>
          <label class="flex items-center">
            <input type="radio" v-model="importFormat" value="XML" class="mr-2" />
            XML
          </label>
        </div>
      </div>

      <div class="mb-4">
        <label class="block mb-2 font-medium">Загрузить файл данных:</label>
        <input
          type="file"
          @change="handleFileUpload"
          :accept="importFormat === 'JSON' ? '.json' : '.xml'"
          class="block w-full text-sm text-gray-500 file:mr-4 file:py-2 file:px-4 file:rounded file:border-0 file:text-sm file:font-semibold file:bg-blue-50 file:text-blue-700 hover:file:bg-blue-100"
        />
        <p class="text-sm text-gray-500 mt-1">Выберите {{ importFormat.toUpperCase() }} файл</p>
      </div>

      <button
        @click="handleImport"
        class="cursor-pointer px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600 transition"
      >
        Импортировать
      </button>
    </div>

    <form
      @submit.prevent="handleExport"
      v-if="showExportOptions"
      class="p-4 bg-gray-50 rounded-lg shadow"
    >
      <div class="mb-4">
        <label class="block mb-2 font-medium">Формат экспорта:</label>
        <div class="flex gap-4">
          <label class="flex items-center">
            <input type="radio" v-model="exportFormat" value="JSON" class="mr-2" />
            JSON
          </label>
          <label class="flex items-center">
            <input type="radio" v-model="exportFormat" value="XML" class="mr-2" />
            XML
          </label>
        </div>
      </div>

      <button
        type="button"
        @click="handleExport"
        class="cursor-pointer px-4 py-2 bg-green-500 text-white rounded hover:bg-green-600 transition"
      >
        Экспортировать
      </button>
    </form>
  </div>
</template>
