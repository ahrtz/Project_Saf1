<template>
  <!-- KCE: 렌더링 타이밍 이슈로 회원 정보 새로고침하면 데이터 다 날아감, 원인은 아래 코드(isLoggedIn) 날려서 그런거임!! 지우지마세요.-->
  <div id="app" v-if="isLoggedIn != null"> 
    <!-- 모든 페이지 공통 -->
    <header1 :isLoginPage='isLoginPage'/>
    <!-- 뷰별 페이지 -->
    <router-view class="router-view" />
  </div>
</template>

<script>
import header1 from '../src/component/header1.vue'

let data = {}
let log = 'test'
export default {
  
  name: 'App',
  components:{ header1 },
  data: () => ({
    isLoggedIn: null,
    isLoginPage: false
  }),
  async beforeCreate(){
    try {
      let userData = await this.$api.getMe()
      this.$store.commit('userData', userData)
    } catch(e) {
      console.error(e)
      this.$store.commit('userData',{})
    }

    try {
      this.isLoggedIn = await this.$api.isLoggedIn()
      this.$store.commit('isLoggedIn',this.isLoggedIn)
    } catch(e) {
      console.log(e)
      this.$store.commit('isLoggedIn',false)
    }
  },
  created() {
    console.log(this.$route.path)
    if(this.$route.path == '/') {
      this.isLoginPage = true;
    }
  }

}

</script>

<style>
* {
  font-family: NoTo Sans KR,sans-serif;
  color: #21262e;
}

.router-view {
  padding-top: 70px;
}
</style>
