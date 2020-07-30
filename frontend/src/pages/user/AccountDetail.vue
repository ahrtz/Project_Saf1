<template>
  <div>
    <v-container>
      <v-row>
        <v-col cols="9">
          email
          <v-text-field v-model="userdata.email"></v-text-field>
          nickname
          <v-text-field v-model="userdata.nickname"></v-text-field>
          <h3>git 관련</h3>
          id
          <v-text-field v-model="userdata.gitId"></v-text-field>
          url
          <v-text-field v-model="userdata.gitUrl"></v-text-field>
          token
          <v-text-field v-model="userdata.gitToken"></v-text-field>
          소개 
          <v-textarea v-model="userdata.intro"></v-textarea>
          <v-btn class="mr-4" @click="updateUser()">수정</v-btn>
        </v-col>
        <v-col cols="3">
          <user-sidebar/>

        </v-col>
      </v-row>
    </v-container>  
      

  </div>

</template>

<script>
import userSidebar from '../../component/user_sidebar.vue'
import axios from 'axios'
axios.defaults.headers.common['Access-Control-Allow-Origin'] = '*';

export default {
    name: 'AccountDetail',
    components : {
        userSidebar
        },
    data(){
      return{
        userdata:{

        }
    }

},
created(){
  this.userdata = this.$store.state.user
},
methods:{
  updateUser(){
    // axios.put('http://i3a110.p.ssafy.io:3000/users/',this.userdata,)
    // axios.put('http://localhost:3000/users/')
    // .then(this.$router.push({name:'AccountDetail'}
    // // alert('수정 완료')))
    // .catch(err => console.log(err))
  }
}
}
</script>

<style>

</style>