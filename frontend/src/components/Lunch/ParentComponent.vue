<template>
  <div>
    <DraggableComponent
      v-for="(position, index) in componentPositions"
      :key="index"
      :initialPosition="position"
      @position-updated="updateComponentPosition(index, $event)"
    />
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import DraggableComponent from './DraggableComponent.vue';

const componentPositions = ref<Array<{ x: number; y: number }>>([{ x: 0, y: 0 }, { x: 100, y: 100 }]);

const updateComponentPosition = (index: number, newPosition: { x: number; y: number }): void => {
  componentPositions.value[index] = newPosition;
};
</script>