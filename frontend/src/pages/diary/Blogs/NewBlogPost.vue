<template>
  <div>
    <v-btn class="ma-2 float-right" tile color="indigo" dark @click="goback()">뒤로가기</v-btn>
      포스트 작성 페이지
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
      v-model="post.rating"
      background-color="orange lighten-3"
      color="orange"
    ></v-rating>
      <v-container fluid>
          여기 들어오는 곳이 다이어리에서 온건지 프로젝트에서 온건지 구분해서 v-show 걸면 될것 
        <div :id="'t'+commit.cid" v-for="commit in commitList" :key="commit.cid">
          <!-- {{commit.commitcontent}} 
             <v-btn class="mr-4" @click="addcommit(commit)">추가</v-btn> -->
          <v-checkbox  v-model="post.selected" :label="commit.commitcontent" :value="{'cid':commit.cid,'commit':commit.commitcontent}">
          </v-checkbox>
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
      {{post.tag}}
      <br>
      <v-btn class="mr-4" >작성</v-btn>
      <v-btn class="mr-4">임시저장</v-btn>
      <v-btn @click="clear()" >초기화</v-btn>
    </v-form>
  </div>
</template>

<script>
export default {
    name:'NewBlogPost',
    data(){
      return{
        tag:'',
        post:{
          selected:[] ,
          title:'',
          content:'',
          rating:0,
          tag:[],
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
    methods:{
      clear(){
        this.$refs.form.reset()
      },
      goback(){
            this.$router.go(-1)
        },
      addtag(){
        const tmp = '#'+this.tag
        this.post.tag.push(tmp)
        this.tag=''
      },
      addcommit(commit){
        this.post.selected.push(commit)
      },
      errlog(){
        console.log()
      }
    }
}
</script>

<style>

</style>