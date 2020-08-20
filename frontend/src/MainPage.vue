<template>
  <div class="d-flex main-page-container">
    <div class="d-flex flex-grow-0 main-page-inner">
      <div class="d-flex flex-grow-0" style="width: 300px">
        <div class="main-page-contact">
          <s-contact />
        </div>
      </div>
      <div class="d-flex justify-center">
        <div class="d-flex flex-column" style="width:100%;">
          <Status :uid="uid" :isProj="2" />
          <div class="d-flex">
            <div class="d-flex">
              <div
                class="d-flex flex-column flex-grow-0 flex-shrink-0"
                style="width: 404px;margin-right:32px"
              >
                <div
                  class="d-flex align-center flex-grow-0"
                  style="padding-bottom: 8px;margin-bottom:16px; border-bottom:solid 1px #dde3ea"
                >
                  <div class="main-page-section-title">Project Post</div>
                  <div class="d-flex" />
                  <div
                    class="d-flex justify-center flex-grow-0 align-center main-page-btn"
                    style="margin-bottom:5px;"
                    @click="$router.push({name:'DiaryMain',params:{uid:uid,test:1}})"
                  >more projects</div>
                </div>
                <div
                  class="d-flex flex-column flex-grow-0 main-page-card"
                  v-for="(post,indexs) in list_proj"
                  :key="post.id"
                  @click="$router.push({name:'PostDetail',params:{pid:post.id}})"
                >
                  <!-- card layout -->
                  <!-- 프로필 이미지, 닉네임  -->
                  <div class="d-flex align-center main-card-header">
                    <img :src="post.userinfo.img" alt class="main-card-header-img" />
                    <div class="main-card-header-nick_date">
                      <div class="main-page-nickname">{{post.userinfo.nickname}}</div>
                      <div class="main-page-cdate">{{post.cdate}}</div>
                    </div>
                    <v-icon class="d-flex justify-end">mdi-source-commit</v-icon>
                    <span class="main-page-commitcnt">{{post.commitCnt}}</span>
                  </div>
                  <!-- 포스트 제목 / 컨텐츠 -->
                  <div class="main-card-article" style="cursor:pointer">
                    <div class="main-page-content-title">{{post.title}}</div>
                    <div class="main-page-content-text" v-html="compiledMarkdown(post)"></div>
                  </div>

                  <div class="d-flex main-page-tag-container">
                    <div
                      class="d-flex flex-grow-0 main-page-tag"
                      v-for="(tags,index) in tag_proj[indexs]"
                      :key="'t-'+index"
                    >#{{tags.name}}</div>
                  </div>
                </div>
                <infinite-loading
                  slot="append"
                  @infinite="infiniteHandler"
                  spinner="waveDots"
                  force-use-infinite-wrapper=".el-table__body-wrapper"
                ></infinite-loading>
              </div>
            </div>
            <div class="d-flex">
              <div class="d-flex flex-column flex-shrink-0" style="width: 404px;">
                <div
                  class="d-flex align-center flex-grow-0"
                  style="padding-bottom: 8px;margin-bottom:16px; border-bottom:solid 1px #dde3ea"
                >
                  <div class="main-page-section-title">Blog Post</div>
                  <div class="d-flex" />
                  <div
                    class="d-flex justify-center flex-grow-0 align-center main-page-btn"
                    style="margin-bottom:5px;"
                    @click="$router.push({name:'DiaryMain',params:{uid:uid,test:0}})"
                  >more blogs</div>
                </div>
                <div
                  class="main-page-card"
                  @click="$router.push({name:'PostDetail',params:{pid:post.id}})"
                  v-for="(post,indexss) in list_blog"
                  :key="post.id"
                >
                  <div>
                    <!-- 프로필 이미지, 닉네임  -->
                    <div class="d-flex align-center main-card-header">
                      <img :src="post.userinfo.img" alt class="main-card-header-img" />
                      <div class="main-card-header-nick_date">
                        <div class="main-page-nickname">{{post.userinfo.nickname}}</div>
                        <div class="main-page-cdate">{{post.cdate}}</div>
                      </div>
                    </div>
                    <!-- 포스트 제목 / 컨텐츠 -->
                    <div class="main-card-article" style="cursor:pointer">
                      <div class="main-page-content-title">{{post.title}}</div>
                      <div class="main-page-content-text" v-html="compiledMarkdown(post)"></div>
                    </div>
                    <div class="d-flex main-page-tag-container">
                      <div
                        class="d-flex flex-grow-0 main-page-tag"
                        v-for="(tags,index) in tag_blog[indexss]"
                        :key="'t-'+index"
                      >#{{tags.name}}</div>
                    </div>
                  </div>
                </div>
                <infinite-loading
                  slot="append"
                  @infinite="infiniteHandler2"
                  spinner="waveDots"
                  force-use-infinite-wrapper=".el-table__body-wrapper"
                ></infinite-loading>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import Status from '@/component/Status.vue';
