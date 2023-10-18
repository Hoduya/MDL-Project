<template>
  <h4>

  </h4>
  <div class="container mt-5">
    <div class="d-flex justify-content-center mb-4">
      <DatePicker v-model="selectedDate" mode="date" />
    </div>
    <div class="mb-4">
      <label for="formFile" class="form-label"><b>"{{ weekRangeText }}"</b> 주차 상암사옥 주간 식단표 업로드</label>
      <input class="form-control" type="file" id="formFile" @change="handleFileUpload">
    </div>

    <div v-if="uploadImagePath">
      <h3 v-show="isDetecting">텍스트 인식중...</h3>
      <div id="menuImage">
        <img :src="uploadImagePath" alt="Uploaded Image" class="img-fluid">
        <div class="mark" />
        <div v-for="(mark, index) in marks" :key="index" class="mark" :style="mark.style" />
      </div>
    </div>
  </div>

  <!-- <MenuCanvas :original-image-url="`/assets/image.png`"></MenuCanvas> -->
</template>

<script setup lang="ts">
import { onUnmounted, onMounted, computed, ref, watch } from 'vue';
import { createWorker, detect } from 'tesseract.js';
import { getWeekRange } from '@/utils/DateUtils'
import { DatePicker } from 'v-calendar';
import MenuCanvas from '@/components/MenuCanvas.vue'
import 'v-calendar/style.css';

interface TextBox {
  day: string
  bbox: Tesseract.Bbox
}

const selectedDate = ref(new Date());
const weekRangeText = ref("");
const uploadImagePath = ref<string | null>();
const uploadImageFile = ref<File | null>();
const isDetecting = ref(false);
const dayBoxes = ref<TextBox[]>([]);
const weekdays = ['월', '화', '수', '목', '금']

const marks = ref([
  { style: { width: '50px', height: '50px', top: '85px', left: '95px' } },
  { style: { width: '30px', height: '30px', top: '120px', left: '150px' } },
]);

const handleFileUpload = (event: Event) => {
  const input = event.target as HTMLInputElement;
  const file = input.files?.[0];

  if (file && file.type.startsWith('image/')) {
    const reader = new FileReader();
    reader.onload = () => {
      uploadImagePath.value = reader.result as string;
      uploadImageFile.value = file;
    };
    reader.readAsDataURL(file);
  } else {
    console.error('올바르지 않은 이미지 파일입니다.');
  }
}

const detectText = async (imageFile: File) => {
  isDetecting.value = true
  const worker = await createWorker('kor');
  // const greyImage = (await worker.recognize(imageFile, { rotateAuto: true }, { imageColor: true, imageGrey: true, imageBinary: true })).data.imageGrey
  // const words = (await worker.recognize(greyImage)).data.words
  const result = await worker.recognize(imageFile)
  console.log(result)
  const textBoxes = result.data.symbols.map(e => ({ day: e.text, bbox: e.bbox }));
  let dBoxes: TextBox[] = []
  weekdays.forEach((day) => {
    const index = textBoxes.findIndex((ele) => ele.day === day)
    if (index != -1) {
      const dayBox = {
        day: day,
        bbox: textBoxes[index].bbox
      }
      dBoxes.push(dayBox)
    }
  })
  dayBoxes.value = dBoxes
  isDetecting.value = false
}

onMounted(() => {
  selectedDate.value = new Date()
  uploadImagePath.value = '/assets/menu.png'
})

watch(selectedDate, () => {
  const { text, param } = getWeekRange(selectedDate.value)
  weekRangeText.value = text
})

watch(uploadImageFile, () => {
  if (uploadImageFile.value) {
    detectText(uploadImageFile.value)
  }
})

watch(dayBoxes, () => {
  dayBoxes.value.forEach(e => {
    const width = e.bbox.x1 - e.bbox.x0 + 5
    const height = e.bbox.y1 - e.bbox.y0 + 5
    const top = e.bbox.y0 * (1000 / 1504) -3
    const left = e.bbox.x0 * (1000 / 1504) -3

    marks.value.push({ style: { width: `${width}px`, height: `${height}px`, top: `${top}px`, left: `${left}px` } })
  })
})

</script>
<style scoped>
#menuImage {
  position: relative;
  width: 1000px;
}

.mark {
  position: absolute;
  border-style: solid;
  border-radius: 10px;
  border-color: red;
  background: none;
}
</style>




