<template>
  <div>
      포스트 작성 페이지
    <form>
      <v-text-field
        v-model="title"
        label="Title"
        required
        outlined
      ></v-text-field>
      <v-textarea
        v-model="content"
        label="content"
        required
        outlined
      ></v-textarea>
      
      <v-checkbox
        v-model="checkbox"
        v-validate="'required'"
        
        value="1"
        label="공개여부"
        
        type="checkbox"
        required
      ></v-checkbox>
      
      <v-btn class="mr-4" >작성</v-btn>
      <v-btn class="mr-4">임시저장</v-btn>
      <v-btn >초기화</v-btn>
    </form>
  </div>
</template>

<script>
export default {
    name:'NewBlogPost',
    data(){
      return{
        post:{
          title:'',
          content:'',
          select: null,
          items:[
            'item1',
            'item2',
          ],
          checkbox:null,
          
        }
      }
    },
}
</script>

<style>

</style>