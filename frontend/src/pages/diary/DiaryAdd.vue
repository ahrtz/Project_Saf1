<template>
  <div>
      <v-container>
          <v-row>
      <v-col cols="12">
      다이어리 : <v-text-field v-model="blogData.title" type = "text" placeholder="다이어리 이름을 입력하세요"></v-text-field>
      </v-col>
      <!-- <v-col cols="12" v-show="isProject">
      gitName : <v-text-field v-model="blogData.gitName" type = "text" placeholder="깃 레포지토리 이름을 입력하세요 "></v-text-field>
      </v-col>
      <v-col cols="12" v-show="isProject">
      gitUrl : <v-text-field v-model="blogData.gitUrl" type = "text" placeholder="깃 주소를 입력하세요 "></v-text-field>
      </v-col> -->

        <v-layout row v-if="this.$route.path[7] =='p'">
        <v-dialog v-model="dialog" scrollable max-width="300px">
        <template v-slot:activator="{ on }">
            <v-btn color="primary" dark v-on="on">repository 선택하기</v-btn>
        </template>
            <v-card
                color="primary"
                dark
            v-if="repoData.length==0"
            >
                <v-card-text>
                repository 정보 받아 오는중
                <v-progress-linear
                    indeterminate
                    color="white"
                    class="mb-0"
                    buffer-value="0"
                    stream
                ></v-progress-linear>
                </v-card-text>
            </v-card>


            <v-card v-if="repoData.length!=0">
                <v-card-title>레포 선택</v-card-title>
                <v-divider></v-divider>
                
                
                

                <v-card-text style="height: 220px;">
                    <v-radio-group v-model="repoChecked" column>
                        <v-radio v-for="repo in repoData.slice((this.page-1)*5,(this.page)*5)" :v-if="repoData!=null" :key="repo.url" :label="repo.repoName" :value="repo"></v-radio>
                        
                    </v-radio-group>
                </v-card-text>
                <div class="text-center">
                    <v-pagination
                      v-model="page"
                      :length="Math.ceil(repoData.length / 5)"
                      :total-visible="7"
                    ></v-pagination>
                </div>
                <v-divider></v-divider>
                    <v-card-actions>
                    <v-btn color="blue darken-1" text @click="dialog = false">Save</v-btn>
                    </v-card-actions>
                </v-card>
            </v-dialog>
        </v-layout>
     
        <v-text-field  readonly v-model="repoChecked.repoName" ></v-text-field>
      <v-col cols="12">
            대표 이미지
            <v-file-input ref="file" label="imagefile" prepend-icon="mdi-camera" ></v-file-input>
      </v-col>
      <v-col cols="6">
        <v-menu
            v-model="blogData.menu2"
            :close-on-content-click="false"
            :nudge-right="40"
            transition="scale-transition"
            offset-y
            min-width="290px"
        >
            <template v-slot:activator="{ on, attrs }">
            <v-text-field
                v-model="blogData.sdate"
                label="시작날짜"
                prepend-icon="event"
                readonly
                v-bind="attrs"
                v-on="on"
            ></v-text-field>
            </template>
            <v-date-picker v-model="blogData.sdate" @input="blogData.menu2 = false"></v-date-picker>
        </v-menu>
        </v-col>
      
        <v-col cols="12">
            간단 설명
            <v-textarea v-model="blogData.intro" label="intro"></v-textarea>
        </v-col>
          </v-row>
      </v-container>
      <div style="float:right">
          <v-btn class="mr-4 float-right" color="indigo" dark  @click="goback()">취소</v-btn>
          <v-btn class="mr-4 float-right" color="indigo" dark @click="AddProj()">작성완료</v-btn>

          
      </div>

  </div>
</template>

<script>

import axios from 'axios'
axios.defaults.headers.common['Access-Control-Allow-Origin'] = '*';
export default {
    name : 'BlogAdd',
    data(){
        return {
            config:{
            },
            blogData:{
                uid :"" ,
                title : null,
                intro : null,
                img : null, //얘는 이미지의 주소가 string 형태로 들어가는거겠지?
                gitUrl : null, //얘는 블로그에선 필요없는 요소. 맞지?
                gitName: null,
                isProj : 0, // 블로그는 프로젝트가 아니니까 무조건 0으로 해놓음.
                sdate: new Date().toISOString().substr(0, 10),
                edate: new Date().toISOString().substr(0, 10),
                modal: false,
                menu2: false,
            },
            repoData:[],
            repoChecked:{
                
            },
            page:1,
            dialog:false
        }
    },
    watch:{
        repoChecked(){
            this.blogData.gitUrl = this.repoChecked.url
            this.blogData.gitName = this.repoChecked.repoName
        }
    },
    async created(){
      this.blogData.uid= this.$store.state.user.id

        if (this.$route.path[7] =='p'){
            this.blogData.isProj=1
        
            try{
                let tmpspace = await this.$api.getRepoData()
                console.log(tmpspace)
                this.repoData = tmpspace
            }catch(e){
                console.log(e)
            }
        
            return true
        }else{
            this.blogData.isProj=0
            return false
        }
        
    },
    computed:{
        
        isProject(){
            

        },

        
    },
    methods:{
    
    goback(){
            this.$router.go(-1)
        },
    async AddProj(){
        try{
            await this.$api.AddProject(this.blogData)
            console.log('성공')
            this.$router.push({name:'DiaryMain',params:{uid:this.blogData.uid,test:2}})
        }catch(e){
            console.log('실패')
        }
        
    
    }
    }
}
</script>

<style >


</style>

