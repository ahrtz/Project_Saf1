<template>
  <div class="search-page-container ">
<div class="search-page-inner d-flex justify-center">
    <Status />
    <br>
    <br>
      <v-col cols="8">
          <div class="d-flex align-center justify-space-between" style="margin-bottom:16px; border-bottom:solid 1px grey">
              <div class="search-page-section-title"><h2>Diary Post</h2></div>
          </div>
          <v-card
          class="ma-2"
          flat
          v-for="post in searchResult" :key="post.id"
          style="margin-top:10px; border-bottom:dashed 1px grey"

          >
          <!-- card layout -->
          <div>
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
          </div>
          </v-card>
      </v-col>

</div>
</div>
</template>

<script>
import axios from 'axios'
import Status from '@/component/Status.vue'

export default {
    name: 'tmp',
    data(){
        return{
            ddd:this.$route.params,
            searchResult:{}
        }
    },
    created(){
        console.log(this.ddd.key)
        axios.post("/api/posts/all/",{
            keyword:this.$route.params.key,
            is_temp:"0",
            uid:"",
            type:"2"
        })
        .then(res=> (this.searchResult = res.data))
        console.log(this.searchResult);
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
  padding: 77px 0;
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
