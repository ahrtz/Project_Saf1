<template>
  <div>
    <h2>헤더</h2>

    <router-link to="/">Main</router-link>
    <router-link :to="{name:'Diary'}">Diary</router-link>
    <router-link :to="{name:'GroupMain'}">Group</router-link>
    여기엔 포폴 링크,
    검색창
    <router-link :to="{name:'Login'}">로그인</router-link>
    <router-link :to="{name:'AccountDetail'}">회원 정보 </router-link>
    
    
  </div>
</template>

<script>
export default {
  name:'header1'
}
</script>

<style>

</style>