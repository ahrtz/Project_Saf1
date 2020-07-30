<template>
<div class="main-page-container">
<div class="main-page-inner d-flex flex-column justify-center">
    <status />
    <br>
    <br>
    <v-container fluid>
        <v-row>
            <v-col cols="6">
                <div class="d-flex align-center justify-space-between" style="margin-bottom:16px; border-bottom:solid 1px grey">
                    <div class="main-page-section-title">Project Post</div>
                    <div class="d-flex justify-center flex-grow-0 align-center main-page-btn" style="margin-bottom:5px;">더보기</div>
                </div>
                <v-card
                class="ma-2"
                flat
                v-for="post in project_posts" :key="post.id"
                style="margin-top:10px; border-bottom:dashed 1px grey"

                >
                <!-- card layout -->
                <div>
                  <!-- 프로필 이미지, 닉네임  -->
                  <header class="main-card-header">
                    <img :src="post.userinfo.img" alt="" class="main-card-header-img">
                    <div class="main-card-header-nick_date">
                      <span> 닉네임 :
                        {{post.userinfo.nickname}}
                      </span>
                      <span> 작성일 :
                        {{post.cdate}}
                      </span>
                    </div>
                  </header>
                  <!-- 포스트 제목 / 컨텐츠 -->
                  <article class="main-card-article">
                    <h3 style="margin-left:10px;">{{post.title}}</h3>
                    <p style="margin-left:10px; margin-top:5px;">
                        {{post.content}}
                    </p>
                  </article>
                  <footer>
                    <!-- TODO: tags -->
                  </footer>
                </div>
                </v-card>
            </v-col>
            <v-col cols="6">
                <div class="d-flex align-center justify-space-between" style="margin-bottom:16px; border-bottom:solid 1px grey">
                    <div class="main-page-section-title">Blog Post</div>
                    <div class="d-flex justify-center flex-grow-0 align-center main-page-btn" style="margin-bottom:5px;">더보기</div>
                </div>
                <v-card
                class="ma-2"
                flat
                v-for="post in blog_posts" :key="post.id"
                style="margin-top:10px;border-bottom:dashed 1px grey"
                >
                <div>
                  <!-- 프로필 이미지, 닉네임  -->
                  <header class="main-card-header">
                    <img :src="post.userinfo.img" alt="" class="main-card-header-img">
                    <div class="main-card-header-nick_date">
                      <span> 닉네임 :
                        {{post.userinfo.nickname}}
                      </span>
                      <span> 작성일 :
                        {{post.cdate}}
                      </span>
                    </div>
                  </header>
                  <!-- 포스트 제목 / 컨텐츠 -->
                  <article class="main-card-article">
                    <h3 style="margin-left:10px;">{{post.title}}</h3>
                    <p style="margin-left:10px; margin-top:5px;">
                        {{post.content}}
                    </p>
                  </article>
                  <footer>
                    <!-- TODO: tags -->
                  </footer>
                </div>
                </v-card>

            </v-col>

        </v-row>
    </v-container>
</div>
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
            blog_posts:{},
            project_posts:{},
            user:{},
        }
    },
    mounted(){
      this.getPost();
    },
    methods:{
      getPost(){

          //로그인 안 되어 있는 경우 전체 post
          //TODO : 로그인 세션 추가
          //if(isLogin)
          // console.log(111);
          // var curUid = 1;
          // console.log(curUid.toString());
          axios.post('/api/posts/all/' ,{uid:"1",type:"0",is_temp:"0"})
          .then(res=>{
              console.log(res.data)
              this.blog_posts = res.data}
          )
          axios.post('/api/posts/all/'  ,{uid:"1",type:"1",is_temp:"0"})
          .then(res=>{
              console.log(res.data)
              this.project_posts = res.data}
          )
          //로그인 되어 있는 경우 나의 post
          //TODO
          //else

        },
    },

}
</script>

<style>
.main-card-header{
  background-color:white;
  /* background:#ffffffde; */
  font-size: 16px;
  color: black;
  height: 55px;

}
.main-card-header-nick_date{
  display:flex;
  flex-direction: column;
}

.main-card-header-img{
  width:35px;
  height:35px;
  margin:10px 10px 10px 10px;
  float:left;
}
.main-card-article{
  background-color:white;
  float:unset;
  width:100%;
  height:100px;
}
.main-page-container {
  width: 100%;
}

.main-page-inner {
  width: 1140px;
  margin: 0 auto;
  padding: 77px 0;
}

.main-page-btn {
  margin-left: 4px;
  font-size: 14px;
  background: #0051cb;
  font-weight: 600;
  color: #fff;
  border-radius: 6px;
  width: 80px;
  height: 40px;
  cursor: pointer;
}

.main-page-section-title {
    font-size: 20px;
    font-weight: 700;

}
</style>
