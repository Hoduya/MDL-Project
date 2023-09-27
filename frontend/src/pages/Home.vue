<template>
  <div class="container">
    <div class="feed-toggle">
      <BoardNavigation useGlobalFeed useMyFeed />
    </div>
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