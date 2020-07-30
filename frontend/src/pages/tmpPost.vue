<template>
  <div>
    <v-container>
        <v-row>
            <v-col cols="9">
              <h2>임시 저장 글</h2>

            </v-col>
            <v-col cols="3">
                <user-sidebar/>
            </v-col>
        </v-row>

    </v-container>
  </div>
</template>

<script>
import userSidebar from '../component/user_sidebar.vue'
export default {
    name:'tmpPost',
    components:{
        userSidebar
    },
    data(){
        return{
            uid:''
        }
    },
    created(){
        this.uid=this.$store.state.user.id
    }
}
</script>

<style>

</style>