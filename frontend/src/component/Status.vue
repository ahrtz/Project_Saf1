<template>
  <div class="pa-2 status-board">
      <!-- 스테이터스를 넣어보자
      <br>
      이건 메인이랑 블로그 디테일이랑 다이어리 디테일에서 쓰일 겁니다. -->
      <profile class="mr-2" v-for="(i,index) in 3" :key="index">
      </profile>
      
      <!-- <img style="width: 100%" src="/static/images/grass.png" /> -->
  </div>
</template>

<script>
import profile from './GitProfile.vue'
export default {
name:'status',
components : {
  profile,
}
}
</script>

<style>
.status-board {
  display:flex;
  flex-direction: row;
  flex-grow: 0;
  justify-content:center;
}
</style>