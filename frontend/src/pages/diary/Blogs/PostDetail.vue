<template>
  <div>
      <h1>포스트 별로 디테일한 정보</h1>
      <section>
        <nav>
          <PostSidebar />
        </nav>
        <article>
          <v-btn class="ma-2" tile color="indigo" dark @click="goback()">목록으로</v-btn>
          <v-btn class="ma-2 float-right" tile color="indigo" dark >수정하기</v-btn>
          <v-container fluid>
              제목
              <v-text-field
              filled
              v-model="tmp.title"
              readonly />
              우선순위
              <v-rating
                v-model="tmp.priority"
                background-color="orange lighten-3"
                color="orange"
                readonly
                ></v-rating>
              <div v-show="checkcommit">
                <p v-show="checkcommit">커밋리스트</p>
                
                <v-checkbox class="my-0" v-show="commitcheck == false" :label="commit.commitcontent" v-for="commit in commitList.slice(0,5)" :key="commit.cid" v-model="commit.checked" readonly/>
                <v-btn v-show="commitcheck==false" class="ma-2" tile color="indigo" dark @click="commitwide()">펼치기</v-btn>

                <v-checkbox  class="my-0" v-show="commitList && commitcheck == true" v-for="commit in commitList" :key="commit.cid" v-model="commit.checked" :label="commit.commitcontent" readonly></v-checkbox>
                <v-btn v-show='commitcheck==true' class="ma-2" tile color="indigo" dark @click="commitwide()">접기</v-btn>
                <br>
              </div>
              내용
              <v-textarea filled v-model="tmp.content" readonly/>
          </v-container>
          <v-btn class="ma-2" tile color="grey" dark v-if="likeData.likechecked == false" @click="like()" >좋아요</v-btn>
          좋아요 숫자 {{tmp.cntLike}}
          <v-btn class="ma-2" tile color="red" dark v-if="likeData.likechecked" @click="like()">좋아요 취소</v-btn>
          <v-btn class="ma-2" tile color="indigo" dark >스크랩</v-btn>
          <v-btn class="ma-2" tile color="indigo" dark @click="grapurl()" >공유</v-btn>

          <br>
          댓글

          <div v-for="comment in comments" :key="comment.cid">

              <p class="my-0 text-end">작성자 : {{comment.user}} </p>
              <v-text-field
              filled
              v-model="comment.commentcontent"
              readonly />
          </div>
          <div>
          댓글 작성
          <v-text-field
          filled
          v-model="commentData.content"
          />
          <v-btn class="ma-2" tile color="indigo" dark >댓글 작성</v-btn>
          </div>
        </article>
        <aside>
          <ContentSidebar/>
        </aside>
      </section>

  </div>
</template>

<script>
import PostSidebar from '../../../component/PostSidebar.vue'
import ContentSidebar from '../../../component/ContentSidebar.vue'
import axios from 'axios'

export default {
    name:'PostDetail',
    components:{PostSidebar, ContentSidebar},
    data(){
        return{
        uid:null,
        id:this.$route.params,//pid임
        commitcheck:false,
        commentData:
                {
                    content:''
                }
            ,
        tmp:{},
        likeData:{
            likechecked:false
        },
        commitList:[],
        comments:[
        {
        cid:0,
        commentcontent:'1번 댓글',
        user:'1번'
        },
        {
        cid:1,
        commentcontent:'2번 댓글',
        user:'2번'
        },
        {
        cid:2,
        commentcontent:'3번 댓글',
        user:'1번'
        },
    ]
}
    },
    created(){
        this.uid=this.$store.state.user.id
        
        axios.get('http://i3a110.p.ssafy.io:3000/posts/'+this.id.pid)
        .then(res=>{console.log(res)
        this.tmp = res.data
        })
        .catch(err => console.log(err))

        axios.get('http://i3a110.p.ssafy.io:3000/likes/'+this.id.pid)
        .then(res=>{console.log('성공')
        console.log(res.data)})
    }
    ,
    methods:{
        goback(){
            this.$router.go(-1)
        },
        like(){
            if (this.likeData.likechecked==true){
                this.likeData.likechecked=false
            }else{
                this.likeData.likechecked=true
            }
        },

        grapurl(){             
            alert(this.$route.path)
        },
        commitwide(){
            if (this.commitcheck==false){
                this.commitcheck = true
            }else{
                this.commitcheck = false
            }
        },
        scrap(){
            // axios.post('http://localhost:3000/scraps',{
            //     uid:this.uid,
            //     pid:this.id.pid,
            //     publisher:this.tmp.uid,
            //     status:1})
            //     .then(alert())
        },
        commentwrite(){

        }
    },
    computed:{
        limited: function(){
            return this.commitList
        },
        checkcommit(){
            if (this.commitList.length==0){
                return false
            }else{
                return true
            }
        }
    }
}
</script>

<style>

nav{
  float: left;
  width: 20%;
  height: 100%;
  background: #ccc;
  padding: 20px;
}
aside{
  float: left;
  width: 20%;
  height: 100%;
  background: #ccc;
  padding: 20px;
}
article {
  float: left;
  padding: 20px;
  width: 60%;
  background-color: #f1f1f1;
  height: 100%;
}
</style>
