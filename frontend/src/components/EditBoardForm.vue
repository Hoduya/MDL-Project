<template>
    <form @submit.prevent="onSubmit">
      <fieldset>
        <fieldset class="form-group">
          <input
            v-model="formData.title"
            type="text"
            class="form-control form-control-lg"
            placeholder="제목"
            data-test="title"
          />
        </fieldset>
        <fieldset class="form-group">
          <textarea
            v-model="formData.content"
            class="form-control"
            rows="8"
            placeholder="내용을 입력해주세요"
            data-test="content"
          ></textarea>
        </fieldset>
        <fieldset class="form-group">
          <input
            v-model="newTag"
            type="text"
            class="form-control"
            placeholder="Enter tags"
            @keydown.enter.prevent="addTag"
            data-test="newTag"
          />
          <TagList :tags="formData.tagList" editable @click="removeTag" />
        </fieldset>
        <button class="btn btn-lg pull-xs-right btn-primary" type="submit">
          Publish Article
        </button>
      </fieldset>
    </form>
  </template>
  
  <script lang="ts">
  export default {
    name: 'EditArticleForm',
  }
  </script>
  
  <script lang="ts" setup>
  import { ref, reactive, computed, watch } from 'vue'
  import { useRoute } from 'vue-router'
  import { routerPush } from '../router'
  import { getBoardBySlug } from '../services/board/getBoard'
  import { postBoard, putBoard } from '../services/board/postBoard'
  import TagList from './TagList.vue'
  
  interface Form {
    title: string
    content: string
    tagList: string[]
  }
  
  const route = useRoute()
  const slug = computed(() => route.params.slug as string)
  
  const formData = reactive<Form>({
    title: '',
    content: '',
    tagList: [],
  })
  
  const newTag = ref('')
  
  const addTag = () => {
    const val = newTag.value.trim()
    if (!formData.tagList.find((t) => t === val)) {
      formData.tagList.push(newTag.value.trim())
      newTag.value = ''
    }
  }
  
  const removeTag = (tag: string) => {
    formData.tagList = formData.tagList.filter((t) => t !== tag)
  }

  const onSubmit = async () => {
    let board = null
    if (slug.value) {
      board = await putBoard(slug.value, formData)
    } else {
      board = await postBoard(formData)
    }
    console.log(board)
    if (board) await routerPush('board', { slug: board.bno })
  }
  
  async function fetchArticle() {
    if (!slug.value) return
    const article = await getBoardBySlug(slug.value)
    formData.title = article.title
    formData.content = article.content
  }
  
  await fetchArticle()
  </script>
  