import InfiniteLoading from 'vue-infinite-loading';
import SContact from '@/component/s-contact.vue';
import marked from 'marked';

var renderer = new marked.Renderer();

var count = 0;
export default {
  name: 'MainPage',
  components: {
    Status,
    InfiniteLoading,
    SContact,
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
      isLogin: false,
      uid: '',
      tag_proj: {},
      tag_blog: {},
      tag_rank:{}
    };
  },
  async created() {
    this.isLogin = this.$store.state.isLoggedIn;
    this.uid = this.$route.params.uid;
    this.tag_rank = await this.$api.tagRank({
      did:0,
      uid:this.$route.params.uid,
      num:3


    })
  },
  methods: {
    compiledMarkdown: function (posttmp) {
      let vm = posttmp;
      // console.log(vm);
      renderer.em = function (text) {
        return '<em>' + '' + '</em>';
      };
      var tmp1 = marked(posttmp.content, { renderer: renderer });

      return tmp1;
    },

    async infiniteHandler($state) {
      let temp = this.$route.params.uid;
      await axios
        .post('/api/posts/all/', {
          uid: temp,
          isProj: '1',
          isTemp: '0',
          keyword: '',
          limit: this.limit_proj + 10,
        })
        .then((res) => {
          setTimeout(async () => {
            if (res.data.length >= this.limit_proj) {
              this.list_proj = res.data;
              // console.log(res.data[1]);
              for (var i = 0; i < res.data.length; i++) {
                this.tag_proj[i] = await this.$api.tagIndex(res.data[i].id);
              }

              $state.loaded();
              this.limit_proj += 10;
            } else {
              $state.complete();
            }
          }, 1000);
          this.$forceUpdate();
        })
        .catch((err) => {
          // console.log(err);
        });
    },
    async infiniteHandler2($state) {
      let temp = this.$route.params.uid;
      await axios
        .post('/api/posts/all/', {
          uid: temp,
          isProj: '0',
          isTemp: '0',
          keyword: '',
          limit: this.limit_blog + 10,
        })
        .then((res) => {
          setTimeout(async () => {
            if (res.data.length >= this.limit_blog) {
              this.list_blog = res.data;
              for (var i = 0; i < res.data.length; i++) {
                this.tag_blog[i] = await this.$api.tagIndex(res.data[i].id);
              }

              $state.loaded();
              this.limit_blog += 10;
            } else {
              $state.complete();
            }
          }, 1000);
          this.$forceUpdate();
        })
        .catch((err) => {
          // console.log(err);
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
  width: 100%;
  border-bottom: solid 1px #dde3ea;
}
.main-page-commitcnt {
  font-size: 14px;
}

.main-card-header-nick_date {
  display: flex;
  flex-direction: column;
}

.main-card-header-img {
  border-radius: 50%;
  border: solid 1px #dde3ea;
  width: 35px;
  height: 35px;
  margin-right: 10px;
}
.main-card-article {
  margin: 16px;
  overflow: hidden;
  background-color: white;
  float: unset;
  height: 60px;
  max-height: 60px;
}
.main-page-container {
  width: 100%;
}

.main-page-inner {
  width: 1140px;
  margin: 0 auto;
  padding-bottom: 70px;
}

.main-page-contact {
  width: 268px;
}

.main-page-btn {
  margin-left: 4px;
  font-size: 12px;
  background: #0051cb;
  font-weight: normal;
  color: #fff;
  border-radius: 6px;
  padding: 0 16px;
  height: 30px;
  cursor: pointer;
}

.main-page-section-title {
  font-size: 18px;
  font-weight: 700;
}

.main-page-card {
  padding: 6px 10px 16px 10px;
  margin-top: 15px;
  border: 1px solid #dde3ea;
  border-radius: 8px;
  margin: 15px 5px;
  text-decoration: none;
  color: #21262e;
  -webkit-transition: -webkit-box-shadow 0.2s;
  transition: -webkit-box-shadow 0.2s;
  transition: box-shadow 0.2s;
  transition: box-shadow 0.2s, -webkit-box-shadow 0.2s;
  cursor: pointer;
  height: 200px;
}

.main-page-card:hover {
  box-shadow: 1px 1px 10px 4px #dde3ea;
}

.main-page-nickname {
  font-size: 14px;
  font-weight: 700;
}

.main-page-cdate {
  font-size: 12px;
  color: #21262e;
}

.main-page-content-title {
  font-size: 14px;
  font-weight: 700;
  margin-bottom: 8px;
}

.main-page-content-text {
  font-size: 12px;
}

.main-page-tag-container {
  width: 100%;
  overflow: hidden;
}

.main-page-tag {
  white-space: nowrap;
  margin-bottom: 8px;
  margin-right: 8px;
  padding: 0 12px;
  background: #fff;
  border: solid 1px #0051cb;
  border-radius: 20px;
  cursor: pointer;
  color: #0051cb;
  font-size: 14px;
}
</style>
