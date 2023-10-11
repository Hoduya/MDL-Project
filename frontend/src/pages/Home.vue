<template>
  <div class="board-container mt-3">
    <BoardNavigation useGlobalFeed useMyFeed />
    <div class="d-flex justify-content-center">
      <BoardSearchBar @text-search="searchEvent" />
    </div>
    <suspense>
      <template #default>
        <div class="container-fluid full-width">
          <BoardList :searchOption="searchOption" />
        </div>
      </template>
      <template #fallback>
        <div class="article-preview">Loading boards...</div>
      </template>
    </suspense>

  </div>
</template>
  
<script lang="ts" setup>
import BoardNavigation from '../components/BoardNavigation.vue'
import BoardList from '../components/BoardList.vue'
import BoardSearchBar from '@/components/BoardSearchBar.vue';
import { ref } from 'vue';

const searchOptionRef = ref<SearchOption | null>()
const searchEvent = (searchOption: SearchOption | null) => {
  searchOptionRef.value = searchOption
}
</script>

<style scoped>
.board-container {
  max-width: 954px;
  margin: 0 auto;
}
</style>
