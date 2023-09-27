<template>
  <AppNavigation />
  <RouterView />
</template>

<script setup lang="ts">
import AppNavigation from './components/AppNavigation.vue'
</script>

<style>

@font-face {
  font-family:'NanumSquare';
  src: url('https://cdn.jsdelivr.net/gh/moonspam/NanumSquare@2.0/nanumsquare.css') format('truetype');
  font-weight: 700;
}

</style>