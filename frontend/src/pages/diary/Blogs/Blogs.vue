<template>
    <div>
        <h2>블로그 카드뷰</h2>
        <br>
        <router-link :to="{name:'BlogAdd'}" tag="button">블로그 추가(버튼임)</router-link>
        <br>
        <br>

        <v-container fluid>

        <v-row>
        <v-col cols="4">
        <div v-for="blog in blogs" :key="blog.did"> <!-- key는 이 값을 기준으로 안겹치게 list에서 뽑아줄 것이다 -->
        <!-- <v-flex xs12 sm6 md4 lg3 xl2>    -->
        
        <v-flex>
             <v-card
    class="mx-auto"
    max-width="344"
  >
    <v-img
      src="https://cdn.vuetifyjs.com/images/cards/sunshine.jpg"
      height="200px"
    ></v-img>

    <v-card-title>
      {{blog.dname}}
    </v-card-title>

    <v-card-subtitle>
      1,000 miles of wonder
    </v-card-subtitle>

    <v-card-actions>
      <v-btn text>Share</v-btn>

      <v-btn
        color="purple"
        text
      >
        Explore
      </v-btn>

      <v-spacer></v-spacer>

      <v-btn
        icon
        @click="show = !show"
      >
        <v-icon>{{ show ? 'mdi-chevron-up' : 'mdi-chevron-down' }}</v-icon>
      </v-btn>
    </v-card-actions>

    <v-expand-transition>
      <div v-show="show">
        <v-divider></v-divider>

        <v-card-text>
          I'm a thing. But, like most politicians, he promised more than he could deliver. You won't have time for sleeping, soldier, not with all the bed making you'll be doing. Then we'll go with that data file! Hey, you add a one and two zeros to that or we walk! You're going to do his laundry? I've got to find a way to escape.
        </v-card-text>
      </div>
    </v-expand-transition>
  </v-card>
            <!-- <router-link :to="{name:'BlogDetail',params:{did:blog.did}}">들어가보기</router-link> -->
        </v-flex>
       
        </div>
         </v-col>
        </v-row>
        </v-container>

    </div>
</template>

<script>
import cardView from '../../../component/diary_cardview.vue'

export default {
    name:'Blogs',
    components : {
        cardView
    },
    data(){
        return{
            blogs:[{
                did:0,
                dname:'1번 블로그'
            },
            {
                did:1,
                dname:'2번 블로그'
            },
            {
                did:2,
                dname:'3번 블로그'
            },
            {
                did:3,
                dname:'4번 블로그'
            },
            {
                did:4,
                dname:'5번 블로그'
            },
            ]
        }
    },
}
</script>

<style>

</style>