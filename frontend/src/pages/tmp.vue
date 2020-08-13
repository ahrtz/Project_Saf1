<template>
  <div class="search-page-container ">
    
    <div class="search-page-inner d-flex justify-center">
        
        <br>
          <v-col cols="2">
            
              <v-btn @click="res=0" >제목검색 ({{searchResult.length}})</v-btn>
              <v-btn @click="res=1" >태그검색 ({{tagResult.length}})</v-btn>
            
          </v-col>
          <v-col cols="10" >
              <div class="d-flex align-center justify-space-between" style="margin-bottom:16px; border-bottom:solid 1px grey">
                  <div class="search-page-section-title"><h2>Diary Post</h2></div>
              </div>
              <!-- 여기 v-if 구분 -->
              <div :key=componentKey
              v-if="res==0"
              >
                <v-card
                  class="ma-2"
                  flat
                  style="margin-top:10px; border-bottom:dashed 1px grey"
                  v-for="post in searchResult" :key="'sr-' + post.id"
                >
               
                  <header class="search-card-header">
                    <img :src="post.userinfo.img" alt="" class="search-card-header-img">
                    <div class="search-card-header-nick_date">
                      <span> 닉네임 :
                        {{post.userinfo.nickname}}
                      </span>
                      <span> 작성일 :
                        {{post.cdate}}
                      </span>
                    </div>
                  </header>
                  <article class="search-card-article" @click="$router.push({name:'PostDetail',params:{pid:post.id}})" style="cursor:pointer">
                    <h3 style="margin-left:10px;">{{post.title}}</h3>
                    <p style="margin-left:10px; margin-top:5px;">
                        {{post.content}}
                    </p>
                  </article>
                  <footer>
                  </footer>
                </v-card>
              </div>
              
              
              <div :key=componentKey+1
              v-if="res==1"
              >
                <v-card
                  class="ma-2"
                  flat
                  style="margin-top:10px; border-bottom:dashed 1px grey"
                  v-for="posts in tagResult" :key="'src-' + posts.id"
                >
                
                  <header class="search-card-header">
                    <img :src="posts.userinfo.img" alt="" class="search-card-header-img">
                    <div class="search-card-header-nick_date">
                      <span> 닉네임 :
                        {{posts.userinfo.nickname}}
                      </span>
                      <span> 작성일 :
                        {{posts.postinfo.cdate}}
                      </span>
                    </div>
                  </header>
                  <article class="search-card-article" @click="$router.push({name:'PostDetail',params:{pid:posts.pid}})" style="cursor:pointer">
                    <h3 style="margin-left:10px;">{{posts.postinfo.title}}</h3>
                    <p style="margin-left:10px; margin-top:5px;">
                        {{posts.postinfo.content}}
                    </p>
                  </article>
                  <footer>
                  </footer>
                </v-card>
              </div>
            </v-col>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
// import Status from '@/component/Status.vue'


export default {
    name: 'tmp',
    
    data(){
        return{
            ddd:this.$route.params,
            p_data:{
              uid:"",
              keyword:this.$route.params.key,
              isTemp:"0",
              limit:"",
              isProj:"2"
            },
            t_data:{
              keyword:this.$route.params.key,
            },
            tagResult:[],
            searchResult:[],
            componentKey:2,
            res:0
        }
    },
    async created(){
      try{
        let tmpspace = await this.$api.searchTemp(this.p_data)
        this.searchResult = tmpspace.data
      }catch(e){
        console.log('포스트 검색 실패')
      }
      try{
        let tmpspace1 = await this.$api.tagSearch(this.t_data)
        this.tagResult = tmpspace1
      }catch(e){
        console.log('태그 검색 실패')
      }

    },
    methods:{
      changeval(){
        if (this.res==0){
          this.res=1
        }else{
          this.res=0
        }
      }
    }
    
    
  }

</script>

<style>
.search-card-header{
  background-color:white;
  /* background:#ffffffde; */
  font-size: 16px;
  color: black;
  height: 55px;

}
.search-card-header-nick_date{
  display:flex;
  flex-direction: column;
}

.search-card-header-img{
  width:35px;
  height:35px;
  margin:10px 10px 10px 10px;
  float:left;
}
.search-card-article{
  background-color:white;
  float:unset;
  width:100%;
  height:100px;
}

.search-page-container {
  width: 100%;
}

.search-page-inner {
  width: 1140px;
  margin: 0 auto;
  padding-bottom: 70px;
}

.search-page-btn {
  margin-left: 4px;
  font-size: 14px;
  background: #0051cb;
  font-weight: 600;
  color: #fff;
  border-radius: 6px;
  width: 80px;
  height: 40px;
  cursor: pointer;
}

.search-page-section-title {
    font-size: 20px;
    font-weight: 700;

}
</style>
