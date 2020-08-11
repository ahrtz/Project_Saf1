<template>
  <div class="s-contact-container d-flex flex-column align-center justify-center">
    <img :src="user.img?user.img:'/static/images/user.png'" class="s-contact-image" />
    <div class="s-contact-nickname">{{user.nickname}}</div>
    <div class="s-contact-intro">{{user.intro ? user.intro : "소개말이 없습니다."}}</div>
    <div class="d-flex align-center justify-center s-contact-follow" v-if="!followcheck" dark @click="alerting()">Follow</div>
    <div class="d-flex align-center justify-center s-contact-follow" v-if="followcheck" dark @click="alerting()">Un Follow</div>
    <div class="d-flex flex-column s-contact-contents">
      <div class="d-flex">
        <v-icon class="d-flex flex-grow-0" color="#21262e" size="20">email</v-icon>
        <a
          class="d-flex"
          v-if="!user.isSocial"
          :href="`mailto:${user.email}`"
          target="_top"
          style="text-decoration:none;margin-left:8px;color:#21262e"
        >{{user.email}}</a>
      </div>
      <div class="d-flex">
        <v-icon class="d-flex flex-grow-0" color="#21262e" size="20">link</v-icon>
        <a
          class="d-flex"
          v-if="user.gitUrl"
          :href="user.gitUrl"
          target="_top"
          style="text-decoration:none;margin-left:8px;color:#21262e"
        >{{user.gitUrl}}</a>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name:'s-contact',
  data(){
    return{
      user:{},
      dummy:this.$route.params.uid,
      mydata:"",
      followcheck:false
    }
  },
  methods: {
    alerting() {
      alert('업데이트 예정입니다.');
    },
    follow(){
      if (this.followcheck==false){
        this.followcheck=true
        this.$api.makeFollow({userTo:this.dummy,status:1})
      }else{
        this.followcheck=false
        this.$api.makeFollow({userTo:this.dummy,status:0})
      }

    }
  },
  async created(){
    this.mydata = this.$store.state.user
    try{

        let tmpspace = await this.$api.contactBar(this.$route.params.uid)
        this.user =tmpspace.data
        console.log(this.user,'vdasvdsa')
    }catch(e){
        console.log(e)
    }
    console.log(this.user)
    try{
      let tmpspace1 = await this.$api.confirmFollow(this.dummy)
      if (tmpspace1.length !=0){
        if (tmpspace1.data.status==1){
          this.followcheck=true
        }else{
          this.followcheck=false
        }
      }else{
        this.followcheck=false
      }
      
    }catch(e){
      console.log(e)
    }
  },
};
</script>

<style>
.s-contact-container {
  position: fixed;
  border: solid 1px #dde3ea;
  padding: 16px;
  background: #fff;
}

.s-contact-nickname {
  margin-top: 12px;
  width: 100%;
  font-size: 20px;
  font-weight: 800;
}

.s-contact-intro {
  margin-top: 4px;
  width: 100%;
  font-size: 12px;
}

.s-contact-image {
  width: 230px;
  height: 230px;
  border-radius: 50%;
  border: solid 1px #e8e8e8;
}

.s-contact-follow {
  margin-top: 16px;
  padding: 0 16px;
  width: 100%;
  font-size: 14px;
  background: #fff;
  font-weight: 600;
  color: #0051cb;
  border-radius: 6px;
  border: solid 1px #0051cb;
  height: 30px;
  cursor: pointer;
}

.s-contact-contents {
  width: 100%;
  margin-top: 32px;
  font-size: 12px;
  white-space: nowrap;
  color: #21262e;
}
</style>
