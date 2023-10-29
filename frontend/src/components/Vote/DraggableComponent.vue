<template>
  <button
    @mousedown="startDragging"
    class="btn btn-outline-primary"
    :style="{ position: 'absolute', left: `${component.coordX}px`, top: `${component.coordY}px` }">
    {{ component.userName }}
  </button>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue';

const props = defineProps<{
  component: Component
  maxX: number
  maxY: number
  minY: number
}>();

const emit = defineEmits<{
  (e: 'position-change', updateComponent: UpdateComponent): void
}>()

const component = ref(props.component)
const isDragging = ref(false)
const offsetX = ref(0)
const offsetY = ref(0)

const startDragging = (event: MouseEvent) => {
  isDragging.value = true;
  event.preventDefault()
  offsetX.value = event.clientX - component.value.coordX;
  offsetY.value = event.clientY - component.value.coordY;
};

const handleDragging = (event: MouseEvent) => {
  if (isDragging.value) {
    const newX = event.clientX - offsetX.value;
    const newY = event.clientY - offsetY.value;
    
    // 바운더리 계산
    const maxX = props.maxX - 60; 
    const maxY = props.maxY - 60;
    const minY = props.minY;
    
    // 바운더리 밖으로 나가지 않도록
    component.value.coordX = Math.max(0, Math.min(newX, maxX));
    component.value.coordY = Math.max(minY, Math.min(newY, maxY));
  }
};

// 상위 컴포넌트로 변경된 위치 전달
const stopDragging = () => {
  if(isDragging.value) {
    const updateComponent: UpdateComponent = component.value
    emit('position-change', updateComponent)
  }
  isDragging.value = false
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