<template>
  <div>
      <h2>다이어리(블로그든 플젝이든) 카드뷰 형식으로 보여주는거</h2>
      <router-link :to="{name:'Project'}">Projects</router-link>
      <router-link :to="{name:'Blog'}">Blogs</router-link>
      {{diaries}}
  </div>
</template>

<script>
export default {
    name:'DiaryMain',
    data(){
        return{
            diaries:['1번 다이어리','1번 다이어리','1번 다이어리','1번 다이어리','1번 다이어리']
        }
    }
}
</script>

<style>

</style>