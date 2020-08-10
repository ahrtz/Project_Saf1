<template>
  <div class="account-detail-container">
    <div class="d-flex account-detail-inner">
      <div class="d-flex justify-center">
        <div class="d-flex flex-column" style="width: 500px;">
          <div class="account-detail-title">회원 정보</div>
          <div class="d-flex flex-column">
            <v-text-field
              class="d-flex justify-center account-detail-input"
              placeholder="이메일"
              outlined
              dense
              hide-details
              v-model="userdata.email"
              required
              type="email"
              style="margin-bottom:16px;"
              readonly
            ></v-text-field>
            <v-text-field
              class="d-flex justify-center account-detail-input"
              placeholder="비밀번호"
              outlined
              dense
              hide-details
              v-model="userdata.pwd"
              required
              type="password"
              style="margin-bottom:16px;"
            ></v-text-field>
            <v-text-field
              class="d-flex justify-center account-detail-input"
              placeholder="비밀번호 확인"
              outlined
              dense
              hide-details
              v-model="userdata.pwdconfirm"
              required
              type="password"
              style="margin-bottom:16px;"
            ></v-text-field>
            <v-text-field
              class="d-flex justify-center account-detail-input"
              placeholder="닉네임"
              outlined
              dense
              hide-details
              v-model="userdata.nickname"
              required
              style="margin-bottom:16px;"
            ></v-text-field>

            <div class="input-wrap">
              <img :src="uploadImageFile" style="width: 100px;height: 100px;border-radius: 50%;border: 1px solid #ccc;"/>
              <input @change="onFileSelected($event)" ref="file" type="file" name="file" accept="image/*"/>
            </div>
            <span class="login-hint" :style="{visibility:visi}">아이디는 Github에 로그인이 되었을때 프로필상에 나오는 이름을 의미합니다.</span>
            <v-text-field
              class="d-flex justify-center account-detail-input"
              placeholder="Git 아이디"
              outlined
              dense
              hide-details
              v-model="userdata.gitId"
              required
              style="margin-bottom:16px;"
            ></v-text-field>
            
            <span class="login-hint" :style="{visibility:visi}">아이디와 토큰 모두 일치해야 인증이 완료 됩니다</span>
            <v-text-field
              class="d-flex justify-center account-detail-input"
              placeholder="Git token"
              outlined
              dense
              hide-details
              v-model="userdata.gitToken"
              required
              style="margin-bottom:16px;"
            ></v-text-field>
            <div>
              <v-btn class="primary float-right " style="margin-bottom:16px;" @click="certifyGit()">토큰 검증</v-btn>
            </div>
            <v-text-field
              class="d-flex justify-center account-detail-input"
              placeholder="Git url"
              outlined
              dense
              hide-details
              v-model="userdata.gitUrl"
              required
              style="margin-bottom:16px;"
            ></v-text-field>
                        
            
            <v-textarea solo label="자기소개" v-model="userdata.intro"></v-textarea>
          </div>
          <div class="d-flex justify-center align-center account-detail-btn" @click="updateUser">수정</div>
        </div>
      </div>
      <user-sidebar />
    </div>
  </div>
</template>

<script>
import userSidebar from '../../component/user_sidebar.vue';
import axios from 'axios';
axios.defaults.headers.common['Access-Control-Allow-Origin'] = '*';

export default {
  name: 'AccountDetail',
  components: {
    userSidebar,
  },
  data() {
    return {
      userdata: {},
      uploadImageFile: ''
    };
  },
  created() {
    this.userdata = this.$store.state.user;
    this.uploadImageFile = this.userdata.img;
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
    async updateUser() {
      try {
        if(this.$refs.file != null) {
          this.userdata.file=this.$refs.file.files[0];
        }
        console.log(this.userdata.file);
        const formData = new FormData();
        formData.append('email', this.userdata.email);
        formData.append('pwd', this.userdata.pwd);
        formData.append('file', this.userdata.file);
        formData.append('img', this.userdata.img);
        formData.append('nickname',this.userdata.nickname);
        formData.append('gitId', this.userdata.gitId);
        formData.append('gitUrl',this.userdata.gitUrl);
        formData.append('gitToken', this.userdata.gitToken);
        formData.append('intro',this.userdata.intro);
        formData.append('isSocial', this.userdata.isSocial);
        formData.append('isCertified',this.userdata.isCertified);
        // console.log(formData)

        await this.$api.userUpdate(formData, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
          });
        alert('정보 수정 성공');
        // location.reload();
      } catch (e) {
        console.log('실패');
        console.log(e);
      }
    },
    async certifyGit(){
      try{
        let tmpid=this.userdata.gitId
        let tmpToken = this.userdata.gitToken
        await this.$api.certgitToken({gitId:tmpid,accessToken:tmpToken})
        this.userdata.isCertified = 1
        alert('인증되었습니다')
      }catch(e){
        console.log(e)
      }
    }
  },
};
</script>

<style>
.account-detail-container {
  width: 100%;
}

.account-detail-inner {
  padding-top: 77px;
  width: 1140px;
  margin: 0 auto;
}

.account-detail-title {
  font-weight: 600;
  margin-bottom: 32px;
}

.account-detail-subtitle {
  margin-top: 10px;
  margin-bottom: 30px;
  font-size: 12px;
  color: #21262e;
}

.account-detail-btn {
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

.account-detail-cancel-btn {
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

.account-detail-input {
  margin-bottom: 8px;
}
</style>