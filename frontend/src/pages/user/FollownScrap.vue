<template>
  <div class="follow-container">
    <div class="d-flex follow-inner">
      <div class="d-flex justify-center">
        <!-- <div class="d-flex flex-column" style="width: 500px;">
          <div class="scrap-title">팔로우 리스트</div>
          <div>준비중입니다.</div>
        </div> -->
        <v-container>
            <v-row>
                <!--팔로우 리스트 뿌려줄 곳 -->
                <v-col col="6">
                    <v-row>
                        <v-col cols="12">
                        <v-card>
                            <v-list two-line>
                            <v-subheader class="follow-n-scrap-category">followers</v-subheader>
                            <template v-for="(follower,index) in followers.slice(0,3)">
                                <hr class="hr-css" :key="index">
                                <v-list-item :key="follower.email" > <!--@click=""-->
                                <v-list-item-avatar class="mr-3">
                                    <img :src="follower.img">
                                </v-list-item-avatar>
                                <v-list-item-content>
                                    <v-list-item-title> {{follower.nickname}} </v-list-item-title>
                                    <v-list-item-subtitle class="font-weight-bold"> {{follower.email}} </v-list-item-subtitle>
                                </v-list-item-content>
                                </v-list-item>
                               
                            </template>
                            </v-list>
                        </v-card>
                        </v-col>
                    </v-row>                    
                </v-col>
                <!--팔로잉 리스트 뿌려줄 곳 -->
                <v-col col="6">
                    <v-row>
                        <v-col cols="12">
                        <v-card>
                            <v-list two-line>
                            <v-subheader class="follow-n-scrap-category">following</v-subheader>
                            <template v-for="(followee,index) in followees.slice(0,3)">
                                <hr class="hr-css" :key="index">
                                <v-list-item :key="followee.email" > <!--@click=""-->
                                <v-list-item-avatar class="mr-3">
                                    <img :src="followee.img">
                                </v-list-item-avatar>
                                <v-list-item-content>
                                    <v-list-item-title> {{followee.nickname}} </v-list-item-title>
                                    <v-list-item-subtitle class="font-weight-bold"> {{followee.email}} </v-list-item-subtitle>
                                </v-list-item-content>
                                </v-list-item>
                            </template>
                            </v-list>
                        </v-card>
                        </v-col>
                    </v-row>                    
                </v-col>
            </v-row>
            <!-- Scrap 글 뿌려줄 곳 -->
            <v-row class="justify-center">
                <div class="d-flex flex-column align-center" style="width: 900px;">
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
            </v-row>
        </v-container>
      </div>
      <user-sidebar />
    </div>
  </div>
</template>


<script>
import userSidebar from '../../component/user_sidebar';

export default {
    name : 'FollownScrap',
    components:{
        userSidebar,
    },
    data: () => ({
      uid : null,
      followers :[],
      followees :[],
      followertype :{
          type : "0",
      },
      followeetype :{
          type : "1",
      },
       scrapData:[],
    }),
    // 한번 쓰고 냅둘 것은 함수화 하지 말고 created 안에 넣는 걸 선호.
    // update와 같이 수정 작업으로 인한 재호출이 필요할 떈 함수화가 더 낫다.

    async created() {
        this.uid = this.$store.state.user.id
        this.getFollowers();
        this.getFollowees();
        let tmpspace  = await this.$api.getScrapInfo(this.uid)
        this.scrapData = tmpspace
    },
    methods : {
        async getFollowers(){
            try{
                let temp = await this.$api.searchFollow(this.followertype);
                this.followers = temp.userinfo
            }catch (e) {
                console.log('실패');
            }
        },
        async getFollowees()
        {
            try{
                let temp2 = await this.$api.searchFollow(this.followeetype);
                this.followees = temp2.userinfo;// 여기 에서 쓰는 this 는 data 가 될수도 있고 이 함수내의 것을 의미할수도있음 this는 쓸때 항상 주의하면서
                console.log("followee 성공!! " + temp2);
            }catch(e)
            {   console.log(e)
                console.log('getFollowees() 실패.');
            }
        },
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
}
</script>

<style>
.follow-n-scrap-category{
    color: black;
    font-size:medium;
    font-weight:700;
}
.hr-css {
    border-color: grey;
    opacity: 0.2;
}
.scrap-container {
  width: 100%;
}

.scrap-inner {
  padding-bottom: 70px;
  width: 1140px;
  margin: 0 auto;
}

.scrap-title {
  font-weight: 600;
  margin-bottom: 32px;
}
</style>