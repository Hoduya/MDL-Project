<template>
  <div>
    <DraggableComponent
      v-for="(piece, index) in pieces"
      :key="index"
      :piece="piece"
    />
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import DraggableComponent from './DraggableComponent.vue'
import NormalComponent from './NormalComponent.vue'

interface Piece {
  x: number
  y: number
  userName: string
  userId: number
}
const pieces = ref<Array<Piece>>([{ x: 0, y: 0, userName: "asd", userId: 123 }, { x: 0, y: 0, userName: "asd", userId: 123 }]);



// const updateComponentPosition = (index: number, newPosition: { x: number; y: number }): void => {
//   pieces.value[index] = newPosition;
// };

// @position-updated="updateComponentPosition(index, $event)"

</script>