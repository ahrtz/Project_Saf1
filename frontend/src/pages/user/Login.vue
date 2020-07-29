<template>
  <div>
      <form class="login-form">
          <center>
            <table align="center">
              <caption>
              <h2>Login for BloGit </h2>
              </caption>
              <tr>
                <td style="margin:5px;padding: 5px;" align="left">Email</td> 
                <td><div><input class="login-id" v-model="loginData.email" required type="email" ></div></td>
              </tr>
              <tr>
                <td style="margin:5px;padding: 5px;" align="left">Password </td>
                <td><input class="login-passwd" v-model="loginData.pwd" type="password"  required placeholder= "" @focus="visi='visible'" @blur="visi='hidden'"><br>
                     
                </td>
              </tr>
              <tr>
                <td></td>
                <td><span :style="{visibility:visi}">*8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.</span></td>
              </tr>
            </table>
            <v-card
              class="login-flex-container align-start"
              flat
              color="white lighten-2"
              height="60"
            >
              <v-card
                class="login-flex-item "
                @click="active = true"
                color="green accent-1"
                onclick=""
              >
                <v-btn class="mr-4 d-flex" color="indigo" dark  @click="login()">로그인</v-btn>
              </v-card>
              <v-card
                class="login-flex-item "
                @click="active = true"
                color="white"
                onclick=""
                flat
              >
                <router-link class="login-router-link" :to="{name:'MainPage'}" exact >
                  <td><img src="../../../static/git_logo.png" style="height:40px;width:40px;" alt="git login" type="button"></td>
                </router-link>
              </v-card>
              <v-card
                class="login-flex-item "
                @click="active = true"
                color="white accent-2"
                onclick=""
                flat
              >
                <router-link class="login-router-link" :to="{name:'MainPage'}" exact >
                  <td><img src="../../../static/google_logo.png" style="height:40px;width:40px;" alt="google login" type="button"></td>  
                </router-link>
            </v-card>
            </v-card>
            <br>
            <router-link class="login-register" :to="{name:'SignUp'}">회원가입</router-link>
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
          email:null,
          pwd:null
        },
        dummy:null,
        visi:'hidden',
      }
    },
    methods:{
      async login() {
        try {
          await this.$api.login(this.loginData)
          this.$router.push({name:'MainPage'})
          location.reload()
        } catch (e) {
          console.log('실패')
        }
      }
    }
}
</script>

<style>
.login-form{
  /* background-color: yellow; */
  display: table; margin-left: auto; margin-right: auto;
}
table{ 
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
  margin: 5px;
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
  padding-left:5px;
  padding-right:5px;
}
.login-gitlogin{
  /* float: left; */
  float:unset;
}
.login-googlelogin{
  float:unset;
}
.login-register{
  float:unset;
}


.login-flex-container {
  width:100%;
  display:flex;
  flex-direction: row;
  justify-content: center;
  margin: 0px;
  padding: 0px;
  border-radius: 5px;
}
.login-flex-item {
  display:flex;
  margin: 10px;
  padding: 0px;
  text-align: center;
  height:40px;
  border-radius: 5px;
  color:"green accent-2";
  font-family: Recursive;
  font-size:20px;
}
.login-router-link {
  text-decoration: none;
}
.login-router-link-exact-active  {
  color: white;
  background-color:#56c068;
}
</style>