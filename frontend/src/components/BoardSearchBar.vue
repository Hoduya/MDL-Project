<template>
  <div class="input-group mb-3" style="max-width: 500px;">
    <div class="input-group-text p-0">
      <select v-model="searchFilter" class="form-select form-select shadow-none bg-light border-0">
        <option value=1>제목</option>
        <option value=2>내용</option>
        <option value=3>작성자</option>
      </select>
    </div>
    <input type="text" v-model="searchText" class="form-control" placeholder="Search Here">
    <button class="input-group-text px-4 btn-warning" @click="onSearch">
      검색
    </button>
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'

const searchFilter = ref(1)
const searchText = ref('')

const searchOption = computed(() => {
  if (searchText.value !== null && searchText.value !== '') {
    return { searchFilter: searchFilter.value, searchText: searchText.value}
  } else {
    return undefined
  }
})

const emit = defineEmits<{
  (e: 'text-search', searchOption?: SearchOption): void
}>()

const onSearch = () => emit('text-search', searchOption.value)
</script>