<template>
  <div class="d-flex flex-column s-ranking-container">
    <div class="d-flex align-center justify-center">
      <img style="width: 25px;height:25px;" src="/static/images/crown.png" />
      <div class="s-ranking-title">Tag Ranking</div>
      <img style="width: 25px;height:25px;" src="/static/images/crown.png" />
    </div>
    <div class="d-flex">
      <div
        v-for="(rtag, rti) in data"
        :key="`rank_tag-${rti}`"
        class="d-flex align-center s-ranking-item"
        @click="onTagClick(rtag.name)"
      >
        <div
          class="d-flex algin-center justify-center flex-shrink-0 flex-grow-0 s-ranking-number"
          :class="{
                    's-ranking-gold': rti+1==1,
                    's-ranking-silver': rti+1==2,
                    's-ranking-bronze': rti+1==3
                }"
        >{{rti+1}}</div>
        <div class="d-flex s-ranking-tag">{{rtag.name}}</div>
        <div class="d-flex flex-grow-0 flex-shrink-0 s-ranking-cnt">{{rtag.cnt}}회</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 's-ranking',
  props: {
    data: {},
  },
  methods: {
    onTagClick(key) {
      this.$router.push({
        name: 'tmp',
        params: { key: key, type: 'tag' },
      });
    },
  },
};
</script>

<style>
.s-ranking-container {
  padding-bottom: 10px;
  margin-bottom: 30px;
  border-bottom: solid 1px #ddd;
}

.s-ranking-title {
  font-size: 20px;
  padding: 0 8px;
  font-weight: 600;
  letter-spacing: -1.2px;
}

.s-ranking-item {
  margin: 8px;
  margin-top: 16px;
  padding: 8px;
  cursor: pointer;
}

.s-ranking-item:hover {
  background: #deebff;
  border-radius: 6px;
}

.s-ranking-number {
  border-radius: 50%;
  width: 35px;
  height: 35px;
  font-size: 20px;
  font-weight: bold;
  margin-right: 8px;
}

.s-ranking-gold {
  border: solid 2px rgb(255, 211, 66);
  background: rgba(255, 211, 66, 0.2);
}

.s-ranking-silver {
  border: solid 2px rgb(182, 182, 182);
  background: rgba(182, 182, 182, 0.2);
}

.s-ranking-bronze {
  border: solid 2px rgb(207, 135, 68);
  background: rgba(207, 135, 68, 0.2);
}

.s-ranking-tag {
  font-size: 16px;
  letter-spacing: -1px;
  font-weight: bold;
  color: #21262ec0;
  overflow: hidden;
}

.s-ranking-cnt {
  font-size: 12px;
  color: #21262ec0;
}
</style>