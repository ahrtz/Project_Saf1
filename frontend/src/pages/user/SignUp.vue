<template>
  <div>
      <h2>회원가입 페이지</h2>
      <v-container>
        <v-row>
          <v-col cols="12">
            id*
            <v-text-field v-model="signupData.email" label="Email 형식으로 입력하세요" required></v-text-field>
          </v-col>
          <v-col cols="12">
            password*
            <v-text-field v-model="signupData.pwd" label="비밀번호" type="password" required></v-text-field>
          </v-col>
          <v-col cols="12">
            password 확인*
            <v-text-field v-model="signupData.pwdconfirm" label="비밀번호 확인" type="password" 
            :rules='[]' required></v-text-field>
          </v-col>
          <v-col cols="12">
            닉네임*
            <v-text-field v-model="signupData.nickname" label="닉네임"  required></v-text-field>
          </v-col>
          <v-col cols="12">
            대표 이미지
            <v-file-input ref="file" label="imagefile" prepend-icon="mdi-camera" ></v-file-input>
          </v-col>
          <v-col cols="12">
            git id
            <v-text-field v-model="signupData.git_id" label="git id" ></v-text-field>
          </v-col>
          <v-col cols="6">
            git url
            <v-text-field v-model="signupData.git_url" label="git url"></v-text-field>
          </v-col>
          <v-col cols="6">
            git token
            <v-text-field v-model="signupData.git_token" label="git token"></v-text-field>
          </v-col>
          <v-col cols="12">
            자기소개
            <v-textarea v-model="signupData.intro" label="intro"></v-textarea>
          </v-col>
        </v-row>
        <v-btn class="mr-4 float-right" color="indigo" dark @click="signup()">회원가입</v-btn>
        <v-btn class="mr-4 float-right" color="indigo" dark @click="goback()">취소</v-btn>
      </v-container>
  </div>
</template>

<script>
axios.defaults.headers.common['Access-Control-Allow-Origin'] = '*';
import axios from 'axios'
export default {
    name:'SignUp',
    data(){
      return{
        signupData:{
          email:'',
          pwd:'',
          pwdconfirm:'',
          img:"",
          nickname:'',
          git_id:'',
          git_url:'',
          git_token:'',
          intro:'',
          is_social:'0',
          is_certified:'0'
        }
      }
    },
    methods:{
      goback(){
            this.$router.go(-1)
        },
      async signup(){
          if(this.signupData.pwd != this.signupData.pwdconfirm){
            this.signupData.pwdconfirm=''
            console.log('dnajk')
            alert('비밀번호가 다름')}
            else{

              try{
                this.$api.signupp(this.signupData,{headers:{'Content-Type':'application/json'}})
                console.log('성공')
                this.$router.push({name:'Login'})

              }catch(e){
                console.log(e)
                console.log('실패')
              }

        }},
        checkpwd(){
          if(this.pwd != this.pwdconfirm){
            this.pwdconfirm=''
            console.log('dnajk')
            alert('비밀번호가 다름')
          }
        }
    },
    
    
}
</script>

<style>

</style>