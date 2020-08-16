<template>
  <div class="follow-container">
    <div class="d-flex follow-inner">
      <div class="d-flex justify-center">
        <div class="d-flex flex-column" style="width: 100%;padding-right:32px">
          <div class="follow-title">팔로우 리스트</div>
          <v-row>
            <!--팔로우 리스트 뿌려줄 곳 -->
            <v-col col="6">
              <v-row>
                <v-col cols="12">
                  <v-card>
                    <v-list two-line>
                      <v-subheader>Followers</v-subheader>
                      <template
                        v-for="(follower,index) in followers.slice((this.page1-1)*3,(this.page1)*3)"
                      >
                        <hr class="hr-css" :key="index" />
                        <v-list-item
                          :key="follower.email"
                          @click="$router.push({ name: 'MainPagefor', params: { uid: follower.id } })"
                        >
                          <!-- @click="" -->
                          <v-list-item-avatar class="mr-3">
                            <img
                              :src="follower.img == null ? '/static/images/user.png' : follower.img"
                            />
                          </v-list-item-avatar>
                          <v-list-item-content class="follow">
                            <v-list-item-title>{{follower.nickname}}</v-list-item-title>
                            <v-list-item-subtitle class="font-weight-bold">{{follower.email}}</v-list-item-subtitle>
                          </v-list-item-content>
                        </v-list-item>
                      </template>
                    </v-list>
                    <div class="text-center">
                      <v-pagination
                        v-model="page1"
                        :length="Math.ceil(followers.length / 3)"
                        :total-visible="7"
                      ></v-pagination>
                    </div>
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
                      <v-subheader class="follow-category">Following</v-subheader>
                      <template
                        v-for="(followee,index) in followees.slice((this.page2 - 1)*3,(this.page2)*3)"
                      >
                        <hr class="hr-css" :key="index" />
                        <v-list-item
                          :key="followee.email"
                          @click="$router.push({ name: 'MainPagefor', params: { uid: followee.id } })"
                        >
                          <!--@click=""-->
                          <v-list-item-avatar class="mr-3">
                            <img
                              :src="followee.img == null ? '/static/images/user.png' : followee.img"
                            />
                          </v-list-item-avatar>
                          <v-list-item-content>
                            <v-list-item-title>{{followee.nickname}}</v-list-item-title>
                            <v-list-item-subtitle class="font-weight-bold">{{followee.email}}</v-list-item-subtitle>
                          </v-list-item-content>
                        </v-list-item>
                      </template>
                    </v-list>
                    <div class="text-center">
                      <v-pagination
                        v-model="page2"
                        :length="Math.ceil(followees.length / 3)"
                        :total-visible="7"
                      ></v-pagination>
                    </div>
                  </v-card>
                </v-col>
              </v-row>
            </v-col>
          </v-row>
        </div>
      </div>
      <user-sidebar />
    </div>
  </div>
</template>


<script>
import userSidebar from '../../component/user_sidebar';

export default {
  name: 'Follow',
  components: {
    userSidebar,
  },
  data: () => ({
    page1: 1,
    page2: 1,
    uid: '',
    followers: [],
    followees: [],
    followertype: {
      type: '0',
    },
    followeetype: {
      type: '1',
    },
  }),
  // 한번 쓰고 냅둘 것은 함수화 하지 말고 created 안에 넣는 걸 선호.
  // update와 같이 수정 작업으로 인한 재호출이 필요할 떈 함수화가 더 낫다.

  async created() {
    this.uid = this.$store.state.user.id;
    this.getFollowers();
    this.getFollowees();
  },
  methods: {
    async getFollowers() {
      try {
        let temp = await this.$api.searchFollow(this.followertype);
        this.followers = temp.userinfo;
      } catch (e) {
        console.log('실패');
      }
    },
    async getFollowees() {
      try {
        let temp2 = await this.$api.searchFollow(this.followeetype);
        this.followees = temp2.userinfo; // 여기 에서 쓰는 this 는 data 가 될수도 있고 이 함수내의 것을 의미할수도있음 this는 쓸때 항상 주의하면서
        console.log('followee 성공!! ' + temp2);
      } catch (e) {
        console.log(e);
        console.log('getFollowees() 실패.');
      }
    },
  },
};
</script>

<style>
.hr-css {
  border-color: grey;
  opacity: 0.2;
}

.follow-container {
  width: 100%;
}

.follow-inner {
  padding-bottom: 70px;
  min-height: 80vh;
  width: 1140px;
  margin: 0 auto;
}

.follow-title {
  font-weight: 600;
  margin-bottom: 8px;
}

.follow-diary {
  margin-bottom: 16px;
  padding: 20px;
  width: 100%;
  border: solid 1px #ccc;
  border-radius: 6px;
}

.follow-date {
  font-size: 12px;
}

.follow-tmp-title {
  width: 100px;
  font-size: 12px;
}

.follow-tmp-content {
  font-weight: bold;
  font-size: 14px;
}

.follow-btn {
  margin-right: 8px;
  padding: 0 16px;
  font-size: 12px;
  background: #0051cb;
  color: #fff;
  border-radius: 6px;
  height: 30px;
  cursor: pointer;
}

.follow-btn-white {
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

.follow-btn-red {
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