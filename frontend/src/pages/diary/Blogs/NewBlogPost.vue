<template>
  <div>
      포스트 작성 페이지
    <v-form ref="form">
      제목
      <v-text-field
        v-model="post.title"
        label="Title"
        required
        outlined
      ></v-text-field>
      
      <v-container fluid>
          여기 들어오는 곳이 다이어리에서 온건지 프로젝트에서 온건지 구분해서 v-show 걸면 될것 
        <div v-for="commit in commitList" :key="commit.cid">
          <v-checkbox v-model="post.selected" :label="commit.commitcontent" :value="{'cid':commit.cid,'commit':commit.commitcontent}">
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
        post:{
          selected:[] ,
          title:'',
          content:'',
        },
        commitList:[
                {cid:0,
                commitcontent:'1번 커밋',
                checked:true
                },
                {cid:1,
                commitcontent:'2번 커밋',
                checked:true
                },
                {cid:2,
                commitcontent:'3번 커밋',
                checked:true
                },
                {cid:3,
                commitcontent:'4번 커밋',
                checked:true
                },
                {cid:4,
                commitcontent:'5번 커밋',
                checked:true
                },
                {cid:5,
                commitcontent:'6번 커밋',
                checked:true
                },
                ]
      }
    },
    methods:{
      clear(){
        this.$refs.form.reset()
      },
    }
}
</script>

<style>

</style>