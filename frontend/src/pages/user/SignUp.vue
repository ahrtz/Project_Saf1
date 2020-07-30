<template>
  <div class="signup-container">
    <div class="signup-inner">
      <div class="signup-title">회원가입</div>
      <div class="signup-subtitle">email과 Git 아이디로 Blogit을 시작해보세요.</div>
      <div class="d-flex flex-column">
        <v-text-field
          class="d-flex justify-center signup-input"
          placeholder="이메일"
          outlined
          dense
          hide-details
          v-model="signupData.email"
          required
          type="email"
        ></v-text-field>
        <v-text-field
          class="d-flex justify-center signup-input"
          placeholder="비밀번호"
          outlined
          dense
          hide-details
          v-model="signupData.pwd"
          required
          type="password"
        ></v-text-field>
        <v-text-field
          class="d-flex justify-center signup-input"
          placeholder="비밀번호 확인"
          outlined
          dense
          hide-details
          v-model="signupData.pwdconfirm"
          required
          type="password"
        ></v-text-field>
        <v-text-field
          class="d-flex justify-center signup-input"
          placeholder="닉네임"
          outlined
          dense
          hide-details
          v-model="signupData.nickname"
          required
        ></v-text-field>
        <v-file-input ref="file" label="대표 이미지" prepend-icon="mdi-camera"></v-file-input>

        <v-text-field
          class="d-flex justify-center signup-input"
          placeholder="Git 아이디"
          outlined
          dense
          hide-details
          v-model="signupData.git_id"
          required
        ></v-text-field>
        <v-text-field
          class="d-flex justify-center signup-input"
          placeholder="Git url"
          outlined
          dense
          hide-details
          v-model="signupData.git_url"
          required
        ></v-text-field>
        <v-text-field
          class="d-flex justify-center signup-input"
          placeholder="Git token"
          outlined
          dense
          hide-details
          v-model="signupData.git_token"
          required
        ></v-text-field>
        <v-textarea solo label="자기소개" v-model="signupData.intro"></v-textarea>
      </div>
      <div class="d-flex justify-center align-center signup-btn" @click="signup">완료</div>
      <div class="d-flex justify-center align-center signup-cancel-btn" @click="goback">취소</div>
    </div>
  </div>
</template>

<script>
axios.defaults.headers.common['Access-Control-Allow-Origin'] = '*';
import axios from 'axios';
export default {
  name: 'SignUp',
  data() {
    return {
      signupData: {
        email: '',
        pwd: '',
        pwdconfirm: '',
        img: '',
        nickname: '',
        git_id: '',
        git_url: '',
        git_token: '',
        intro: '',
        is_social: '0',
        is_certified: '0',
      },
    };
  },
  methods: {
    goback() {
      this.$router.go(-1);
    },
    async signup() {
      if (this.signupData.pwd != this.signupData.pwdconfirm) {
        this.signupData.pwdconfirm = '';
        console.log('dnajk');
        alert('비밀번호가 다름');
      } else {
        try {
          this.$api.signupp(this.signupData, {
            headers: { 'Content-Type': 'application/json' },
          });
          console.log('성공');
          this.$router.push({ name: 'Login' });
        } catch (e) {
          console.log(e);
          console.log('실패');
        }
      }
    },
    checkpwd() {
      if (this.pwd != this.pwdconfirm) {
        this.pwdconfirm = '';
        console.log('dnajk');
        alert('비밀번호가 다름');
      }
    },
  },
};
</script>

<style>
.signup-container {
  padding-top: 77px;
  width: 100%;
}

.signup-inner {
  width: 450px;
  margin: 0 auto;
}

.signup-title {
  font-size: 24px;
  font-weight: 700;
}

.signup-subtitle {
  margin-top: 10px;
  margin-bottom: 30px;
  font-size: 12px;
  color: #21262e;
}

.signup-btn {
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

.signup-signup-btn {
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

.signup-btn {
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

.signup-cancel-btn {
  margin-top: 8px;
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

.signup-input {
  margin-bottom: 8px;
}
</style>