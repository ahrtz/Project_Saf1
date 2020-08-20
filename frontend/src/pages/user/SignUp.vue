<template>
  <div class="signup-container">
    <div class="signup-inner">
      <div class="signup-title">회원가입</div>
      <div class="signup-subtitle">email과 Git 아이디로 Blogit을 시작해보세요.</div>
      <div class="d-flex flex-column">
        <span v-if="!checkmail" class="login-hint" :style="{visibility:visimail}">이메일 형식에 맞춰주세요</span>
        <span v-if="checkmail" class="login-hint" :style="{visibility:visimail}">중복된 이메일이 있는지 체크해주세요</span>
        <v-text-field
          class="d-flex justify-center signup-input"
          placeholder="이메일"
          outlined
          dense
          hide-details
          v-model="signupData.email"
          required
          type="email"
          style="margin-bottom:16px;"
          @focus="visimail='visible'"
          @blur="visimail='hidden'"
        ></v-text-field>
        <div>
          <div
            class="d-flex justify-center align-center flex-grow-0 s-button-blue"
            @click="checkId()"
            style="margin-bottom:16px;"
          >이메일 체크</div>
        </div>
        <span v-if="!checkpwd && !checkpwdlength" class="login-hint" :style="{visibility:visipw}">*8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.</span>
        <span 
        v-if="!checkpwd && checkpwdlength" class="login-hint" :style="{visibility:visipw}">*8~16자 내에서 형식을 맞춰 주세요(영문 대 소문자, 숫자, 특수문자)</span>
        
        <div>
          <span v-if="checkpwd" class="login-complete" :style="{visibility:visipw}">사용가능한 비밀번호 입니다</span>
          <v-icon v-if="checkpwd" class=' justify-end' color="green" :style="{visibility:visipw}">mdi-check-bold</v-icon>
        </div>
        <v-text-field
          class="d-flex justify-center signup-input"
          placeholder="비밀번호"
          outlined
          dense
          hide-details
          v-model="signupData.pwd"
          required
          type="password"
          style="margin-bottom:16px;"
          @focus="visipw='visible'"
          @blur="visipw='hidden'"
        ></v-text-field>
        <span v-if="!samepwd" class="login-hint" :style="{visibility:visipw2}">동일한 비밀번호를 입력해주세요</span>
        <span v-if="samepwd " class="login-hint" :style="{visibility:visipw2}">동일한 비밀번호 입니다.</span>
        <v-text-field
          class="d-flex justify-center signup-input"
          placeholder="비밀번호 확인"
          outlined
          dense
          hide-details
          v-model="signupData.pwdconfirm"
          required
          type="password"
          style="margin-bottom:16px;"
          @focus="visipw2='visible'"
          @blur="visipw2='hidden'"
        ></v-text-field>




        <v-text-field
          class="d-flex justify-center signup-input"
          placeholder="닉네임"
          outlined
          dense
          hide-details
          v-model="signupData.nickname"
          required
          style="margin-bottom:16px;"
        ></v-text-field>

        <div class="input-wrap">
          <img
            :src="uploadImageFile"
            style="width: 100px;height: 100px;border-radius: 50%;border: 1px solid #ccc;"
          />
          <input
            @change="onFileSelected($event)"
            ref="file"
            type="file"
            name="file"
            accept="image/*"
          />
        </div>

        <span
          class="git-hint"
          :style="{visibility:visigit}"
        >아이디는 Github에 로그인이 되었을때 프로필상에 나오는 이름을 의미합니다.</span>
        <v-text-field
          class="d-flex justify-center signup-input"
          placeholder="Git 아이디"
          outlined
          dense
          hide-details
          v-model="signupData.gitId"
          required
          style="margin-bottom:16px;"
          @focus="visigit='visible'"
          @blur="visigit='hidden'"
        ></v-text-field>
        <span class="git-hint" :style="{visibility:visig}">아이디와 토큰 모두 일치해야 인증이 완료 됩니다</span>
        <div>
          <v-text-field
            class="d-flex justify-center signup-input"
            placeholder="Git token"
            outlined
            dense
            hide-details
            v-model="signupData.gitToken"
            required
            style="margin-bottom:16px;"
            @focus="visig='visible'"
            @blur="visig='hidden'"
          ></v-text-field>
          <v-dialog v-model="dialog" max-width="800px">
            <template v-slot:activator="{on}">
              <i
                v-on="on"
                class="far fa-question-circle"
                style="cursor:pointer;float:right;margin-bottom:8px;font-size: 14px;font-weight: 600"
              >도움말</i>
            </template>
            <template>
              <!-- prev-icon="mdi-arrow-left"
              next-icon="mdi-arrow-right"-->
              <v-carousel :show-arrows="false">
                <v-carousel-item v-for="(image,i) in images" :key="i" :src="image"></v-carousel-item>
              </v-carousel>
            </template>
          </v-dialog>
        </div>
        <div>
          <div
            class="d-flex justify-center align-center flex-grow-0 s-button-blue"
            style="margin-bottom:16px;"
            @click="certifyGit()"
          >토큰 검증</div>
        </div>
        <v-text-field
          class="d-flex justify-center signup-input"
          placeholder="본인 Blog 주소"
          outlined
          dense
          hide-details
          v-model="signupData.gitUrl"
          required
          style="margin-bottom:16px;"
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
      visimail:'hidden',
      visipw: 'hidden',
      visipw2: 'hidden',
      visig: 'hidden',
      visigit: 'hidden',
      tmpresult: '',
      signupData: {
        email: '',
        pwd: '',
        pwdconfirm: '',
        file: {},
        img: '',
        nickname: '',
        gitId: '',
        gitUrl: '',
        gitToken: '',
        intro: '',
        isSocial: '0',
        isCertified: '0',
      },
      uploadImageFile: '',
      dialog: false,
      images: [
        '/static/images/token1.png',
        '/static/images/token2.png',
        '/static/images/token3.png',
        '/static/images/token4.png',
        '/static/images/token5.png',
      ],
    };
  },

  methods: {
    onFileSelected(event) {
      var input = event.target;
      if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = (e) => {
          this.uploadImageFile = e.target.result;
        };
        reader.readAsDataURL(input.files[0]);
      }
    },
    async checkId() {
      let tmpId = this.signupData.email;
      if(/^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i.test(tmpId)==false){
        alert('이메일 형식을 맞춰 주십시오')
      }else{
        try {
        let tmpres = await this.$api.isthere(tmpId);
        // console.log(tmpres)  
        alert('이미 존재하는 id 입니다')
      }catch(e){
        alert(e.response.data.errMsg)
      }
      }



        // if (tmpres.length == '') {
        //   alert('가입 가능한 아이디입니다.');
        // } else {
        //   alert('가입 불가능한 아이디입니다. 다른 아이디를 사용해주세요.');
        // }

    },

    goback() {
      this.$router.go(-1);
    },
    async signup() {
      if (this.signupData.pwd != this.signupData.pwdconfirm) {
        this.signupData.pwdconfirm = '';
        // console.log('dnajk');
        alert('비밀번호가 다릅니다.');
      } else if (
        this.signupData.pwd.length < 8 ||
        this.signupData.pwd.length > 16
      ) {
        alert('8자에서 16자 사이에서 입력해 주세요 ');
      } else if (
        /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,16}$/.test(
          this.signupData.pwd
        ) == false
      ) {
        alert('소문자 한개 숫자 한개 특수문자 한개는 필수조건입니다');
      } else {
        try {
          if (this.$refs.file != null) {
            this.signupData.file = this.$refs.file.files[0];
          }

          const formData = new FormData();
          formData.append('email', this.signupData.email);
          formData.append('pwd', this.signupData.pwd);
          formData.append('file', this.signupData.file);
          formData.append('img', this.signupData.img);
          formData.append('nickname', this.signupData.nickname);
          formData.append('gitId', this.signupData.gitId);
          formData.append('gitUrl', this.signupData.gitUrl);
          formData.append('gitToken', this.signupData.gitToken);
          formData.append('intro', this.signupData.intro);
          formData.append('isSocial', this.signupData.isSocial);
          formData.append('isCertified', this.signupData.isCertified);
          // console.log(formData);

          await this.$api.signupp(formData, {
            headers: {
              'Content-Type': 'multipart/form-data',
            },
          });
          // console.log('성공');
          this.$router.push({ name: 'Login' });
        } catch (e) {
          alert(e.response.data.errMsg);
        }
      }
    },
    // checkpwd(pw) {
    //    return /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,}$/.test(pw)
    //  if(pw.length < 8 || pw.length > 16){

    //   alert("8자리 ~ 16자리 이내로 입력해주세요.");
    //   return false;
    // }else if(pw.search(/\s/) != -1){
    //   alert("비밀번호는 공백 없이 입력해주세요.");
    //   return false;
    // }else if(num < 0 || eng < 0 || spe < 0 ){
    //   alert("영문,숫자, 특수문자를 혼합하여 입력해주세요.");
    //   return false;
    // }else {
    //   console.log("통과");
    //     return true;
    // }

    // if (this.pwd != this.pwdconfirm) {
    //   this.pwdconfirm = '';
    //   console.log('dnajk');
    //   alert('비밀번호가 다릅니다.');
    // }
    // },
    async certifyGit() {
      try {
        let tmpid = this.signupData.gitId;
        let tmpToken = this.signupData.gitToken;
        let res = await this.$api.certgitToken({
          gitId: tmpid,
          accessToken: tmpToken,
        });

        if (res.data == false) {
          alert('인증 실패! \n깃 닉네임과 토큰을 다시 확인 해 주세요');
        } else {
          this.signupData.isCertified = 1;
          alert('인증되었습니다');
        }
      } catch (e) {
        // console.log(e);
      }
    },
  },
  computed: {
    checkpwd() {
      return /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,16}$/.test(this.signupData.pwd);
    },
    checkmail(){
      return /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i.test(this.signupData.email)
    },
    samepwd(){
      return this.signupData.pwd==this.signupData.pwdconfirm
    },
    checkpwdlength(){
      if (this.signupData.pwd.length==0){
        return false
      }else{
        return true
      }
    },
    checkpwd2length(){
      if (this.signupData.pwdconfirm.length==0){
        return false
      }else{
        return true
      }
    }
  },
};
</script>

<style>
.signup-container {
  padding-bottom: 70px;
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

.login-complete{
  display: inline-block;
}
</style>
