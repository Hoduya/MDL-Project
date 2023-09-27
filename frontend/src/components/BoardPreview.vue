<template>
  <a href="#" class="list-group-item list-group-item-action py-3 lh-sm">
        <div class="d-flex w-100 align-items-center justify-content-between">
          <strong class="mb-1">List group item heading</strong>
          <small class="text-body-secondary">Tues</small>
        </div>
        <div class="col-10 mb-1 small">Some placeholder content in a paragraph below the heading and date.</div>
  </a>
  
  <div class="article-preview">
    <div class="article-meta">
      <AppLink
        class="preview-link"
        name="profile"
        :params="{ username: board.writerName }"
      >
        <img src="../assets/defaultProfile.png" style="max-width: 25px;"/>
      </AppLink>
      <div class="info">
        <AppLink
          class="author"
          name="profile"
          :params="{ username: board.writerName }"
        >
          {{ board.writerName }}
        </AppLink>
        <span class="date">
          {{ new Date(board.regDate).toDateString() }}
        </span>
      </div>
    </div>
    <AppLink
      name="board"
      :params="{ slug: board.bno }"
      class="preview-link"
    >
      <h1>{{ board.title }}</h1>
      <p>{{ board.content }}</p>
      <span>Read more...</span>
    </AppLink>
  </div>
</template>
<script lang="ts" setup>
import { defineProps } from 'vue'
import AppLink from './AppLink.vue'

interface Board {
  bno: string
  id: string
  writerName: string
  title: string
  content: string
  regDate: string
  updateDate: string
}


interface Props {
  // eslint-disable-next-line no-undef
  board: Board
}

defineProps<Props>()

</script>
