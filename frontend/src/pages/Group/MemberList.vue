<template>
  <div>
      <h2>멤버 리스트</h2>
      
      <br>
      group relation 을 요구 할건데 이때 id 뿐아니라 닉네임 과 이메일도 요청을 해야함
      -> 이건 제거를 위한거고 추가를 위한건 뭘 요구해야할까
      <br>

      정확한 이메일을 쳐야만 추가 되도록 
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