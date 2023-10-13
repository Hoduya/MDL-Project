<template>
  <div class="container">
    <div class="row align-items-center justify-content-center">
      <div class="col-md-8">
        <AppLink
          name="board"
          :params="{ slug: board.boardId.toString() }"
          class="list-group-item list-group-item-action py-3 lh-sm">
          <h3>{{ board.title }}</h3>
          <p>{{ board.content }}</p>
        </AppLink>
      </div>
      <div class="col-6 col-md-4">
        <BoardMeta :board="board"></BoardMeta>
      </div>
    </div>
  </div>
  <hr>
</template>
<script lang="ts" setup>
import { defineProps } from 'vue'
import AppLink from './AppLink.vue'
import BoardMeta from './BoardMeta.vue';

interface Props {
  board: Board
}

defineProps<Props>()

</script>
