<template>
  <div>
      포스트 별로 디테일한 정보 
      <PostSidebar />
        <v-container fluid>
            제목 
            <v-text-field
            filled
            v-model="PostData.title"
            readonly />
            내용   
            <v-textarea filled v-model="PostData.content" readonly/>
        </v-container>
        좋아요
  </div>
</template>

<script>
import PostSidebar from '../../../component/PostSidebar.vue'


export default {
    name:'PostDetail',
    components:{PostSidebar},
    data(){
        return{
        PostData:{
            pid:0,
            title:'글 제목',
            content:'글 내용',
            commitList:null,


        }
        }
    },
}
</script>

<style>

</style>