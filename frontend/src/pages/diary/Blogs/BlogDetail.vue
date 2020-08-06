<template>
  <div class="blog-detail-container">
    <div class="blog-detail-inner">
      <Status />
      <div class="d-flex">
        <div class="d-flex" />
        <v-btn @click="diaryDelete()"> 다이어리 삭제</v-btn>
        <div
          v-if="!isProj"
          class="d-flex justify-center align-center flex-grow-0 blog-detail-btn"
          @click="$router.push({name:'NewBlogPost',params:{did:diaryid.did}})"
        >글 작성</div>

        <div
          v-if="isProj"
          class="d-flex justify-center align-center flex-grow-0 blog-detail-btn"
          @click="$router.push({name:'NewProjectPost',params:{did:diaryid.did}})"
        >글 작성</div>
      </div>
      <div></div>
      <div>
        <v-container fluid>
          <v-row>
            <v-col cols="12">
              <div class="text-center d-flex justify-space-between " style="margin-bottom:16px; border-bottom:solid 1px grey;">
                <div><h2>{{isProj ? "프로젝트 글" : "블로그 글"}}</h2></div>
              </div>
              <v-card class="ma-2"
              flat
              v-for="post in postdata"
              :key="post.id"
              style="margin-top:10px; border-bottom:dashed 1px grey"
              >
                <router-link :to="{name:'PostDetail',params:{pid:post.id}}">
                  <!-- {{post}} -->
                  <!-- card layout -->
                  <div>
                    <!-- 프로필 이미지, 닉네임  -->
                    <header class="blog-card-header">
                      <!-- <img :src="post.userinfo.img" alt="" class="search-card-header-img"> -->
                      <div class="blog-card-header-nick_date">
                        <h3 > 제목 :
                          {{post.title}}
                        </h3>
                        <span> 작성일 :
                          {{post.c_date}}
                        </span>
                      </div>
                    </header>
                    <!-- 포스트 제목 / 컨텐츠 -->
                    <article class="blog-card-article" @click="$router.push({name:'PostDetail',params:{pid:post.id}})" style="cursor:pointer">
                      <h3 style="margin-left:10px;">{{post.title}}</h3>
                      <p style="margin-left:10px; margin-top:5px;">
                          {{post.content}}
                      </p>
                    </article>
                    <footer>
                      <!-- TODO: tags -->
                    </footer>
                  </div>
                </router-link>
              </v-card>
            </v-col>
          </v-row>
        </v-container>
      </div>
    </div>
  </div>
</template>

<script>
import Status from '@/component/Status.vue';
import axios from 'axios';
axios.defaults.headers.common['Access-Control-Allow-Origin'] = '*';
export default {
  name: 'BlogDetail',
  components: {
    Status,
  },
  data() {
    return {
      diaryid: this.$route.params,
      config: {
        keyword: '',
        isTemp: 0,
      },
      postdata: [],
    };
  },
  methods: {
    diaryDelete(){
      try {
        this.$api.deleteDiary(this.diaryid.did)
        console.log('다이어리 삭제 완료')
        this.$router.go(-1)
      }catch(e){
        console.log(e)
      }
    }


  },
  async created() {
    try {
      let tmpspace = await this.$api.diarydetail(
        this.$route.params.did,
        this.config
      );
      console.log(tmpspace);
      this.postdata = tmpspace;
      console.log('성공');
    } catch (e) {
      console.log('실패');
    }
  },
  computed: {
    isProj() {
      if (this.$route.path[7] == 'p') {
        return true;
      } else {
        return false;
      }
    },
  },
};
</script>

<style>
.blog-detail-container {
  width: 100%;
}

.blog-detail-inner {
  padding-top: 77px;
  width: 1140px;
  margin: 0 auto;
}

.blog-detail-btn {
  margin-top: 32px;
  padding: 0 16px;
  font-size: 14px;
  background: #0051cb;
  font-weight: 600;
  color: #fff;
  border-radius: 6px;
  height: 40px;
  cursor: pointer;
}
.blog-card-header{
  background-color:white;
  /* background:#ffffffde; */
  font-size: 16px;
  color: black;
  height: 55px;
}
.blog-card-header-nick_date{
  display:flex;
  flex-direction: column;
}
.blog-card-article{
  background-color:white;
  float:unset;
  width:100%;
  height:100px;
}
</style>
