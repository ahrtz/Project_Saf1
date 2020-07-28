<template>
  <div>
    <v-card
      class="header-flex-container align-start"
      flat
      color="green lighten-2"
      height="60"
    >
      <v-card
        class="header-flex-item "
        @click="active = true"
        color="green accent-2"
        onclick=""
      >
        <router-link class="router-link" :to="{name:'MainPage'}" exact ><a>Home</a></router-link>
      </v-card>
      <v-card
        class="header-flex-item"
        v-click-outside="onClickOutside"
        @click="active = true"
        color="green accent-2"
      >
        <router-link class="router-link" :to="{name:'Project'}" exact>Projects </router-link>
      </v-card>
      <v-card
        class="header-flex-item"
        v-click-outside="onClickOutside"
        @click="active = true"
        color="green accent-2"
      >
        <router-link class="router-link" :to="{name:'Blog'}">Blogs</router-link>
      </v-card>
      <v-card
        class="header-flex-item mr-auto"
        v-click-outside="onClickOutside"
        @click="active = true"
        color="green accent-2"
      >
        <router-link class="router-link" :to="{name:'GroupMain'}">Group</router-link>
      </v-card>
      <div class="header-search header-flex-item">
        <input id="searchInput"
        autocomplete="off"
        type="text"
        placeholder="Search posts"
        class="header-search-input"
        >
        <button class="header-search-button">검색</button>
      </div>

      <v-card
        class="header-flex-item"
        color="green accent-2"
        v-click-outside="onClickOutside"
        @click="active = true"
      >
        <router-link class="router-link" :to="{name:'Login'}">Login</router-link>
      </v-card>
      <v-card
        class="header-flex-item"
        color="green accent-2"
        v-click-outside="onClickOutside"
        @click="active = true"
      >
        <router-link class="router-link" :to="{name:'AccountDetail'}">MyPage</router-link>
      </v-card>
    </v-card>

  </div>
</template>

<script>
export default {
  name:'header1',
  data: () => ({
      title: [
        'Main',
        'Blog',
        'Projects',
        '검색',
        'Myinfo',
      ],
  }),
  methods: {
      onClickOutside () {
        this.active = false
      },
  },
}

</script>

<style>
@font-face{
  font-family:Recursive;
  src:url(https://fonts.googleapis.com/css2?family=Recursive:wght@700&display=swap);
}


.header-flex-container {
  width:100%;
  display:flex;
  flex-direction: row;
  margin: 0px;
  padding: 0px;
  border-radius: 5px;
}
.header-flex-item {
  display:flex;
  margin: 10px;
  padding: 0px;
  text-align: center;
  height:40px;
  border-radius: 5px;
  color:"green accent-2";
  font-family: Recursive;
  font-size:20px;
}
.header-search{
  margin: 12px;
  padding:5px;
  height:40px;
  width:400px;
  border:1px solid #1b5ac2;
  background-color: white;
}
.header-search-input{
  width:325px;
  font-size:16px;
  border:0px;
  outline:none;
}
.header-search-button{
  background:#32681c;
  border:0px;
  width:50px;
  height:100%;
  outline:none;
  color:#ffffff;
}
.router-link {
  text-decoration: none;
  margin:5px;
}
.router-link-exact-active  {
  color: white;
  background-color:#56c068;

}
</style>
