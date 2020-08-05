<template>
  <div>
      <h2>그룹 상세정보 페이지</h2>
      <router-link :to="{name:'MemberList',params:{gid:this.groupId}}">멤버 관리 ( 이건 관리자만 보일거임)</router-link> 
      
      <br>
      <div>
        {{groupInfo.introduction}}
      </div>
      <br>
      <div>
        여기엔 등수를 3분할 쳐서 넣을거고
      </div>
      <br>
      <div>
        여기엔 그룹에 가입된 사람을 2분할 치고 쫙 뿌려서 넣어줄것이다
      </div>
  </div>
</template>

<script>
export default {
    name : 'GroupDetail',
    data(){
      return{
        groupId: this.$route.params ,
        groupInfo:{
          introduction:"비밀 스러운 디테일",
          organizationId:1,
          organizationName:'비밀',
        }
}
    }
}
</script>

<style>

</style>