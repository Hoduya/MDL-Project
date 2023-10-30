<template>
  <div class="container">
    <h2 class="title"> 현재 투표 결과: <b>{{ voteResultText }}</b></h2>
    <div class="unvote-rectangle" />
    <div class="vote-rectangle">
      <div class="vertical-line"></div>
      <FixedComponents
      v-for="(component, index) in fixedComponents"
      :key="index"
      :component="component"
      />
      <DraggableComponent v-if="userComponent"
        :component="userComponent"
        :max-x="750"
        :max-y="500"
        :min-y="-150"
        @position-change="updatePosition"
      />
      <h2 class="eat-in" style="z-index: -1;">
        구내식당
      </h2>
      <h2 class="eat-out" style="z-index: -1;">
        외식 
      </h2>
      <button @click="onRefresh" class="refresh-button">
        <i class="fa-solid fa-rotate-right fa-2xl" />  
      </button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useUserStore } from '@/store/user';
import { useToast } from 'vue-toastification';
import DraggableComponent from './DraggableComponent.vue'
import FixedComponents from './FixedComponent.vue'
import api from '@/api'

const userStore = useUserStore()
const toast = useToast()
const currentUserId = userStore.currentUser?.userId
const currentDeptId = userStore.currentUser?.deptId
const fixedComponents = ref<Component[]>([]);
const userComponent = ref<Component>();
const voteResultText = ref('');

const updatePosition = async (component: UpdateComponent) => {
  if(!isVotePosition(component.coordY)) {
    component.voteState = 0;
    toast.warning("투표를 진행해주세요", { timeout: false })
  } else {
    component.voteState = component.coordX < 375 ? 1 : 2
    toast.clear()
  }

  console.log(component.voteState)

  await api.updateUserComponent(component) // 현재 유저 컴포넌트 업데이트
  await fetchComponents() // 다른 모든 컴포넌트 정보 로드
}

const fetchComponents = async () => {
  const components = await api.fetchComponents(currentDeptId || 0)  
  fixedComponents.value = components.filter((component) => component.userId !== currentUserId)
  userComponent.value = components.find((component) => component.userId === currentUserId)
  
  updateVoteResult(components)
}

const updateVoteResult = (components: Component[]) => {
  const cafeteriaCount = components.filter((component) => component.voteState === 1 ).length // 구내식당 투표 수
  const eatOutCount = components.filter(component => component.voteState === 2).length // 외식 투표 수

  voteResultText.value = (cafeteriaCount >= eatOutCount) ? "구내식당" : "외식"
}

const isVotePosition = (coordY: number) => {
  return coordY >= 0
}

onMounted(() => {
  fetchComponents()
})

const onRefresh = () => {
  fetchComponents()
}
</script>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-start;
  position: relative;
}

.title {
  font-size: 24px;
  margin-top: 20px;
  text-align: center;
}

.unvote-rectangle {
  width: 750px;
  height: 150px;
  position: relative;
  border: 10px solid rgba(20, 33, 61, 0.8);
  border-bottom: 0;
  border-top-right-radius: 30px;
  border-top-left-radius: 30px;
}

.vote-rectangle {
  width: 750px;
  height: 500px;
  position: relative;
  border: 10px solid rgba(20, 33, 61, 0.8);
}

.vertical-line {
  position: absolute;
  left: 50%;
  top: 0;
  bottom: 0;
  border: 3px dashed rgba(252, 163, 17, 0.8);
}

.refresh-button {
  position: absolute;
  border: 0; 
  background-color: 
  transparent;
  right: 3%;
  bottom: 5%;
  z-index: 0;
}

.eat-in {
  position: absolute;
  border: 0; 
  background-color: 
  transparent;
  left: 18%;
  top: 40%;
  z-index: 0;
  color: rgba(153, 153, 153, 0.897);
}

.eat-out {
  position: absolute;
  border: 0; 
  background-color: 
  transparent;
  right: 20%;
  top: 40%;
  z-index: 0;
  color: rgba(153, 153, 153, 0.897);
}

</style>