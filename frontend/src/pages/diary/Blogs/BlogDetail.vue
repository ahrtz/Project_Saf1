<template>
  <div class="blog-detail-container">
    <div class="blog-detail-inner">
      <Status />
      <div class="d-flex">
        <div class="d-flex" />
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
              <div class="text-center d-flex justify-space-between">
                <div>{{isProj ? "프로젝트 글" : "블로그 글"}}</div>
              </div>
              <v-card class="pa-2" outlined tile v-for="post in postdata" :key="post.id">
                <router-link :to="{name:'PostDetail',params:{pid:post.id}}">{{post}}</router-link>
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
  methods: {},
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
</style>