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

let data = {}
let log = 'test'
export default {
  
  name: 'App',
  components:{ header1 },
  
  async beforeCreate(){
    try {
      let userData = await this.$api.getMe()
      this.$store.commit('userData', userData)
    } catch(e) {
      console.error(e)
      this.$store.commit('userData',{})
    }

    try {
      let isLoggedIn = await this.$api.isLoggedIn()
      this.$store.commit('isLoggedIn',isLoggedIn)
    } catch(e) {
      console.log(e)
      this.$store.commit('isLoggedIn',false)
    }
  },
}

</script>

<style>
* {
  font-family: NoTo Sans KR,sans-serif;
  color: #21262e;
}
</style>
