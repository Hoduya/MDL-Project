<template>
  <div class="board-container mt-3">
    <BoardNavigation useGlobalFeed useMyFeed />
    <suspense>
      <template #default>
        <div class="container-fluid full-width">
          <BoardList />
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
</script>

<style scoped>
.board-container {
  max-width: 954px;
  margin: 0 auto;
}
</style>
