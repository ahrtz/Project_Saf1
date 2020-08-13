<template>
  <div class="d-flex flex-column status-board">
    <div class="d-flex">
      <div v-for="(t, ti) in 3" :key="ti" class="d-flex flex-grow-0">
        <div class="d-flex flex-column">
          <div
            v-for="(r, ri) in 4"
            :key="ri"
            class="status-board-tile-container d-flex flex-grow-0"
          >
            <div
              v-for="(c, ci) in dres.slice((ri+ti*4) * 7, (ri+ti*4) * 7 + 7)"
              :key="`ci-${ci}`"
              class="d-flex flex-grow-0 align-center justify-center status-board-tile"
              :class="{
            'status-board-level0': c == 0,
            'status-board-level1': c >= 1 && c <= 2,
            'status-board-level2': c >= 3 && c <= 4,
            'status-board-level3': c >= 5 && c <= 6,
            'status-board-level4': c >= 7,
        }"
            ></div>
            <div
              v-for="(i, ii) in res.slice((ri+ti*4) * 7, (ri+ti*4) * 7 + 7)"
              :key="`ii-${ii}`"
              class="status-board-tile-img"
            >
              <img
                v-if="type == 'project' && i >= 1"
                style="height: 36px"
                src="/static/images/sprout.png"
              />
              <img
                v-if="type == 'blog' && i >= 1"
                style="height: 24px;opacity:0.7;"
                src="/static/images/flower.png"
              />
              <img
                v-if="type == 'all' && i >= 1"
                style="height: 28px;opacity:0.6"
                src="/static/images/tree.png"
              />
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="d-flex">
      <div class="d-flex" />
      <div class="d-flex flex-grow-0">
        <img style="height: 40px" src="/static/images/legend.png" />
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
  props: {
    uid: 0,
    did: 0,
    repo: '',
    type: '',
  },
  data: () => ({
    dreq: {},
    dres: [],
    req: {},
    res: [],
  }),
  methods: {
    async getDStatus() {
      try {
        let tmp = await this.$api.getCommitStatus(this.dreq);
        this.dres = tmp;
      } catch (e) {
        console.log('commit status 가져오기 실패');
      }
      this.dres = Object.values(this.dres);
    },
    async getStatus() {
      try {
        let tmp = await this.$api.getPostStatus(this.req);
        this.res = tmp;
      } catch (e) {
        console.log('post status 가져오기 실패');
      }

      this.res = Object.values(this.res);
      console.log('***' + this.res);
    },
  },
  async created() {
    this.dreq.uid = this.req.uid = this.uid;
    this.req.did = this.did;
    this.dreq.repoName = this.repo;

    this.getDStatus();
    this.getStatus();
  },
};
</script>

<style>
.status-board {
  width: 100%;
  padding-bottom: 40px;
}

.status-board-tile-container {
  position: relative;
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

.status-board-tile-img {
  position: absolute;
}
</style>