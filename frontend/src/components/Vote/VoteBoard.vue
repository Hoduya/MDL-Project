<template>
  <div class="container">
    <h2 class="title"> {{  }} </h2>
    <div class="unvote-rectangle">

    </div>
    <div class="vote-rectangle">
      <div class="vertical-line"></div>
      <FixedComponents
      v-for="(component, index) in fixedComponents"
      :key="index"
      :component="component"
      />
      <DraggableComponent v-if="draggableComponent"
        :component="draggableComponent"
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
import { ref, onMounted, watch, computed } from 'vue'
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
const draggableComponent = ref<Component>();
const voteResultText = ref('');

const fetchComponents = async () => {
  const components = await api.fetchComponents(currentDeptId || 0)
  fixedComponents.value = components.filter((component) => component.userId !== currentUserId)
  draggableComponent.value = components.find((componet) => componet.userId === currentUserId)
  updateVoteResult(components)
}

const updatePosition = async (component: UpdateComponent) => {
  await api.updateComponent(component)
  await fetchComponents()
}

const updateVoteResult = (components: Component[]) => {
  const voteTotalCount = components.length
  let cafeteriaCount = 0
  components.forEach((component) => {
    if (component.coordX < 375) {
      cafeteriaCount += 1;
    }
  })
  voteResultText.value = (voteTotalCount / 2 <= cafeteriaCount) ? "구내식당" : "외식"
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