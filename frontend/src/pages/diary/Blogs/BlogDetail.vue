<template>
  <div>
      다이어리(블로그) 세부 페이지
      <router-link :to="{name:'NewBlogPost',params:{did:diaryid.did}}">
        <v-btn>글 작성 </v-btn>
        </router-link>
      
      <div></div>
      <div>
        <v-container fluid>
        <v-row>
            <v-col cols="12">
                <div class="text-center d-flex justify-space-between">
                    <div >다이어리 글</div>     
                </div>
                <v-card
                class="pa-2"
                outlined
                tile
                v-for="post in diarypost" :key="post.pid"
                >
                <router-link :to="{name:'PostDetail',params:{pid:post.pid}}">
                {{post}}
                </router-link>
                  
                
                </v-card>
            </v-col>
            
        </v-row>
    </v-container>
      </div>
  </div>
</template>

<script>
export default {
    name:'BlogDetail',
    data(){
      return{
        diaryid:this.$route.params,
        diarypost:[{
                pid:0,
                pname:'1번 글'
            },
            {
                pid:1,
                pname:'2번 글'
            },
            {
                pid:2,
                pname:'3번 글'
            },
            {
                pid:3,
                pname:'4번 글'
            },
            {
                pid:4,
                pname:'5번 글'
            },
            ]
    }
  },
  methods:{
    
  }

}
</script>

<style>

</style>