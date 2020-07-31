<template>
  <div class="follow-container">
    <div class="d-flex follow-inner">
      <div class="d-flex justify-center">
        <div class="d-flex flex-column" style="width: 500px;">
          <div class="scrap-title">팔로우 리스트</div>
          <div>준비중입니다.</div>
        </div>
      </div>
      <user-sidebar />
    </div>
  </div>
</template>

<script>
import userSidebar from '../../component/user_sidebar';

export default {
  name: 'Follow',
  components: {
    userSidebar,
  },
};
</script>

<style>
.follow-container {
  width: 100%;
}

.follow-inner {
  padding-top: 77px;
  width: 1140px;
  margin: 0 auto;
}

.follow-title {
  font-weight: 600;
  margin-bottom: 32px;
}
</style>