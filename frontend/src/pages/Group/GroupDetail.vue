<template>
  <div>
      <h2>그룹 리스트 조회 페이지</h2>
      <router-link :to="{name:'MemberList'}">멤버조회 및 관리</router-link> 
      <br>우측에 관리자 메뉴 필요 ( 그룹 관련 기본 사항 수정 멤버 관리는 멤버 조회내부에서 )
      <br>
      {{desc}}
      
  </div>
</template>

<script>
export default {
    name : 'GroupDetail',
    data(){
      return{
        desc:'그룹소개와 기타 등등이  올 곳 아니면 '
      }
    }
}
</script>

<style>

</style>