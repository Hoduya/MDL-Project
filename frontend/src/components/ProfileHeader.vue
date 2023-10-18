<template>
  <div class="user-profile-container">
    <div class="me-3">
      <img src="../assets/defaultProfile.png" alt="" class="rounded-circle profile-image">
      <h3 class="name">{{ user?.name }}</h3>
    </div>
    <div class="user-details">
      <h5>소속부서: {{ user?.deptName }}</h5>
      <h5>가입일자: {{ regDate }}</h5>
      <h5>Email: {{ user?.email }}</h5>
    </div>
  </div>
  <AppLink v-if="showEdit" name="settings" class="btn btn-sm btn-outline-secondary action-btn">
    <i class="ion-gear-a"></i>
    프로필 수정
  </AppLink>
</template>

<script lang="ts" setup>
import AppLink from '../components/AppLink.vue'
import { computed } from 'vue'
import { useUserStore } from '../store/user'
import { formatDate } from '@/utils/DateUtils';

interface Props {
  user?: User
}

const props = defineProps<Props>()

const userStore = useUserStore()
const userId = computed(() => props.user?.userId)
const showEdit = computed(() => userStore.currentUser?.userId === userId.value)
const regDate = computed(() => {
  if (props.user) return formatDate(props.user.regDate)
  else return ""
})

</script>

<style scoped>
.user-profile-container {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
  margin-top: 40px;
}

.profile-image {
  width: 60px;
  height: 60px;
}

.user-details {
  margin-left: 20px;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}
</style>@/utils/DateUtils