<template>
  <div class="container mt-5">
    <div class="d-flex justify-content-center mb-4">
      <DatePicker v-model="selectedDate" mode="date" />
    </div>
    <div class="mb-4">
      <label for="formFile" class="form-label"><b>"{{ weekRangeText }}"</b> 주차 상암사옥 주간 식단표 업로드</label>
      <input class="form-control" type="file" id="formFile" @change="handleFileUpload">
    </div>

    <div v-if="imagePath">
      <div class="d-flex justify-content-center mb-4" id="menuImage">
        <img :src="imagePath" alt="Uploaded Image" class="img-fluid">
      </div>
      <div class="d-flex justify-content-center mt-4">
        <button v-show="showUploadButton" class="btn btn-primary" @click="onUpload">식단표 업로드</button>
      </div>    
    </div>
    <div v-else>
      <h4>해당 주차에 업로드된 이미지가 없습니다.</h4>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref, watch } from 'vue'
import { getWeekRange } from '@/utils/DateUtils'
import { DatePicker } from 'v-calendar'
import 'v-calendar/style.css'
import api from '@/api'

const selectedDate = ref(new Date())
const weekRangeText = ref("")
const imagePath = ref<string | null>()
const imageFile = ref<File | null>()
const showUploadButton = ref(false)

const handleFileUpload = (event: Event) => {
  const input = event.target as HTMLInputElement;
  const file = input.files?.[0];

  if (file && file.type.startsWith('image/')) {
    const reader = new FileReader();
    reader.onload = () => {
      imagePath.value = reader.result as string;
      imageFile.value = file;
    };
    reader.readAsDataURL(file);
    showUploadButton.value = true;
  } else {
    console.error('올바르지 않은 이미지 파일입니다.');
  }
}

const onUpload = async () => {
  if(!imageFile.value) { return }
  const formData = new FormData();
  const { formalString } = getWeekRange(selectedDate.value)
  console.log(formalString)
  formData.append('imageFile', imageFile.value);
  
  api.uploadMenuImage(formData, formalString)
}

onMounted(() => {
  selectedDate.value = new Date()
})

watch(selectedDate, async () => {
  const { text, formalString } = getWeekRange(selectedDate.value)
  showUploadButton.value = true;
  weekRangeText.value = text
  try {
    const base64Image = await api.fetchMenuImage(formalString);
    imagePath.value = `data:image/png;base64,${base64Image}`
  } catch {
    imagePath.value = null
  }
})

</script>



