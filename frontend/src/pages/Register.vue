<template>
  <div class="auth-page">
    <div class="container page">
      <div class="row">
        <div class="col-md-6 offset-md-3 col-xs-12">
          <h1 class="text-xs-center">회원가입</h1>
          <p class="text-xs-center">
            <app-link name="login">Have an account?</app-link>
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
                placeholder="이름"
              />
            </fieldset>
            <fieldset class="form-group">
              <input
                v-model="form.email"
                class="form-control form-control-lg"
                type="email"
                required
                placeholder="이메일"
              />
            </fieldset>
            <fieldset class="form-group">
              <input
                v-model="form.password"
                class="form-control form-control-lg"
                type="password"
                required
                placeholder="비밀번호"
              />
            </fieldset>
            <button
              class="btn btn-lg btn-primary pull-xs-right"
              type="submit"
              :disabled="loadding"
            >
              Sign up
            </button>
          </form>
        </div>
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
  .finally(()=> {
    loadding.value = false
  })
  routerPush("login")
}

</script>