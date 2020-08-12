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
        <v-layout row v-show="this.isProj==true">
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
          <v-text-field label="태그를 추가" v-model="tag" @keyup.enter="addtag()" @keyup.space="addtag()"></v-text-field>
          <div class="d-flex justify-center align-center flex-grow-0 update-blog-post-btn"  @click="addtag()">
          태그추가</div>
        </div>
        <br />
        <!-- tags -->
        <!-- TODO : 오른쪽으로 나열하고 싶은데 잘 안됨 -->
        <div class="d-flex flex-row mb-6" flat tile v-for="(tag,index) in originaltag" :key="'t-1'+index">
          <div class="pa-2">
            <v-card
              color="grey lighten-4"
            >
              <a >#{{tag}} &nbsp; </a>
              <button @click="tagdelete(tag.id)"> X </button>
            </v-card>
          </div>
        </div>
<!-- 새태그 넣기 -->
        <div class="d-flex flex-row mb-6" flat tile v-for="(tag,index) in tags" :key="'t-'+index">
          <div class="pa-2">
            <v-card
              color="grey lighten-4"
            >
              <a >#{{tag}} &nbsp; </a>
              <button @click="removetag(tag)"> X </button>
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
    <v-card height="500px">
      <v-container fill-height>
        <v-layout
          
        >
          <v-btn
            color="blue"
            dark
            @click.stop="drawer = !drawer"
          >
            임시저장글 보기
          </v-btn>
        </v-layout>
      </v-container>

      <v-navigation-drawer
        v-model="drawer"
        absolute
        temporary
        right
      >
        <!-- 여따가 시작 -->
        <div v-for="temp in tmppost" :key="temp.id">
          {{temp.dName}}다이어리 <br>
          제목 : {{temp.title}} 

          <v-btn @click="$router.push({name:'UpdatePost',params:{pid:temp.id}})" > 수정 </v-btn>
        </div>
        <v-divider></v-divider>

        
      </v-navigation-drawer>
    </v-card>
  </div>
</template>

<script>
import axios from 'axios'
axios.defaults.headers.common['Access-Control-Allow-Origin'] = '*';
export default {
    name:'UpdatePost',
    data(){
      return{
        drawer:null,
        dialog:false,
        tag:'',
        newtag:{
          pid:'',
          name:''
        },
        tags:[],
        originaltag:{},
        pid:this.$route.params.pid,
        selected:[] ,
        selectedCommits:[],
        page:1,
        post:{
          uid:'',
          did:this.$route.params.did,
          title:'',
          content:'',
          priority:0,
          cntLike:0,
          isTemp:0,
          cDate:new Date().toISOString().substr(0, 10)
        },
        commitList:[
                ],
        config: {
        uid: '',
        isProj: 2,
        keyword: '',
        isTemp: 1,
        limit:10
      },
        isProj:false,
        tmppost:[],
      }
    },
    props:['value'],
    async created(){
      this.post.uid= this.$store.state.user.id
      this.config.uid = this.$store.state.user.id;
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
          this.originaltag = tmpspace3;
        }catch(e){
          console.log(e)
      }
      try {
      let tmpspace = await this.$api.searchTemp(this.config);
      this.tmppost = tmpspace.data;
      console.log('성공');
    } catch (e) {
      console.log(e);
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
      removetag(data){
      let index = this.tags.indexOf(data)
      this.tags.splice(index,1)
      },
      async tagdelete(id){
        this.$api.deleteTag(id)
        let tmpspace3= await this.$api.tagIndex(this.pid)
        this.originaltag = tmpspace3;
      }
      ,
      addtag(){
        if(this.tag == '' || this.tag == null){
          alert('값이 입력되지 않았습니다');
          
        }
        else if(this.tags.length!=0){
            for(var i=0;i<this.tags.length;i++){
              if(this.tags[i].toLowerCase() == this.tag.toLowerCase()){
                alert('중복된 태그 입니다');
                this.tag = '';
                return
                
          
              }
            }
        }
        else if (this.originaltag.length!=0){
          for(var i=0;i<this.originaltag.length;i++){
            if(this.originaltag[i].name.toLowerCase()==this.tag.toLowerCase()){
              alert('중복된 태그 입니다');
                this.tag = '';
                return
            }
          }
        }
          this.tags.push(this.tag)
          this.tag = '';
        
        
        //tags 가져오기
        
      },



      addcommit(commit){
        this.post.selected.push(commit)
      },

      async writePost(){
        try{
          this.post.isTemp=0
          await this.$api.updatePost(this.post)
          console.log('성공11')
          try{
                if (this.selected.length !=0){
                  for (var i=0;i<this.selected.length;i++){
                    // this.selected[i].uid = this.post.id
                    this.selected[i].pid = this.pid
                    this.selected[i].sha=this.selected[i].sha1
                    this.selected[i].date=this.selected[i].date.substr(0,10)
                    delete this.selected[i].sha1
                    await this.$api.addCommit(this.selected[i])
                    console.log(this.selected[i],'부악')
                    console.log('성공',i)
                    }
                    }
                if (this.tags.length!=0){
                  for(var i=0;i<this.tags.length;i++){
                    this.newtag.pid=this.pid
                    this.newtag.name=this.tags[i]
                    await this.$api.createTag(this.newtag)
                  }
                }
                    this.$router.push({name : 'PostDetail', params :{uid:this.config.uid,pid:this.pid}})
                    console.log(this.selected)
                }catch(e){
                console.log(e)
                }
        }catch(e){
          console.log(e)
          console.log('실패')
        }
      },
      async writetmpPost(){
        this.post.isTemp=1

        try {
           await this.$api.updatePost(this.post);
            try{
                if (this.selected.length !=0){
                  for (var i=0;i<this.selected.length;i++){
                    // this.selected[i].uid = this.post.id
                    this.selected[i].pid = this.pid
                    this.selected[i].sha=this.selected[i].sha1
                    this.selected[i].date=this.selected[i].date.substr(0,10)
                    delete this.selected[i].sha1
                    await this.$api.addCommit(this.selected[i])
                    console.log('성공',i)
                    }
                    }
                    this.$router.push({name : 'PostDetail', params :{uid:this.config.uid,pid:this.pid}})
                    // this.$router.go(-1);
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
  padding-bottom: 70px;
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
