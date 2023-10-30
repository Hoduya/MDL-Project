<template>
    <div class="editor-page">
      <div class="container page">
        <div class="row">
          <div class="col-md-10 offset-md-1 col-xs-12">
            <suspense>
              <template #default>
                <EditBoardForm />
              </template>
              <template #fallback>
                <div class="row article-content">로딩중...</div>
              </template>
            </suspense>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script lang="ts" setup>
  import EditBoardForm from '@/components/Board/EditBoardForm.vue'
  </script>
  