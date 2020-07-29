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
import axios from 'axios'

let data = {}
export default {
  
  name: 'App',
  components:{ header1 },
  
  async beforeCreate(){
    try {
      data = await axios.get('http://localhost:3000/users/me').data
      this.$store.commit('userData',data)
    }catch(e){
      console.error(e)
      this.$store.commit('userData',{})
    }

    try{
      let {log} = await axios.get('http://localhost:3000/users/is-logged-in').data
      this.$store.commit('isLoggedIn',log)
    }catch(e){
      console.log(e)
      this.$store.commit('isLoggedIn',false)
    }
  },
  
}

</script>

<style>
</style>
