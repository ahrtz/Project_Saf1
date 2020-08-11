<template>
    <div>
        <v-container fluid>
            <!-- 윗부분 -->
            <v-row>
                <v-row>
                    <!-- 그룹 이름 & 그룹 디테일 -->
                    <v-col cols="6">
                        <v-row>
                            {{ group_info.name }}
                        </v-row>
                        <v-row>
                            {{ group_info.intro }}
                        </v-row>
                    </v-col>
                    <!-- 그룹장 정보 -->
                    <v-col cols="6">
                        <!-- 그룹장 썸네일 -->
                        <v-row>
                            <v-col>
                                <img class = "groupdetail-leader-thumbnail" :src="group_info.lImg == null ? '/static/images/user.png' : group_info.lImg">
                            </v-col>
                            <!-- 그룹장 닉네임 이메일 -->
                            <v-col>
                                <v-row>
                                    {{ group_info.lName }}
                                </v-row>
                                <v-row>
                                    {{ group_info.lEmail }}
                                </v-row>
                            </v-col>
                        </v-row>
                        <!-- 그룹  -->
                    </v-col>
                </v-row>
            </v-row>
            <v-row>
                <!-- 순위 리스트 3개 -->
                <v-row>
                    <!-- 좋아요 순위(Top 3) -->
                    <v-col cols="4">
                        <v-list subheader>
                            <v-subheader> 좋아요 Top 5 </v-subheader>

                            <v-list-item
                              v-for="like_item in lRateList"
                              :key="like_item.nickname"> <!--@click="" -->
                              <v-list-item-avatar>
                                <v-img :src="like_item.img == null ? '/static/images/user.png' : like_item.img"></v-img>
                              </v-list-item-avatar>
                                <v-list-item-title>{{like_item.nickname}} <v-spacer></v-spacer>{{like_item.cnt}}</v-list-item-title>
                              <v-list-item-content>
                              </v-list-item-content>
                            </v-list-item>
                        </v-list>
                    </v-col>
                    <!-- 팔로워 순위(Top 3)  -->
                    <v-col cols="4">
                        <v-list subheader>
                            <v-subheader> 팔로우 Top 5 </v-subheader>

                            <v-list-item
                              v-for="follower_item in fRateList"
                              :key="follower_item.nickname"> <!--@click="" -->
                              <v-list-item-avatar>
                                <v-img :src="follower_item.img == null ? '/static/images/user.png' : follower_item.img"></v-img>
                              </v-list-item-avatar>
                                <v-list-item-title>{{follower_item.nickname}} <v-spacer></v-spacer>{{follower_item.cnt}}</v-list-item-title>
                              <v-list-item-content>
                              </v-list-item-content>
                            </v-list-item>
                        </v-list>
                    </v-col>
                    <!-- 포스트 순위(Top 3) -->
                    <v-col cols="4">
                        <v-list subheader>
                            <v-subheader> 포스트 Top 5 </v-subheader>

                            <v-list-item
                              v-for="post_item in pRateList"
                              :key="post_item.nickname"> <!--@click="" -->
                              <v-list-item-avatar>
                                <v-img :src="post_item.img == null ? '/static/images/user.png' : post_item.img"></v-img>
                              </v-list-item-avatar>
                                <v-list-item-title>{{post_item.nickname}} <v-spacer></v-spacer>{{post_item.cnt}}</v-list-item-title>
                              <v-list-item-content>
                              </v-list-item-content>
                            </v-list-item>
                        </v-list>
                    </v-col>
                </v-row>
            </v-row>
            <hr>
            <!-- 아랫 부분 (그룹에 속한 멤버 리스트) -->
            <v-row>
                <v-container fluid class="pa-10">
                    <v-row>Member List</v-row>
                    <v-row>
                        <v-text-field v-model="newMember" type="text" placeholder="추가할 사용자의 이메일을 입력하세요"/>
                        <v-btn class="ma-2" tile color="indigo" dark @click="addMember">멤버 추가</v-btn>
                    </v-row>
                    <v-card flat>
                        <v-card-title>
                            <v-row>
                                <v-col cols="8">
                                    <v-text-field
                                        v-model="search"
                                        append-icon="search"
                                        label="Search"
                                        single-line
                                        hide-details
                                    ></v-text-field>
                                </v-col>
                            </v-row>
                        </v-card-title>
                        <v-data-table
                        :headers="headers"
                        :items="members"
                        :search="search"
                        >
                        <!-- @click:row="goGroup" -->
                        </v-data-table>
                    </v-card>
                </v-container>
            </v-row>
        </v-container>
    </div>
