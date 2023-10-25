<template>
  <div>
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


</script>

<style scoped>
.imageContainer {
  max-width: 200%;
  max-height: 200px;
}
</style>