<template>
  <div class="group-detail-container">
    <div class="d-flex group-detail-inner">
      <div class="d-flex justify-center">
        <div class="d-flex flex-column" style="width: 100%">
          <!-- 윗부분 -->
          <div class="d-flex" style="margin-bottom: 32px;">
            <div class="d-flex" />
            <v-dialog v-model="dialog" max-width="500px">
              <template v-slot:activator="{on}">
                <div class="d-flex justify-center align-center flex-grow-0 s-button-white" style="margin-right: 4px" v-on="on">그룹 수정</div>
              </template>
              <v-card>
                <v-card-title>
                  <span class="headline">그룹 내용 수정</span>
                </v-card-title>
                <v-card-text>
                  <v-container grid-list-md>
                    <v-layout column>
                      <v-flex xs12 sm6 md4>
                        <v-text-field
                          v-model="updateItem.name"
                          label="Group name"
                          class="group-text-box"
                        ></v-text-field>
                      </v-flex>
                      <v-flex xs12 sm6 md4>
                        <v-textarea
                          v-model="updateItem.intro"
                          label="Description"
                          class="group-text-box"
                        ></v-textarea>
                      </v-flex>
                    </v-layout>
                  </v-container>
                </v-card-text>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <div class="d-flex justify-center align-center flex-grow-0 s-button-white" style="margin-right: 4px;" @click="close()">취소</div>
                  <div class="d-flex justify-center align-center flex-grow-0 s-button-blue" @click="updateGroup()">확인</div>
                </v-card-actions>
              </v-card>
            </v-dialog>
            <div class="d-flex justify-center align-center flex-grow-0 s-button-red" @click="removeGroup">그룹 해체</div>
          </div>

          <v-row style="width: 768px;margin:0 auto;">
            <!-- 그룹 이름 & 그룹 디테일 -->
            <v-col class="group-detail-info-box">
                <div class="group-detail-info-title">그룹 정보</div>
              <div class="group-detail-info-name">{{ group_info.name }}</div>
              <div class="group-detail-info-content">{{ group_info.intro }}</div>
            </v-col>
            <div class="d-flex flex-grow-0" style="width: 32px;"/>
            <!-- 그룹장 정보 -->
            <v-col
            class="group-detail-info-box"
              style="cursor:pointer"
              @click="$router.push({ name: 'MainPagefor', params: { uid: group_info.lid } })"
            >
            <div class="group-detail-info-title">그룹장 정보</div>
              <!-- 그룹장 썸네일 -->
              <v-row>
                <v-col class="flex-grow-0">
                  <img
                    class="groupdetail-leader-thumbnail"
                    :src="group_info.lImg == null ? '/static/images/user.png' : group_info.lImg"
                  />
                </v-col>
                <!-- 그룹장 닉네임 이메일 -->
                <v-col>
                  <v-row class="group-detail-info-name">{{ group_info.lName }}</v-row>
                  <v-row class="group-detail-info-content">{{ group_info.lEmail }}</v-row>
                </v-col>
              </v-row>
              <!-- 그룹  -->
            </v-col>
          </v-row>
          <v-row class="flex-column" style="width: 768px;margin:0 auto;margin-top: 32px;">
              <div class="d-flex align-center justify-center" style="padding-bottom: 16px;">
                  <img style="width: 30px" src="/static/images/crown.png"/>
                  <div class="group-detail-rank-title">Rainking Top 5</div>
                  <img style="width: 30px" src="/static/images/crown.png"/>
              </div>
              <div class="d-flex">
            <!-- 순위 리스트 3개 -->
              <!-- 좋아요 순위(Top 3) -->
              <v-col class="group-detail-info-box">
                <v-list subheader>
                  <v-subheader class="group-detail-rank-subtitle" style="border-bottom: solid 1px #dde3ea;margin-bottom:8px">좋아요 Top 5</v-subheader>

                  <v-list-item
                    v-for="like_item in lRateList"
                    :key="like_item.nickname"
                    style="cursor:pointer;"
                    @click="$router.push({ name: 'MainPagefor', params: { uid: like_item.uid } })"
                  >
                    <v-list-item-avatar>
                      <v-img
                        :src="like_item.img == null ? '/static/images/user.png' : like_item.img"
                      ></v-img>
                    </v-list-item-avatar>
                    <v-list-item-title style="padding-left:8px;">
                      {{like_item.nickname}}
                      <v-spacer></v-spacer>
                      {{like_item.cnt}}개
                    </v-list-item-title>
                    <v-list-item-content></v-list-item-content>
                  </v-list-item>
                </v-list>
              </v-col>
              <div class="d-flex flex-grow-0" style="width: 32px;"/>
              <!-- 팔로워 순위(Top 3)  -->
              <v-col class="group-detail-info-box">
                <v-list subheader>
                  <v-subheader class="group-detail-rank-subtitle" style="border-bottom: solid 1px #dde3ea;margin-bottom:8px">팔로우 Top 5</v-subheader>

                  <v-list-item
                    v-for="follower_item in fRateList"
                    :key="follower_item.nickname"
                    style="cursor:pointer"
                    @click="$router.push({ name: 'MainPagefor', params: { uid: follower_item.uid } })"
                  >
                    <v-list-item-avatar>
                      <v-img
                        :src="follower_item.img == null ? '/static/images/user.png' : follower_item.img"
                      ></v-img>
                    </v-list-item-avatar>
                    <v-list-item-title style="padding-left:8px;">
                      {{follower_item.nickname}}
                      <v-spacer></v-spacer>
                      {{follower_item.cnt}}명
                    </v-list-item-title>
                    <v-list-item-content></v-list-item-content>
                  </v-list-item>
                </v-list>
              </v-col>
              <div class="d-flex flex-grow-0" style="width: 32px;"/>
              <!-- 포스트 순위(Top 3) -->
              <v-col class="group-detail-info-box">
                <v-list subheader>
                  <v-subheader class="group-detail-rank-subtitle" style="border-bottom: solid 1px #dde3ea;margin-bottom:8px">포스트 Top 5</v-subheader>
                  <v-list-item
                    v-for="post_item in pRateList"
                    :key="post_item.nickname"
                    style="cursor:pointer"
                    @click="$router.push({ name: 'MainPagefor', params: { uid: post_item.uid } })"
                  >
                    <!--@click="" -->
                    <v-list-item-avatar>
                      <v-img
                        :src="post_item.img == null ? '/static/images/user.png' : post_item.img"
                      ></v-img>
                    </v-list-item-avatar>
                    <v-list-item-title style="padding-left:8px;">
                      {{post_item.nickname}}
                      <v-spacer></v-spacer>
                      {{post_item.cnt}}개
                    </v-list-item-title>
                    <v-list-item-content></v-list-item-content>
                  </v-list-item>
                </v-list>
              </v-col>
              </div>
            </v-row>
          <div class="d-flex align-center justify-center" style="margin-top:48px">
            <div class="d-flex flex-grow-0" style="width: 400px;" v-if="isLeader">
              <v-text-field
                hide-details
                dense
                outlined
                v-model="newMember"
                type="text"
                placeholder="추가할 사용자의 이메일을 입력하세요"
              />
              <div class="d-flex justify-center align-center flex-grow-0 s-button-blue" @click="addMember" style="margin-left: 4px;">멤버 추가</div>
            </div>
          </div>
          <!-- 아랫 부분 (그룹에 속한 멤버 리스트) -->
          <v-row class="justify-center">
            <v-card flat>
              <v-card-title>
                <v-row>
                  <v-col>
                    <v-text-field
                      v-model="search"
                      append-icon="search"
                      label="멤버의 닉네임 또는 이메일로 검색해주세요."
                      single-line
                      hide-details
                    ></v-text-field>
                  </v-col>
                </v-row>
              </v-card-title>
              <v-data-table
                v-if="members != null"
                :headers="headers"
                :items="members"
                :search="search"
                @click:row="goUser"
                style="cursor:pointer"
              >
                <!-- @click:row="goGroup" -->
                <template v-if="isLeader" v-slot:[`item.actions`]="{ item }">
                  <v-icon
                    v-if="item.id != group_info.lid"
                    small
                    class="mr-2"
                    @click="deleteMember(item)"
                  >mdi-close</v-icon>
                </template>
              </v-data-table>
            </v-card>
          </v-row>
        </div>
      </div>
      <!-- <user-sidebar /> -->
    </div>
  </div>
