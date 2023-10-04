<template>
  <img src="../assets/defaultProfile.png" alt="" class="rounded-circle me-2" style="width: 60px; height: 60px;">
  <h4>{{ username }}</h4>

  <AppLink
    v-if="showEdit"
    name="settings"
    class="btn btn-sm btn-outline-secondary action-btn"
  >
    <i class="ion-gear-a"></i>
    프로필 수정
  </AppLink>
</template>

<script lang="ts" setup>
import AppLink from '../components/AppLink.vue'
import { computed } from 'vue'
import { userStore } from '../store/user'
import { useRoute } from 'vue-router'

const route = useRoute()

const store = userStore()

const username = computed(() => route.params?.username as string)

const showEdit = computed(() => store.user?.name === username.value)

</script>
