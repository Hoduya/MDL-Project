<template>
  <div>
    <div class="mb-3">
      <label for="exampleFormControlTextarea1" class="form-label">Example textarea</label>
      <textarea class="form-control" id="exampleFormControlTextarea1" rows="3"></textarea>
    </div>
    <img class="imageContainer" :src="imagePath" draggable='false'/>
  </div>


  <div>
    <VoteCanvas>
    </VoteCanvas>
  </div>
</template>

<script setup lang="ts">
import { onUnmounted, onMounted, computed, ref } from 'vue';
import { createWorker } from 'tesseract.js';
import VoteCanvas from '../components/Lunch/VoteCanvas.vue'

const imagePath = ref('/assets/image.png');

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
      detectText()
    }
  }
}

const detectText = async () => {
  const worker = await createWorker('kor');
  const result = await worker.recognize(imagePath.value)

  if (result.data) {
    console.log(result.data)
    console.log(result.data.text.replace(/ +/g, " ").split(" "))
  }
}

onMounted(() => {
  document.addEventListener('paste', onPaste);
  detectText()
})

onUnmounted(() => {
  document.removeEventListener('paste', onPaste);
})


</script>

<style scoped>
.imageContainer {
  max-width: 200%;
  max-height: 200px;
}
</style>