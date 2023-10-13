<template>
  <ul class="pagination">
    <li
      v-for="pageNumber in pagesCount"
      :key="pageNumber"
      :class="['page-item', { active: isActive(pageNumber) }]">
      <a
        :aria-label="`Go to page ${pageNumber}`"
        class="page-link"
        href="#"
        @click="onPageChange(pageNumber)">
        {{ pageNumber }}
      </a>
    </li>
  </ul>
</template>
<script lang="ts" setup>
import api from '@/api';
import { computed, defineProps, defineEmits, ref } from 'vue'

interface Props {
  count: number
}

const props = defineProps<Props>()

const currentPage = ref(1)

const emit = defineEmits<{
  (e: 'page-change', index: number): void
}>()

const pagesCount = computed(() => Math.ceil(props.count / api.fetchBoardsLimit))

const isActive = (index: number) => currentPage.value === index

const onPageChange = (index: number) => { 
  currentPage.value = index
  emit('page-change', index)
}
</script>
