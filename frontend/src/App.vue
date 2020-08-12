<template>
  <!-- KCE: 렌더링 타이밍 이슈로 회원 정보 새로고침하면 데이터 다 날아감, 원인은 아래 코드(isLoggedIn) 날려서 그런거임!! 지우지마세요.-->
  <div id="app" v-if="isLoggedIn != null"> 
    <!-- 모든 페이지 공통 -->
    <header1 />
    <!-- 뷰별 페이지 -->
    <router-view :key="$route.fullPath" class="router-view" />
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
  }

}

</script>

<style>
* {
  font-family: NoTo Sans KR,sans-serif;
  color: #21262e;
}

.router-view {
  padding-top: 110px;
}

input, .v-text-field__slot, label {
  font-size: 12px !important;
}

.v-btn {
  font-family: NoTo Sans KR,sans-serif !important;
  padding: 0 16px !important;
  font-weight: 600;
  letter-spacing: 0px;
  height: 40px !important;
  border-radius: 6px;
}

.v-application .primary {
  background-color: #0051cb !important;
  border-color: #0051cb !important;
  box-shadow: none !important;
  color: #fff !important;
}

.v-pagination__item {
  background-color: #fff !important;
  color: #0051cb !important;
  box-shadow: none !important;
  border: solid 1px #0051cb !important;
}

.v-application .error {
  background-color: #db4455 !important;
  border-color: #db4455 !important;
  box-shadow: none !important;
  color: #fff !important;
}

.v-application .info {
  background-color: #fff !important;
  border-color: #0051cb !important;
  border: solid 1px;
  box-shadow: none !important;
  color: #0051cb !important;
}

.v-subheader {
  color: #21262e !important;
  font-size: 16px;
  font-weight: 600;
}

.v-list-item__title {
  font-size: 12px !important;
  color: #21262e !important;
  font-weight: 600;
}

.v-list-item__subtitle {
  font-size: 12px !important;
  font-weight: 600;
}
</style>
