<template>
  <form v-if="store.user" class="card comment-form">
    <div class="card-block">
      <textarea
        v-model="comment"
        class="form-control"
        placeholder="Write a comment..."
        rows="3"
      />
    </div>
    <div class="card-footer">
      <img
        src="../assets/defaultProfile.png"
        class="comment-author-img"
      />
      <button class="btn btn-sm btn-primary">
        Post Comment
      </button>
    </div>
  </form>
  <p v-else>
    <AppLink name="login">Sign in</AppLink> or
    <AppLink name="register">sign up</AppLink>to add comments on this article.
  </p>
</template>
<script lang="ts" setup>
import { ref, defineProps, defineEmits } from 'vue'
import { userStore } from '../store/user'
import AppLink from './AppLink.vue'

interface Props {
  articleSlug: string
}

const props = defineProps<Props>()

const store = userStore()

const comment = ref('')

</script>
