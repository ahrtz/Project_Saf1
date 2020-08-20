<template>
  <div class="diary-main-container">
    <div class="d-flex diary-main-toggle">
      <div class="d-flex justify-center">
        <div
          @click="test=0;getDiary()"
          class="d-flex flex-grow-0 align-center justify-center diary-main-toggle-item"
          :class="{'diary-main-toggle-item--selected': test==0}"
        >
          <div>
            <img class="diary-main-toggle-img" src="/static/images/blog.png" />
          </div>
          <div>
            <div class="diary-main-toggle-text">Blog</div>
            <div
              class="diary-main-toggle-text"
              style="font-size:14px;font-weight:300"
            >Daily life, etc...</div>
          </div>
        </div>
        <div
          @click="test=1;getDiary()"
          class="d-flex flex-grow-0 align-center justify-center diary-main-toggle-item"
          :class="{'diary-main-toggle-item--selected': test==1}"
        >
          <div>
            <img class="diary-main-toggle-img" src="/static/images/branch.png" />
          </div>
          <div>
            <div class="diary-main-toggle-text">Project</div>
            <div class="diary-main-toggle-text" style="font-size:14px;font-weight:300">Git commit</div>
          </div>
        </div>
      </div>
    </div>
    <div class="diary-main-inner">
      <div class="d-flex">
        <v-text-field
          id="header-texts"
          class="d-flex justify-center flex-grow-0"
          placeholder="Search by Title"
          outlined
          dense
          type="text"
          hide-details
          append-icon="search"
          style="width: 300px"
          v-model="keyw"
        ></v-text-field>
        <div class="d-flex" />
        <router-link
          v-show="test==0 || test==2 "
          v-if="mydata.id==uid"
          class="float-right"
          :to="{name:'BlogAdd'}"
          tag="button"
        >
          <div class="d-flex flex-grow-0 justify-center align-center diary-main-add-btn">블로그 추가</div>
        </router-link>

        <router-link
          v-show="test==1 ||test==2"
          v-if="mydata.id==uid"
          class="float-right"
          :to="{name:'ProjectAdd'}"
          tag="button"
        >
          <div
            style="margin-left:4px;"
            class="d-flex flex-grow-0 justify-center align-center diary-main-add-btn"
          >프로젝트 추가</div>
        </router-link>
      </div>
      <br />
      <br />

      <!-- <h2>블로그 카드 뷰</h2> -->
      <div class="d-flex">
        <v-row>
          <v-col cols="4" v-for="blog in diarys" :key="blog.id" v-show="blog.title.includes(keyw)">
            <v-hover v-slot:default="{ hover }" enabled>
              <v-card
                :elevation="hover? 12 : 2"
                :img="!blog.img?'../../../static/images/Blogit_logo.png':blog.img"
                class="ma-2"
                :class="{ 'on-hover': hover }"
                style="height:300px;"
                outlined
                @click="goBlog(blog.id)"
              >
                <!-- hover -->
                <div v-if="hover" class="black div-reveal">
                  <v-card-title style="color:white;font-size:24px;font-weight:bold">{{blog.title}}</v-card-title>
                  <v-card-subtitle style="margin-top: 16px;">
                    <div class="d-flex diary-main-content-container">
                      <div class="d-flex flex-grow-0 diary-main-content-title">기간</div>
                      <div
                        class="d-flex flex-grow-0 justify-center align-center diary-main-content-text"
                      >{{blog.sdate.substr(0,10)}} ~ {{blog.edate.substr(0,10)}}</div>
                    </div>
                    <div
                      v-if="blog.languages  && blog.languages.length>0"
                      class="d-flex diary-main-content-container"
                    >
                      <div class="d-flex flex-grow-0 diary-main-content-title">언어</div>
                      <div class="d-flex">
                        <div
                          class="d-flex flex-grow-0 justify-center align-center diary-main-content-text"
                          v-for="language in blog.languages"
                          :key="language.id"
                        >{{language}}</div>
                      </div>
                    </div>

                    <div
                      v-if="tagdata[blog.id].length!=0"
                      class="d-flex diary-main-content-container"
                    >
                      <div class="d-flex flex-grow-0 diary-main-content-title">태그</div>
                      <div
                        v-for="tag in tagdata[blog.id]"
                        :key="tag.name"
                        class="d-flex flex-grow-0 justify-center align-center diary-main-content-text"
                      >{{tag.name}}</div>
                    </div>
                  </v-card-subtitle>
                  <v-card-text v-if="blog.intro" style="color:white;font-weight:bold;">
                    <div class="diary-main-content-container">
                      <div class="diary-main-content-title">설명</div>
                      <div style="color:#fff">{{blog.intro}}</div>
                    </div>
                  </v-card-text>
                </div>

                <!-- unhover -->
                <div v-else>
                  <v-card-title class="title-unhover">{{blog.title}}</v-card-title>
                </div>
              </v-card>
            </v-hover>
          </v-col>
        </v-row>
        <!-- <template class="justify-center" v-if="!blog.title.includes(keyw)">
          <img src="/static/images/Blogit_logo_grey.png" />
        </template>-->
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'Blogs',
  data() {
    return {
      test: 2,
      activeBtn: 0,
      showNav: true,
      show: false,
      diarys: {},
      uid: '',
      mydata: [],
      keyw: '',
      blog: {
        title: '',
      },
      tmp: '',
      tagdata: {},
    };
  },
  created() {
    this.tmp = this.$route.params;
    this.uid = this.$route.params.uid;
    this.test = this.$route.params.test;
    if (this.test) {
    } else {
      this.test = 2;
    }

    this.mydata = this.$store.state.user;
    this.getDiary();
  },
  methods: {
    goBlog(param) {
      if (this.test == 1) {
        this.$router.push({ name: 'BlogDetail', params: { did: param } });
      } else {
        this.$router.push({ name: 'BlogDetail', params: { did: param } });
      }
    },

    async getDiary() {
      try {
        let tempspace = await this.$api.getDiaries(this.uid, {
          isProj: this.test,
          keyword: '',
        });
        this.diarys = tempspace;
        for( var i =0 ;tempspace.length>i;i++){
          // console.log(tempspace[i]['id'])
          // console.log(tempspace[i]['uid'])
          var configss ={did:`${tempspace[i]['id']}`,
          uid:`${tempspace[i]['uid']}`,
          num:"3"}
          // console.log(configss)
          try{
          this.tagdata[tempspace[i]['id']]=await this.$api.tagRank(
          configss)
          }
          catch(e){
            // console.log(e)
          }
        }
        // console.log('성공');
      } catch (e) {
        // console.log(e);
      }
    },
  },
};
</script>

