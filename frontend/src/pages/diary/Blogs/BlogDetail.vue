<template>
  <div>
      다이어리 세부 페이지
      <Status />
      
      <router-link v-show="isProj==false" class="float-right" :to="{name:'NewBlogPost',params:{did:diaryid.did}}">
        <v-btn> 글 작성 </v-btn>
      </router-link>
      <router-link v-show="isProj==true" class="float-right" :to="{name:'NewProjectPost',params:{did:diaryid.did}}">
        <v-btn> 글 작성 </v-btn>
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
                v-for="post in postdata" :key="post.id"
                >
                <router-link :to="{name:'PostDetail',params:{pid:post.id}}">
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
import Status from '@/component/Status.vue'
import axios from 'axios'
axios.defaults.headers.common['Access-Control-Allow-Origin'] = '*';
export default {
    name:'BlogDetail',
    components:{
      Status
    },
    data(){
      return{
        diaryid:this.$route.params,
        config:{
          keyword:"",
          isTemp:0
        },
        postdata:[],
        
    }
  },
  methods:{
    
  },
  created(){
    axios.post('http://localhost:3000/posts/'+this.diaryid.did,this.config,)
    .then(res=>{
      console.log(res.data)
      this.postdata = res.data
      }
    )
    .catch(err => console.log(err))
  },
  computed:{
      isProj(){
        if (this.$route.path[7]=='p'){
          return true
        }else{
          return false
        }
      }
    },
  

}

</script>

<style>

</style>