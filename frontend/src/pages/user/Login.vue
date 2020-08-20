<template>
  <div class="d-flex login-container">
    <div class="d-flex login-left">
      <div class="d-flex flex-column" style="width: 100%">
        <div class="d-flex flex-grow-0 algin-center login-logo-container">
          <div class="d-flex align-center">
            <img class="login-logo" src="/static/images/Blogit_logo.png" />
          </div>
        </div>
        <div class="d-flex justify-center">
          <div class="d-flex flex-column" style="width: 320px">
            <div class="login-title">
              Blogit에 오신것을
              <br />환영합니다.
            </div>
            <div class="login-subtitle">이메일 주소 또는 SNS 간편 로그인을 통하여 로그인 해 주세요.</div>
            <v-text-field
              class="d-flex flex-grow-0 justify-center"
              placeholder="아이디"
              outlined
              dense
              hide-details
              v-model="loginData.email"
              required
              type="email"
            ></v-text-field>
            <v-text-field
              class="d-flex flex-grow-0 justify-center"
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
            <div
              class="d-flex flex-grow-0 justify-center align-center login-btn"
              @click="login()"
            >로그인</div>
            <div
              class="d-flex flex-grow-0 justify-center align-center login-signup-btn"
              @click="$router.push({name:'SignUp'})"
            >회원가입</div>
            <div class="d-flex justify-center login-social-logo-container">
              <img
                @click="githubLogin()"
                class="login-social-logo"
                style="margin-right:16px;"
                src="/static/git_logo.png"
              />
              <img class="login-social-logo" src="/static/google_logo.png" @click="googleLogin()" />
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="d-flex login-right">
      <div class="login-right-img"></div>
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
        pwd: '',
      },
      dummy: null,
      visi: 'hidden',
    };
  },
  created() {
    this.dummy = this.$store.state.isLoggedIn;
    if (this.dummy) {
      let temp = this.$store.state.user.id;
      this.$router.push({ name: 'MainPagefor', params: { uid: temp } });
      // console.log('>>>>');
    }
  },
  methods: {
    async login() {
      try {
        let tmp = await this.$api.login(this.loginData);
        // console.log(tmp.data);
        this.$router.push({ name: 'MainPagefor', params: { uid: tmp.data } });
        location.reload();
      } catch (e) {
        alert('아이디 또는 비밀번호를 확인해주세요.');
        // console.log('실패');
      }
    },
    githubLogin() {
      var temp = window.open('/api/oauth2/authorization/github');
    },
    googleLogin() {
      var temp = window.open('/api/oauth2/authorization/google');
    },
  },
};
</script>

<style>
.login-container {
  width: 100%;
  margin-top: -110px;
}

.login-left {
  min-width: 450px;
  width: 150px;
}

.login-logo-container {
  padding: 12px;
}

.login-logo {
  width: 50px;
  height: 50px;
}

.login-right {
  background: #0051cb;
  width: 300px;
}

.login-right-img {
  width: 100%;
  height: 100%;
  background: url('/static/images/mac_main.png') no-repeat;
  background-position: center 235px;
}

.login-title {
  padding-top: 70px;
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

.login-social-logo-container {
  padding-top: 24px;
}

.login-social-logo {
  width: 40px;
  height: 40px;
  cursor: pointer;
}

.login-hint {
  font-size: 12px;
  color: #21262e;
}
</style>