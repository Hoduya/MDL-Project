<template>
  <div class="article-page">
    <suspense>
      <template #default>
        <BoardDetail />
      </template>
      <template #fallback>
        <div class="container page">
          <div class="row article-content">Loading Board...</div>
        </div>
      </template>
    </suspense>
  </div>
</template>
<script lang="ts" setup>
import BoardDetail from '@/components/BoardDetail.vue';
</script>
