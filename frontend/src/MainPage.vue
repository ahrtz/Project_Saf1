<template>
<div class="main-page-container">
<div class="main-page-inner d-flex flex-column justify-center">
    <!-- <h2>메인페이지</h2> -->
    <!-- <br>스테이터스가 컴포넌트로 해놓긴 햇는데 데이터를 어케 받아서 넘겨줄지 고민  -->
    <status />
    <br>
    <br>
    <v-container fluid>
        <v-row>
            <v-col cols="6">
                <div class="d-flex align-center justify-space-between" style="margin-bottom:16px">
                    <div class="main-page-section-title">Project Post</div>     
                    <div class="d-flex justify-center flex-grow-0 align-center main-page-btn">더보기</div>
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
                <div class="d-flex align-center justify-space-between"  style="margin-bottom:16px">
                    <div class="main-page-section-title">Blog Post</div>     
                    <div class="d-flex justify-center flex-grow-0 align-center main-page-btn">더보기</div>
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
 .main-page-container {
  width: 100%;
}

.main-page-inner {
  width: 1140px;
  margin: 0 auto;
  padding: 77px 0;
}

.main-page-btn {
  margin-left: 4px;
  font-size: 14px;
  background: #0051cb;
  font-weight: 600;
  color: #fff;
  border-radius: 6px;
  width: 80px;
  height: 40px;
  cursor: pointer;
}

.main-page-section-title {
    font-size: 20px;
    font-weight: 700;

}
</style>
