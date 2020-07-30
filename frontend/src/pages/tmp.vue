<template>
  <div>apdfpa</div>
</template>

<script>
import axios from 'axios'
export default {
    name: 'tmp',
    data(){
        return{
            ddd:this.$route.params
        }
    },
    created(){
        axios.get("http://i3a110.p.ssafy.io:3000/",{params:{
            keyword:this.$route.params
        }})
        .then(res=> (this.ddd= res.data))
    }
}
</script>

<style>

</style>