<template>
  <div class="auth-page">
    <div class="container page">
      <div class="row">
        <div class="col-md-6 offset-md-3 col-xs-12">
          <h1 class="text-xs-center">로그인</h1>
          <p class="text-xs-center">
            <app-link name="register">Need an account?</app-link>
          </p>

          <ul class="error-messages">
            <li v-for="(error, field) in errors" :key="field">
              {{ field }} {{ error ? error[0] : '' }}
            </li>
          </ul>

          <form @submit.prevent="onLogin">
            <fieldset class="form-group">
              <input
                v-model="form.email"
                class="form-control form-control-lg"
                type="email"
                placeholder="이메일"
                required />
            </fieldset>
            <fieldset class="form-group">
              <input
                v-model="form.password"
                class="form-control form-control-lg"
                type="password"
                placeholder="비밀번호"
                required />
            </fieldset>
            <button
              class="btn btn-lg btn-primary pull-xs-right"
              type="submit"
              :disabled="loadding">
              로그인
            </button>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>
<script lang="ts" setup>
import { ref, reactive } from 'vue'
import { routerPush } from '@/router'
import AppLink from '../components/AppLink.vue'
import { useUserStore } from '../store/user'

const errors = ref('')
const loadding = ref(false)

const form = reactive<PostLoginForm>({
  'email': '',
  'password': '',
})

const userStore = useUserStore()

const onLogin = async () => {
  loadding.value = true
  await userStore.login(form)
  .catch((error) => {
    errors.value = error
  })
  .finally(()=> {
    loadding.value = false
  })
  routerPush("global-feed")
}

</script>

