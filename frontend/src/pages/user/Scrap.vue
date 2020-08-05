<template>
  <div class="tmpPost-container">
    <div class="d-flex tmpPost-inner">
      <div class="d-flex justify-center">
        <div class="d-flex flex-column" style="width: 600px;">
          <div class="tmpPost-title">스크랩 글</div>
          <div
            class="d-flex align-center flex-grow-0 tmpPost-diary"
            v-for="post in scrapData"
            :key="post.id"
          >
            <div class="d-flex" style="overflow: hidden">다이어리 이름 :{{post.dName}} | 제목: {{post.postinfo.title}}</div>
            <div class="d-flex align-center justify-center flex-grow-0 tmpPost-btn" @click="$router.push({name:'PostDetail',params:{pid:post.pid}})">보러가기</div>
            <div class="d-flex align-center justify-center flex-grow-0 tmpPost-btn-white" @click="scrapDelete(post.pid)" >삭제</div>
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
  data(){
    return{
      scrapData:[],
      uid:""
    }
  },
  async created(){
    this.uid = this.$store.state.user.id
    let tmpspace  = await this.$api.getScrapInfo(this.uid)
    this.scrapData = tmpspace
    
  },
  methods:{
    async scrapDelete(scrapid){
      try{
      await this.$api.deleteScrap(scrapid)
      console.log('삭제 성공')
      
        try{
          let tmpspace  = await this.$api.getScrapInfo(this.uid)
          this.scrapData = tmpspace
          console.log(tmpspace)
          }catch(e){
          console.log(e)
        }
      
      }
      catch(e){
        console.log(e)
      }
    
    
    }
  }

};
</script>

<style>
.scrap-container {
  width: 100%;
}

.scrap-inner {
  padding-top: 77px;
  width: 1140px;
  margin: 0 auto;
}

.scrap-title {
  font-weight: 600;
  margin-bottom: 32px;
}
</style>