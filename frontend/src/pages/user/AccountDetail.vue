<template>
  <div class="account-detail-container">
    <div class="d-flex account-detail-inner">
      <div class="d-flex justify-center">
        <div class="d-flex flex-column" style="width: 500px;">
          <div class="account-detail-title">회원 정보</div>
          <div class="d-flex flex-column">
            <v-text-field
              v-if="!userdata.isSocial"
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
              v-if="!userdata.isSocial"
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
              v-if="!userdata.isSocial"
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
              style="margin-bottom:32px;"
            ></v-text-field>

            <div class="d-flex flex-column input-wrap">
              <div class="account-detail-profile-image-container">
                <img
                  class="account-detail-profile-image"
                  :src="uploadImageFile ? uploadImageFile : '/static/images/user.png'"
                />
              </div>
              <div class="account-detail-image-notice">* 1x1 규격으로 업로드해주세요.</div>
              <input
                @change="onFileSelected($event)"
                ref="file"
                type="file"
                name="file"
                accept="image/*"
              />
            </div>
            <span
              class="login-hint"
              :style="{visibility:visi}"
            >아이디는 Github에 로그인이 되었을때 프로필상에 나오는 이름을 의미합니다.</span>
            <v-text-field
              class="d-flex justify-center account-detail-input"
              placeholder="Git 아이디"
              outlined
              dense
              hide-details
              v-model="userdata.gitId"
              required
              style="margin-bottom:16px;"
              :disabled="isDisabled"
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
              :disabled="isDisabled"
            ></v-text-field>
            <div class="d-flex" style="margin-bottom:32px;">
              <div class="d-flex" />
              <div
                class="d-flex align-center justify-center flex-grow-0 account-detail-white-btn"
                @click="uncertifyGit()"
              >인증 취소</div>
              <div
                class="d-flex align-center justify-center flex-grow-0 account-detail-blue-btn"
                style="margin-left:8px;"
                @click="certifyGit()"
              >토큰 검증</div>
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
          <div
            class="d-flex justify-center align-center account-detail-white-btn"
            style="margin-top: 32px;"
            @click="updateUser"
          >수정</div>
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
      uploadImageFile: '',
      visi: 'hidden',
    };
  },
  created() {
    this.userdata = this.$store.state.user;
    this.uploadImageFile = this.userdata.img;
  },
  computed: {
    isDisabled() {
      if (this.userdata.isCertified) {
        return true;
      } else {
        return false;
      }
    },
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
    async updateUser() {
      try {
        if (this.userdata.pwd != this.userdata.pwdconfirm) {
          alert('비밀번호가 일치 하지 않습니다');
        } else {
          if (this.$refs.file != null) {
            this.userdata.file = this.$refs.file.files[0];
          }
          console.log(this.userdata.file);
          const formData = new FormData();
          formData.append('email', this.userdata.email);
          formData.append('pwd', this.userdata.pwd);
          formData.append('file', this.userdata.file);
          formData.append('img', this.userdata.img);
          formData.append('nickname', this.userdata.nickname);
          formData.append('gitId', this.userdata.gitId);
          formData.append('gitUrl', this.userdata.gitUrl);
          formData.append('gitToken', this.userdata.gitToken);
          formData.append('intro', this.userdata.intro);
          formData.append('isSocial', this.userdata.isSocial);
          formData.append('isCertified', this.userdata.isCertified);
          // console.log(formData)

          await this.$api.userUpdate(formData, {
            headers: {
              'Content-Type': 'multipart/form-data',
            },
          });
          alert('회원 정보를 수정하였습니다.');
          this.$router.push({ name: 'MainPagefor', params: { uid: this.userdata.id } });
        }
      } catch (e) {
        console.log('실패');
        console.log(e);
      }
    },
    async certifyGit() {
      try {
        let tmpid = this.userdata.gitId;
        let tmpToken = this.userdata.gitToken;
        await this.$api.certgitToken({ gitId: tmpid, accessToken: tmpToken });
        this.userdata.isCertified = 1;
        alert('인증되었습니다');
      } catch (e) {
        console.log(e);
      }
    },
    uncertifyGit() {
      this.userdata.gitId = '';
      this.userdata.gitToken = '';
      this.userdata.isCertified = 0;
      alert('인증이 취소 되었습니다');
      this.$api.gitCancel();
    },
  },
};
</script>

<style>
.account-detail-container {
  width: 100%;
}

.account-detail-inner {
  padding-bottom: 70px;
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

.account-detail-blue-btn {
  padding: 0 16px;
  font-size: 14px;
  background: #0051cb;
  font-weight: 600;
  color: #fff;
  border-radius: 6px;
  height: 40px;
  cursor: pointer;
}

.account-detail-white-btn {
  padding: 0 16px;
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

.account-detail-image-notice {
  font-size: 12px;
  margin-bottom: 8px;
  font-weight: 600;
}

.account-detail-profile-image-container {
  width: 100px;
  height: 100px;
  border-radius: 50%;
  border: 1px solid #ccc;
  margin-bottom: 24px;
  overflow: hidden;
}

.account-detail-profile-image {
  width: 100px;
  height: 100px;
}
</style>