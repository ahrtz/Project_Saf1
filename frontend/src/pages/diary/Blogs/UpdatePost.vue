<template>
  <div class="update-blog-post-container">
    <div class="d-flex flex-column update-blog-post-inner justify-center">
      <div class="d-flex">
        <div class="d-flex" />
        <div
          class="d-flex justify-center align-center flex-grow-0 update-blog-post-btn"
          @click="goback()"
        >뒤로가기</div>
      </div>
      <div class="d-flex flex-column" style="width:60%;align-self:center; ">
        <div>포스트 수정</div>
        <br />
        <br />제목
        <v-text-field v-model="post.title" required outlined></v-text-field>중요도
        <v-rating v-model="post.priority" background-color="orange lighten-3" color="orange"></v-rating>
        <div v-for="(scommit,index) in selectedCommits" :key="index">
          {{index}}{{scommit.msg}}<v-btn @click="commitDelete(scommit.id),index">삭제</v-btn>
        </div>

        <v-container fluid>

         <div :id="'t'+commit.msg" v-for="(commit,index) in commitList" :key="index">
            <p>
              <input
                type="checkbox"

                v-model="selected"
                :value="commit"
              />
              <label :for="commit">{{commit.msg}}</label>
            </p>
          </div>
        </v-container>내용
        <v-textarea v-model="post.content" label="content" required outlined></v-textarea>

        <h3>태그</h3>
        <div class="d-flex">
          <v-text-field label="태그를 추가" v-model="tag"></v-text-field>
          <div class="d-flex justify-center align-center flex-grow-0 update-blog-post-btn" @click="addtag()">
          태그추가</div>
        </div>
        <br />
        <!-- tags -->
        <!-- TODO : 오른쪽으로 나열하고 싶은데 잘 안됨 -->
        <div class="d-flex flex-row mb-6" flat tile v-for="tag in tags" :key="'t-'+tag.id">
          <div class="pa-2">
            <v-card
              color="grey lighten-4"
            >
              <a @click="searchTag(tag.name)">#{{tag.name}} &nbsp; </a>
              <button @click="deleteTag(tag.name)"> X </button>
            </v-card>
          </div>
        </div>

        <br />
        <div class="d-flex" style="margin-top: 72px">
          <div
            class="d-flex justify-center align-center flex-grow-0 update-blog-post-btn-white"
            @click="writetmpPost()"
          >임시저장</div>
          <div class="d-flex" />
          <div
            class="d-flex justify-center align-center flex-grow-0 update-blog-post-btn-white"
            @click="clear()"
            style="margin-right: 8px;"
          >초기화</div>
          <div class="d-flex justify-center align-center flex-grow-0 update-blog-post-btn" @click="writePost()">작성</div>
        </div>
      </div>
    </div>
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
        newtag:{
          pid:'',
          name:''
        },
        tags:[],
        pid:this.$route.params.pid,
        selected:[] ,
        selectedCommits:[],
        post:{
          uid:'',
          did:this.$route.params.did,
          title:'',
          content:'',
          priority:0,
          cnt_like:0,
          is_temp:0,
          cDate:new Date().toISOString().substr(0, 10)
        },
        commitList:[
                ],
        isProj:false
      }
    },
    props:['value'],
    async created(){
      this.post.uid= this.$store.state.user.id
      try{
            let tmpspace = await this.$api.postdetail(this.pid)
            this.post =tmpspace
            console.log('성공')

            try{
              let tempspace1= await this.$api.individualDiary(this.post.did)
              if (tempspace1.gitName.length>0){
              console.log(tempspace1,'vdsa')
              this.isProj=true
              try{
                let listCommit = await this.$api.getCommitList({repoName:tempspace1.gitName})
                this.commitList=listCommit
              }
              catch(e){
                console.log('커밋 받아오기  에러')
              }}
            }catch(e){
              console.log('깃네임 받아오기 에러')
            }
        }catch(e){
          console.log(e)
        }
      try{
        let selectedCommit = await this.$api.getPostCommit(this.pid)
        this.selectedCommits = selectedCommit
        console.log('선택한 커밋 불러오기')
      }catch(e){
      }
      //tags 가져오기
      try{
          let tmpspace3= await this.$api.tagIndex(this.pid)
          this.tags = tmpspace3;
        }catch(e){
          console.log(e)
      }
      // console.log(this.tags);
    },
    methods:{
      clear(){
        this.$refs.form.reset()
      },
      searchTag(tagName){
        document.getElementById('header-text').value=tagName;
        this.$router.push({name: 'tmp',params:{key:tagName}})
      },
      goback(){
            this.$router.go(-1)
        },
      async addtag(){
        if(this.tag == '' || this.tag == null){
          alert('값이 입력되지 않았습니다');
          return;
        }
        for(var i=0;i<this.tags.length;i++){
          if(this.tags[i].name.toLowerCase() == this.tag.toLowerCase()){
            alert('중복된 태그 입니다');
            return;
          }
        }
        this.newtag.pid = this.pid;
        this.newtag.name = this.tag;
        try{
          await this.$api.createTag(this.newtag)
        }catch(e){
          console.log(e)
        }
        this.tag = '';
        //tags 가져오기
        try{
            let tmpspace3= await this.$api.tagIndex(this.pid)
            this.tags = tmpspace3;
        }catch(e){
          console.log(e)
        }
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
          this.post.isTemp=0
          this.$api.updatePost(this.post)
          console.log('성공11')
          this.$router.go(-1)
        }catch(e){
          console.log(e)
          console.log('실패')
        }
      },
      async writetmpPost(){
        this.post.isTemp=1

        try {
           await this.$api.savePost(this.post);
            try{
                if (this.selected.length !=0){
                  for (var i=0;i<this.selected.length;i++){
                    this.selected[i].uid = this.posts.uid
                    this.selected[i].pid = this.pid
                    this.selected[i].sha=this.selected[i].sha1
                    this.selected[i].date=this.selected[i].date.substr(0,10)
                    delete this.selected[i].sha1
                    await this.$api.addCommit(this.selected[i])
                    console.log('성공',i)
                    }
                    }
                    this.$router.go(-1);
                    console.log(this.selected)
                }catch(e){
                console.log(e)
                }

          } catch (e) {
            console.log(e);
            console.log('실패');
          }
      },
      commitDelete(id,index){
        this.$api.deleteCommit(id)
        this.selectedCommits.splice(index,1)
      }
    },
}
</script>

<style>
.update-blog-post-container {
  width: 100%;
}

.update-blog-post-inner {
  padding-top: 77px;
  width: 1140px;
  margin: 0 auto;
}

.update-blog-post-btn {
  padding: 0 16px;
  margin-bottom: 8px;
  font-size: 14px;
  background: #0051cb;
  font-weight: 600;
  color: #fff;
  border-radius: 6px;
  height: 40px;
  cursor: pointer;
}

.update-blog-post-btn-white {
  padding: 0 16px;
  font-size: 14px;
  background: #fff;
  font-weight: 600;
  color: #0051cb;
  border: solid 1px #0051cb;
  border-radius: 6px;
  height: 40px;
  cursor: pointer;
}
</style>