</template>

<script>
export default {
    data() {
        return {
            gid : this.$route.params.gid, //gid를 디폴트로 갖추고 있어야 얘네들이 다 구성할 수 있다!
            group_info : {},
            search : '',
            headers: [
                { text : 'Nickname', align : 'start', value : 'nickname',},
                { text : 'Email' , value : 'email',},
                { text : '최근 Post 날짜', value : 'recent_post',},
                { text : '1D1C 달성률(3 months)', value : 'odoc', filterable : false, },
                { text : '1D1P 달성률(3 months)', value : 'odop', filterable : false, },
                { text : 'Action' , value : 'action', filterable :false, },
            ],
            rateFlag : {
                oid : "",
                type : "",
                cnt : "5",
            },
            //아래 3개의 RateList 중 commit, post 는 rate 테이블 이용해야함. 
            lRateList : [],
            fRateList : [],
            pRateList : [],
            newMember : "",
            members : [],   //그룹원 데이터 테이블에 넣어줄 자료구조.
            
            
        }
    },
    async created() {
        console.log("CKCK : " + this.$route.params.gid);
        this.rateFlag.oid = this.$route.params.gid;
        
        this.getMembers();
        this.getLikeRate();
        this.getFollowerRate();
        this.getPostRate();
    },

    methods:{
        async addMember() {
            await this.$api.groupRelationAdd({email:this.newMember, oid: this.$route.params.gid});
            this.newMember="";
            this.getMembers();
        },
        async getMembers(){
            try{
                this.members=[];
                let temp = await this.$api.groupDetail(this.$route.params.gid);
                this.group_info = temp;
                // this.members = this.group_info.userinfo;
                for(var i = 0; i < this.group_info.userinfo.length; i++)
                {
                    var member = {};
                    member['nickname'] = this.group_info.userinfo[i].nickname;
                    member['email'] = this.group_info.userinfo[i].email;
                    this.group_info.userinfo[i].lastPost == null ?
                     member['recent_post'] = "-" : member['recent_post'] = this.getRecentPost(this.group_info.userinfo[i].lastPost.date); 
                    // member['odoc'] = 
                    // member['odop'] = 
                    
                    this.members.push(member);

                }
                console.log("GroupDetail API 성공");
            }catch(e){
                console.log("GroupDetail API 실패");
                console.log(e);
            }
        },
        async getLikeRate(){
            try{
                this.rateFlag.type = "0";
                let temp2 = await this.$api.getTopMembers(this.rateFlag);
                this.lRateList = temp2;
            
                console.log("getLikeRate API 성공");
            }
            catch(e){
                console.log("getLikeRate API 실패");
            }
        },
        async getPostRate(){
            try{
                this.rateFlag.type = "1";
                let temp3 = await this.$api.getTopMembers(this.rateFlag);
                this.pRateList = temp3;

                console.log("getPostRate API 성공");                
            }catch(e){
                console.log("getPostRate API 실패");
            }
        },
        async getFollowerRate(){
            try{
                this.rateFlag.type = "2";
                let temp4 = await this.$api.getTopMembers(this.rateFlag);
                this.fRateList = temp4;

                console.log("getFollowerRate API 성공");                
            }catch(e){
                console.log("getFollowerRate API 실패");
            }
        },
        getRecentPost(recentPost){
            var ymd = recentPost.substr(0,10)
            // var timestamp = ("00" + recentPost.getHours()).slice(-2) + ':' + ("00" + recentPost.getMinutes()).slice(-2)
                            // + ':' + ("00" + recentPost.getSeconds()).slice(-2)
            var timestamp = recentPost.substr(11,8)
            return ymd + ' ' + timestamp;
            //console.log(this.commentData.cDate)
        }
    },
}
</script>

<style>
.groupdetail-leader-thumbnail{
    border-radius : 50%;
    width : 50px;
    height : 50px;
}
</style>