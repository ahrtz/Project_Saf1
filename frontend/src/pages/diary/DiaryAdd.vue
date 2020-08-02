<template>
  <div>
      <v-container>
          <v-row>
      <v-col cols="12">
      다이어리 : <v-text-field v-model="blogData.title" type = "text" placeholder="다이어리 이름을 입력하세요"></v-text-field>
      </v-col>
      <v-col cols="12" v-show="isProject">
      gitName : <v-text-field v-model="blogData.gitName" type = "text" placeholder="깃 레포지토리 이름을 입력하세요 "></v-text-field>
      </v-col>
      <v-col cols="12" v-show="isProject">
      gitUrl : <v-text-field v-model="blogData.gitUrl" type = "text" placeholder="깃 주소를 입력하세요 "></v-text-field>
      </v-col>
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
                v-model="blogData.sDate"
                label="시작날짜"
                prepend-icon="event"
                readonly
                v-bind="attrs"
                v-on="on"
            ></v-text-field>
            </template>
            <v-date-picker v-model="blogData.sDate" @input="blogData.menu2 = false"></v-date-picker>
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
                uid :"" ,// 회원 pk. 임시로 사용 
                title : null,
                intro : null,
                img : null, //얘는 이미지의 주소가 string 형태로 들어가는거겠지?
                gitUrl : null, //얘는 블로그에선 필요없는 요소. 맞지?
                gitName: null,
                isProj : 0, // 블로그는 프로젝트가 아니니까 무조건 0으로 해놓음.
                sDate: new Date().toISOString().substr(0, 10),
                eDate: new Date().toISOString().substr(0, 10),
                modal: false,
                menu2: false,
            },
        }
    },
    created(){
      this.blogData.uid= this.$store.state.user.id
    },
    computed:{
        
        isProject(){
            if (this.$route.path[7] =='p'){
                this.blogData.isProj=1
                return true
            }else{
                this.blogData.isProj=0
                return false
            }
            

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
            this.$router.push({name:'DiaryMain'})
        }catch(e){
            console.log('실패')
        }
        
    
    }
    }
}
</script>

<style>

</style>