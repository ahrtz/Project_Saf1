<template>
<div>
    <h2>메인페이지</h2>
    <br>스테이터스가 컴포넌트로 해놓긴 햇는데 데이터를 어케 받아서 넘겨줄지 고민 
    <status />
    <br>
    <br>
    <v-container fluid>
        <v-row>
            <v-col cols="6">
                <div class="text-center d-flex justify-space-between">
                    <div >프로젝트 글</div>     
                    <v-btn class="ma-2  " tile color="indigo" dark>view all</v-btn>
                </div>
                <v-card
                class="pa-2"
                outlined
                tile
                v-for="post in diarypost" :key="post.pid"
                >
                {{post}}
                </v-card>
            </v-col>
            <v-col cols="6">
                <div class="text-center d-flex justify-space-between">
                    <div >블로그 글</div>     
                    <v-btn class="ma-2  " tile color="indigo" dark>view all</v-btn>
                </div>
                <v-card
                class="pa-2"
                outlined
                tile
                v-for="post in projectpost" :key="post.pid"
                >
                {{post}}
                </v-card>
                
            </v-col>
        </v-row>
    </v-container>
</div>
</template>

<script>
import Status from '../src/component/Status.vue'

export default {
    name:'MainPage',
    components:{
        Status
    },
    data(){
        return{
            status:['날짜정보 혹은 뭐 커밋 정보'],
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
            ],
            projectpost:[{
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
            ],
        }
    }
}
</script>

<style>

</style>
