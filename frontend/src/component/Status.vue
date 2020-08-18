<template>
  <div class="d-flex flex-column status">
    <div class="d-flex justify-center status-title">
      <v-icon size="20">date_range</v-icon>&nbsp;Status Calendar
    </div>
    <div class="d-flex justify-center status-container">
      <div v-for="(t, ti) in 3" :key="ti" class="d-flex flex-grow-0">
        <div class="d-flex flex-column">
          <div v-for="(r, ri) in 4" :key="ri" class="status-tile-container d-flex flex-grow-0">
            <template v-if="isProj == 0">
              <div
                v-for="(d, di) in res.slice((ri+ti*4) * 7, (ri+ti*4) * 7 + 7)"
                :key="`di0-${di}`"
                class="d-flex flex-grow-0 align-center justify-center status-tile status-tile-yellow"
              >
                <img
                  v-if="d >= 1"
                  style="height: 24px;opacity:0.7;"
                  src="/static/images/flower.png"
                />
              </div>
            </template>

            <template v-if="isProj == 1">
              <div
                v-for="(c, ci) in dres.slice((ri+ti*4) * 7, (ri+ti*4) * 7 + 7)"
                :key="`ci-${ci}`"
                class="d-flex flex-grow-0 align-center justify-center status-tile status-level0"
                :class="{
            'status-level1': c >= 1 && c <= 2,
            'status-level2': c >= 3 && c <= 4,
            'status-level3': c >= 5 && c <= 6,
            'status-level4': c >= 7,
        }"
              ></div>

              <div
                v-for="(d, di) in res.slice((ri+ti*4) * 7, (ri+ti*4) * 7 + 7)"
                :key="`di1-${di}`"
                :style="`position:absolute;left:${di*38}px`"
              >
                <img v-if="d >= 1" style="height: 36px" src="/static/images/sprout.png" />
              </div>
            </template>

            <template v-if="isProj == 2">
              <div
                v-for="(c, ci) in dres.slice((ri+ti*4) * 7, (ri+ti*4) * 7 + 7)"
                :key="`ci-${ci}`"
                class="d-flex flex-grow-0 align-center justify-center status-tile status-level0"
                :class="{
            'status-level1': c >= 1 && c <= 2,
            'status-level2': c >= 3 && c <= 4,
            'status-level3': c >= 5 && c <= 6,
            'status-level4': c >= 7,
        }"
              ></div>

              <div
                v-for="(d, di) in comb.slice((ri+ti*4) * 7, (ri+ti*4) * 7 + 7)"
                :key="`di23-${di}`"
                :style="`position:absolute;left:${di*38}px`"
              >
                <img
                  v-if="d == 0"
                  style="height: 24px;opacity:0.7;"
                  src="/static/images/flower.png"
                />
                <img v-if="d == 1" style="height: 36px" src="/static/images/sprout.png" />
                <img v-if="d == 2" style="height: 28px;opacity:0.6;margin-top:6px;margin-left:6px;" src="/static/images/tree.png" />
              </div>
            </template>
          </div>
        </div>
        <div v-if="ti != 2" class="d-flex flex-grow-0 status-div" />
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
    uid: '',
    did: '',
    repoId: '',
    isProj: '',
  },
  data: () => ({
    dreq: {},
    dres: [],
    req: {},
    res: [],
    bres: [],
    pres: [],
    dData: [
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
    ],
    comb: [],
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
    },
  },
  async created() {
    this.dreq.uid = this.req.uid = this.uid;
    this.req.did = this.did ? this.did : '0';
    this.dreq.repoId = this.repoId ? this.repoId : '';
    this.req.isProj = this.isProj;
    console.log('--', this.isProj);
    if (this.isProj == 0) {
      this.getStatus();
    } else if (this.isProj == 1) {
      this.getDStatus();
      this.getStatus();
    } else if (this.isProj == 2) {
      this.getDStatus();

      this.req.isProj = 1;
      console.log(this.req.isProj);
      await this.getStatus();
      this.bres = this.res;
      console.log('blog' + this.bres + this.bres.length);

      this.req.isProj = 2;
      console.log(this.req.isProj);
      await this.getStatus();
      this.pres = this.res;
      console.log('project' + this.pres + this.pres.length);

      for (let i = 0; i < this.bres.length; ++i) {
        if (this.bres[i] && this.pres[i]) {
          this.comb[i] = 2;
        } else if (this.bres[i] && !this.pres[i]) {
          this.comb[i] = 0;
        } else if (!this.bres[i] && this.pres[i]) {
          this.comb[i] = 1;
        }
      }
    }
  },
};
</script>

<style>
.status {
  width: 100%;
  padding-bottom: 40px;
}

.status-container {
  width: 100%;
}

.status-title {
  padding-top: 8px;
  font-size: 18px;
  font-weight: 600;
  margin-bottom: 8px;
  color: rgb(0, 0, 0, 0.54);
}

.status-div {
  margin: 0 8px;
  border: dashed 2px #ddd;
}

.status-tile-container {
  position: relative;
}

.status-tile {
  width: 35px;
  height: 35px;
  border-radius: 4px;
  margin: 1.5px;
}

.status-tile-yellow {
  background-color: rgb(247, 240, 184);
}

.status-level0 {
  background-color: rgba(27, 31, 35, 0.04);
}

.status-level1 {
  background-color: rgba(155, 233, 168, 0.8);
}

.status-level2 {
  background-color: rgba(64, 196, 99, 0.8);
}

.status-level3 {
  background-color: rgba(48, 161, 78, 0.8);
}

.status-level4 {
  background-color: rgba(33, 110, 57, 0.8);
}

.status-tile-img {
  position: absolute;
}
</style>