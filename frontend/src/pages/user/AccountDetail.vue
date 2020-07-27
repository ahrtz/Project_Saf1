<template>
  <div>
      <h2>AccoutnDetail 페이지</h2>
      <user-sidebar/>
      <div>유저 사이드 바는 우측으로 밀고</div>
      <div>{{user}}</div>
  </div>

</template>

<script>
import userSidebar from '../../component/user_sidebar.vue'



export default {
    name: 'AccountDetail',
    components : {
        userSidebar
        },
    data(){
      return{
        user:[
        {name: '유저이름',
        email : '등등 이런 정보 여기다가 form 형식으로 빼주면 댐'}
      ]
    }

}
}
</script>

<style>

</style>