<template>
  <div>
      <h2>user 사이드바</h2>
      <router-link :to="{name:'AccountDetail'}">회원 정보</router-link>
      <br>
      <router-link :to="{name:'Follow'}">팔로우</router-link>
      <br>
      <router-link :to="{name:'Scrap'}">스크랩</router-link>
  </div>
</template>

<script>
export default {
    name:'userSidebar'
}
</script>

<style>

</style>