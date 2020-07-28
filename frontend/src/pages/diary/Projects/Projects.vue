<template>
    <div>
        <h2>프로젝트 카드 뷰</h2>
         
        <router-link class="float-right" :to="{name:'BlogAdd'}" tag="button">
          <v-btn class="mr-4 " color="indigo" dark>일기장 추가</v-btn>
        </router-link>
        <br>
        <br>

       <v-container fluid>
           <v-row>
               <v-col cols="4" v-for="project in projects" :key="project.did">
                   <v-hover
                        v-slot:default="{ hover }"
                        enabled
                        >   
                    <v-card 
                    :elevation="hover? 12 : 2"
                    :img="project.img"
                    class=" ma-2 " 
                    :class="{ 'on-hover': hover }"
                    style="height:300px;" 
                    outlined
                    @click="goProject(project.did)">
                    <!-- hover -->
                    <div 
                    v-if="hover"
                    class = "black div-reveal"
                    >
                        
                        <v-card-title>
                           {{project.dname}}
                        </v-card-title>

                        <v-card-subtitle>
                            <div style="color:white;" v-for="tag in project.tags" :key="tag">
                                {{tag}}
                            </div>
                        </v-card-subtitle>

                        <v-card-text>
                            {{project.intro}} 
                        </v-card-text>

                    </div>

                    <!-- unhover -->
                    <div v-else>
                        <v-card-title class = "title-unhover">
                           {{project.dname}}
                        </v-card-title>

                    </div>
                    </v-card>
                </v-hover>
                </v-col>
                   
           </v-row>
       </v-container>
    </div>
</template>

<script>


export default {
    name:'Projects',
    data(){
        return{
            show: false,
            projects:[{
                did:0,
                dname:'1번 프로젝트',
                tags : [
                    '#Android',
                    '#Java',
                    '#Kotlin', 
                ],
                img : "https://cdn.vuetifyjs.com/images/cards/sunshine.jpg",
                intro : '1번 프로젝트의 내용입니다.',
            },
            {
                did:1,
                dname:'2번 프로젝트',
                tags : [
                    '#Web',
                    '#Vue',
                    '#HTML',
                    '#JavaScript',
                    'Spring-boot', 
                ],
                img : "https://cdn.vuetifyjs.com/images/cards/sunshine.jpg",
                intro : '2번 프로젝트의 내용입니다.',

            },
            {
                did:2,
                dname:'3번 프로젝트',
                tags : [
                    '#C++',
                    '#MFC',
                    '#Media Processing',
                ],
                img : "https://cdn.vuetifyjs.com/images/cards/sunshine.jpg",
                intro : '3번 프로젝트의 내용입니다.',

            },
            {
                did:3,
                dname:'4번 프로젝트',
                tags : [
                    '#C#',
                    '#Winform',
                    '#Mutil Thread',
                ],
                img : "https://cdn.vuetifyjs.com/images/cards/sunshine.jpg",
                intro : '4번 프로젝트의 내용입니다.',

            },
            {
                did:4,
                dname:'5번 프로젝트',
                tags : [
                    '#Algorithms',
                    '#C',
                    '#C++',
                    '#Dijkstra',
                ],
                img : "https://cdn.vuetifyjs.com/images/cards/sunshine.jpg",
                intro : '5번 프로젝트의 내용입니다.',
            },

            ]
        }
    },
    methods:{
        goProject( param ){
            this.$router.push({name : 'ProjectDetail', params :{did:param}})
        }
    }
   
}
</script>

<style>
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

