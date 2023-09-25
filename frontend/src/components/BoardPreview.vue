<template>
  <div class="article-preview">
    <div class="article-meta">
      <AppLink
        class="preview-link"
        name="profile"
        :params="{ username: board.name }"
      >
        <img src="../assets/defaultProfile.png" style="max-width: 25px;"/>
      </AppLink>
      <div class="info">
        <AppLink
          class="author"
          name="profile"
          :params="{ username: board.name }"
        >
          {{ board.name }}
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

declare interface Board {
  bno: string
  id: string
  name: string
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
