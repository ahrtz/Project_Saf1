<template>
  <div>
    <v-container>
        <v-row>
            <v-col cols="9">
              <h2>임시 저장 글</h2>
                <v-row>
                    <v-col cols="12">
                        <div class="text-center d-flex justify-space-between">
                            <div >다이어리 글</div>     
                        </div>
                        <v-card
                        class="pa-2"
                        outlined
                        tile
                        v-for="post in tempPost" :key="post.id"
                        >
                        {{post.did}}번 다이어리 : 제목 {{post.title}}
                        <router-link :to="{name:'PostDetail',params:{pid:post.id}}">
                           <v-btn> 보러가기 </v-btn>
                        </router-link>
                        <v-btn @click="deleteP(post.id)"> 삭제 </v-btn>
                        
                        </v-card>
                    </v-col>
                    
                </v-row>




            </v-col>
            <v-col cols="3">
                <user-sidebar/>
            </v-col>
        </v-row>

    </v-container>
  </div>
</template>

<script>
import userSidebar from '../component/user_sidebar.vue'
export default {
    name:'tmpPost',
    components:{
        userSidebar
    },
    data(){
        return{
            config:{
                uid:'',
                type:2,
                keyword:"",
                is_temp:1
            },
            tempPost:''
            

        }
    },
    async created(){
        this.config.uid=this.$store.state.user.id
        try{
            let tmpspace = await this.$api.searchTemp(this.config)
            this.tempPost = tmpspace
            console.log('성공')
        }catch(e){
            console.log(e)
        }
    },
    methods:{
        async deleteP(postid){
            try{
                this.$api.deletePost(postid)
                console.log('성공')
                this.$router.push({name:'tmpPost'})
                // 여기 리디렉션? 같은거 해서 삭제한걸 새롭게.. 받아 와야함 라우터 푸시로 안될거같은데 
                
            }catch(e){
                console.log(e)
            }

        }
    }
}
</script>

<style>

</style>