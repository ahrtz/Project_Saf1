<template>
  <div>
       성공햇음
  </div>
</template>

<script>
export default {
    name:'oauth2Success',
    created(){
        // window.opener.$root.$router.push({name:'DiaryMain'})
        window.opener.location.href = '/';
        self.close();
    }

}
</script>

<style>

</style>