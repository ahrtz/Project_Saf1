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
let log = 'test'
export default {
  
  name: 'App',
  components:{ header1 },
  
  async beforeCreate(){
    try {
      // data = await axios.get('http://localhost:3000/users/me').data
      axios.get('http://localhost:3000/users/me').then(res=>{console.log(res)})
      console.log(data)
      this.$store.commit('userData',data)
    }catch(e){
      console.error(e)
      this.$store.commit('userData',{})
    }

    try{
      log = await axios.get('http://localhost:3000/users/is-logged-in').data
      console.log(log)
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
