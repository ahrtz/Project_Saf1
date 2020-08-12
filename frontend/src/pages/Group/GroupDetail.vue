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
                    <v-col cols="6"
                     style="cursor:pointer"
                     @click="$router.push({ name: 'MainPagefor', params: { uid: group_info.lid } })">
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
                              :key="like_item.nickname"
                              style="cursor:pointer"
                              @click="$router.push({ name: 'MainPagefor', params: { uid: like_item.uid } })"> 
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
                              :key="follower_item.nickname"
                              style="cursor:pointer"
                              @click="$router.push({ name: 'MainPagefor', params: { uid: follower_item.uid } })"> 
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
                              :key="post_item.nickname"
                              style="cursor:pointer"
                              @click="$router.push({ name: 'MainPagefor', params: { uid: post_item.uid } })"
                              > <!--@click="" -->
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
                <v-row>
                    
                </v-row>
            </v-row>
            <v-row>
                <v-dialog v-model="dialog" max-width="500px">
                    <template v-slot:activator="{on}">
                    <v-btn v-on="on" color="primary" dark class="mr-2">그룹 수정</v-btn>
                    </template>     
                    <v-card>
                    <v-card-title>
                        <span class="headline">그룹 내용 수정 </span>
                    </v-card-title>

                    <v-card-text>
                        <v-container grid-list-md>
                        <v-layout column>
                            <v-flex xs12 sm6 md4>
                            <v-text-field v-model="group_info.name" label="Group name" class="group-text-box"></v-text-field>
                            </v-flex>
                            <v-flex xs12 sm6 md4>
                            <v-textarea v-model="group_info.intro" label="Description" class="group-text-box"></v-textarea>
                            </v-flex>
                        </v-layout>
                        </v-container>
                    </v-card-text>

                    <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn class="ma-2" color="blue darken-1" dark @click="close()">cancel</v-btn>
                        <v-btn class="ma-2" color="blue darken-1" dark @click="updateGroup()">confirm</v-btn>
                    </v-card-actions>
                    </v-card>
                </v-dialog>













                <v-btn color="primary" dark @click="removeGroup"> 그룹 해체 </v-btn>

            </v-row>
            <hr>
            <!-- 아랫 부분 (그룹에 속한 멤버 리스트) -->
            <v-row>
                <v-container fluid class="pa-10">
                    <v-row>Member List</v-row>
                    <v-row v-if="isLeader">
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
                        v-if="members != null"
                        :headers="headers"
                        :items="members"
                        :search="search"
                        @click:row="goUser"
                        style="cursor:pointer"
                        >
                        <!-- @click:row="goGroup" -->
                        <template v-if="isLeader" v-slot:[`item.actions`]="{ item }">
                            <v-icon v-if="item.id != group_info.lid"
                            small
                            class="mr-2"
                            @click="deleteMember(item)"
                            >
                            mdi-close
                            </v-icon>
                        </template>
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
            uid : this.$store.state.user.id,
            isLeader : false,
            group_info : {},
            search : '',
            headers: [
                { text : 'Nickname', align : 'start', value : 'nickname',},
                { text : 'Email' , value : 'email',},
                { text : '최근 Post 날짜', value : 'recent_post',},
                { text : '1D1C 달성률(3 months)', value : 'odoc', filterable : false, },
                { text : '1D1P 달성률(3 months)', value : 'odop', filterable : false, },
                { text : 'Actions' , value : 'actions', filterable :false, sortable : false, },
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
            dialog : false,
            
        }
    },
    async created() {
        this.rateFlag.oid = this.$route.params.gid;
        this.uid = this.$store.state.user.id;

        await this.getMembers();

        await this.getRatingInfo();
        

        if(this.group_info.lid == this.$store.state.user.id)
        {
            this.isLeader = true;
        }   
        
    },
    watch : {
      dialog (val) {
        val || this.close()
      }
    },
    methods:{
        async addMember() {
            await this.$api.groupRelationAdd({email:this.newMember, oid: this.$route.params.gid});
            this.newMember="";
            await this.getMembers();
            await this.getRatingInfo();
        },
        async getMembers(){
            try{ 
                let temp = await this.$api.groupDetail(this.$route.params.gid);
                this.group_info = temp;
                var templist = [];
                for(var i = 0; i < this.group_info.userinfo.length; i++)
                {
                    var member = {};
                    member['id'] = this.group_info.userinfo[i].id
                    member['nickname'] = this.group_info.userinfo[i].nickname;
                    member['email'] = this.group_info.userinfo[i].email;
                    this.group_info.userinfo[i].lastPost == null ?
                     member['recent_post'] = "-" : member['recent_post'] = this.getRecentPost(this.group_info.userinfo[i].lastPost.date); 
                    // member['odoc'] = 
                    // member['odop'] = 
                    
                    templist.push(member);
                }
                this.members = templist;
            }catch(e){
            }
        },
        async getRatingInfo(){
            try{
                this.rateFlag.type = "0";
                let temp2 = await this.$api.getTopMembers(this.rateFlag);
                this.lRateList = temp2;

                this.rateFlag.type = "1";
                let temp3 = await this.$api.getTopMembers(this.rateFlag);
                this.pRateList = temp3;

                this.rateFlag.type = "2";
                let temp4 = await this.$api.getTopMembers(this.rateFlag);
                this.fRateList = temp4;
            
            }
            catch(e){
            }
        },
        // async getPostRate(){
        //     try{
        //         this.rateFlag.type = "1";
        //         let temp3 = await this.$api.getTopMembers(this.rateFlag);
        //         this.pRateList = temp3;
                
        //     }catch(e){
        //     }
        // },
        // async getFollowerRate(){
        //     try{
        //         this.rateFlag.type = "2";
        //         let temp4 = await this.$api.getTopMembers(this.rateFlag);
        //         this.fRateList = temp4;
                               
        //     }catch(e){ }
        // },
        async deleteMember(item){
            console.log("deleteMember " + item.id);
            this.$api.groupRelationDelete({oid:this.$route.params.gid,uid:item.id})
    
            await this.getMembers();
            await this.getRatingInfo();
        },
        async updateGroup(){
            try{
                await this.$api.groupDetailUpdate({
                id:this.$route.params.gid,
                name:this.group_info.name,
                intro:this.group_info.intro
            })
            await this.getMembers();
            }
            catch(e)
            {
                console.log(e);
            }
            
            this.close();
        },
        async removeGroup()
        {
            try{
                await this.$api.deleteGroup(this.$route.params.gid)
                console.log("그룹 삭제 성공")
                this.$router.go(-1)
            }
            catch(e)
            {

            }
        },
        getRecentPost(recentPost){
            var ymd = recentPost.substr(0,10)
            var timestamp = recentPost.substr(11,8)
            return ymd + ' ' + timestamp;
        },
        close () {
            this.dialog = false
        },
        goUser(param)
        {
            console.log("GoUser()!!!" + param.id);
            this.$router.push({ name: 'MainPagefor', params: { uid: param.id } });
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