<template>
    <div>
        <h2>프로젝트 카드 뷰</h2>
         
         <router-link :to="{name:'ProjectAdd'}" tag="button">프로젝트 추가(버튼임)</router-link>
        <br>
        <br>

       <div v-for="project in projects" :key="project.did">
            {{project.dname}}
        
            <!-- <router-link :to="{name:'ProjectDetail',params:{did:project.did}}">들어가보기</router-link> -->
       </div>

       <v-container fluid>
           <v-row>
               <v-col v-for="project in projects" :key="project.did">
                    <v-card id="CardView" img="https://cdn.vuetifyjs.com/images/cards/sunshine.jpg" class="ma-2 pa-2" stlye="height:300px" outlined>
                       <v-card-title v-show="show">
                           {{project.dname}}
                           </v-card-title>
                
                           <v-card-subtitle v-show="show">
                               <div v-for="tag in project.tags" :key="tag">
                                   {{tag}}
                               </div>
                           </v-card-subtitle>

                           <v-card-text v-show="show">
                               {{project.descrip}} 
                           </v-card-text>
                    </v-card>
                </v-col>
                   
           </v-row>
       </v-container>
    </div>
</template>

<script>
import plugin from '../../../plugins/vuetify.js'; // 이거 모르겠음..
import Vue from 'vue';
import Vuetify from 'vuetify';

window.onload = function(){
    new Vue({
    el: '#CardView',
    vuetify: new Vuetify(),
    data: () => ({
    show: false,
    opacityStyle:{
      opacity : "1.0",
    },
  }),
  methods: {
    changecardStyle : function(){
      this.opacityStyle.opacity = "0.4";
      this.show = true;
    },
    originalcardStyle : function(){
      this.opacityStyle.opacity = "1.0";
      this.show = false;
    }
  }
});
}


export default {
    name:'Projects',
    data(){
        return{
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
    }
}
</script>

<style>

</style>