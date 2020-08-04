<template>
  <div>
      <h2>내가 가입한 그룹 보는 페이지</h2>
      
      <div v-for="group in groups" :key="group.id">
        <router-link :to="{name:'GroupDetail',params:{gid:group.id}}">{{group}}그룹조회</router-link>"
        그룹들을 다 받아 와야합니다 그리고 아래에 깔면 그룹 아이디를 기준으로 넘김
        <br>
        <br>이건 카드뷰로 빼던지.... 리스트로 빼던지... 테이블로 빼던지....

        <!-- 이 안에 그룹 리스트 조회 (팔로우처럼 나오게) 라우터링크
        관리페이지 링크 (관리자만) -->

      </div>
  </div>
</template>

<script>
export default {
    name: 'GroupMain',
    data(){
      return{
        groups:[
          {
            id:1,
            OrganizationName:"1번그룹",
            Intro:'설명'
          },
          {
            id:2,
            OrganizationName:"2번그룹",
            Intro:'설명'
          },
          {
            id:3,
            OrganizationName:"3번그룹",
            Intro:'설명'
          },
        ]
      }
    }
}
</script>

<style>

</style>