<template>
  <AppNavigation />
  <RouterView />
</template>

<script setup lang="ts">
import AppNavigation from './components/AppNavigation.vue'
</script>

<style>
a {
  text-decoration: none;
}
</style>