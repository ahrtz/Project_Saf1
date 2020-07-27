<template>
  <div id="app">
    <!-- 모든 페이지 공통 -->
    <header1 />
    <!-- 뷰별 페이지 -->
    <router-view/>
  </div>
</template>

<script>
import header1 from '../src/component/header1.vue'

export default {
  name: 'App',
  components:{ header1 }
};
</script>

<style>
</style>
