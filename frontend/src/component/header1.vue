<template>
  <div v-if="!isLoginPage" class="header-container">
    <div class="header-inner">
      <div class="d-flex align-center">
        <img class="header-logo" @click="gotomain()" src="/static/images/Blogit_simple.png" />
        <div class="d-flex justify-center flex-grow-0 header-menu" @click="gotomaineach()">Overview</div>
        <div class="d-flex justify-center flex-grow-0 header-menu" @click="onDiaryBtnClick()">Diary</div>

        <div class="d-flex" />
        <v-text-field
          id="header-text"
          class="d-flex justify-center flex-grow-0"
          placeholder="Search by Title"
          outlined
          dense
          hide-details
          v-model="keyw"
          @keyup.enter="search()"
        ></v-text-field>
        <div class="d-flex justify-center flex-grow-0 align-center header-btn" @click="search()">검색</div>
        <!-- <div class="d-flex justify-center flex-grow-0 header-menu" @click="onGroupBtnClick()">Group</div> -->
        <div
          class="d-flex align-center flex-grow-0 header-profile"
          v-if="islogin==true"
          @click="$router.push({name:'AccountDetail',params:{uid:userdata.id}})"
        >
          <div>
            <img class="header-profile-image" :src="userImg ? userImg : '/static/images/user.png'" />
          </div>
          <div>{{userNickname}}</div>
        </div>
        <div
          class="d-flex justify-center flex-grow-0 align-center header-btn-white"
          v-if="islogin==true"
          @click="logout()"
        >Logout</div>
        <div
          class="d-flex justify-center flex-grow-0 align-center header-btn"
          style="margin-right:0"
          v-if="islogin==false"
          @click="$router.push({name:'Login'})"
        >Login</div>
        <div
          class="d-flex justify-center flex-grow-0 align-center header-btn-white"
          style="margin-left:8px"
          v-if="islogin==false"
          @click="$router.push({name:'SignUp'})"
        >Signup</div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
export default {
  name: 'header1',
  data: () => ({
    title: ['Main', 'Blog', 'Projects', '검색', 'Myinfo'],
    keyw: '',
    signin: false,
    target: '',
    isLoginPage: false,
    userdata:null,
  }),
  created() {
    this.userdata= this.$store.state.user
    this.target = this.$route.params.uid;
    // console.log(this.$route.path);
    if (this.$route.path == '/') {
      this.isLoginPage = true;
    }
    // this.target = temp
  },
  methods: {
    onDiaryBtnClick() {
      if (this.signin) {
        this.$router.push({
          name: 'DiaryMain',
          params: { uid: this.target, test: 2 },
        });
      } else {
        this.$router.push({
          name: 'DiaryMain',
          params: { uid: this.target, test: 2 },  //이거 test 의도를 모르겠심.. written by 창경.
        });
        // alert('로그인이 필요합니다.');
        // this.$router.push({ name: 'Login' });
      }
    },
    onGroupBtnClick() {
      if (this.signin) {
        this.$router.push({ name: 'GroupMain' });
      } else {
        alert('로그인이 필요합니다.');
        this.$router.push({ name: 'Login' });
      }
    },
    onClickOutside() {
      this.active = false;
    },
    search() {
      this.$router.push({
        name: 'tmp',
        params: { key: this.keyw, type: 'title' },
        
      });
      
    },
    alerts(msg) {
      alert(msg);
    },
    gotomain() {
      this.$router.push({ name: 'MainPagefor', params: { uid: this.userid } });
      location.reload()
    },
    gotomaineach() {
      this.$router.push({ name: 'MainPagefor', params: { uid: this.$route.params.uid } });
      
    },
  },
  computed: {
    userNickname() {
      return this.$store.state.user.nickname;
    },
    userImg() {
      return this.$store.state.user.img;
    },
    userid() {
      return this.$store.state.user.id;
    },
    islogin() {
      return (this.signin = this.$store.state.isLoggedIn);
    },
    async logout() {
      try {
        await this.$api.logout();
        this.$router.push({ name: 'MainPage' });
        location.reload();
      } catch (e) {
        // console.log('실패');
      }
    },
  },
};
</script>

<style>
@font-face {
  font-family: Recursive;
  src: url(https://fonts.googleapis.com/css2?family=Recursive:wght@700&display=swap);
}

.header-container {
  background: #fff;
  z-index: 99;
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  border-bottom: solid 1px #dde3ea;
}

.header-inner {
  width: 1140px;
  margin: 0 auto;
}

.header-logo {
  margin-right: 16px;
  width: 50px;
  height: 50px;
  cursor: pointer;
}

.header-menu {
  height: 100%;
  line-height: 70px;
  padding: 0 20px;
  color: #21262e;
  font-size: 14px;
  font-weight: 800;
  cursor: pointer;
}

.header-menu:hover {
  color: #0051cb;
}

.header-btn {
  margin-left: 4px;
  margin-right: 32px;
  font-size: 14px;
  background: #0051cb;
  font-weight: 600;
  color: #fff;
  border-radius: 6px;
  padding: 0 16px;
  height: 40px;
  cursor: pointer;
}

.header-btn-white {
  margin-left: 24px;
  font-size: 14px;
  background: #fff;
  font-weight: 600;
  color: #0051cb;
  border-radius: 6px;
  border: solid 1px #0051cb;
  width: 80px;
  height: 40px;
  cursor: pointer;
}

.header-flex-container {
  width: 100%;
  display: flex;
  flex-direction: row;
  margin: 0px;
  padding: 0px;
  border-radius: 5px;
}

.header-flex-item {
  display: flex;
  margin: 10px;
  padding: 0px;
  text-align: center;
  height: 40px;
  border-radius: 5px;
  color: 'green accent-2';
  font-family: Recursive;
  font-size: 20px;
}

.header-profile {
  margin-left: 24px;
  color: #21262e;
  font-size: 12px;
  font-weight: 800;
  cursor: pointer;
}

.header-profile-image {
  margin-right: 8px;
  width: 30px;
  height: 30px;
  border: solid 1px #dde3ea;
  border-radius: 50%;
}
</style>
