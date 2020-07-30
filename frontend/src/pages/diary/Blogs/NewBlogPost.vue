<template>
  <div class="new-blog-post-container">
    <div class="d-flex flex-column new-blog-post-inner justify-center">
      <div class="d-flex">
        <div class="d-flex" />
        <div
          class="d-flex justify-center align-center flex-grow-0 new-blog-post-btn"
          @click="goback()"
        >뒤로가기</div>
      </div>
      <div class="d-flex flex-column" style="width: 800px;align-self:center">
        <div>포스트 작성</div>
        <br />
        <br />제목
        <v-text-field v-model="post.title" required outlined></v-text-field>중요도
        <v-rating v-model="post.priority" background-color="orange lighten-3" color="orange"></v-rating>
        <v-container v-show="isProj" fluid>
          여기 들어오는 곳이 다이어리에서 온건지 프로젝트에서 온건지 구분해서 v-show 걸면 될것
          <div :id="'t'+commit.cid" v-for="(commit,key,index) in commitList" :key="index">
            <p>
              <input
                type="checkbox"
                :id="key"
                v-model="selected"
                :value="{'cid':commit.cid,'commit':commit.commitcontent}"
              />
              <label :for="key">{{commit.commitcontent}}</label>
            </p>
          </div>
          {{post.selected}}
        </v-container>내용
        <v-textarea v-model="post.content" label="content" required outlined></v-textarea>

        <h3>태그</h3>
        <div class="d-flex">
        <v-text-field label="태그를 검색 혹은 추가" v-model="tag"></v-text-field>
        <div class="d-flex justify-center align-center flex-grow-0 new-blog-post-btn" @click="addtag()">태그추가</div>
        </div>
        <br />
        {{tags}}
        <br />
        <div class="d-flex" style="margin-top: 72px">
          <div
            class="d-flex justify-center align-center flex-grow-0 new-blog-post-btn-white"
            @click="writetmpPost()"
          >임시저장</div>
          <div class="d-flex" />
          <div
            class="d-flex justify-center align-center flex-grow-0 new-blog-post-btn-white"
            @click="clear()"
            style="margin-right: 8px;"
          >초기화</div>
          <div class="d-flex justify-center align-center flex-grow-0 new-blog-post-btn" @click="writePost()">작성</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
axios.defaults.headers.common['Access-Control-Allow-Origin'] = '*';
export default {
  name: 'NewBlogPost',
  data() {
    return {
      tag: '',
      tags: [],
      did: this.$route.params.did,
      selected: [],
      post: {
        uid: '',
        did: this.$route.params.did,
        title: '',
        content: '',
        priority: 0,
        cnt_like: 0,
        is_temp: 0,
        created_date: new Date().toISOString().substr(0, 10),
      },
      commitList: [
        { cid: 0, commitcontent: '1번 커밋', checked: false },
        { cid: 1, commitcontent: '2번 커밋', checked: false },
        { cid: 2, commitcontent: '3번 커밋', checked: false },
        { cid: 3, commitcontent: '4번 커밋', checked: false },
        { cid: 4, commitcontent: '5번 커밋', checked: false },
        { cid: 5, commitcontent: '6번 커밋', checked: false },
      ],
    };
  },
  props: ['value'],
  created() {
    this.post.uid = this.$store.state.user.id;
  },
  methods: {
    clear() {
      this.$refs.form.reset();
    },
    goback() {
      this.$router.go(-1);
    },
    addtag() {
      const tmp = '#' + this.tag;
      this.tags.push(tmp);
      this.tag = '';
    },
    addcommit(commit) {
      this.post.selected.push(commit);
    },
    toggle() {
      this.$emit('input', !this.value);
      console.log(this.post);
    },
    async writePost() {
      try {
        this.$api.savePost(this.post);
        console.log('성공');
        this.$router.go(-1);
      } catch (e) {
        console.log(e);
        console.log('실패');
      }
    },
    writetmpPost() {
      this.post.is_temp = 1;

      try {
        this.$api.savePost(this.post);
        console.log('성공');
        this.$router.go(-1);
      } catch (e) {
        console.log(e);
        console.log('실패');
      }
    },
  },
  computed: {
    userid() {},
    isProj() {
      if (this.$route.path[7] == 'p') {
        return true;
      } else {
        return false;
      }
    },
  },
};
</script>

<style>
.new-blog-post-container {
  width: 100%;
}

.new-blog-post-inner {
  padding-top: 77px;
  width: 1140px;
  margin: 0 auto;
}

.new-blog-post-btn {
  padding: 0 16px;
  margin-bottom: 8px;
  font-size: 14px;
  background: #0051cb;
  font-weight: 600;
  color: #fff;
  border-radius: 6px;
  height: 40px;
  cursor: pointer;
}

.new-blog-post-btn-white {
  padding: 0 16px;
  font-size: 14px;
  background: #fff;
  font-weight: 600;
  color: #0051cb;
  border: solid 1px #0051cb;
  border-radius: 6px;
  height: 40px;
  cursor: pointer;
}
</style>
