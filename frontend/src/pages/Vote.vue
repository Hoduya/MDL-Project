<template>
<div class="container mt-5 mb-5">
  <div v-if="imagePath" class="d-flex justify-content-center mb-4">
    <img :src="imagePath" alt="Uploaded Image" class="img-fluid" style="max-width: 1000px; width: 100%;">
  </div>
  <div v-else class="d-flex justify-content-center mb-4" id="menuImage">
    <h4> 식단표가 업로드되지 않았습니다.</h4>
  </div>
  <VoteBoard />
</div>

</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue';
import VoteBoard from '../components/Vote/VoteBoard.vue'
import { getWeekRange } from '@/utils/dateUtils';
import api from '@/api';

const imagePath = ref();
const { formalString } = getWeekRange(new Date())

onMounted( async () => {
  try {
    const base64Image = await api.fetchMenuImage(formalString);
    imagePath.value = `data:image/png;base64,${base64Image}`
  } catch {
    imagePath.value = null
  }})

</script>