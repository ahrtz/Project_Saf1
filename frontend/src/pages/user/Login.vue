<template>
  <div>
      <form class="login-form">
          <center>
            <table align="center">
              <caption>
              <h2>Login for BloGit </h2>
              </caption>
              <tr>
                <td align="left">Email</td> 
                <td><div><input class="login-id" v-model="loginData.id" required type="email" ></div></td>
              </tr>
              <tr>
                <td align="left">Password </td>
                <td><input class="login-passwd" v-model="loginData.password" type="password"  minlength="8" required placeholder= "" @focus="visi='visible'" @blur="visi='hidden'"><br>
                     <span :style="{visibility:visi}">*8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.</span>
                </td>
              </tr>
            </table>
            <center>
              <button type="submit" class="login-login">Login</button>
              <button class="login-gitlogin">Git Login</button>
              <button class="login-googlelogin">Google Login</button>
            </center>
            <br>
            <router-link class="login-register" :to="{name:'SignIn'}">회원가입</router-link>
          </center>
      </form>
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
        dummy:null,
        visi:'hidden',
      }
    },
}
</script>

<style>
.login-form{
  /* background-color: yellow; */
  display: table; margin-left: auto; margin-right: auto;
}
table{ 
  visibility:;
  border:"1px solid black" ;
}
.login-id{
  margin:5px;
  padding: 5px;
  padding-left: 10px;
  width:350px;
  border:2px solid rgb(112, 223, 208);  
}
.login-id:focus{
  width:350px;
  /* background-color: aquamarine; */
  border:2px solid rgb(219, 40, 40);
}
.login-passwd{
  margin:5px;
  padding: 5px;
  padding-left: 10px;
  width:350px;
  border:2px solid rgb(112, 223, 208);  
}
.login-passwd:focus{
  width:350px;
  color:black;
  border:2px solid aquamarine;
  /* background-color: aquamarine; */
}
.login-login{
  /* float: left; */
  margin:10px;
}
.login-gitlogin{
  /* float: left; */
  margin: 10px;
}
.login-googlelogin{
  float:unset;
  margin: 10px;
}
.login-register{
  float:unset;
}
</style>