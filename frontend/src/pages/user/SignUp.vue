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
          style="margin-bottom:16px;"
        ></v-text-field>
        <div>
          <v-btn @click="checkId()" style="margin-bottom:16px;" class="primary float-right">이메일 체크</v-btn>
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
          style="margin-bottom:16px;"
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
            <img :src="uploadImageFile" style="width: 100px;height: 100px;border-radius: 50%;border: 1px solid #ccc;"/>
            <input @change="onFileSelected($event)" ref="file" type="file" name="file" accept="image/*"/>
          </div>

        <v-text-field
          class="d-flex justify-center signup-input"
          placeholder="Git 아이디"
          outlined
          dense
          hide-details
          v-model="signupData.gitId"
          required
          style="margin-bottom:16px;"
        ></v-text-field>
        <v-text-field
          class="d-flex justify-center signup-input"
          placeholder="Git url"
          outlined
          dense
          hide-details
          v-model="signupData.gitUrl"
          required
          style="margin-bottom:16px;"
        ></v-text-field>
        <v-text-field
          class="d-flex justify-center signup-input"
          placeholder="Git token"
          outlined
          dense
          hide-details
          v-model="signupData.gitToken"
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
      tmpresult:"",
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
      uploadImageFile: ''
    };
  },
  methods: {
    onFileSelected(event) {
      var input = event.target;
      if (input.files && input.files[0]) { 
        var reader = new FileReader();
        reader.onload = (e) => { 
          this.uploadImageFile = e.target.result;
        }
        reader.readAsDataURL(input.files[0]);
      }
    },
    async checkId(){
      try{
        let tmpId=this.signupData.email
        let tmpres=await this.$api.isthere(tmpId)
        
        if (tmpres.length==''){
          alert('가입가능 ID')
        }else{
          alert('가입 불가능한 ID')
        }

      } catch(e){
        console.log(e)
      }
    },

    
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
          if(this.$refs.file != null) {
            this.signupData.file=this.$refs.file.files[0];
          }

          const formData = new FormData();
          formData.append('email', this.signupData.email);
          formData.append('pwd', this.signupData.pwd);
          formData.append('file', this.signupData.file);
          formData.append('img', this.signupData.img);
          formData.append('nickname',this.signupData.nickname);
          formData.append('gitId', this.signupData.gitId);
          formData.append('gitUrl',this.signupData.gitUrl);
          formData.append('gitToken', this.signupData.gitToken);
          formData.append('intro',this.signupData.intro);
          formData.append('isSocial', this.signupData.isSocial);
          formData.append('isCertified',this.signupData.isCertified);
          console.log(formData)

          await this.$api.signupp(formData, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
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