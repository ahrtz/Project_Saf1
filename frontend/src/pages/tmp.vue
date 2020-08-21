<template>
  <div class="search-page-container">
    <div class="search-page-inner d-flex justify-center">
      <div class="d-flex flex-grow-0 flex-shrink-0 search-page-sidebar">
        <div class="d-flex flex-column" style="width: 100%">
          <div
            class="d-flex flex-grow-0 search-page-menu"
            :class="{'search-page-menu--selected': res==0}"
            @click="res=0"
          >제목검색 ({{searchResult ? searchResult.length : "0"}})</div>
          <div
            class="d-flex flex-grow-0 search-page-menu"
            :class="{'search-page-menu--selected': res==1}"
            @click="res=1"
          >태그검색 ({{tagResult ? tagResult.length : "0"}})</div>
        </div>
      </div>
      <div class="d-flex">
        <div class="d-flex flex-column" style="width: 100%">
          <div
            class="d-flex align-center justify-space-between"
            style="margin-bottom:16px;padding-bottom: 8px; border-bottom:solid 1px #dde3ea"
          >
            <div class="search-page-section-title">검색 결과</div>
          </div>
          <!-- 여기 v-if 구분 -->
          <div :key="componentKey" v-if="res==0">
            <div
              class="d-flex flex-column flex-grow-0 search-page-card"
              v-for="post in searchResult"
              :key="`search-${post.id}`"
              @click="$router.push({name:'PostDetail',params:{uid:post.userinfo.id, pid:post.id}})"
            >
              <!-- 프로필 이미지, 닉네임  -->
              <div class="d-flex align-center search-card-header">
                <img :src="post.userinfo.img" alt class="search-card-header-img" />
                <div class="search-card-header-nick_date">
                  <div class="search-page-nickname">{{post.userinfo.nickname}}</div>
                  <div class="search-page-cdate">{{post.cdate}}</div>
                </div>
                <v-icon v-if="post.commitCnt" class="d-flex justify-end">mdi-source-commit</v-icon>
                <span v-if="post.commitCnt" class="search-page-commitcnt">{{post.commitCnt}}</span>
              </div>
              <!-- 포스트 제목 / 컨텐츠 -->
              <div class="search-card-article" style="cursor:pointer">
                <div class="search-page-content-title">{{post.title}}</div>
                <div class="search-page-content-text" v-html="compiledMarkdown(post)"></div>
              </div>
            </div>
          </div>

          <div :key="componentKey+1" v-if="res==1">
            <div
              class="d-flex flex-column flex-grow-0 search-page-card"
              v-for="(posts,index) in tagResult"
              :key="`tag-${posts.id}`"
              @click="$router.push({name:'PostDetail',params:{uid:posts.userinfo.id, pid:posts.postinfo.id}})"
            >
              <!-- 프로필 이미지, 닉네임  -->
              <div class="d-flex align-center search-card-header">
                <img :src="posts.userinfo.img" alt class="search-card-header-img" />
                <div class="search-card-header-nick_date">
                  <div class="search-page-nickname">{{posts.userinfo.nickname}}</div>
                  <div class="search-page-cdate">{{posts.postinfo.cdate}}</div>
                </div>
                <v-icon
                  v-if="tagcommit[index] ? tagcommit[index].length>0 : false"
                  class="d-flex justify-end"
                >mdi-source-commit</v-icon>
                <span
                  v-if="tagcommit[index] ? tagcommit[index].length>0 : false"
                  class="search-page-commitcnt"
                >{{tagcommit[index] ? tagcommit[index].length : "0"}}</span>
              </div>
              <!-- 포스트 제목 / 컨텐츠 -->
              <div class="search-card-article" style="cursor:pointer">
                <div class="search-page-content-title">{{posts.postinfo.title}}</div>
                <div class="search-page-content-text" v-html="compiledMarkdown(posts.postinfo)"></div>
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
import marked from 'marked';
// import Status from '@/component/Status.vue'

var renderer = new marked.Renderer();

