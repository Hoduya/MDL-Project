<template>
  <div>
    <img class="imageContainer" :src="imagePath" draggable='false'/>
  </div>

  <div>
    
  </div>

  <div>
    <VoteBoard/>
  </div>
</template>

<script setup lang="ts">
import { onUnmounted, onMounted, computed, ref } from 'vue';
import { createWorker } from 'tesseract.js';
import VoteBoard from '../components/Vote/VoteBoard.vue'

const imagePath = ref('/assets/image.png');


</script>

<style scoped>
.imageContainer {
  max-width: 200%;
  max-height: 200px;
}
</style>