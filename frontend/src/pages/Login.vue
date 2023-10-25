<template>
  <div class="auth-page">
    <div class="container page d-flex justify-content-center align-items-center">
      <div class="col-md-4 col-xs-12 text-center mx-auto">
        <h1 class="text-center mb-3 mt-5">로그인</h1>
          <p class="text-center mb-4">
            <app-link name="register">계정이 없으신가요?</app-link>
          </p>
          <p style="color:red">
            {{ loginErrorText }}
          </p>
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
</template>
<script lang="ts" setup>
import { ref, reactive, toDisplayString } from 'vue'
import { routerPush } from '@/router'
import { useToast } from 'vue-toastification'
import AppLink from '../components/AppLink.vue'
import { useUserStore } from '../store/user'
import api from '@/api';

const toast = useToast();
const loginErrorText = ref('')
const loadding = ref(false)

const form = reactive<PostLoginForm>({
  'email': '',
  'password': '',
})

const userStore = useUserStore()

const onLogin = async () => {
  loadding.value = true

  try {
    const { user, token } = await api.login(form)
    userStore.updateUser(user, token)
    toast.success("로그인되었습니다.", {
      timeout: 2000
    })
    routerPush("global-feed")
  } catch(error: any) {
    loginErrorText.value = error.message
  } finally {
    loadding.value = false
  }
}

</script>

<style scoped>
.form-group {
  margin: 10px;
}
</style>