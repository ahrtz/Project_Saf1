<template>
  <div>
    <v-container>
        <v-row>
            <v-col cols="9">
              <h2>팔로우 페이지</h2>

            </v-col>
            <v-col cols="3">
                <user-sidebar/>
            </v-col>
        </v-row>

    </v-container>


  </div>
</template>

<script>
import userSidebar from '../../component/user_sidebar'

export default {
    name : 'Follow',
    components : {
        userSidebar
    }
}
</script>

<style>

</style>