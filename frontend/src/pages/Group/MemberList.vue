<template>
  <div>
      <h2>멤버 리스트</h2>
      
      <br>
      group relation 을 요구 할건데 이때 id 뿐아니라 닉네임 과 이메일도 요청을 해야함
      -> 이건 제거를 위한거고 추가를 위한건 뭘 요구해야할까
      <br>
      <v-container fluid>
        <v-row>
          <v-col cols="9">
            <v-text-field v-model="this.newuser" type = "text" placeholder="추가할 사용자의 이메일을 입력하세요"></v-text-field>
          </v-col>
          <v-col cols="3">
            <v-btn @click="addgroup">추가</v-btn>
          </v-col>
        </v-row>
      </v-container>
      
      <div v-for="member in MemberList" :key="member.id" >
        <div>
          {{member}}  <v-btn @click="rejectuser(member.id)"> 삭제 </v-btn>
        </div>
      </div>
  </div>
</template>

<script>
export default {
    name:'MemberList',
    data(){
      return{
        newuser:'',
        MemberList:{},
        groupInfo:[]
      }
    },
    async created(){
      let tmpspace = await this.$api.groupDetail(this.$route.params.gid)
      this.MemberList = tmpspace.userinfo
      
    },
    methods:{
      
    }
}
</script>

<style>

</style>