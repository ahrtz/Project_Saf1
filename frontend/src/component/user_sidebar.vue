<template>
  <div class="user-sidebar">
    <div class="d-flex align-center user-sidebar-menu" @click="$router.push({name:'AccountDetail'})">회원 정보</div>
    <div class="d-flex align-center user-sidebar-menu" @click="$router.push({name:'GroupMain'})">그룹</div>
    <div class="d-flex align-center user-sidebar-menu" @click="$router.push({name:'Follow'})">팔로우</div>
    <div class="d-flex align-center user-sidebar-menu" @click="$router.push({name:'Scrap'})">스크랩</div>
    <div class="d-flex align-center user-sidebar-menu" @click="$router.push({name:'tmpPost'})">임시 저장 글</div>
  </div>
</template>

<script>
export default {
    name:'userSidebar'
}
</script>

<style>
.user-sidebar {
  width: 200px;
  
}

.user-sidebar-menu {
  font-size: 14px;
  font-weight: bold;
  height: 50px;
  cursor: pointer;
}
</style>