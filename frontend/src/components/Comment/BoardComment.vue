<template>
  <div class="mt-2 border">
  <div class="d-flex flex-row p-1 align-items-center">
    <div class="w-10">
      <div class="d-flex align-items-center">
        <div class="d-flex flex-row align-items-center">
          <span class="fs-5 fw-bolder">
            <AppLink
              name="profile"
              :params="{ slug: comment.author.userId.toString() }"
              class="me-2 text-body">
              {{ comment.author.name }}
            </AppLink>
          </span>
        </div>
      </div>
    </div>
    <button class="btn me-2 ml-auto btn-sm btn-outline-danger" @click="emit('delete')" v-if="canModify">
      삭제
    </button>
    <samll> {{ new Date(comment.regDate).toLocaleString() }}</samll>
  </div>
  <p class="text-justify comment-text mt-2">
    {{ comment.content }}
  </p>
</div>

</template>
<script lang="ts" setup>
import { computed } from 'vue'
import { useUserStore } from '@/store/user'
import AppLink from '@/components/AppLink.vue'

interface Props {
  comment: BoardComment
}

const props = defineProps<Props>()

const userStore = useUserStore()

const canModify = computed(
  () => userStore.currentUser?.userId === props.comment.author.userId
)

const emit = defineEmits<{
  (e: 'delete'): boolean
}>()
</script>