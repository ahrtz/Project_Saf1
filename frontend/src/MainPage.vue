<template>
<div>
    <h2>메인페이지</h2>
    <br>스테이터스가 컴포넌트로 해놓긴 햇는데 데이터를 어케 받아서 넘겨줄지 고민
    <status />
    <br>
    <br>
    <v-container fluid>
        <v-row>
            <v-col cols="6">
                <div class="d-flex justify-space-between">
                    <div >프로젝트 글</div>
                    <v-btn class="ma-2  " tile color="indigo" dark>view all</v-btn>
                </div>
                <v-card
                class="pa-2"
                outlined
                tile
                v-for="post in project_posts" :key="post.id"
                >
                <!-- card layout -->
                <div>
                  <!-- 프로필 이미지, 닉네임  -->
                  <header class="main-card-header">
                    <img src="post.user.img" alt="" class="main-card-header-img">
                    <span> 닉네임 :
                      <!-- {{post.user.nickname}} -->
                    </span><br>
                    <span> 작성일 :
                      <!-- {{post.user.id}} -->
                    </span>
                  </header>
                  <!-- post 제목, 컨텐츠(간략하게) -->

                  <article class="main-card-article">
                    <h3>{{post.title}}</h3>
                    <p>
                        {{post.content}}
                    </p>
                  </article>
                </div>
                <!-- {{post.title}} -->
                </v-card>
            </v-col>
            <v-col cols="6">
                <div class="text-center d-flex justify-space-between">
                    <div >블로그 글</div>
                    <v-btn class="ma-2  " tile color="indigo" dark>view all</v-btn>
                </div>
                <v-card
                class="pa-2"
                outlined
                tile
                v-for="post in blog_posts" :key="post.id"
                >
                {{post.title}}
                </v-card>

            </v-col>
        </v-row>
    </v-container>
</div>
</template>

<script>
import axios from 'axios'
import Status from '../src/component/Status.vue'

export default {
    name:'MainPage',
    components:{
        Status
    },
    data(){
        return{
            status:['날짜정보 혹은 뭐 커밋 정보'],
            diarypost:[
            {
                pid:0,
                pname:'1번 글'
            },
            {
                pid:1,
                pname:'2번 글'
            },
            {
                pid:2,
                pname:'3번 글'
            },
            {
                pid:3,
                pname:'4번 글'
            },
            {
                pid:4,
                pname:'5번 글'
            },
            ],
            projectpost:[{
                pid:0,
                pname:'1번 글'
            },
            {
                pid:1,
                pname:'2번 글'
            },
            {
                pid:2,
                pname:'3번 글'
            },
            {
                pid:3,
                pname:'4번 글'
            },
            {
                pid:4,
                pname:'5번 글'
            },
            ],
            blog_posts:{},
            project_posts:{},
            user:{},
        }
    },
    mounted(){
      this.getPost();
      this.getUser();
    },
    methods:{
      getPost(){

          //로그인 안 되어 있는 경우 전체 post
          //TODO : 로그인 세션 추가
          //if(isLogin)
          // console.log(111);
          // var curUid = 1;
          // console.log(curUid.toString());
          axios.post('http://localhost:3000/posts/all/' ,{uid:"1",type:"0"})
          .then(res=>{
              // console.log(res.data)
              this.blog_posts = res.data}
          )
          axios.post('http://localhost:3000/posts/all/'  ,{uid:"1",type:"1"})
          .then(res=>{
              // console.log(res.data)
              this.project_posts = res.data}
          )
          //로그인 되어 있는 경우 나의 post
          //TODO
          //else

        },
        getUser(){
          //TODO : 현재 세션 로그인 값으로 수정
          var curUserEmail="dominicong@naver.com";
          console.log(curUserEmail);
          axios.get('http://localhost:3000/users/' + curUserEmail)
          .then(res=>{
              console.log(res.data)
              this.user = res.data}
          )
        }


    }
}
</script>

<style>
.main-card-header{
  background-color:#c3d1eb;
  padding: 10px;
  font-size: 16px;
  color: black;
}
.main-card-header-img{
  width:60px;
  height:60px;
  float:left;
}
.main-card-article{
  background-color:#a0bbee;
  float:unset;
  width:100%;
  height:100px;
}
</style>
