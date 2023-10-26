<template>
  <button
    class="btn btn-outline-dark"
    :style="{ position: 'absolute', left: `${component.coordX}px`, top: `${component.coordY}px` }">
    {{ component.userName }}
  </button>

</template>

<script setup lang="ts">
defineProps<{
  component: Component
}>();
</script>