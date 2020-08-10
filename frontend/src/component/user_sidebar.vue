<template>
  <div class="user-sidebar">
    <div class="d-flex align-center user-sidebar-menu" @click="$router.push({name:'AccountDetail',params:{uid:userdata.id}})">회원 정보</div>
    <div class="d-flex align-center user-sidebar-menu" @click="$router.push({name:'GroupMain',params:{uid:userdata.id}})">그룹</div>
    <div class="d-flex align-center user-sidebar-menu" @click="$router.push({name:'Follow',params:{uid:userdata.id}})">팔로우</div>
    <div class="d-flex align-center user-sidebar-menu" @click="$router.push({name:'Scrap',params:{uid:userdata.id}})">스크랩</div>
    <div class="d-flex align-center user-sidebar-menu" @click="$router.push({name:'tmpPost',params:{uid:userdata.id}})">임시 저장 글</div>
  </div>
</template>

<script>
export default {
    name:'userSidebar',
    userdata:null,

    created(){
      this.userdata = this.$store.state.user
    }
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