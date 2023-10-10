<template>
  <div class="input-group mb-3" style="max-width: 500px; margin-left: auto;">
    <div class="input-group-text p-0">
      <select v-model="selectedFilter" class="form-select form-select-lg shadow-none bg-light border-0">
        <option con>제목</option>
        <option>내용</option>
        <option>작성자</option>
      </select>
    </div>
    <input type="text" v-model="searchText" class="form-control" placeholder="Search Here">
    <button class="input-group-text px-4 btn-warning" @click="onSearch">
      검색
    </button>
  </div>
</template>

<script setup lang="ts">
import { useRoute, useRouter } from 'vue-router';
import { routerPush } from '@/router';
import { ref } from 'vue'

const route = useRoute()
const router = useRouter()

const selectedFilter = ref("title")
const searchText = ref("")

const onSearch = () => {
  routerPush("profile", {userId: searchText.value}).then(()=>{
    console.log(route.params)
  })
}

</script>