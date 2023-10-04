<template>
  <div class="d-flex align-items-center mb-3">
    <AppLink
      name="profile"
      class="text-body "
      :params="{ username: board.writerName }">
      <img src="../assets/defaultProfile.png" alt="" class="rounded-circle me-2" style="width: 45px; height: 45px;">
    </AppLink>
    <div>
      <AppLink
        name="profile"
        class="text-body "
        :params="{ username: board.writerName }">
        <h5 class="mb-1">
          {{ board.writerName }}
        </h5>
      </AppLink>
      <small class="text-muted mb-0">{{ formattedDate }}</small>
    </div>
  </div>

  <!-- <AppLink name="profile" :params="{ username: article.author.username }">
    <img :src="article.author.image || $config.DEFAULT_AVATAR" />
  </AppLink>

  <div class="info">
    <AppLink
      name="profile"
      :params="{ username: article.author.username }"
      class="author">
      {{ article.author.username }}
    </AppLink>
    <span class="date">{{ new Date(article.createdAt).toDateString() }}</span>
  </div>

  <template v-if="showEdit">
    <AppLink
      name="edit-article"
      :params="{ slug: article.slug }"
      class="btn btn-outline-secondary btn-sm">
      <i class="ion-edit"></i>
      &nbsp; Edit Article
    </AppLink>
    &nbsp;&nbsp;
    <button @click="onDelete" class="btn btn-outline-danger btn-sm">
      <i class="ion-trash-a"></i>
      &nbsp; Delete Article
    </button>
  </template>
  < v-else>
    <button
      @click="() => followProfile()"
      :disabled="followPending"
      class="btn btn-sm btn-outline-secondary">
      <i class="ion-plus-round"></i>
      &nbsp; {{ article.author.following ? 'Unfollow' : 'Follow' }}
      {{ article.author.username }}
    </button>
    &nbsp;&nbsp;
    <button
      @click="() => favoriteArticle()"
      :disabled="favoriteArticlePending"
      class="btn btn-sm btn-outline-primary">
      <i class="ion-heart"></i>
      &nbsp; {{ isFavorited ? 'Unfavorite' : 'Favorite' }} Post
      <span class="counter">({{ article.favoritesCount }})</span>
    </button> -->
</template>

<script lang="ts" setup>
import AppLink from './AppLink.vue';
import formatDateTime from '@/utils/formatDateTime';
import { defineProps } from 'vue';

interface Props {
  board: Board
}

const props = defineProps<Props>()
const formattedDate = formatDateTime(props.board.regDate);

</script>
