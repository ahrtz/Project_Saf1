<template>
<div>
<div class="profile" v-for="(j,key) in 4" :key="key" >
    <div class="profile-tile" :class="{'profile-tile-black': j * i % 2 == 1, 'profile-tile-darkgrey' : (i%2 == 0)&(j%2)==1 }" v-for="(i, index) in 7" :key="index">
        
    </div>  
</div>
</div>
</template>

<script>
import axios from 'axios'

export default {
    name : 'GitProfile'
}
</script>
<style>
.profile{
    width:100%;
    display:flex;
    flex-direction: row;
}

.profile-tile {
    width : 40px;
    height : 40px;
    background-color : rgb(51, 50, 50);
    border-radius:  4px;
    margin : 1px;
}

.profile-tile-black {
    background-color : black;
}

.profile-tile-darkgrey {
    background-color:darkgrey;
}
</style>