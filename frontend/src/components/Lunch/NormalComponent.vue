<template>
  <div
    :style="{ position: 'absolute', width: '50px', height: '50px', backgroundColor: 'red', left: `${piece.x}px`, top: `${piece.y}px` }">
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue';

interface PieceInfo {
  name: string
  x: number;
  y: number;
}

const props = defineProps<{
  piece: PieceInfo;
}>()

</script>