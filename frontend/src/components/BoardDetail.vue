<template>
  <div class="container mt-5">
    <div class="row">
      <div class="col-lg-">
        <!-- Post content-->
        <article>
          <!-- Post header-->
          <header class="mb-3">
            <!-- Post title-->
            <h1 class="fw-bolder mb-1">{{ board.title }}</h1>
            <!-- Post meta content-->
            <div class="d-flex align-items-center mt-4">
              <BoardMeta :board="board" class="me-3 fst-italic" />
              <AppLink name='edit-board' class="btn btn-sm btn-success me-2" v-if="showEdit">
                글 수정
              </AppLink>
              <button class="btn btn-sm btn-danger" v-if="showEdit" v-on:click="onDelete">
                글 삭제
              </button>
            </div>
          </header>
          <hr>
          <section class="p-2">
            <p class="fs-5 mb-4">{{ board.content }}</p>
          </section>
        </article>
      </div>
    </div>

    <div class="row">
      <div class="col-xs-12 col-md-10 offset-md-1">
        <suspense>
          <template #default>
            <BoardComments />
          </template>
          <template #fallback>
            <div class="card">
              <div class="card-block">Loading comments...</div>
            </div>
          </template>
        </suspense>
      </div>
    </div>
  </div>
</template>
<script lang="ts" setup>
import { ref, computed } from 'vue'
import { useRoute } from 'vue-router'
import AppLink from './AppLink.vue';
import BoardMeta from './BoardMeta.vue';
import { useUserStore } from '@/store/user';
import { routerPush } from '@/router';
import BoardComments from './BoardComments.vue';
import api from '@/api';
import { useToast } from 'vue-toastification';

const userStore = useUserStore()
const route = useRoute()
const slug = route.params.slug as string
const board = ref<Board>(await api.fetchBoard(slug))
const toast = useToast()

const showEdit = computed(
  () => 
  { console.log(userStore.currentUser)
    console.log(board.value)
    return userStore.currentUser?.userId === board.value.author.userId }
)

const onDelete = async () => {
  await api.deleteBoard(slug)
  toast.success("게시글 삭제 완료", {
      timeout: 2000
  })
  routerPush('global-feed')
}
</script>
