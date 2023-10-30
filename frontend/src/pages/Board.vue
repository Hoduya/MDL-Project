<template>
  <div class="board-container">
    <suspense>
      <template #default>
        <BoardDetail />
      </template>
      <template #fallback>    
          <div>Loading Board...</div>
      </template>
    </suspense>
  </div>
</template>

<script lang="ts" setup>
import BoardDetail from '@/components/Board/BoardDetail.vue';
</script>
