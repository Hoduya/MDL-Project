<!-- <template>
  <div class="mb-3">
    <label for="formFile" class="form-label">상암사옥 주간 식단표 업로드</label>
    <input class="form-control" type="file" id="formFile">
  </div>

  <div>
    <div class="mb-3">
      <label for="exampleFormControlTextarea1" class="form-label">식단표</label>
      <textarea class="form-control" id="exampleFormControlTextarea1" rows="3">이곳에 식단표 이미지를 붙여넣으세요.</textarea>
    </div>
    <img class="imageContainer" :src="imagePath" draggable='false' />
  </div>
</template>

<script setup lang="ts">
import { onUnmounted, onMounted, computed, ref } from 'vue';
import { createWorker } from 'tesseract.js';

const imagePath = ref("");

const onPaste = (pasteEvent: ClipboardEvent) => {
  const item = pasteEvent.clipboardData?.items[0]

  if (item && item.type.indexOf("image") === 0) {
    var blob = item.getAsFile();

    var reader = new FileReader();
    reader.onload = function (event) {
      if (event.target?.result)
        imagePath.value = event.target.result.toString();
    };

    if (blob) {
      reader.readAsDataURL(blob);
    }
  }
}

const detectText = async (greyScaleImage?: any) => {
  const worker = await createWorker('kor');
  const result = await worker.recognize(imagePath.value, { rotateAuto: true }, { imageColor: true, imageGrey: true, imageBinary: true });

  if (result.data) {
    console.log(result.data.text.replace(/ +/g, " ").split(" "))
    console.log(result.data)
  }
}

const convertToGreyScale = async () => {
  const worker = await createWorker('kor');
  const result = await worker.recognize(imagePath.value, { rotateAuto: true }, { imageColor: true, imageGrey: true, imageBinary: true });
  const imageSrc = result.data.imageGrey
  if (imageSrc) {
    const greyScaleImage = convertImage(imageSrc)
    detectText(greyScaleImage)
  }
}

const convertImage = (imageSrc: string) => {
  const data = atob(imageSrc.split(',')[1])
    .split('')
    .map((c) => c.charCodeAt(0));

  return new Uint8Array(data);
}

onMounted(() => {
  document.addEventListener('paste', onPaste)
})

onUnmounted(() => {
  document.removeEventListener('paste', onPaste)
})


</script>

<style scoped>
.imageContainer {
  max-width: 200%;
  max-height: 200px;
}
</style> -->

<template>
  <div class="container mt-5">
    <div class="mb-3">
      <label for="formFile" class="form-label">상암사옥 주간 식단표 업로드</label>
      <input class="form-control" type="file" id="formFile" @change="handleFileUpload">
    </div>



    <div v-if="uploadedImage">
      <h3>업로드 이미지</h3>
      <div class="container">
        <div class="image-container">
          <div class="gradient-overlay"></div>
          <img :src="uploadedImage" alt="Uploaded Image" class="img-fluid">
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onUnmounted, onMounted, computed, ref } from 'vue';
import { createWorker } from 'tesseract.js';

const uploadedImage = ref<string | null>(null);

const handleFileUpload = (event: Event) => {
  const input = event.target as HTMLInputElement;
  const file = input.files?.[0];

  if (file) {
    const reader = new FileReader();
    reader.onload = () => {
      uploadedImage.value = reader.result as string;
    };
    reader.readAsDataURL(file);
    detectText(file)
  }
};

const detectText = async (imageFile: File) => {
  const worker = await createWorker('kor');
  const result = await worker.recognize(imageFile, { rotateAuto: true }, { imageColor: true, imageGrey: true, imageBinary: true });
  // const result = await worker.recognize(imageFile)

  if (result.data) {
    console.log(result.data.text.replace(/ +/g, " ").split(" "))
    console.log(result.data)
  }
}

function getWeekRange(dateString: string) {
  const date = new Date(dateString);
  const dayOfWeek = date.getDay(); // 주어진 날짜의 요일 (0: 일요일, 1: 월요일, ..., 6: 토요일)
  const startDate = new Date(date); // 입력된 날짜를 복사하여 시작 날짜로 설정
  const endDate = new Date(date);

  // 입력된 날짜가 일요일이면 startDate와 endDate를 변경하지 않고, 그대로 사용
  // 그 외의 경우, 입력된 날짜를 일요일로 맞춤
  startDate.setDate(date.getDate() - dayOfWeek); // 현재 날짜에서 요일을 뺌으로써 일요일을 구함
  endDate.setDate(date.getDate() - dayOfWeek + 6); // 일요일에서 6일을 더하여 토요일을 구함

  const startYear = startDate.getFullYear();
  const startMonth = startDate.getMonth() + 1;
  const startDay = startDate.getDate();
  const endYear = endDate.getFullYear();
  const endMonth = endDate.getMonth() + 1;
  const endDay = endDate.getDate();

  return `${startYear}년 ${startMonth}월 ${startDay}일 ~ ${endYear}년 ${endMonth}월 ${endDay}일`;
}

// 예시 사용법
const inputDate = '2023-10-31'; // 사용자가 입력한 날짜
const weekRange = getWeekRange(inputDate);
// console.log(weekRange); // 출력: '2023년 10월 15일 ~ 2023년 10월 21일 주차'

</script>
<style scoped>
.image-container {
  position: relative;
  width: 100%;
  height: 400px;
  /* 이미지 높이에 맞게 조절 */
  overflow: hidden;
}

.gradient-overlay {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: linear-gradient(to right, rgba(0, 0, 0, 0), rgba(0, 0, 0, 1));
  /* 수평으로 점점 어두워지는 그래디언트 */
}
</style>




