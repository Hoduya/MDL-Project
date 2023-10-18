<template>
  <div class="canvas-container">
    <canvas ref="canvas" class="canvas"></canvas>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref, defineProps } from 'vue';

interface Coordinates {
  x0: number;
  y0: number;
  x1: number;
  y1: number;
}

const props = defineProps({
  originalImageUrl: {
    type: String,
    required: true,
  },
});

const canvas = ref<HTMLCanvasElement | null>(null)
let ctx: CanvasRenderingContext2D

const drawRectangle = (coords: Coordinates) => {
  if (ctx && canvas.value) {
    ctx.clearRect(0, 0, canvas.value.width, canvas.value.height);
    ctx.beginPath();
    ctx.rect(coords.x0, coords.y0, coords.x1 - coords.x0, coords.y1 - coords.y0);
    ctx.strokeStyle = 'red';
    ctx.lineWidth = 2;
    ctx.stroke();
  }
};

const drawImageOnCanvas = () => {
  if (ctx && canvas.value) {
    const image = new Image();
    image.src = props.originalImageUrl;
    image.onload = () => {
      if (canvas.value) {
        const canvasWidth = 700;
        const canvasHeight = (image.height / image.width) * canvasWidth;
        canvas.value.width = canvasWidth;
        canvas.value.height = canvasHeight;
        ctx.drawImage(image, 0, 0, canvasWidth, canvasHeight);
      }
    }
  }
}

const drawRectangleOnCanvas = (coords: Coordinates) => {
  drawRectangle(coords);
};

onMounted(() => {
  ctx = canvas.value!.getContext('2d')!;

  drawImageOnCanvas();
})

</script>

<style scoped>
.canvas-container {
  width: 700px;
  overflow: hidden;
}

.canvas {
  display: block;
}
</style>