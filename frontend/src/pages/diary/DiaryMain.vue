<template>
<div class="diary-main-container">
    <div class="d-flex diary-main-toggle">
        <div class="d-flex justify-center">
            <div @click="activeBtn=0" class="d-flex flex-grow-0 align-center justify-center diary-main-toggle-item">
                <div>
                    <img class="diary-main-toggle-img" src="/static/images/branch.png" />
                </div>
                <div>
                    <div class="diary-main-toggle-text">Project</div>
                    <div class="diary-main-toggle-text" style="font-size:14px;font-weight:300">Git commit</div>
                </div>
                
            </div>
            <div @click="activeBtn=1" class="d-flex flex-grow-0 align-center justify-center diary-main-toggle-item">
                <div>
                    <img class="diary-main-toggle-img" src="/static/images/blog.png" />
                </div>
                <div>
                    <div class="diary-main-toggle-text">Blog</div>
                    <div class="diary-main-toggle-text" style="font-size:14px;font-weight:300">Daily life, etc...</div>
                </div>
                
            </div>
        </div>
     </div>
    <div class="diary-main-inner">
        <!-- <h2>블로그 카드 뷰</h2> -->
        
        <div class="overflow-hidden">
            <!-- <div class="text-center mb-2">
            <v-btn
                text
                color="deep-purple"
                @click="showNav = !showNav"
            >
                Toggle Nav
            </v-btn>
            </div> -->


            <v-bottom-navigation
            :input-value="showNav"
            :value="activeBtn"
            v-model="test"
            color="indigo"
            >
            <v-btn @click="getDiary()">
                <span>Project</span>
                <v-icon>history</v-icon>
            </v-btn>
            <v-btn @click="getDiary()">
                <span>Blog</span>
                <v-icon>favorite</v-icon>
            </v-btn>
            


            </v-bottom-navigation>
        </div>




        <!-- <v-btn class="mr-4 " color="indigo" dark @click="testa()">xx</v-btn> -->



        <router-link v-show="test==1" class="float-right" :to="{name:'ProjectAdd'}" tag="button">
          <div class="d-flex flex-grow-0 justify-center align-center diary-main-add-btn">추가</div>
        </router-link>

        <router-link v-show="test==0" class="float-right" :to="{name:'BlogAdd'}" tag="button">
          <div class="d-flex flex-grow-0 justify-center align-center diary-main-add-btn">추가</div>
        </router-link>
        <br>
        <br>

       <v-container fluid>
           <v-row>
               <v-col cols="4" v-for="blog in diarys" :key="blog.id">
                   <v-hover
                        v-slot:default="{ hover }"
                        enabled
                        >   
                    <v-card 
                    :elevation="hover? 12 : 2"
                    :img="blog.img"
                    class=" ma-2 " 
                    :class="{ 'on-hover': hover }"
                    style="height:300px;" 
                    outlined
                    @click="goBlog(blog.id)">
                    <!-- hover -->
                    <div 
                    v-if="hover"
                    class = "black div-reveal"
                    >
                        
                        <v-card-title>
                           {{blog.title}}
                        </v-card-title>

                        <v-card-subtitle>
                            <div style="color:white;" v-for="tag in blog.tags" :key="tag">
                                {{tag}}
                            </div>
                            
                        </v-card-subtitle>

                        <v-card-text>
                            {{blog.intro}} 
                        </v-card-text>

                    </div>

                    <!-- unhover -->
                    <div v-else>
                        <v-card-title class = "title-unhover">
                           {{blog.title}}
                        </v-card-title>

                    </div>
                    </v-card>
                </v-hover>
                </v-col>
                   
           </v-row>
       </v-container>
    </div>
</div>
</template>

<script>
import axios from 'axios'


export default {
    name:'Blogs',
    data(){
        return{
            test:0,
            activeBtn: 0,
            showNav: true,
            show: false,
            diarys:{},
            
        }
    },
    methods:{
        goBlog( param ){
            if (this.test==0){
                this.$router.push({name : 'BlogDetail', params :{did:param}})
            }else{
                this.$router.push({name : 'ProjectDetail', params :{did:param}})
                
            }
        },
        testa(){
            axios.get('http://localhost:3000/users/is-logged-in')
            .then(res=>{console.log(res)})
        },
        getDiary(){
            axios.post('http://localhost:3000/diaries/1',{type:this.test,keyword:""})
            .then(res=>{
                console.log(res.data)
                this.diarys = res.data})
                .catch(err=>{console.log(err)
                console.log('실패')})
        }
    },

   
}
</script>

<style>
 .diary-main-container {
  width: 100%;
}

.diary-main-inner {
  width: 1140px;
  margin: 0 auto;
  padding: 77px 0;
}

.diary-main-add-btn {
  margin-left: 4px;
  margin-right: 32px;
  font-size: 14px;
  background: #0051cb;
  font-weight: 600;
  color: #fff;
  border-radius: 6px;
  width: 80px;
  height: 40px;
  cursor: pointer;
}

.diary-main-toggle {
    width: 100%;
    height: 130px;
    background: #f7f9fd;
}

.diary-main-toggle-item {
    min-width: 160px;
    margin-right: 32px;
    cursor: pointer;
}

.diary-main-toggle-text {
    font-size: 14px;
    font-weight: 700;
}

.diary-main-toggle-img {
    width: 65px;
    height: 65px;
    margin-right: 10px;
}

.v-card:not(.on-hover){
    opacity : 1.0;
}
.div-reveal {
    color : white;
    opacity: .5;
    width : 100%;
    height : 100%;
}
.title-unhover {
    color : black;
    font-weight: bold;
    background-color : white;
    opacity: .5;
    width: 100%;
    height : 100%;
}

/* TODO : 반응형으로 작성(half page : 줄당 2개) 모바일 : 줄당 1개 */
</style>

