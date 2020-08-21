<template>
  <div class="scrap-container">
    <div class="d-flex scrap-inner">
      <div class="d-flex justify-center">
        <div class="d-flex flex-column" style="width: 500px;">
          <div class="scrap-title d-flex flex-grow-0">스크랩 글</div>
          <div
            class="d-flex flex-column flex-grow-0 scrap-diary"
            v-for="post in scrapData"
            :key="post.id"
          >
            <div class="scrap-date">{{post.cdate}}</div>
            <div class="d-flex align-center" style="margin-top: 16px;">
              <div class="d-flex flex-grow-0 scrap-tmp-title">다이어리 이름</div>
              <div class="d-flex scrap-tmp-content">{{post.dName}}</div>
            </div>
            <div class="d-flex align-center" style="margin-top: 4px;">
              <div class="d-flex flex-grow-0 scrap-tmp-title">글 제목</div>
              <div class="d-flex scrap-tmp-content">{{post.postinfo.title}}</div>
            </div>
            <div class="d-flex">
              <div class="d-flex" />
              <div
                class="d-flex align-center justify-center flex-grow-0 scrap-btn"
                @click="$router.push({name:'PostDetail',params:{uid:post.postinfo.uid, pid:post.postinfo.id}})"
              >보러가기</div>
              <div
                class="d-flex align-center justify-center flex-grow-0 scrap-btn-red"
                @click="scrapDelete(post.id)"
              >삭제</div>
            </div>
          </div>
          <div v-if="scrapData == null || scrapData.length == 0">저장된 글이 없습니다.</div>
        </div>
      </div>
      <user-sidebar />
    </div>
  </div>
</template>

<script>
import userSidebar from '../../component/user_sidebar';

export default {
  name: 'Scrap',
  components: {
    userSidebar,
  },
  data() {
    return {
      scrapData: [],
      uid: '',
    };
  },
  async created() {
    this.uid = this.$store.state.user.id;
    let tmpspace = await this.$api.getScrapInfo(this.uid);
    this.scrapData = tmpspace;
  },
  methods: {
    async scrapDelete(scrapid) {
      try {
        await this.$api.deleteScrap(scrapid);
        // console.log('삭제 성공');

        try {
          let tmpspace = await this.$api.getScrapInfo(this.uid);
          this.scrapData = tmpspace;
          // console.log(tmpspace);
        } catch (e) {
          // console.log(e);
        }
      } catch (e) {
        // console.log(e);
      }
    },
  },
};
</script>

<style>
.scrap-container {
  width: 100%;
}

.scrap-inner {
  padding-bottom: 70px;
  min-height: 80vh;
  width: 1140px;
  margin: 0 auto;
}

.scrap-title {
  font-weight: 600;
  margin-bottom: 32px;
}

.scrap-diary {
  margin-bottom: 16px;
  padding: 20px;
  width: 100%;
  border: solid 1px #ccc;
  border-radius: 6px;
}

.scrap-date {
  font-size: 12px;
}

.scrap-tmp-title {
  width: 100px;
  font-size: 12px;
}

.scrap-tmp-content {
  font-weight: bold;
  font-size: 14px;
}

.scrap-btn {
  margin-right: 8px;
  padding: 0 16px;
  font-size: 12px;
  background: #0051cb;
  color: #fff;
  border-radius: 6px;
  height: 30px;
  cursor: pointer;
}

.scrap-btn-white {
  margin-right: 8px;
  padding: 0 16px;
  font-size: 12px;
  background: #fff;
  color: #0051cb;
  border: solid 1px #0051cb;
  border-radius: 6px;
  height: 30px;
  cursor: pointer;
}

.scrap-btn-red {
  padding: 0 16px;
  font-size: 12px;
  background: #db4455;
  color: #fff;
  border: solid 1px #db4455;
  border-radius: 6px;
  height: 30px;
  cursor: pointer;
}
</style>