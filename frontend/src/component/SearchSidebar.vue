<template>
  <div >
     <v-card
        class="ma-2"
        flat
        style="margin-top:10px; border-bottom:dashed 1px grey"
      >
      Title Search
     </v-card>
     <v-card
        class="ma-2"
        flat
        style="margin-top:10px; border-bottom:dashed 1px grey"
      >
      Tag Search
     </v-card>

  </div>
</template>

<script>

export default {
  name:'SearchSide'
}
</script>

<style>
.search-sidebar {
  display:flex;
  flex-direction: row;
  flex-grow: 0;
  justify-content:left;
}
</style>