</template>

<script>
import userSidebar from '../../component/user_sidebar';

export default {
  components: {
    userSidebar,
  },
  data() {
    return {
      gid: this.$route.params.gid, //gid를 디폴트로 갖추고 있어야 얘네들이 다 구성할 수 있다!
      uid: this.$store.state.user.id,
      isLeader: false,
      group_info: {},
      search: '',
      headers: [
        { text: 'Nickname', align: 'start', value: 'nickname' },
        { text: 'Email', value: 'email' },
        { text: '최근 Post 날짜', value: 'recent_post' },
        { text: '1D1C 달성률(3 months)', value: 'odoc', filterable: false },
        { text: '1D1P 달성률(3 months)', value: 'odop', filterable: false },
        {
          text: 'Actions',
          value: 'actions',
          filterable: false,
          sortable: false,
        },
      ],
      rateFlag: {
        oid: '',
        type: '',
        cnt: '5',
      },
      //아래 3개의 RateList 중 commit, post 는 rate 테이블 이용해야함.
      lRateList: [],
      fRateList: [],
      pRateList: [],
      newMember: '',
      members: [], //그룹원 데이터 테이블에 넣어줄 자료구조.
      dialog: false,
      updateItem : {
        name : '',
        intro : '',
      }
    };
  },
  async created() {
    this.rateFlag.oid = this.$route.params.gid;
    this.uid = this.$store.state.user.id;

    await this.getMembers();

    await this.getRatingInfo();

    if (this.group_info.lid == this.$store.state.user.id) {
      this.isLeader = true;
    }
  },
  watch: {
    dialog(val) {
      val || this.close();
    },
  },
  methods: {
    async addMember() {
      try{
      let tmp= await this.$api.groupRelationAdd({
        email: this.newMember,
        oid: this.$route.params.gid,
      });
        this.newMember = '';
        await this.getMembers();
        await this.getRatingInfo();
      }
      catch(e){
        alert('존재하지 않는 이메일 입니다')
        this.newMember = '';
      }
      
    },
    async getMembers() {
      try {
        let temp = await this.$api.groupDetail(this.$route.params.gid);
        this.group_info = temp;
        var templist = [];
        for (var i = 0; i < this.group_info.userinfo.length; i++) {
          var member = {};
          member['id'] = this.group_info.userinfo[i].id;
          member['nickname'] = this.group_info.userinfo[i].nickname;
          member['email'] = this.group_info.userinfo[i].email;
          this.group_info.userinfo[i].lastPost == null
            ? (member['recent_post'] = '-')
            : (member['recent_post'] = this.getRecentPost(
                this.group_info.userinfo[i].lastPost.date
              ));
          this.group_info.userinfo[i].odocRate == null 
          ? member['odoc'] = '-'
          : member['odoc'] = this.group_info.userinfo[i].odocRate + "% (" + this.group_info.userinfo[i].odocCnt + ")"
          this.group_info.userinfo[i].odocRate == null 
          ? member['odop'] = '-'
          : member['odop'] = this.group_info.userinfo[i].odopRate + "% (" + this.group_info.userinfo[i].odopCnt + ")" 

          templist.push(member);
        }
        this.members = templist;
      } catch (e) {}
    },
    async getRatingInfo() {
      try {
        this.rateFlag.type = '0';
        let temp2 = await this.$api.getTopMembers(this.rateFlag);
        this.lRateList = temp2;

        this.rateFlag.type = '1';
        let temp3 = await this.$api.getTopMembers(this.rateFlag);
        this.pRateList = temp3;

        this.rateFlag.type = '2';
        let temp4 = await this.$api.getTopMembers(this.rateFlag);
        this.fRateList = temp4;
      } catch (e) {}
    },
    async deleteMember(item) {
      this.$api.groupRelationDelete({
        oid: this.$route.params.gid,
        uid: item.id,
      });

      await this.getMembers();
      await this.getRatingInfo();
    },
    async updateGroup() {
      try {
        if(this.updateItem.name != '')
        {
          await this.$api.groupDetailUpdate({
          id: this.$route.params.gid,
          name: this.updateItem.name,
          intro: this.updateItem.intro,
          });
          await this.getMembers();
          this.close();
          this.updateItem.name='';
          this.updateItem.intro='';
        }
        else
        {
          alert("수정할 그룹의 이름을 기입하세요.");
        }
      } catch (e) {
        console.log(e);
      }
    },
    async removeGroup() {
      try {
        await this.$api.deleteGroup(this.$route.params.gid);
        this.$router.go(-1);
      } catch (e) {}
    },
    getRecentPost(recentPost) {
      var ymd = recentPost.substr(0, 10);
      var timestamp = recentPost.substr(11, 8);
      return ymd + ' ' + timestamp;
    },
    close() {
      this.dialog = false;
    },
    goUser(param) {
      this.$router.push({ name: 'MainPagefor', params: { uid: param.id } });
    },
  },
};
</script>

