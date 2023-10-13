<template>
  <div class="auth-page">
    <div class="container page d-flex justify-content-center align-items-center">
      <div class="col-md-6 col-xs-12 text-center mx-auto">
        <h1 class="text-center mb-4 mt-5">회원가입</h1>
        <p class="text-center">
          <app-link name="login">계정이 있으신가요?</app-link>
        </p>

        <ul class="error-messages">
          <li v-for="(error, field) in errors" :key="field">
            {{ field }} {{ error ? error[0] : '' }}
          </li>
        </ul>

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
            <select required class="custom-select form-select shadow-none bg-light border-0">
              <option value=""> 소속 부서 </option>
              <option value=1>제목</option>
              <option value=2>내용</option>
              <option value=3>작성자</option>
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
import { reactive, ref } from 'vue'
import AppLink from '../components/AppLink.vue'
import { useUserStore } from '../store/user'
import { routerPush } from '../router'

const errors = ref('')
const loadding = ref(false)

const form = reactive<PostRegisterForm>({
  'email': '',
  'password': '',
  'name': '',
})

const userStore = useUserStore()

const onRegister = async () => {
  await userStore.register(form)
  await userStore.login(form)
    .catch((error) => {
      errors.value = error
    })
    .finally(() => {
      loadding.value = false
    })
  routerPush("login")
}

</script>

<style scoped>
.form-group {
  margin: 10px;
}
</style>