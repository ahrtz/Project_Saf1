<template>
  <div>
    <v-container>
      <v-row>
        <v-col cols="9">
          email
          <v-text-field v-model="email"></v-text-field>
          nickname
          <v-text-field v-model="nickname"></v-text-field>
          <h3>git 관련</h3>
          id
          <v-text-field v-model="git_id"></v-text-field>
          url
          <v-text-field v-model="git_url"></v-text-field>
          token
          <v-text-field v-model="git_token"></v-text-field>
          소개 
          <v-textarea v-model="desc"></v-textarea>
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



export default {
    name: 'AccountDetail',
    components : {
        userSidebar
        },
    data(){
      return{
          email:'a',
          pwd:'b',
          nickname:'d',
          git_id:'d',
          git_url:'s',
          git_token:'v',
          desc:'q'
    }

}
}
</script>

<style>

</style>