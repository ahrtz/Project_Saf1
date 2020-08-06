<template>
  <div>
       성공햇음
  </div>
</template>

<script>
export default {
    name:'socialSuccess',
    created(){
        // window.opener.$root.$router.push({name:'DiaryMain'})
        let tmp = this.$store.state.user
        console.log(tmp)
        window.opener.location.href = '/'+tmp.id;
        self.close();
    }

}
</script>

<style>

</style>