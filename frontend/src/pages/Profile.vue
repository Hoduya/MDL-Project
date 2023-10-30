<template>
  <div class="container">
    <div v-if="user && !loadingUser">
      <div class="row col-xs-12 col-md-9 offset-md-1 mt-5">
        <EditProfileForm :user="user" :isEditPage="false" />
      </div>
      <div class="row col-xs-12 col-md-9 offset-md-1">
        <BoardNavigation useUserFeed useUserFavorited />
        <suspense>
          <template #default>
            <BoardList />
          </template>
          <template #fallback>
            <div>Loading articles...</div>
          </template>
        </suspense>
      </div>
    </div>
    <div v-else>
      유저정보 로딩중...
    </div>
  </div>
</template>

<script lang="ts" setup>
import BoardNavigation from '@/components/Board/BoardNavigation.vue';
import BoardList from '@/components/Board/BoardList.vue';
import EditProfileForm from '@/components/Profile/EditProfileForm.vue';
import { useRoute } from 'vue-router';
import { ref, onMounted } from 'vue';
import api from '@/api';

const route = useRoute()
const userId = route.params.slug as string
const user = ref<User>()
const loadingUser = ref(true)

onMounted(async () => {
  try {
    const userData = await api.fetchUser(userId)
    user.value = userData;
  } catch (error) {
    console.error('유저 정보를 불러오는 중에 오류가 발생했습니다:', error)
  } finally {
    loadingUser.value=false
  }
})
</script>