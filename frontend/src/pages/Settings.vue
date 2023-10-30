<template>
  <div class="profile-container row col-xs-9s col-md-6 offset-md-3 mt-5">
    <EditProfileForm v-if="user" :user="user" is-edit-page/>
  </div>
</template>

<script setup lang="ts">
import EditProfileForm from '@/components/Profile/EditProfileForm.vue';
import { useUserStore } from '@/store/user';
import { ref } from 'vue';

const userStore = useUserStore()
const user = ref<User | null>(userStore.currentUser)

</script>
