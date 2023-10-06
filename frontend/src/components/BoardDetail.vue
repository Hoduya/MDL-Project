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
import { getBoardBySlug } from '@/services/board/getBoard'
import AppLink from './AppLink.vue';
import BoardMeta from './BoardMeta.vue';
import { userStore } from '@/store/user';
import { deleteBoard } from '@/services/board/deleteBoard'
import { routerPush } from '@/router';
import BoardComments from './BoardComments.vue';

const userStroe = userStore()

const route = useRoute()

const slug = route.params.slug as string

const board = ref<Board>(await getBoardBySlug(slug))

const showEdit = computed(
  () => userStroe.user?.name === board.value.writerName
)

const onDelete = async () => {
  await deleteBoard(slug)
  routerPush('global-feed')
}
</script>
