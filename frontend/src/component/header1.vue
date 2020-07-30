<template>
<div class="header-container">
  <div class="header-inner">
    <v-card
      class="header-flex-container justify-center align-center"
      flat
      color="#fff"
      height="70"
    >
      <img class="header-logo" @click="$router.push({name:'MainPage'})" src="/static/images/Blogit_simple.png"/>
      <div class="d-flex justify-center flex-grow-0 header-menu" @click="$router.push({name:'MainPage'})">Home</div>
      <div class="d-flex justify-center flex-grow-0 header-menu" @click="$router.push({name:'DiaryMain'})">Diary</div>
      <div class="d-flex justify-center flex-grow-0 header-menu" @click="$router.push({name:'GroupMain'})">Group</div>
      <div class="d-flex"/>
      <v-text-field
        class="d-flex justify-center flex-grow-0"
            placeholder=""
            outlined
            dense
            hide-details
          ></v-text-field>
      <div class="d-flex justify-center flex-grow-0 align-center header-btn">검색</div>
      <div class="d-flex justify-center flex-grow-0 header-menu" @click="$router.push({name:'Login'})">Login</div>
      <div class="d-flex justify-center flex-grow-0 header-menu" @click="$router.push({name:'Follow'})">MyPage</div>
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
  }),
  methods: {
      onClickOutside () {
        this.active = false
      }
  },
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
  cursor: pointer;
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
