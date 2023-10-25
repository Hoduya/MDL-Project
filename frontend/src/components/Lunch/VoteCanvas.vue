<!-- <template>
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

</script> -->

<template>
  <div class="container">
    <div class="rectangle">
      <div class="vertical-line"></div>
      <div class="draggable-component" :style="{ left: `${draggablePosition.x}px`, top: `${draggablePosition.y}px` }" @mousedown="startDragging"></div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, Ref } from "vue";

const draggablePosition: Ref<{ x: number; y: number }> = ref({ x: 0, y: 0 });
const boundary = { left: 0, right: 750, top: 0, bottom: 500 }; // 1000x650 container size

const startDragging = (event: MouseEvent) => {
  const offsetX = event.clientX - draggablePosition.value.x;
  const offsetY = event.clientY - draggablePosition.value.y;

  const onMouseMove = (event: MouseEvent) => {
    const x = event.clientX - offsetX;
    const y = event.clientY - offsetY;

    // Restrict movement within the boundary
    draggablePosition.value.x = Math.max(boundary.left, Math.min(boundary.right, x));
    draggablePosition.value.y = Math.max(boundary.top, Math.min(boundary.bottom, y));
  };

  const onMouseUp = () => {
    window.removeEventListener("mousemove", onMouseMove);
    window.removeEventListener("mouseup", onMouseUp);
  };

  window.addEventListener("mousemove", onMouseMove);
  window.addEventListener("mouseup", onMouseUp);
};
</script>

<style scoped>
.container {
  width: 1000px;
  height: 650px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.rectangle {
  width: 750px;
  height: 500px;
  position: relative;
  border: 2px solid black;
}

.vertical-line {
  position: absolute;
  left: 50%;
  top: 0;
  bottom: 0;
  width: 2px;
  background-color: red;
}

.draggable-component {
  width: 50px;
  height: 50px;
  background-color: blue;
  cursor: grab;
  position: absolute;
}
</style>