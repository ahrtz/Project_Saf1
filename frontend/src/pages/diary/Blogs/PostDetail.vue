<template>
  <div>
      <section>
        <nav>
          <PostSidebar />
        </nav>
        <article>
          <v-btn class="ma-2" tile color="indigo" dark @click="goback()">목록으로</v-btn>
          <v-btn class="ma-2 float-right" tile color="indigo" dark v-show="this.uid==this.tmp.uid" @click="$router.push({name:'UpdatePost',params:{pid:tmp.id}})">수정하기</v-btn>
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
                <v-checkbox class="my-0" v-show="commitcheck == false" :label="commit.msg" v-for="commit in commitList.slice(0,5)" :key="commit.cid" v-model="commit.checked" readonly/>
                <v-btn v-show="commitcheck==false & commitList.length>5" class="ma-2" tile color="indigo" dark @click="commitwide()">펼치기</v-btn>

                <v-checkbox  class="my-0" v-show="commitList && commitcheck == true" v-for="commit in commitList" :key="commit.cid" v-model="commit.checked" :label="commit.msg" readonly></v-checkbox>
                <v-btn v-show='commitcheck==true' class="ma-2" tile color="indigo" dark @click="commitwide()">접기</v-btn>
                <br>
              </div>
              내용
              <v-textarea filled v-model="tmp.content" readonly/>
          </v-container>

          <div v-for="tag in tags" :key="'t-'+tag.id">
              <a style="float: left;" @click="searchTag(tag.name)">
                #{{tag.name}}&nbsp;&nbsp;
              </a>
          </div>
          <br>
          <v-btn class="ma-2" tile color="grey" dark v-if="likeData.likechecked == false" @click="like()" >좋아요</v-btn>
          <v-btn class="ma-2" tile color="red" dark v-if="likeData.likechecked" @click="like()">좋아요 취소</v-btn>
          <v-btn class="ma-2" tile color="indigo" dark >스크랩</v-btn>
          <v-btn class="ma-2" tile color="indigo" dark @click="grapurl()" >공유</v-btn>
          <v-btn class="ma-2 float-right" tile color="indigo" dark v-show="this.uid==this.tmp.uid" @click="deleteP(tmp.id)" >삭제</v-btn>

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
          <v-btn class="ma-2" tile color="indigo" dark @click="commentwrite()" >댓글 작성</v-btn>
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
        likedummy :'',
        tags:{},
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
    async created(){
        this.uid=this.$store.state.user.id
        //post 데이터 가져오기
        try{
            let tmpspace = await this.$api.postdetail(this.id.pid)
            this.tmp =tmpspace
            console.log('성공')
        }catch(e){
            console.log(e)
        }
        //좋아요 데이터 가져오기
        try{
            let tmpspace1= await this.$api.likedatas(this.id.pid)
                this.likedummy = tmpspace1
            if (tmpspace1.length!=0){

            if(tmpspace1.status==1){
                this.likeData.likechecked=true
            }else{
                this.likeData.likechecked=false
            }}
            else{
                this.likeData.likechecked=false
            }
        }catch(e){
            console.log(e)
        }
        //commit data가져오기
        try{
            let tmpspace2 = await this.$api.getPostCommit(this.id.pid)
            this.commitList= tmpspace2
            console.log('커밋 부르기 성공')

        }catch(e){
            console.log(e)
        }

        //tag 데이터 가져오기
        try{
            let tmpspace3= await this.$api.tagIndex(this.id.pid)
            this.tags = tmpspace3;
        }catch(e){
            console.log(e)
        }
    },
    methods:{
        goback(){
            this.$router.go(-1)
        },
        like(){
            if (this.likeData.likechecked==true){
                this.likeData.likechecked=false
                this.$api.likeDislike({pid:this.id.pid,status:0})
                console.log('좋아요 취소')
            }else{
                console.log('좋아요')
                this.likeData.likechecked=true
                this.$api.likeDislike({pid:this.id.pid,status:1})
            }
        },
        searchTag(tagName){

          document.getElementById('header-text').value=tagName;
          this.$router.push({name: 'tmp',params:{key:tagName}})
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
            alert('준비중입니다.')
        },
        async deleteP(postid){
            try{
                this.$api.deletePost(postid)
                console.log('성공')
                this.$router.go(-1)
            }catch(e){
                console.log(e)
            }

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
        },
        likechecking(){
            if (this.likedummy.status==1){

                this.likeData.likechecked==true
            }else if(this.likedummy,length==0)
            {
                this.likeData.likechecked==false
            }else{
                this.likeData.likechecked==false

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
