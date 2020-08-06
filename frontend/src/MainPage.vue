<template>
  <div class="main-page-container">
    <div class="main-page-inner d-flex flex-column justify-center">
      
      
      
     
      <Status />
     
     
     
     
      <br />
      <br />
      <v-container fluid>
        <v-row>
          <v-col cols="6">
            <div
              class="d-flex align-center justify-space-between"
              style="margin-bottom:16px; border-bottom:solid 1px grey"
            >
              <div class="main-page-section-title">Project Post</div>
              <div
                class="d-flex justify-center flex-grow-0 align-center main-page-btn"
                style="margin-bottom:5px;"
              >더보기</div>
            </div>
            <v-card
              class="ma-2"
              flat
              v-for="post in list_proj"
              :key="post.id"
              style="margin-top:10px; border-bottom:dashed 1px grey"
            >
              <!-- card layout -->
              <div>
                <!-- 프로필 이미지, 닉네임  -->
                <header class="main-card-header">
                  <img :src="post.userinfo.img" alt class="main-card-header-img" />
                  <div class="main-card-header-nick_date">
                    <span>
                      닉네임 :
                      {{post.userinfo.nickname}}
                    </span>
                    <span>
                      작성일 :
                      {{post.cdate}}
                    </span>
                  </div>
                </header>
                <!-- 포스트 제목 / 컨텐츠 -->
                <article
                  class="main-card-article"
                  @click="$router.push({name:'PostDetail',params:{pid:post.id}})"
                  style="cursor:pointer"
                >
                  <h3 style="margin-left:10px;">{{post.title}}</h3>
                  <p style="margin-left:10px; margin-top:5px;">{{post.content}}</p>
                </article>
                <footer>
                  <!-- TODO: tags -->
                </footer>
              </div>
            </v-card>

            <infinite-loading
              slot="append"
              @infinite="infiniteHandler"
              spinner="waveDots"
              force-use-infinite-wrapper=".el-table__body-wrapper"
            ></infinite-loading>
          </v-col>
          <v-col cols="6">
            <div
              class="d-flex align-center justify-space-between"
              style="margin-bottom:16px; border-bottom:solid 1px grey"
            >
              <div class="main-page-section-title">Blog Post</div>
              <div
                class="d-flex justify-center flex-grow-0 align-center main-page-btn"
                style="margin-bottom:5px;"
              >더보기</div>
            </div>
            <v-card
              class="ma-2"
              flat
              v-for="post in list_blog"
              :key="post.id"
              style="margin-top:10px;border-bottom:dashed 1px grey"
            >
              <div>
                <!-- 프로필 이미지, 닉네임  -->
                <header class="main-card-header">
                  <img :src="post.userinfo.img" alt class="main-card-header-img" />
                  <div class="main-card-header-nick_date">
                    <span>
                      닉네임 :
                      {{post.userinfo.nickname}}
                    </span>
                    <span>
                      작성일 :
                      {{post.cdate}}
                    </span>
                  </div>
                </header>
                <!-- 포스트 제목 / 컨텐츠 -->
                <article
                  class="main-card-article"
                  @click="$router.push({name:'PostDetail',params:{pid:post.id}})"
                  style="cursor:pointer"
                >
                  <h3 style="margin-left:10px;">{{post.title}}</h3>
                  <p style="margin-left:10px; margin-top:5px;">{{post.content}}</p>
                </article>
                <footer>
                  <!-- TODO: tags -->
                </footer>
              </div>
            </v-card>
            <infinite-loading
              slot="append"
              @infinite="infiniteHandler2"
              spinner="waveDots"
              force-use-infinite-wrapper=".el-table__body-wrapper"
            ></infinite-loading>
          </v-col>
        </v-row>
      </v-container>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import Status from '@/component/Status.vue';
import InfiniteLoading from 'vue-infinite-loading';

var count = 0;
export default {
  name: 'MainPage',
  components: {
    Status,
    InfiniteLoading,
  },
  data() {
    return {
      status: ['날짜정보 혹은 뭐 커밋 정보'],
      blog_posts: {},
      project_posts: {},
      user: {},
      limit_proj: 0,
      limit_blog: 0,
      page: 1,
      list_proj: [],
      list_blog: [],
      isLogin : false
    };
  },
  created() {
    this.isLogin=this.$store.state.isLoggedIn
  },
  methods: {
    getPost() {
      //로그인 안 되어 있는 경우 전체 post
      //TODO : 로그인 세션 추가
      //if(isLogin)

      //TODO : uid 현재 유저로 확인해야됨
      axios
        .post('/api/posts/all/', {
          uid: '',
          isProj: '0',
          isTemp: '0',
          keyword: '',
          limit: '1',
        })
        .then((res) => {
          console.log(res.data);
          this.blog_posts = res.data;
        });

      axios
        .post('/api/posts/all/', {
          uid: '',
          isProj: '1',
          isTemp: '0',
          keyword: '',
          limit: '2',
        })
        .then((res) => {
          console.log(res.data);
          this.project_posts = res.data;
        });
      //로그인 되어 있는 경우 나의 post
      //TODO
      //else
    },
    infiniteHandler($state) {
      let temp = this.$route.params.uid
      axios
        .post('/api/posts/all/', {
          uid: temp,
          isProj: '1',
          isTemp: '0',
          keyword: '',
          limit: this.limit_proj + 10,
        })
        .then((res) => {
          setTimeout(() => {
            if (res.data.length >= this.limit_proj) {
              this.list_proj = res.data;
              $state.loaded();
              this.limit_proj += 10;
            } else {
              $state.complete();
            }
          }, 1000);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    infiniteHandler2($state) {
      let temp = this.$route.params.uid
      axios
        .post('/api/posts/all/', {
          uid: temp,
          isProj: '0',
          isTemp: '0',
          keyword: '',
          limit: this.limit_blog + 10,
        })
        .then((res) => {
          setTimeout(() => {
            if (res.data.length >= this.limit_blog) {
              this.list_blog = res.data;
              $state.loaded();
              this.limit_blog += 10;
            } else {
              $state.complete();
            }
          }, 1000);
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
};
</script>

<style>
.main-card-header {
  background-color: white;
  /* background:#ffffffde; */
  font-size: 16px;
  color: black;
  height: 55px;
}
.main-card-header-nick_date {
  display: flex;
  flex-direction: column;
}

.main-card-header-img {
  width: 35px;
  height: 35px;
  margin: 10px 10px 10px 10px;
  float: left;
}
.main-card-article {
  background-color: white;
  float: unset;
  width: 100%;
  height: 100px;
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
