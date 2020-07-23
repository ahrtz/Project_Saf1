<template>
  <div>
      그룹 메인 페이지
      <br>
      안에 사이드바 하나 만들고 (서브 메뉴창)
      <router-link :to="{name:'MemberList'}">멤버조회</router-link>
      <br>
      이 안에 그룹 리스트 조회 (팔로우처럼 나오게) 라우터링크
      관리페이지 링크 (관리자만)
  </div>
</template>

<script>
export default {
    name: 'GroupMain'
}
</script>

<style>

</style>