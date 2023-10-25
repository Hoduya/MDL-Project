<template>
  <div class="auth-page">
    <div class="container page d-flex justify-content-center align-items-center">
      <div class="col-md-4 col-xs-12 text-center mx-auto">
        <h1 class="text-center mb-3 mt-5">회원가입</h1>
        <p class="text-center mb-4">
          <app-link name="login">계정이 있으신가요?</app-link>
        </p>
        <p style="color:red">
            {{ signupErrorText }}
        </p>
        <form @submit.prevent="onRegister">
          <fieldset class="form-group">
            <input
              v-model="form.name"
              class="form-control form-control-lg"
              type="text"
              required
              placeholder="이름" />
          </fieldset>
          <fieldset class="form-group">
            <input
              v-model="form.email"
              class="form-control form-control-lg"
              type="email"
              required
              placeholder="이메일" />
          </fieldset>
          <fieldset class="form-group">
            <input
              v-model="form.password"
              class="form-control form-control-lg"
              type="password"
              required
              placeholder="비밀번호" />
          </fieldset>
          <fieldset class="form-group">
            <select v-model="form.deptId" required class="form-select shadow-none bg-light border-0">
              <option value="" disabled selected>소속 부서</option>
              <option v-for="department in departments" :key="department.deptId" :value="department.deptId">
                {{ department.name }}
              </option>
            </select>
          </fieldset>
          <button
            class="btn btn-lg btn-primary btn-block"
            type="submit"
            :disabled="loadding">
            회원가입
          </button>
        </form>
      </div>
    </div>
  </div>
</template>


<script lang="ts" setup>
import { reactive, ref, onMounted } from 'vue'
import AppLink from '../components/AppLink.vue'
import { routerPush } from '../router'
import { useToast } from 'vue-toastification'
import api from '@/api';

const toast = useToast()
const signupErrorText = ref('')
const loadding = ref(false)
const departments = ref<Department[]>()

const form = reactive<PostRegisterForm>({
  'email': '',
  'password': '',
  'name': '',
  'deptId': ''
})

const onRegister = async () => {
  loadding.value = true
  try {
    await api.register(form)
    toast.success("회원가입 완료. 로그인 후 이용바랍니다.", {
      timeout: 2000
    })
    routerPush("login")
  } catch (error: any) {
    signupErrorText.value = error.message
  } finally {
    loadding.value = false
  }
}

onMounted(async () => {
  departments.value = await api.fetchDepartments()
})

</script>

<style scoped>
.form-group {
  margin: 10px;
}
</style>