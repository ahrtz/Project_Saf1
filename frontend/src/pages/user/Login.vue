<template>
  <div class="login-container d-flex flex-column align-center">
    <div class="login-inner">
      <div class="login-title">Blogit에 오신것을<br> 환영합니다.</div>
      <div class="login-subtitle">이메일 주소 또는 SNS 간편 로그인을 통하여 로그인 해 주세요.</div>
      <v-text-field
        class="d-flex justify-center"
        placeholder="아이디"
        outlined
        dense
        hide-details
        v-model="loginData.email"
        required
        type="email"
      ></v-text-field>
      <v-text-field
        class="d-flex justify-center"
        style="margin-top:16px;"
        placeholder="비밀번호"
        outlined
        dense
        hide-details
        v-model="loginData.pwd"
        required
        type="password"
        @focus="visi='visible'"
        @blur="visi='hidden'"
        @keypress.enter="login()"
      ></v-text-field>
      <span class="login-hint" :style="{visibility:visi}">*8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.</span>
      <div class="d-flex justify-center align-center login-btn" @click="login()">로그인</div>
      <div
        class="d-flex justify-center align-center login-signup-btn"
        @click="$router.push({name:'SignUp'})"
      >회원가입</div>
      <div class="d-flex justify-center login-logo-container">
        <img @click="onLogoClick" class="login-logo" style="margin-right:16px;" src="/static/git_logo.png"/>
        <img class="login-logo" src="/static/google_logo.png" @click="googleLogin()"/>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Login',
  data() {
    return {
      loginData: {
        email: null,
        pwd: null,
      },
      dummy: null,
      visi: 'hidden',
    };
  },
  methods: {
    onLogoClick() {
      alert("준비중입니다.");
    },
    async login() {
        try {
          await this.$api.login(this.loginData)
          this.$router.push({name:'MainPage'})
          location.reload()
        } catch (e) {
          alert("아이디 또는 비밀번호를 확인해주세요.")
          console.log('실패')
        }
      },
    googleLogin(){

      var temp= window.open('/api/google')
      
    }

  },
};
</script>

<style>
.login-container {
  padding-top: 77px;
  width: 100%;
}

.login-inner {
  width: 380px;
}

.login-title {
  font-size: 24px;
  font-weight: 700;
}

.login-subtitle {
  margin-top: 10px;
  margin-bottom: 30px;
  font-size: 12px;
  color: #21262e;
}

.login-btn {
  margin-top: 32px;
  width: 100%;
  font-size: 14px;
  background: #0051cb;
  font-weight: 600;
  color: #fff;
  border-radius: 6px;
  height: 40px;
  cursor: pointer;
}

.login-signup-btn {
  margin-top: 16px;
  width: 100%;
  font-size: 14px;
  background: #fff;
  font-weight: 600;
  color: #0051cb;
  border: solid 1px #0051cb;
  border-radius: 6px;
  height: 40px;
  cursor: pointer;
}

.login-logo-container {
  padding-top: 24px;
}

.login-logo {
  width: 40px;
  height: 40px;
  cursor: pointer;
}

.login-hint {
  font-size: 12px;
  color: #21262e;
}
</style>