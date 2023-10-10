<template>
  <div>{{ prop.board.content }}</div>
</template>
<script lang="ts" setup>
import { computed, defineProps, onMounted } from 'vue'

interface Props {
  board: Board
}

const prop = defineProps<Props>()
</script>