<style>
.diary-main-container {
  width: 100%;
}

.diary-main-inner {
  width: 1140px;
  margin: 0 auto;
  padding: 70px 0;
}

.diary-main-add-btn {
  font-size: 14px;
  background: #0051cb;
  font-weight: 600;
  color: #fff;
  border-radius: 6px;
  padding: 0 16px;
  height: 40px;
  cursor: pointer;
}

.diary-main-toggle {
  width: 100%;
  height: 130px;
  background: #f7f9fd;
}

.diary-main-toggle-item {
  min-width: 160px;
  padding: 0 32px;
  cursor: pointer;
}

.diary-main-toggle-item--selected {
  background: #ebf0fa;
}

.diary-main-toggle-text {
  font-size: 18px;
  font-weight: 700;
}

.diary-main-toggle-img {
  width: 65px;
  height: 65px;
  margin-right: 10px;
}

.v-card:not(.on-hover) {
  opacity: 1;
}
.div-reveal {
  opacity: 0.6;
  width: 100%;
  height: 100%;
}
.title-unhover {
  font-weight: bold;
  font-size: 24px;
  background-color: white;
  opacity: 0.5;
  width: 100%;
  height: 100%;
}

.diary-main-content-container {
  overflow: hidden;
  padding: 4px;
}

.diary-main-content-title {
  width: 50px;
  font-size: 14px;
  color: #fff;
}

.diary-main-content-text {
  margin-right: 2px;
  font-size: 14px;
  font-weight: bold;
  color: #fff;
  padding: 0 8px;
  border-radius: 10px;
  border: solid 1px #fff;
  white-space: nowrap;
}
/* TODO : 반응형으로 작성(half page : 줄당 2개) 모바일 : 줄당 1개 */
</style>

