<template>
<div class="header-container">
  <div class="header-inner">
    <v-card
      class="header-flex-container justify-center align-center"
      flat
      color="#fff"
      height="70"
    >
      <img class="header-logo" @click="gotomain()" src="/static/images/Blogit_simple.png"/>
      <div class="d-flex justify-center flex-grow-0 header-menu" @click="onDiaryBtnClick()">Diary</div>
      <div class="d-flex justify-center flex-grow-0 header-menu" @click="onGroupBtnClick()">Group</div>

      <div class="d-flex"/>
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
      <div class="d-flex justify-center flex-grow-0 header-menu" v-if="islogin==false" @click="$router.push({name:'Login'})">Login</div>
      <div class="d-flex justify-center flex-grow-0 header-menu" v-if="islogin==false" @click="$router.push({name:'SignUp'})">signup</div>
      <div class="d-flex justify-center flex-grow-0 header-menu" v-if="islogin==true" @click="logout()">Logout</div>


      <div class="d-flex justify-center flex-grow-0 header-menu" v-if="islogin==true" @click="$router.push({name:'Follow'})">MyPage</div>
    </v-card>

  </div>
</div>
</template>

<script>
import axios from 'axios'
export default {
  name:'header1',
  data: () => ({
      title: [
        'Main',
        'Blog',
        'Projects',
        '검색',
        'Myinfo',
      ],
      keyw:'',
      signin:false,
      target:''

  }),
  created(){
    this.target =  this.$route.params.uid
    // this.target = temp
  },
  methods: {
      onDiaryBtnClick() {
        if(this.signin)
        {
          this.$router.push({name:'DiaryMain',params:{uid:this.target,test:2}}
          )
        }
        else{
          alert('로그인이 필요합니다.')
          this.$router.push({name:'Login'})
        }
      },
      onGroupBtnClick() {
        if(this.signin)
        {
          this.$router.push({name:'GroupMain'})
        }
        else{
          alert('로그인이 필요합니다.')
          this.$router.push({name:'Login'})
        }
      }
      ,
      onClickOutside () {
        this.active = false
      },
      search(){
        this.$router.push({name: 'tmp',params:{key:this.keyw,type:'title'}})
      },
      alerts(msg){
        alert(msg)
      },
      gotomain(){
        this.$router.push({name:'MainPagefor',params:{uid:this.userid}})
        location.reload()
      }
  },
  computed:{
    userid(){
      return this.$store.state.user.id
    },
    islogin(){
      return this.signin= this.$store.state.isLoggedIn
    },
    async logout(){
      try {
          await this.$api.logout()
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
@font-face{
  font-family:Recursive;
  src:url(https://fonts.googleapis.com/css2?family=Recursive:wght@700&display=swap);
}

.header-container {
  width: 100%;
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
  width: 80px;
  height: 40px;
  cursor: pointer;
}

.header-flex-container {
  width:100%;
  display:flex;
  flex-direction: row;
  margin: 0px;
  padding: 0px;
  border-radius: 5px;
}
.header-flex-item {
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
.header-search-button{
  background:#32681c;
  border:0px;
  width:50px;
  height:100%;
  outline:none;
  color:#ffffff;
}
.header-router-link {
  text-decoration: none;
  margin:5px;
}
.header-router-link-exact-active  {
  color: white;
  background-color:#56c068;

}
</style>
