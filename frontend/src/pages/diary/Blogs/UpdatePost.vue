<template>
  <div>
    <v-btn class="ma-2 float-right" tile color="indigo" dark @click="goback()">뒤로가기</v-btn>
      포스트 수정 페이지
    <v-form ref="form">
      제목
      <v-text-field
        v-model="post.title"
        label="Title"
        required
        outlined
      ></v-text-field>
      중요도
      <v-rating
      v-model="post.priority"
      background-color="orange lighten-3"
      color="orange"
    ></v-rating>
      <v-container v-show="isProj" fluid>
          여기 들어오는 곳이 다이어리에서 온건지 프로젝트에서 온건지 구분해서 v-show 걸면 될것
        <div :id="'t'+commit.cid" v-for="(commit,key,index) in commitList" :key="index">
          
          <p>
            <input type="checkbox" :id="key" v-model="selected" :value="{'cid':commit.cid,'commit':commit.commitcontent}"  >
            <label :for="key"> {{commit.commitcontent}}</label>
          </p>
        </div>
        {{post.selected}}
      </v-container>
      내용
      <v-textarea
        v-model="post.content"
        label="content"
        required
        outlined
      ></v-textarea>

      <h2>태그를 넣을 것</h2>
      <v-text-field label="태그를 검색 혹은 추가" v-model="tag"></v-text-field>
      <v-btn class="mr-4" @click="addtag()">태그추가</v-btn>
      <br>
      {{tags}}
      <br>
      <v-btn class="mr-4" @click="writePost()" >작성</v-btn>
      <v-btn class="mr-4" @click="writetmpPost()">임시저장</v-btn>
      <v-btn @click="clear()" >초기화</v-btn>
    </v-form>
  </div>
</template>

<script>
import axios from 'axios'
axios.defaults.headers.common['Access-Control-Allow-Origin'] = '*';
export default {
    name:'UpdatePost',
    data(){
      return{
        tag:'',
        tags:[],
        pid:this.$route.params.pid,
        selected:[] ,
        post:{
          uid:'',
          did:this.$route.params.did,
          title:'',
          content:'',
          priority:0,
          cnt_like:0,
          is_temp:0,
          created_date:new Date().toISOString().substr(0, 10)
        },
        commitList:[
                {cid:0,
                commitcontent:'1번 커밋',
                checked:false
                },
                {cid:1,
                commitcontent:'2번 커밋',
                checked:false
                },
                {cid:2,
                commitcontent:'3번 커밋',
                checked:false
                },
                {cid:3,
                commitcontent:'4번 커밋',
                checked:false
                },
                {cid:4,
                commitcontent:'5번 커밋',
                checked:false
                },
                {cid:5,
                commitcontent:'6번 커밋',
                checked:false
                },
                ]
      }
    },
    props:['value'],
    async created(){
      this.post.uid= this.$store.state.user.id
      try{
            let tmpspace = await this.$api.postdetail(this.pid)
            this.post =tmpspace
            console.log('성공')
        }catch(e){
            console.log(e)
        }
    },
    methods:{
      clear(){
        this.$refs.form.reset()
      },
      goback(){
            this.$router.go(-1)
        },
      addtag(){
        const tmp = '#'+this.tag
        this.tags.push(tmp)
        this.tag=''
      },
      addcommit(commit){
        this.post.selected.push(commit)
      },
      toggle(){
        this.$emit('input', !this.value);
        console.log(this.post);
      },
      async writePost(){
        try{
            this.post.is_temp=0
            
          this.$api.updatePost(this.post)
          console.log('성공11')
          this.$router.go(-1)
        }catch(e){
          console.log(e)
          console.log('실패')
        }


      },
      writetmpPost(){
        this.post.is_temp=1
        
        try{
          this.$api.updatePost(this.post)
          console.log('성공')
          this.$router.go(-1)
        }catch(e){
          console.log(e)
          console.log('실패')
        }

        
      }
    },
    computed:{
      userid(){
        
      },
      isProj(){
        if (this.$route.path[7]=='p'){
          return true
        }else{
          return false
        }
      }
    }
}
</script>

<style>

</style>
