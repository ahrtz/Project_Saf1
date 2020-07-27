<template>
  <div>
      <h2>그룹 메인 페이지</h2>
      <router-link :to="{name:'GroupDetail'}">그룹조회(서브메뉴2)</router-link>"
      이 옆에 그룹 추가 버튼 필요
      <br>

      {{groups}}
      <br>이건 카드뷰로 빼던지.... 리스트로 빼던지... 테이블로 빼던지....

      <!-- 이 안에 그룹 리스트 조회 (팔로우처럼 나오게) 라우터링크
      관리페이지 링크 (관리자만) -->
  </div>
</template>

<script>
export default {
    name: 'GroupMain',
    data(){
      return{
        groups:['1번그룹','2번그룹','3번 그룹','4번 그룹','5번 그룹']
      }
    }
}
</script>

<style>

</style>