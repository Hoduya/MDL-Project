<template>
  <div>{{ prop.board.content }}</div>
</template>
<script lang="ts" setup>
import { computed, defineProps, onMounted } from 'vue'

declare interface Board {
  bno: string
  id: string
  writerName: string
  title: string
  content: string
  regDate: string
  updateDate: string
}

interface Props {
  board: Board
}

onMounted(() => {
  console.log(prop.board.content)
})

const prop = defineProps<Props>()
</script>
