<template>
  <div class="container">
    <div class="main-body">
      <div class="row">
        <div class="col-lg-4">
          <div class="card">
            <div class="card-body">
              <div class="d-flex flex-column align-items-center text-center">
                <img src="../assets/defaultProfile.png" alt="Admin"
                  class="rounded-circle" width="110">
                <div class="mt-3">
                  <h4>{{ user.name }}</h4>
                  <p class="text-secondary mb-1">{{ user.deptName }}</p>
                  <AppLink v-if="showEdit && !isEditPage" name="settings" class="btn btn-outline-danger btn-sm mt-2">
                    프로필 수정
                  </AppLink>
                </div>
              </div>
            </div>
          </div>
        </div>
        <form @submit.prevent="onSave" class="col-lg-8">
          <div class="card">
            <div class="card-body">
              <div class="row mb-3">
                <div class="col-sm-3">
                  <h6 class="mb-1">이름</h6>
                </div>
                <div class="col-sm-9 text-secondary">
                  <input type="text" class="form-control" v-model="userForm.name" :disabled="!isEditPage">
                </div>
              </div>
              <div class="row mb-3">
                <div class="col-sm-3">
                  <h6 class="mb-0">소속부서</h6>
                </div>
                <div v-if="isEditPage" class="col-sm-9 text-secondary">
                  <select v-model="userForm.deptId" class="form-select shadow-none bg-light border-0">
                    <option v-for="department in departments" :key="department.deptId" :value="department.deptId"
                      :selected="department.deptId == userForm.deptId">
                      {{ department.name }}
                    </option>
                  </select>
                </div>
                <div v-else class="col-sm-9 text-secondary">
                  <input type="text" class="form-control" v-model="userForm.deptName" :disabled="!isEditPage">
                </div>
              </div>

              <div class="row mb-3">
                <div class="col-sm-3">
                  <h6 class="mb-0">Email</h6>
                </div>
                <div class="col-sm-9 text-secondary">
                  <input type="text" class="form-control" v-model="userForm.email" :disabled="!isEditPage">
                </div>
              </div>

              <div class="row mb-3">
                <div class="col-sm-3">
                  <h6 class="mb-0">가입일자</h6>
                </div>
                <div class="col-sm-9 text-secondary">
                  <input type="text" class="form-control" v-model="formatRegDate" :disabled="true">
                </div>
              </div>

              <div class="row" v-show="isEditPage">
                <div class="col-sm-3"></div>
                <div class="col-sm-9 text-secondary d-flex justify-content-between ">
                  <input type="submit" class="btn btn-primary px-4" value="저장">
                  <button v-if="isEditPage" type="button" class="btn btn-danger px-4" @click.prevent="onWithdrawl">회원탈퇴</button>
                </div>               
              </div>
            </div>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import AppLink from './AppLink.vue';
import { computed, ref, onMounted } from 'vue';
import { useUserStore } from '@/store/user';
import { formatDate } from '@/utils/DateUtils';
import api from '@/api';
import { router, routerPush } from '@/router';

interface Props {
  user: User
  isEditPage: boolean
}

const userStore = useUserStore()
const props = defineProps<Props>()

const userForm = ref<User>({
  ...props.user
})

const departments = ref<Department[]>()

const showEdit = computed(() => userStore.currentUser?.userId === props.user?.userId)
const formatRegDate = computed(() => {
  if (props.user) return formatDate(props.user.regDate)
  else return ""
})

const onSave = async () => {
  await api.updateUser(userForm.value).then((updateUser) => {
    userStore.updateUserInfo(updateUser)
    routerPush("profile", { slug: props.user.userId.toString() })
  })
}

const onWithdrawl = async () => {
  await api.deleteUser(props.user).then(() => {
    userStore.logout()
    routerPush("global-feed")
  })
}

onMounted(async () => {
  console.log(userForm.value)
  if (props.isEditPage) {
    departments.value = await api.fetchDepartments()
  }
})

</script>

<style scoped>
body {
  background: #f7f7ff;
  margin-top: 20px;
}

.card {
  position: relative;
  display: flex;
  flex-direction: column;
  min-width: 0;
  word-wrap: break-word;
  background-color: #fff;
  background-clip: border-box;
  border: 0 solid transparent;
  border-radius: .25rem;
  margin-bottom: 1.5rem;
  box-shadow: 0 2px 6px 0 rgb(218 218 253 / 65%), 0 2px 6px 0 rgb(206 206 238 / 54%);
}

.row {
  display: flex;
  align-items: center;
}

input[type="text"]:disabled {
  background: none;
}
</style>@/utils/DateUtils