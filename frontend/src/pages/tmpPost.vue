<template>
  <div class="tmpPost-container">
    <div class="d-flex tmpPost-inner">
      <div class="d-flex justify-center">
        <div class="d-flex flex-column" style="width: 850px;">
          <div class="tmpPost-title d-flex">임시 저장 글</div>
          <div
            class="d-flex align-center flex-grow-0 tmpPost-diary"
            v-for="post in tempPost"
            :key="post.id"
          >
            <div class="d-flex" style="overflow: hidden">{{post.did}}번 다이어리 | 제목: {{post.title}}</div>
            <div class="d-flex align-center justify-center flex-grow-0 tmpPost-btn" @click="$router.push({name:'PostDetail',params:{pid:post.id}})">보러가기</div>
            <div class="d-flex align-center justify-center flex-grow-0 tmpPost-btn-white" @click="deleteP(post.id)">삭제</div>
          </div>
          <div v-if="tempPost == null || tempPost.length == 0">저장된 글이 없습니다.</div>
        </div>
      </div>
      <user-sidebar />
    </div>
  </div>
</template>

<script>
import userSidebar from '../component/user_sidebar.vue';
export default {
  name: 'tmpPost',
  components: {
    userSidebar,
  },
  data() {
    return {
      config: {
        uid: '',
        isProj: 2,
        keyword: '',
        isTemp: 1,
        limit:10
      },
      tempPost: '',
    };
  },
  async created() {
    this.config.uid = this.$store.state.user.id;
    try {
      let tmpspace = await this.$api.searchTemp(this.config);
      this.tempPost = tmpspace.data;
      console.log('성공');
    } catch (e) {
      console.log(e);
    }
  },
  methods: {
    async deleteP(postid) {
      try {
        await this.$api.deletePost(postid);
        console.log('성공');
        // this.$router.push({ name: 'tmpPost' });
        // 여기 리디렉션? 같은거 해서 삭제한걸 새롭게.. 받아 와야함 라우터 푸시로 안될거같은데
        // FIX: 걍 api 한번 더 불러주면댐~~
        try {
          let tmpspace = await this.$api.searchTemp(this.config);
          this.tempPost = tmpspace.data;
          console.log('성공');
        } catch (e) {
          console.log(e);
        }
      } catch (e) {
        console.log(e);
      }
    },
  },
};
</script>

<style>
.tmpPost-container {
  width: 100%;
}

.tmpPost-inner {
  padding-bottom: 70px;
  width: 1140px;
  margin: 0 auto;
}

.tmpPost-title {
  font-weight: 600;
  margin-bottom: 32px;
}

.tmpPost-diary {
  margin-bottom: 16px;
  padding: 20px;
  height: 70px;
  width: 100%;
  border: solid 1px #ccc;
  border-radius: 4px;
}

.tmpPost-btn {
    margin-right: 8px;
  padding: 0 16px;
  font-size: 14px;
  background: #0051cb;
  font-weight: 600;
  color: #fff;
  border-radius: 6px;
  height: 40px;
  cursor: pointer;
}

.tmpPost-btn-white {
  padding: 0 16px;
  font-size: 14px;
  background: #fff;
  font-weight: 600;
  color: #0051cb;
  border: solid 1px #0051cb;
  border-radius: 6px;
  height: 40px;
  cursor: pointer;
}
</style>