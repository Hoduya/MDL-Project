<template>
  <div class="d-flex align-items-center mb-3">
    <AppLink
      name="profile"
      class="text-body"
      :params="{ slug: board.author.userId.toString() }">
      <img src="../assets/defaultProfile.png" alt="" class="rounded-circle me-2" style="width: 45px; height: 45px;">
    </AppLink>
    <div>
      <AppLink
        name="profile"
        class="text-body"
        :params="{ slug: board.author.userId.toString() }">
        <h5 class="mb-1">
          {{ board.author.name }}
        </h5>
      </AppLink>
      <small class="text-muted mb-0">{{ formattedDate }}</small>
    </div>
  </div>
</template>

<script lang="ts" setup>
import AppLink from './AppLink.vue';
import formatDateTime from '@/utils/formatDateTime';
import { defineProps, ref, onMounted } from 'vue';

interface Props {
  board: Board,
}

const props = defineProps<Props>()
const formattedDate = formatDateTime(props.board.regDate);

</script>
