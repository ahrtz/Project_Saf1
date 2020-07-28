<template>
  <div>
      <v-container>
          <v-row>
      <v-col cols="12">
      블로그 : <v-text-field v-model="blogData.title" type = "text" placeholder="블로그 이름을 입력하세요"></v-text-field>
      </v-col>
      <v-col cols="12">
            대표 이미지
            <v-file-input ref="file" label="imagefile" prepend-icon="mdi-camera" ></v-file-input>
      </v-col>
      <v-col cols="6">
        <v-menu
            v-model="blogData.menu2"
            :close-on-content-click="false"
            :nudge-right="40"
            transition="scale-transition"
            offset-y
            min-width="290px"
        >
            <template v-slot:activator="{ on, attrs }">
            <v-text-field
                v-model="blogData.date"
                label="시작날짜"
                prepend-icon="event"
                readonly
                v-bind="attrs"
                v-on="on"
            ></v-text-field>
            </template>
            <v-date-picker v-model="blogData.date" @input="blogData.menu2 = false"></v-date-picker>
        </v-menu>
        </v-col>
      
        <v-col cols="12">
            간단 설명
            <v-textarea v-model="blogData.desc" label="desc"></v-textarea>
        </v-col>
          </v-row>
      </v-container>
      <div style="float:right">
          <v-btn class="mr-4 float-right" color="indigo" dark  @click="goback()">취소</v-btn>
          <v-btn class="mr-4 float-right" color="indigo" dark>작성완료</v-btn>

          
      </div>

  </div>
</template>

<script>
export default {
    name : 'BlogAdd',
    data(){
        return {
            blogData:{
                uid : null,// 회원 pk.
                title : null,
                desc : null,
                img : null, //얘는 이미지의 주소가 string 형태로 들어가는거겠지?
                git_url : null, //얘는 블로그에선 필요없는 요소. 맞지?
                is_project : 0, // 블로그는 프로젝트가 아니니까 무조건 0으로 해놓음.

                date: new Date().toISOString().substr(0, 10),
                modal: false,
                menu2: false,
            },
        }
    },
    methods:{
      goback(){
            this.$router.go(-1)
        },
    }
}
</script>

<style>

</style>