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
              v-model="PostData.title"
              readonly />
              우선순위
              <v-rating
                v-model="PostData.rating"
                background-color="orange lighten-3"
                color="orange"
                readonly
                ></v-rating>
              <p v-show="PostData.commitList">커밋리스트</p>
              <!-- <v-text-field v-model="PostData.commitList" readonly/> -->
              <v-checkbox class="my-0" v-show="commitcheck == false" :label="commit.commitcontent" v-for="commit in PostData.commitList.slice(0,5)" :key="commit.cid" v-model="commit.checked" readonly/>
              <v-btn v-show="commitcheck==false" class="ma-2" tile color="indigo" dark @click="commitwide()">펼치기</v-btn>

              <v-checkbox  class="my-0" v-show="PostData.commitList && commitcheck == true" v-for="commit in PostData.commitList" :key="commit.cid" v-model="commit.checked" :label="commit.commitcontent" readonly></v-checkbox>
              <v-btn v-show='commitcheck==true' class="ma-2" tile color="indigo" dark @click="commitwide()">접기</v-btn>
              <br>
              내용
              <v-textarea filled v-model="PostData.content" readonly/>
          </v-container>
          <v-btn class="ma-2" tile color="grey" dark v-if="PostData.likechecked == false" @click="like()" >좋아요</v-btn>
          <v-btn class="ma-2" tile color="red" dark v-if="PostData.likechecked" @click="like()">좋아요 취소</v-btn>
          <v-btn class="ma-2" tile color="indigo" dark @click="scrap()">스크랩</v-btn>
          <v-btn class="ma-2" tile color="indigo" dark >공유</v-btn>

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


export default {
    name:'PostDetail',
    components:{PostSidebar, ContentSidebar},
    data(){
        return{
        commitcheck:false,
        commentData:
                {
                    content:''
                }
            ,
        PostData:{
            pid:0,
            title:'글 제목',
            content:'글 내용',
            likechecked:false,
            rating:4,

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
                ],

        },
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
    methods:{
        goback(){
            this.$router.go(-1)
        },
        like(){
            if (this.PostData.likechecked==true){
                this.PostData.likechecked=false
            }else{
                this.PostData.likechecked=true
            }
        },
        scrap(){
            alert(this.$route.path)
        },
        commitwide(){
            if (this.commitcheck==false){
                this.commitcheck = true
            }else{
                this.commitcheck = false
            }
        },
        commentwrite(){

        }
    },
    computed:{
        limited: function(){
            return this.PostData.commitList
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
