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
                        <v-col cols="12" sm="6" offset-sm="3">
                        <v-card>
                            <v-list two-line>
                            <v-subheader>followers</v-subheader>
                            <template v-for="follower in followers.slice(0, 5)">
                                <v-list-item :key="follower.userinfo.id" > <!--@click=""-->
                                <v-list-item-avatar>
                                    <img :src="follower.userinfo.img">
                                </v-list-item-avatar>
                                <v-list-item-content>
                                    <v-list-item-title> {{follower.userinfo.nickname}} </v-list-item-title>
                                    <v-list-item-subtitle class="font-weight-bold"> {{follower.userinfo.email}} </v-list-item-subtitle>
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

                </v-col>
            </v-row>
            <!-- Scrap 글 뿌려줄 곳 -->
            <v-row>
                
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
      type : {
          follower : 0,
          following : 1
      },
      followtype : 0,
    }),
    async created() {
        this.uid = this.$store.state.user.id
        this.getFollowers();
    },
    methods : {
        async getFollowers(){
            try{
                let temp = await this.$api.searchFollow(this.followtype);
                this.followers = temp;
                console.log('성공' + temp);
            }catch (e) {
                console.log('실패');
            }
        },
    }
}
</script>

<style>

</style>