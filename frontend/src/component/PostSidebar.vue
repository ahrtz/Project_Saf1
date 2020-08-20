<template>
  <div>
    <h2>Contacts</h2>
    <img :src="user.img?user.img:'../../static/Blogit_logo.jpg'" alt="Girl in a jacket" width="100" height="150" class="side-img">
    <div style="margin-left:120px;">
      <h3 style="float:none;"> Writer: {{user.nickname}}</h3>
      <h3 style="float:none;"> Introduce :</h3>
       {{user.intro}}
      <h4> {{user.content}}</h4>
      <a :href="`mailto:${user.email}`" target="_top">Send Mail</a>
      <a :href='"http://"+`${user.gitUrl}`' target="_top">Github</a>
      <v-btn class="ma-2" tile color="indigo" dark @click="alerting()">팔로우</v-btn>
    </div>
  </div>

</template>

<script>
import axios from 'axios'

export default {
  name:'sidebar',
  data(){
    return{
      user:{}
    }
  },
  methods:{
    alerting(){
      alert('업데이트 예정입니다.')
    },

  },
  async created(){
    try{

        let tmpspace = await this.$api.getMe()
        this.user =tmpspace
        // console.log(this.user)
    }catch(e){
        // console.log(e)
    }
  }
}
</script>

<style>
.side-img{
  float: left;

}
</style>
