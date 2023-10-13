<template>
  <div class="container d-flex justify-content-center align-items-center">
    <div class="profile-header text-center p-4 rounded" style="background: rgba(255, 255, 255, 0.8);">
      <ProfileHeader :user="user"/>
    </div>
  </div>

    <div class="container">
      <div class="row">
        <div class="col-xs-12 col-md-10 offset-md-1">
          <div class="articles-toggle">
            <BoardNavigation useUserFeed useUserFavorited />
          </div>
          <suspense>
            <template #default>
              <BoardList />
            </template>
            <template #fallback>
              <div class="article-preview">Loading articles...</div>
            </template>
          </suspense>
        </div>
      </div>
    </div>
</template>

<script lang="ts" setup>
import BoardNavigation from '@/components/BoardNavigation.vue';
import ProfileHeader from '../components/ProfileHeader.vue'
import BoardList from '@/components/BoardList.vue';
import { useRoute } from 'vue-router';
import { ref, onMounted } from 'vue';
import api from '@/api';

const route = useRoute()
const userId = route.params.slug as string
const user = ref<User>();

onMounted(async () => {
  try {
    const userData = await api.fetchUserInfo(userId)
    user.value = userData;
  } catch (error) {
    console.error('유저 정보를 불러오는 중에 오류가 발생했습니다:', error)
  }
})
</script>
