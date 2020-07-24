<template>
  <div>
      <h2>로그인 페이지</h2>
      <br>
      id <input type="text" placeholder="id를 입력하세요">
      <br>
      pw <input type="text" placeholder= "pw를 입력하세요">
      <br>
      <button type="submit">Login 버튼</button>
      <br>
      <button>Git Login 버튼</button>
      <br>
      <button>Google Login 버튼</button>
      <br>
      <router-link :to="{name:'SignIn'}">회원가입</router-link>
  </div>
</template>

<script>
export default {
    name: 'Login'
}
</script>

<style>

</style>