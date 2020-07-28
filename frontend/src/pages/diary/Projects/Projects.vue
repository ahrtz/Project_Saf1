<template>
    <div>
        <h2>프로젝트 카드 뷰</h2>
         
        <router-link class="float-right" :to="{name:'ProjectAdd'}" tag="button">
          <v-btn class="mr-4 " color="indigo" dark>프로젝트 추가</v-btn>
          
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
                    outlined>
                    <div 
                    v-if="hover"
                    class = "black div-reveal"
                    >
                        <!-- if hover -->
                        <v-card-title v-if="hover">
                           {{project.dname}}
                        
                        </v-card-title>

                        <v-card-subtitle v-show="hover">
                            <div style="color:white;" v-for="tag in project.tags" :key="tag">
                                {{tag}}
                            </div>
                        </v-card-subtitle>

                        <v-card-text v-show="hover">
                            {{project.descrip}} 
                        </v-card-text>

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
                descrip : '1번 프로젝트의 내용입니다.',
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
                descrip : '2번 프로젝트의 내용입니다.',

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
                descrip : '3번 프로젝트의 내용입니다.',

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
                descrip : '4번 프로젝트의 내용입니다.',

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
                descrip : '5번 프로젝트의 내용입니다.',
            },

            ]
        }
    },
   
}
</script>

<style>
/* .v-card{
    transition: opacity .4s ease-in-out;
    opacity : .4;
} */
.v-card:not(.on-hover){
    opacity : 1.0;
}
.div-reveal {
    color : white;
    opacity: .5;
    width : 100%;
    height : 100%;
}

.v-card-title{
    color : black;
    background-color : #FFFFFF;
    opacity : .5;
}
</style>