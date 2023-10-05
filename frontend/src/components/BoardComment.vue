<template>
  <div class="card">
    <div class="card-block">
      <p class="card-text">
        {{ comment.content }}
      </p>
    </div>
    <div class="card-footer">
      <AppLink
        name="profile"
        :params="{ username: comment.username }"
        class="comment-author">
        <img src="../assets/defaultProfile.png" alt="" class="rounded-circle me-2" style="width: 45px; height: 45px" />
      </AppLink>
      <AppLink
        name="profile"
        :params="{ username: comment.username }"
        class="me-2">
        {{ comment.username }}
      </AppLink>
      <span class="date-posted">
        {{ new Date(comment.regDate).toLocaleString() }}
      </span>
      <span class="mod-options" v-if="canModify">
        <i class="ion-trash-a" @click="emit('delete')">삭제</i>
      </span>
    </div>
  </div>
</template>
<script lang="ts" setup>
import { computed } from 'vue'
import { userStore } from '../store/user'
import AppLink from './AppLink.vue'

interface Props {
  comment: Comment
}

const props = defineProps<Props>()

const store = userStore()

const canModify = computed(
  () => store.user?.name === props.comment.username
)

const emit = defineEmits<{
  (e: 'delete'): boolean
}>()
</script>