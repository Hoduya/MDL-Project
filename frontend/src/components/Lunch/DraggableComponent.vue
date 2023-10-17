<template>
  <div
    @mousedown="startDragging"
    :style="{ position: 'absolute', width: '50px', height: '50px', backgroundColor: 'red', left: `${position.x}px`, top: `${position.y}px` }">
    {{ piece.userName }}
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue';

interface Position {
  x: number;
  y: number;
  userName: string;
  userId: number;
}

const props = defineProps<{
  piece: Position
}>();

const position = ref(props.piece)
const isDragging = ref(false)
const offsetX = ref(0)
const offsetY = ref(0)

const startDragging = (event: MouseEvent) => {
  isDragging.value = true;
  event.preventDefault()
  offsetX.value = event.clientX - position.value.x;
  offsetY.value = event.clientY - position.value.y;
};

const handleDragging = (event: MouseEvent) => {
  if (isDragging.value) {
    const newX = event.clientX - offsetX.value;
    const newY = event.clientY - offsetY.value;
    
    // Get the maximum values for x and y coordinates to prevent moving outside the screen
    const maxX = window.innerWidth - 50; // 50 is the width of the component
    const maxY = window.innerHeight - 50; // 50 is the height of the component
    
    // Update position within the screen boundaries
    position.value.x = Math.max(0, Math.min(newX, maxX));
    position.value.y = Math.max(0, Math.min(newY, maxY));
  }
};

const stopDragging = () => {
  isDragging.value = false;
};

onMounted(() => {
  window.addEventListener('mousemove', handleDragging);
  window.addEventListener('mouseup', stopDragging);
});

onUnmounted(() => {
  window.removeEventListener('mousemove', handleDragging);
  window.removeEventListener('mouseup', stopDragging);
});

</script>