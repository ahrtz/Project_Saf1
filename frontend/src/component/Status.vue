<template>
  <div class="d-flex flex-column status-board">
    <!-- 스테이터스를 넣어보자
      <br>
    이건 메인이랑 블로그 디테일이랑 다이어리 디테일에서 쓰일 겁니다.-->
    <!-- <profile class="" v-for="(i,index) in 3" :key="index">
    </profile>-->

    <!-- <img style="width: 100%" src="/static/images/grass.png" /> -->
    <div class="d-flex">
      <div v-for="(t, ti) in 3" :key="ti" class="d-flex flex-grow-0">
        <div class="d-flex flex-column">
          <div v-for="(r, ri) in 4" :key="ri" class="d-flex flex-grow-0">
            <div
              v-for="(c, ci) in data.slice(ri * 7, ri * 7 + 7)"
              :key="ci"
              class="d-flex flex-grow-0 align-center justify-center status-board-tile"
              :class="{
            'status-board-level0': c == 0,
            'status-board-level1': c == 1,
            'status-board-level2': c == 2,
            'status-board-level3': c == 3,
            'status-board-level4': c == 4
        }"
            >
            <img v-if="c==3" style="height: 24px;opacity:0.7;" src="/static/images/flower.png"/>
            <!-- <v-icon v-if="c==3" color='#ffa6c5' size="20">local_florist</v-icon> -->
            <img v-if="c==2" style="height: 36px" src="/static/images/sprout.png"/>
            <img v-if="c==4" style="height: 28px;opacity:0.6" src="/static/images/tree.png"/>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="d-flex">
      <div class="d-flex" />
      <div class="d-flex flex-grow-0">
        <img style="height: 55px" src="/static/images/legend.png" />
      </div>
    </div>
  </div>
</template>

<script>
import profile from './GitProfile.vue';
export default {
  name: 'status',
  components: {
    profile,
  },
  data: () => ({
    data: [
      1,
      3,
      0,
      0,
      1,
      2,
      4,
      2,
      0,
      4,
      1,
      2,
      3,
      0,
      3,
      1,
      2,
      2,
      0,
      4,
      0,
      1,
      3,
      0,
      4,
      1,
      2,
      1,
    ],
  }),
};
</script>

<style>
.status-board {
  width: 100%;
  padding-bottom: 40px;
}

.status-board-tile {
  width: 36px;
  height: 36px;
  border-radius: 4px;
  margin: 2px;
}

.status-board-level0 {
  background-color: rgba(27, 31, 35, 0.04);
}

.status-board-level1 {
  background-color: rgba(169, 230, 154, 0.2);
}

.status-board-level2 {
  background-color: rgba(169, 230, 154, 0.3);
}

.status-board-level3 {
  background-color: rgba(169, 230, 154, 0.4);
}

.status-board-level4 {
  background-color: rgba(169, 230, 154, 0.5);
}
</style>