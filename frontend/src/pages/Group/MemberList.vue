<template>
  <div>
      <h2>멤버 리스트</h2>
      멤버 리스트 출력(팔로우랑 같은 형식) 아이디는 클릭해서 넘어가는 형식으로
      <br>
      {{MemberList}}
  </div>
</template>

<script>
export default {
    name:'MemberList',
    data(){
      return{
        MemberList:['홍창경','전태준','김채은','오지환','박진수']
      }
    }
}
</script>

<style>

</style>