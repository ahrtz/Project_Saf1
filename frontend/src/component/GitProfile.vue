<!--쓰이는곳 없음-->
<template>
  <div>
    <!-- <div class="profile" v-for="(j,key) in 4" :key="key">
      <div
        class="profile-tile"
        :class="{'profile-tile-black': j * i % 2 == 1, 'profile-tile-darkgrey' : (i%2 == 0)&(j%2)==1 }"
        v-for="(i, index) in 7"
        :key="index"
      ></div>
    </div>-->
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'GitProfile',
  
};
</script>
<style>
.profile {
  width: 100%;
  display: flex;
  flex-direction: row;
}

.profile-tile {
  width: 35px;
  height: 35px;
  border-radius: 4px;
  margin: 2px;
}

.profile-tile-black {
  background-color: black;
}

.profile-tile-darkgrey {
  background-color: darkgrey;
}


</style>