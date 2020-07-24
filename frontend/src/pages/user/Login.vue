<template>
  <div>
      <h2>로그인 페이지</h2>
      <br>
      id <input v-model="loginData.id" type="text" placeholder="id를 입력하세요">
      <br>
      pw <input v-model="loginData.password" type="text" placeholder= "pw를 입력하세요">
      <br>
      <button type="submit">Login 버튼</button>
      <br>
      <button>Git Login 버튼</button>
      <br>
      <button>Google Login 버튼</button>
      <br>
      <router-link :to="{name:'SignUp'}">회원가입</router-link>
  </div>
</template>

<script>
export default {
    name: 'Login',
    data(){
      return{
        loginData:{
          id:null,
          password:null
        },
        dummy:null
      }
    }
}
</script>

<style>

</style>