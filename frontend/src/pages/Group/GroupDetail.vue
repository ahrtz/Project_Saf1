<template>
  <div>
      <h2>그룹 상세정보 페이지</h2>
 
      
      <!-- 이거 둘다 모달로 해보자  -->
      <div class="text-center">
        <v-dialog
          v-model="dialog1"
          width="600"
        >
          <template v-slot:activator="{ on }">
            <v-btn
              dark
              v-on="on"
            >
              그룹 정보 수정
            </v-btn>
          </template>
      
          <v-card>
            <v-card-title
              class="headline grey lighten-2"
              primary-title
            >
              그룹 정보 수정
            </v-card-title>
            <v-row>
              <v-col cols="9">
                <v-text-field v-model="groupInfo.name"> </v-text-field>

              </v-col>
              <v-col cols="9">
                <v-text-field v-model="groupInfo.intro"> </v-text-field>

              </v-col>
            </v-row>


            <v-divider></v-divider>

            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn
                color="primary"
                text
                @click="dialog1 = false ; updategroup()"
              >
                수정하기
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </div>
      <!-- 이거 수정 -->
      <v-layout row >
        <v-dialog v-model="dialog" fullscreen hide-overlay transition="dialog-bottom-transition">
          <template v-slot:activator="{ on }">
            <v-btn color="primary" dark v-on="on">멤버 관리</v-btn>
          </template>
          <v-card>
            <v-toolbar dark color="primary">
              <v-btn icon dark @click="dialog = false">
                <v-icon>close</v-icon>
              </v-btn>
              <v-toolbar-title>멤버 관리</v-toolbar-title>
            </v-toolbar>
          
              <v-subheader>User Controls</v-subheader>
              <v-container fluid>
                <v-row>
                  <v-col cols="9">
                    <v-text-field v-model="newuser" type = "text" placeholder="추가할 사용자의 이메일을 입력하세요"></v-text-field>
                  </v-col>
                  <v-col cols="3">
                    <v-btn @click="addgroup">추가</v-btn>
                  </v-col>
                  <div v-for="member in this.groupInfo.userinfo" :key="member.email" >              
                    <v-col cols="6">
                      {{member}}
                      <v-btn @click="rejectuser(member.id)"> 삭제 </v-btn>
                    </v-col>
                  </div>
            
                </v-row>
            </v-container>

            
          </v-card>
        </v-dialog>
      </v-layout>


      <br>
      <br>
      <br>
      <div>
        {{groupInfo.intro}}
      </div>
      <br>
      <div>
        여기엔 등수를 3분할 쳐서 넣을거고
      </div>
      <br>
      <v-container fluid>
        <v-row>
          <v-col cols="6" v-for="member in groupInfo.userinfo" :key="member.id">
            <div>{{member}}</div>            
          </v-col>

        </v-row>
      </v-container>
      
  </div>
</template>

<script>
export default {
    name : 'GroupDetail',
    data(){
      return{
        newuser:'',
        dialog: false,
        dialog1: false,
        groupId: this.$route.params ,
        groupInfo:[]
}
    },
    async created(){
      let tmpspace = await this.$api.groupDetail(this.$route.params.gid)
      this.groupInfo = tmpspace
    },
    methods:{
      async addgroup(){
        await this.$api.groupRelationAdd({email:this.newuser,oid:this.$route.params.gid})
        this.newuser = ''
        
        let tmpspace = await this.$api.groupDetail(this.$route.params.gid)
        this.groupInfo = tmpspace
      },
      async rejectuser(userid){
        this.$api.groupRelationDelete({oid:this.$route.params.gid,uid:userid})

        let tmpspace = await this.$api.groupDetail(this.$route.params.gid)
        this.groupInfo = tmpspace
      },
      async updategroup(){
        this.$api.groupDetailUpdate({
          id:this.$route.params.gid,
          name:this.groupInfo.name,
          intro:this.groupInfo.intro
          })
        let tmpspace = await this.$api.groupDetail(this.$route.params.gid)
        this.groupInfo = tmpspace
      }
    }
}
</script>

<style>

</style>