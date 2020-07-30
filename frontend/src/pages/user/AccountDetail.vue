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
            <!-- <v-file-input ref="file" label="프로필 사진" prepend-icon="mdi-camera" style="margin-bottom:16px;"></v-file-input> -->

            <v-text-field
              class="d-flex justify-center account-detail-input"
              placeholder="Git 아이디"
              outlined
              dense
              hide-details
              v-model="userdata.git_id"
              required
              style="margin-bottom:16px;"
            ></v-text-field>
            <v-text-field
              class="d-flex justify-center account-detail-input"
              placeholder="Git url"
              outlined
              dense
              hide-details
              v-model="userdata.git_url"
              required
              style="margin-bottom:16px;"
            ></v-text-field>
            <v-text-field
              class="d-flex justify-center account-detail-input"
              placeholder="Git token"
              outlined
              dense
              hide-details
              v-model="userdata.git_token"
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
    };
  },
  created() {
    this.userdata = this.$store.state.user;
  },
  methods: {
    async updateUser() {
      try {
        await this.$api.userUpdate(this.userdata);
        console.log('성공');
      } catch (e) {
        console.log('실패');
        console.log(e);
      }
    },
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