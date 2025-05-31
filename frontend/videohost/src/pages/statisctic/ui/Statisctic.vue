<script setup lang="ts">
import { ref } from 'vue'
import {
  Chart,
  registerables
} from 'chart.js'

import { useFormData } from '@/shared/helpers/useFormData'

const { formatDate } = useFormData()

Chart.register(...registerables)

const chartRef = ref<HTMLCanvasElement | null>(null)
let chartInstance: Chart | null = null

const xAxis = ref('')
const yAxis = ref('')
const chartType = ref('BAR')
const titleContains = ref('')
const descriptionContains = ref('')
const minViews = ref<number | null>(null)
const maxViews = ref<number | null>(null)
const views = ref<number | null>(null)
const uploadedAfter = ref('')
const uploadedBefore = ref('')


const submitForm = async () => {
  const query = new URLSearchParams({
    xAxis: xAxis.value,
    yAxis: yAxis.value,
  }).toString()

  const rawBody = {
    titleContains: titleContains.value,
    descriptionContains: descriptionContains.value,
    views: views.value,
    minViews: minViews.value,
    maxViews: maxViews.value,
    uploadedAfter: uploadedAfter.value ? formatDate(new Date(uploadedAfter.value)) : null,
    uploadedBefore: uploadedBefore.value ? formatDate(new Date(uploadedBefore.value)) : null,
  }

  const filteredBody: Record<string, any> = {}
  for (const key in rawBody) {
    const value = rawBody[key as keyof typeof rawBody]
    if (value !== null && value !== '' && value !== undefined) {
      filteredBody[key] = value
    }
  }

  const response = await fetch(`${import.meta.env.VITE_API}statistics/video?${query}`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      Authorization: 'Bearer ' + localStorage.getItem('token')
    },
    body: JSON.stringify(filteredBody)
  })

  const result = await response.json()

  const chartData = transformApiResponse(result)
  drawChart(chartData)
}

function transformApiResponse(apiData: { _id: string; count: number; yValues: string[] }[]) {
  const transformed: { x: string; y: string; count: number }[] = [];

  for (const item of apiData) {
    const formattedX = isTimestamp(item._id)
      ? formatTimestamp(Number(item._id))
      : item._id;

    for (const y of item.yValues) {
      const formattedY = isTimestamp(y)
        ? formatTimestamp(Number(y))
        : y;

      transformed.push({
        x: formattedX,
        y: formattedY,
        count: item.count
      });
    }
  }

  return transformed;
}

function isTimestamp(value: string): boolean {
  return /^\d{12,}$/.test(value);
}

function formatTimestamp(timestamp: number): string {
  const date = new Date(timestamp);
  const day = String(date.getUTCDate()).padStart(2, '0');
  const month = String(date.getUTCMonth() + 1).padStart(2, '0');
  const year = date.getUTCFullYear();
  return `${day}.${month}.${year}`;
}

function drawChart(data: { x: string, y: string, count: number }[]) {
  if (!chartRef.value) return

  if (chartInstance) {
    chartInstance.destroy()
  }

  const grouped = new Map<string, Map<string, number>>()

  for (const item of data) {
    if (!grouped.has(item.x)) {
      grouped.set(item.x, new Map())
    }
    const yMap = grouped.get(item.x)!
    yMap.set(item.y, (yMap.get(item.y) || 0) + item.count)
  }

  const xLabels = Array.from(grouped.keys())
  const yLabels = new Set<string>()
  for (const map of grouped.values()) {
    for (const yKey of map.keys()) yLabels.add(yKey)
  }

  const datasets = Array.from(yLabels).map(yVal => ({
    label: yVal,
    data: xLabels.map(x => grouped.get(x)?.get(yVal) || 0),
    backgroundColor: getRandomColor()
  }))

  chartInstance = new Chart(chartRef.value, {
    type: chartType.value.toLowerCase() as any,
    data: {
      labels: xLabels,
      datasets
    },
    options: {
      responsive: true,
      scales: chartType.value === 'SCATTER' ? {
        x: { type: 'category' },
        y: { beginAtZero: true }
      } : {}
    }
  })
}

function getRandomColor() {
  const r = () => Math.floor(Math.random() * 255)
  return `rgba(${r()}, ${r()}, ${r()}, 0.6)`
}
</script>

<template>
  <div>
    <h2>📊 Построить статистику</h2>
    <h3 class="text-center">Разрешенные методы для оси x, y - uploadDate, title, description, views</h3>
    <form @submit.prevent="submitForm" class="form-grid">
      <label>Параметры по оси X: <input v-model="xAxis" required /></label>
      <label>Параметры по оси Y: <input v-model="yAxis" required /></label>
      <label>Название видео: <input v-model="titleContains" /></label>
      <label>Описание видео: <input v-model="descriptionContains" /></label>
      <label>Минимальное кол-во просмотров: <input v-model.number="minViews" type="number" /></label>
      <label>Максимальное кол-во просмотров: <input v-model.number="maxViews" type="number" /></label>
      <label>Кол-во просмотров: <input v-model.number="views" type="number" /></label>
      <label>Загружено после: <input v-model="uploadedAfter" type="date" /></label>
      <label>Загружено до: <input v-model="uploadedBefore" type="date" /></label>

      <button type="submit">Построить график</button>
    </form>

    <canvas ref="chartRef" style="max-width: 100%; margin-top: 2rem;" />
  </div>
</template>

<style scoped>
.form-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 1.5rem;
  max-width: 900px;
  margin: 2rem auto;
  padding: 2rem;
  background-color: #f9fafb;
  border-radius: 1rem;
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.05);
}

label {
  display: flex;
  flex-direction: column;
  font-weight: 500;
  font-size: 0.95rem;
  color: #374151;
}

input, select {
  margin-top: 0.4rem;
  padding: 0.6rem 0.75rem;
  border: 1px solid #d1d5db;
  border-radius: 0.5rem;
  background-color: #ffffff;
  font-size: 1rem;
  transition: border-color 0.3s, box-shadow 0.3s;
}

input:focus, select:focus {
  outline: none;
  border-color: #3b82f6;
  box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.2);
}

button {
  grid-column: span 2;
  padding: 0.9rem;
  font-size: 1.1rem;
  background-color: #3b82f6;
  color: white;
  border: none;
  border-radius: 0.6rem;
  cursor: pointer;
  transition: background-color 0.3s, box-shadow 0.3s;
}

button:hover {
  background-color: #2563eb;
  box-shadow: 0 4px 12px rgba(59, 130, 246, 0.3);
}

h2 {
  text-align: center;
  margin-bottom: 1.5rem;
  font-size: 1.75rem;
  color: #1f2937;
}
</style>
