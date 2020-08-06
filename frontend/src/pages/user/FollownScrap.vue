<template>
  <div class="follow-container">
    <div class="d-flex follow-inner">
      <div class="d-flex justify-center">
        <!-- <div class="d-flex flex-column" style="width: 500px;">
          <div class="scrap-title">팔로우 리스트</div>
          <div>준비중입니다.</div>
        </div> -->
        <v-container>
            <v-row>
                <v-col col="6">팔로워</v-col>
                <v-col col="6">팔로잉</v-col>
            </v-row>
            <v-row>
                스크랩
            </v-row>
        </v-container>
      </div>
      <user-sidebar />
    </div>
  </div>
</template>


<script>
import userSidebar from '../../component/user_sidebar';

export default {
    name : 'FollownScrap',
    components:{
        userSidebar,

    },
}
</script>

<style>

</style>