<style>
.groupdetail-leader-thumbnail {
  border-radius: 50%;
  width: 50px;
  height: 50px;
}

.group-text-box {
  background-color: rgb(245, 245, 245);
  border-radius: 10px;
}

.hr-css {
  border-color: grey;
  opacity: 0.2;
}

.group-detail-container {
  width: 100%;
}

.group-detail-inner {
  padding-bottom: 70px;
  min-height: 80vh;
  width: 1140px;
  margin: 0 auto;
}

.group-detail-diary {
  margin-bottom: 16px;
  padding: 20px;
  width: 100%;
  border: solid 1px #ccc;
  border-radius: 6px;
}

.group-detail-date {
  font-size: 12px;
}

.group-detail-info-box {
    border-radius: 6px;
    border: solid 1px #dde3ea;
    padding: 0 8px;
}

.group-detail-info-title {
    padding-top: 16px;
    padding-bottom: 8px;
  font-size: 12px;
  border-bottom: solid 1px #dde3ea;
  margin-bottom: 8px;
}

.group-detail-info-name {
  font-weight: bold;
  font-size: 14px;
}

.group-detail-info-content {
    margin-top: 8px;
  font-size: 12px;
}

.group-detail-rank-title {
  font-size: 20px;
  padding: 0 8px;
  font-weight: 600;
  letter-spacing: -1.2px;
}

.group-detail-rank-subtitle {
  font-size: 14px;
  font-weight: 600;
}
</style>