<template>
  <div class="scrap-container">
    <div class="d-flex scrap-inner">
      <div class="d-flex justify-center">
        <div class="d-flex flex-column" style="width: 500px;">
          <div class="scrap-title">스크랩 리스트</div>
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
  name: 'Scrap',
  components: {
    userSidebar,
  },
};
</script>

<style>
.scrap-container {
  width: 100%;
}

.scrap-inner {
  padding-top: 77px;
  width: 1140px;
  margin: 0 auto;
}

.scrap-title {
  font-weight: 600;
  margin-bottom: 32px;
}
</style>