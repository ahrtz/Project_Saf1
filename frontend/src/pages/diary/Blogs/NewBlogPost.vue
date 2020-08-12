<template>
  <div class="new-blog-post-container">
    <div class="d-flex flex-column new-blog-post-inner justify-center">
      <div class="d-flex">
        <div class="d-flex" />
        <div
          class="d-flex justify-center align-center flex-grow-0 new-blog-post-btn"
          @click="goback()"
        >뒤로가기</div>
      </div>
      <div class="d-flex flex-column" style="width: 800px;align-self:center">
        <div>포스트 작성</div>
        <br />
        <br />제목
        <v-text-field v-model="post.title" required outlined></v-text-field>중요도
        <v-rating v-model="post.priority" background-color="orange lighten-3" color="orange"></v-rating>
        
<!-- 여기서 부터 모달 커밋 -->



      <v-layout row v-show="this.diarys.isProj==1">
        <v-dialog v-model="dialog" scrollable max-width="500px">
        <template v-slot:activator="{ on }">
            <v-btn color="primary" dark v-on="on">커밋 선택하기</v-btn>
        </template>

            <v-card
                color="primary"
                dark
                v-if="commitList.length==0"
            >
                <v-card-text>
                commit 정보 받아 오는중
                <v-progress-linear
                    indeterminate
                    color="white"
                    class="mb-0"
                    buffer-value="0"
                    stream
                ></v-progress-linear>
                </v-card-text>
            </v-card>



            <v-card v-if="commitList.length!=0">
                <v-card-title>커밋 고르기</v-card-title>
                <v-divider></v-divider>
                <v-card-text>
                <div :id="'t'+commit.msg" v-for="(commit,index) in commitList.slice((this.page-1)*10,(this.page*10))" :key="index">
                  
                    
                    <input
                      type="checkbox"
                      
                      v-model="selected"
                      :value="commit"
                    />
                    <label :for="commit">{{commit.msg}}</label>
                  
                </div>
                </v-card-text>
                <div class="text-center">
                    <v-pagination
                      v-model="page"
                      :length="Math.ceil(commitList.length/10)"
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









      
        내용
        <v-textarea v-model="post.content" label="content" required outlined></v-textarea>

        <h3>태그</h3>
        <div class="d-flex">
        <v-text-field label="태그를 검색 혹은 추가" v-model="tag" @keyup.enter="addtag()" @keyup.space="addtag()"></v-text-field>
        <div class="d-flex justify-center align-center flex-grow-0 new-blog-post-btn" @click="addtag()">태그추가</div>
        </div>
        <br />
        {{tags}}
        <br />
        <div class="d-flex" style="margin-top: 72px">
          <div
            class="d-flex justify-center align-center flex-grow-0 new-blog-post-btn-white"
            @click="writetmpPost()"
          >임시저장</div>
          <div class="d-flex" />
          <div
            class="d-flex justify-center align-center flex-grow-0 new-blog-post-btn-white"
            @click="clear()"
            style="margin-right: 8px;"
          >초기화</div>
          <div class="d-flex justify-center align-center flex-grow-0 new-blog-post-btn" @click="writePost()">작성</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
axios.defaults.headers.common['Access-Control-Allow-Origin'] = '*';
export default {
  name: 'NewBlogPost',
  data() {
    return {
      dialog:false,
      diarys:'',
      tag: '',
      tags: [],
      did: this.$route.params.did,
      selected: [],
      page:1,
      post: {
        uid: '',
        did: this.$route.params.did,
        title: '',
        content: '',
        priority: 0,
        cntLike: 0,
        isTemp: 0,
        cDate: new Date().toISOString().substr(0, 10),
      },
      commitList: [],

    
    
    };
  },
  props: ['value'],
  async created() {
    this.post.uid = this.$store.state.user.id;
    try{
        let diaryid = this.did
        let tempspace= await this.$api.individualDiary(diaryid)
        this.diarys = tempspace
        console.log('아이디 받음')
        if (tempspace.isProj){
          try{
            let git= this.diarys.gitName
            let listCommit = await this.$api.getCommitList({repoName:git})
            this.commitList=listCommit
            console.log('커밋 받음')
          }catch(e){
            console.log('커밋쪽 에러')
          }
        }


        }catch(e){
            console.log(e)
        }
  },
  methods: {
    clear() {
      this.$refs.form.reset();
    },
    goback() {
      this.$router.go(-1);
    },
    addtag() {
      const tmp = '#' + this.tag;
      this.tags.push(tmp);
      this.tag = '';
    },
    addcommit(commit) {
      this.post.selected.push(commit);
    },
    toggle() {
      this.$emit('input', !this.value);
      console.log(this.post);
    },
    async writePost() {
      try {
        let post = await this.$api.savePost(this.post);
        console.log(post.data,'vdas');
        try{
        if (this.selected.length !=0){
          for (var i=0;i<this.selected.length;i++){
            this.selected[i].uid = this.diarys.uid
            this.selected[i].pid = post.data
            this.selected[i].sha=this.selected[i].sha1
            this.selected[i].date=this.selected[i].date
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
    async writetmpPost() {
      this.post.isTemp = 1;

      try {
        let post = await this.$api.savePost(this.post);
        console.log(post.data,'vdas');
        try{
        if (this.selected.length !=0){
          for (var i=0;i<this.selected.length;i++){
            this.selected[i].uid = this.diarys.uid
            this.selected[i].pid = post.data
            this.selected[i].sha=this.selected[i].sha1
            this.selected[i].date=this.selected[i].date
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
  },
  // watch:{
  //   commitList: tempcommit()
  // },
  
  computed: {
        
    userid() {},
    isProj() {
      if (this.$route.path[7] == 'p') {
        return true;
      } else {
        return false;
      }
    
    },
  },
};
</script>

<style>
.new-blog-post-container {
  width: 100%;
}

.new-blog-post-inner {
  padding-bottom: 70px;
  width: 1140px;
  margin: 0 auto;
}

.new-blog-post-btn {
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

.new-blog-post-btn-white {
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