export default {
  name: 'tmp',

  data() {
    return {
      ddd: this.$route.params,
      p_data: {
        uid: '',
        keyword: this.$route.params.key,
        isTemp: '0',
        limit: '',
        isProj: '2',
      },
      t_data: {
        keyword: this.$route.params.key,
      },
      tagResult: [],
      searchResult: [],
      componentKey: 2,
      res: 0,
      tagcommit: {},
    };
  },
  methods: {
    changeval() {
      if (this.res == 0) {
        this.res = 1;
      } else {
        this.res = 0;
      }
    },
    compiledMarkdown(posttmp) {
      // console.log("hi")
      let vm = posttmp;
      if (!posttmp || !posttmp.content) {
        return '';
      }
      renderer.em = (text) => {
        return '<em>' + '' + '</em>';
      };

      return marked(posttmp.content, { renderer: renderer });
    },
  },
  async created() {
    
    if (this.$route.params.type == 'tag') {
      this.res = 1;
    }

    try {
      let tmpspace = await this.$api.searchTemp(this.p_data);
      this.searchResult = tmpspace.data;
    } catch (e) {
      // console.log('포스트 검색 실패');
    }
    try {
      let tmpspace1 = await this.$api.tagSearch(this.t_data);
      this.tagResult = tmpspace1;
      for (var i = 0; i < tmpspace1.length; i++) {
        this.tagcommit[i] = await this.$api.getPostCommit(tmpspace1[i]['pid']);
      }
    } catch (e) {
      // console.log('태그 검색 실패');
    }

    this.$forceUpdate();
  },
};
</script>

<style>
.search-card-header {
  background-color: white;
  /* background:#ffffffde; */
  font-size: 16px;
  color: black;
  height: 55px;
}
.search-card-header-nick_date {
  display: flex;
  flex-direction: column;
}

.search-card-header-img {
  width: 35px;
  height: 35px;
  margin: 10px 10px 10px 10px;
  float: left;
}

.search-page-container {
  width: 100%;
}

.search-page-inner {
  width: 1140px;
  margin: 0 auto;
  padding-bottom: 70px;
}

.search-page-btn {
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

.search-page-section-title {
  font-size: 20px;
  font-weight: 700;
}

.search-page-sidebar {
  margin-top: -40px;
  width: 200px;
  padding: 20px 8px;
  margin-right: 32px;
  border: solid 1px #dde3ea;
}

.search-page-menu {
  width: 100%;
  margin-top: 4px;
  padding: 16px;
  font-size: 14px;
  cursor: pointer;
  border-radius: 6px;
  font-weight: 600;
}

.search-page-menu:hover,
.search-page-menu--selected {
  background: #deebff;
  color: #0051cb;
}

.search-card-header {
  background-color: white;
  /* background:#ffffffde; */
  font-size: 16px;
  color: black;
  height: 55px;
  width: 100%;
  border-bottom: solid 1px #dde3ea;
}
.search-page-commitcnt {
  font-size: 14px;
}

.search-card-header-nick_date {
  display: flex;
  flex-direction: column;
}

.search-card-header-img {
  border-radius: 50%;
  border: solid 1px #dde3ea;
  width: 35px;
  height: 35px;
  margin-right: 10px;
}
.search-card-article {
  margin: 16px;
  overflow: hidden;
  background-color: white;
  float: unset;
  min-height: 40px;
  max-height: 80px;
}
.search-page-container {
  width: 100%;
}

.search-page-inner {
  width: 1140px;
  margin: 0 auto;
  padding-bottom: 70px;
}

.search-page-contact {
  width: 268px;
}

.search-page-btn {
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

.search-page-section-title {
  font-size: 18px;
  font-weight: 700;
}

.search-page-card {
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
}

.search-page-card:hover {
  box-shadow: 1px 1px 10px 4px #dde3ea;
}

.search-page-nickname {
  font-size: 14px;
  font-weight: 700;
}

.search-page-cdate {
  font-size: 12px;
  color: #21262e;
}

.search-page-content-title {
  font-size: 14px;
  font-weight: 700;
  margin-bottom: 8px;
}

.search-page-content-text {
  font-size: 12px;
}

.search-page-tag-container {
  width: 100%;
  overflow: hidden;
}

.search-page-tag {
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
