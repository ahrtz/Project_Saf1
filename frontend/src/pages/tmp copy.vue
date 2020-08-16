<template>
  <div class="search-page-container ">
    <SearchSidebar/>
    <div class="search-page-inner d-flex justify-center">
        <!-- <Status /> -->
        <br>
          <v-col cols="8" >
              <div class="d-flex align-center justify-space-between" style="margin-bottom:16px; border-bottom:solid 1px grey">
                  <div class="search-page-section-title"><h2>Diary Post</h2></div>
              </div>
              <div :key=componentKey>
                <v-card
                  class="ma-2"
                  flat
                  style="margin-top:10px; border-bottom:dashed 1px grey"
                  v-for="post in searchResult" :key="'sr-' + post.id"
                >
                <!-- card layout -->
                <!-- TODO : tag title로 검색 / tag로 검색 나누어 주어야 함 -->
                <!-- title -->
                  <!-- 프로필 이미지, 닉네임  -->
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
                  <!-- 포스트 제목 / 컨텐츠 -->
                  <article class="search-card-article" @click="$router.push({name:'PostDetail',params:{pid:post.id}})" style="cursor:pointer">
                    <h3 style="margin-left:10px;">{{post.title}}</h3>
                    <p style="margin-left:10px; margin-top:5px;">
                        {{post.content}}
                    </p>
                  </article>
                  <footer>
                    <!-- TODO: tags -->
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
import SearchSidebar from '../component/SearchSidebar.vue'

export default {
    name: 'tmp',
    components: {
      SearchSidebar
    },
    data(){
        return{
            ddd:this.$route.params,
            s_data:{
              uid:"",
              keyword:"",
              isTemp:"0",
              limit:"0",
              isProj:"2"
            },
            t_data:{
              keyword:"",
            },
            tagsearchResult:[],
            searchResult:[],
            componentKey:2
        }
    },
    async created(){
        console.log('keyword : ' + this.ddd.key);
        this.forceRerender();
        if(this.ddd.type == 'tag'){ //tag search
          console.log("tagsearch");
          this.searchResult = []
          this.t_data.keyword =this.ddd.key;
          try{
            let tmpspace = await this.$api.tagSearch(this.t_data)
            this.tagsearchResult = tmpspace;
            console.log(this.tagsearchResult);
            for(var i=0;i<this.tagsearchResult.length;i++){
              try{
                console.log('postid : ' + this.tagsearchResult[i].pid);
                var dup = 0;
                for(var j=0;j<i;j++){//중복 제거
                  if(this.tagsearchResult[j].pid == this.tagsearchResult[i].pid){
                    dup = 1;break;
                  }
                }
                if(dup==1)continue;
                //엄청난 시간손실
                let tmpspace = await this.$api.postdetail(this.tagsearchResult[i].pid)
                if(tmpspace.isTemp=='1')continue;
                this.searchResult[i]=(tmpspace);
                // console.log(this.searchResult);
              }catch(e){
                console.log(e)
              }
            }
          }catch(e){
            console.log(e)
          }
        }
        else{ // title search
          this.searchResult = []
          console.log("title search");
          this.s_data.keyword = this.ddd.key;
          try{
            let tmpspace = await this.$api.searchTemp(this.s_data)
            this.searchResult = tmpspace.data;
            // console.log(this.searchResult);
          }catch(e){
            console.log(e)
          }
        }
        //API수정해줘야됨
        this.forceRerender();
    },
    methods: {
      forceRerender() {
        console.log(this.componentKey);
        this.componentKey += 1;
    },
    mounted(){
      this.forceRerender();
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
