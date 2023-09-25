<template>
    <div class="home-page">
      <div class="container page">
        <div class="row">
          <div class="col-md-9">
            <div class="feed-toggle">
              <BoardNavigation useGlobalFeed useMyFeed />
            </div>
  
            <suspense>
              <template #default>
                <BoardList />
              </template>
              <template #fallback>
                <div class="article-preview">Loading boards...</div>
              </template>
            </suspense>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script lang="ts" setup>
  import BoardNavigation from '../components/BoardNavigation.vue'
  import BoardList from '../components/BoardList.vue'
  </script>