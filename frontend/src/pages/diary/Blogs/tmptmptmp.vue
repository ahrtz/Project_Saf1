<template>
  <div class="new-post-container">
    <div class="d-flex new-post-inner justify-center">
      <div class="d-flex flex-shrink-0 flex-grow-0 new-post-sidebar">
        <div class="d-flex flex-column" style="width: 100%;">
          <div class="new-post-menu">임시저장 글 보기</div>
          <div>
            <div class="new-post-tmp-container" v-for="temp in tmppost" :key="temp.id">
              <div class="d-flex" style="margin-bottom: 4px;">
                <div class="d-flex flex-grow-0 new-post-tmp-title">날짜</div>
                <div class="new-post-tmp-text">{{temp.cdate}}</div>
              </div>
              <div class="d-flex" style="margin-bottom: 4px;">
                <div class="d-flex flex-grow-0 new-post-tmp-title">다이어리</div>
                <div class="new-post-tmp-text">{{temp.dName}}</div>
              </div>
              <div class="d-flex">
                <div class="d-flex flex-grow-0 new-post-tmp-title">글 제목</div>
                <div class="new-post-tmp-text">{{temp.title}}</div>
              </div>
              <div
                class="d-flex justify-center align-center s-button-blue"
                style="margin-top: 16px;height:30px;font-size:12px"
                @click="$router.push({name:'UpdatePost',params:{pid:temp.id}})"
              >수정</div>
            </div>
          </div>
        </div>
      </div>
      <div class="d-flex">
        <div class="d-flex flex-column" style="width: 100%">
          <div class="d-flex">
            <div class="d-flex" />
            <div
              class="d-flex justify-center align-center flex-grow-0 new-post-btn-white"
              @click="goback()"
            >뒤로가기</div>
          </div>
          <div class="new-post-title">포스트 수정</div>
          <div class="new-post-subtitle">제목</div>
          <v-text-field v-model="post.title" required outlined></v-text-field>
          <div class="new-post-subtitle">중요도</div>
          <v-rating v-model="post.priority" background-color="orange lighten-3" color="orange"></v-rating>

          <div v-if="selectedCommits.length!=0">
            <div
              class="new-post-commit-box"
              v-for="(scommit, index) in selectedCommits"
              :key="index"
            >
              <div class="new-post-commit-date">#{{index+1}} Commits on {{scommit.date}}</div>
              <div class="d-flex flex-column justify-center new-post-commit">
                <div class="new-post-commit-title">{{scommit.msg}}</div>
                <div class="d-flex">
                  <div class="new-post-commit-author">{{scommit.author}}</div>
                  <div class="d-flex" />
                  <div class="new-post-commit-sha">{{scommit.sha1}}</div>
                </div>
              </div>
              <div
                class="d-flex justify-center align-center flex-grow-0 s-button-red"
                @click="commitDelete(scommit.id),index"
              >삭제</div>
            </div>
          </div>

          <v-layout v-show="this.isProj==true">
            <v-dialog v-model="dialog" scrollable max-width="500px">
              <template v-slot:activator="{ on }">
                <div
                  class="d-flex justify-center align-center flex-grow-0 s-button-blue"
                  style="margin: 32px 0;"
                  v-on="on"
                >커밋 선택하기</div>
              </template>

              <v-card color="primary" dark v-if="commitList.length==0">
                <v-card-text>
                  commit 정보 받아 오는중
                  <v-progress-linear
                    indeterminate
                    color="white"
                    class="mb-0"
                    buffer-value="0"
                    stream
                  ></v-progress-linear>
                </v-card-text>
              </v-card>

              <v-card v-if="commitList.length!=0">
                <v-card-title>커밋 고르기</v-card-title>
                <v-divider></v-divider>
                <v-card-text>
                  <div
                    :id="'t'+commit.msg"
                    v-for="(commit,index) in commitList.slice((this.page-1)*10,(this.page*10))"
                    :key="index"
                  >
                    <input type="checkbox" v-model="selected" :value="commit" />
                    <label :for="commit">{{commit.msg}}</label>
                  </div>
                </v-card-text>
                <div class="text-center">
                  <v-pagination
                    v-model="page"
                    :length="Math.ceil(commitList.length/10)"
                    :total-visible="7"
                  ></v-pagination>
                </div>
                <v-divider></v-divider>
                <v-card-actions>
                  <div class="d-flex">
                    <div class="d-flex" />
                    <div
                      class="d-flex justify-center align-center flex-grow-0 s-button-blue"
                      @click="dialog = false"
                    >저장</div>
                  </div>
                </v-card-actions>
              </v-card>
            </v-dialog>
          </v-layout>
          <div v-if="selected.length!=0">
            <div class="new-post-commit-box" v-for="(commit, i) in selected" :key="i">
              <div
                class="new-post-commit-date"
              >#{{i+1+selectedCommits.length}} Commits on {{commit.date}}</div>
              <div class="d-flex flex-column justify-center new-post-commit">
                <div class="new-post-commit-title">{{commit.msg}}</div>
                <div class="d-flex">
                  <div class="new-post-commit-author">{{commit.author}}</div>
                  <div class="d-flex" />
                  <div class="new-post-commit-sha">{{commit.sha1}}</div>
                </div>
              </div>
            </div>
          </div>
          <div class="new-post-subtitle" style="margin-bottom:16px">내용</div>
          <editor />
          <!-- <v-textarea v-model="post.content" label="content" required outlined></v-textarea> -->

          <div class="new-post-subtitle">태그</div>
          <div class="d-flex">
            <v-text-field
              label="엔터, 스페이스 바를 눌러 태그를 추가하세요."
              v-model="tag"
              @keyup.enter="addtag()"
              @keyup.space="addtag()"
            ></v-text-field>
            <div
              class="d-flex justify-center align-center flex-grow-0 new-post-btn"
              style="margin-left: 32px"
              @click="addtag()"
            >태그추가</div>
          </div>
          <br />
          <!-- tags -->

          <div class="d-flex align-center flex-grow-0 new-post-tag-container">
            <div
              class="d-flex flex-grow-0 new-post-tag"
              v-for="(tag,index) in originaltag"
              :key="'t-1'+index"
            >
              <a>#{{tag.name}} &nbsp;</a>
              <button @click="tagdelete(tag.id)">
                <v-icon size="12" color="#0051cb">close</v-icon>
              </button>
            </div>
            <!-- 새태그 넣기 -->
            <div
              class="d-flex flex-grow-0 new-post-tag"
              v-for="(tag,index) in tags"
              :key="'t-'+index"
            >
              #{{tag}} &nbsp;
              <button @click="removetag(tag)">
                <v-icon size="12" color="#0051cb">close</v-icon>
              </button>
            </div>
          </div>

          <br />
          <div class="d-flex" style="margin-top: 72px">
            <div
              class="d-flex justify-center align-center flex-grow-0 s-button-white"
              @click="writetmpPost()"
            >임시저장</div>
            <div class="d-flex" />
            <div
              class="d-flex justify-center align-center flex-grow-0 s-button-white"
              @click="clear()"
              style="margin-right: 8px;"
            >초기화</div>
            <div
              class="d-flex justify-center align-center flex-grow-0 s-button-blue"
              @click="writePost()"
            >작성</div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import editor from '@/component/editor.vue';
import bus from '@/component/EventBus';

axios.defaults.headers.common['Access-Control-Allow-Origin'] = '*';
export default {
  name: 'NewBlogPost',
  components: {
    editor,
  },
  data() {
    return {
      drawer: null,
      dialog: false,
      diarys: '',
      tag: '',

      tags: [],
      did: this.$route.params.did,
      selected: [],
      page: 1,
      config: {
        uid: '',
        isProj: 2,
        keyword: '',
        isTemp: 1,
        limit: 10,
      },
      post: {
        uid: '',
        did: this.$route.params.did,
        title: '',
        content: '',
        priority: 0,
        cntLike: 0,
        isTemp: 0,
        cDate: new Date().toISOString().substr(0, 10),
      },
      commitList: [],
      tmppost: [],
      newtag: {},
      testing: {},
    };
  },
  props: ['value'],
  async created() {
    bus.$on('updateContent', this.updateContent);

    this.post.uid = this.$store.state.user.id;
    this.config.uid = this.$store.state.user.id;
    try {
      let diaryid = this.did;
      let tempspace = await this.$api.individualDiary(diaryid);
      this.diarys = tempspace;
      // console.log('아이디 받음');
      if (tempspace.isProj) {
        try {
          let repoId = this.diarys.repoId;
          let listCommit = await this.$api.getCommitList({ repoId: repoId });

          // let testing = await this.$api.forTest({repoId:repoId})
          // this.commitList=testing

          this.commitList = listCommit;
          // console.log('커밋 받음');
        } catch (e) {
          // console.log('커밋쪽 에러');
        }
      }
    } catch (e) {
      // console.log(e);
    }
    try {
      let tmpspace = await this.$api.searchTemp(this.config);
      this.tmppost = tmpspace.data;
      // console.log('성공');
    } catch (e) {
      // console.log(e);
    }
  },
  methods: {
    updateContent(content) {
      this.post.content = content;
    },

    clear() {
      this.$refs.form.reset();
    },
    goback() {
      this.$router.go(-1);
    },
    removetag(data) {
      let index = this.tags.indexOf(data);
      this.tags.splice(index, 1);
    },
    addtag() {
      if (this.tag == '' || this.tag == null) {
        alert('값이 입력되지 않았습니다');
      } else if (this.tags.length != 0) {
        for (var i = 0; i < this.tags.length; i++) {
          if (this.tags[i].toLowerCase() == this.tag.toLowerCase()) {
            alert(this.tags[i], this.tag.toLowerCase(), '###');
            this.tag = '';
            return;
          }
        }
      }
      this.tags.push(this.tag);
      this.tag = '';

      //tags 가져오기
    },

    addcommit(commit) {
      this.post.selected.push(commit);
    },

    async writePost() {
      if (this.post.title == '' || this.post.title == null) {
        alert('제목은 빈칸이 올 수 없습니다.');
      } else {
        try {
          let post = await this.$api.savePost(this.post);
          // console.log(post.data, 'vdas');
          try {
            if (this.selected.length != 0) {
              for (var i = 0; i < this.selected.length; i++) {
                this.selected[i].uid = this.diarys.uid;
                this.selected[i].pid = post.data;
                this.selected[i].sha = this.selected[i].sha1;
                this.selected[i].date = this.selected[i].date;
                delete this.selected[i].sha1;
                await this.$api.addCommit(this.selected[i]);
                // console.log('성공', i);
              }
            }
            // 여기선 태그를 저장해줘야댐
            if (this.tags.length != 0) {
              for (var i = 0; i < this.tags.length; i++) {
                this.newtag.pid = post.data;
                this.newtag.name = this.tags[i];
                await this.$api.createTag(this.newtag);
              }
            }

            this.$router.push({
              name: 'BlogDetail',
              params: { uid: this.config.uid, did: this.did },
            });
            // console.log(this.selected);
          } catch (e) {
            // console.log(e);
          }
        } catch (e) {
          // console.log(e);
          // console.log('실패');
        }
      }
    },
    async writetmpPost() {
      if (this.post.title == '' || this.post.title == null) {
        alert('제목은 빈칸이 올 수 없습니다.');
      } else {
        this.post.isTemp = 1;

        try {
          let post = await this.$api.savePost(this.post);
          // console.log(post.data, 'vdas');
          try {
            if (this.selected.length != 0) {
              for (var i = 0; i < this.selected.length; i++) {
                this.selected[i].uid = this.diarys.uid;
                this.selected[i].pid = post.data;
                this.selected[i].sha = this.selected[i].sha1;
                this.selected[i].date = this.selected[i].date;
                delete this.selected[i].sha1;
                await this.$api.addCommit(this.selected[i]);
                // console.log('성공', i);
              }
            }
            if (this.tags.length != 0) {
              for (var i = 0; i < this.tags.length; i++) {
                this.newtag.pid = post.data;
                this.newtag.name = this.tags[i];
                await this.$api.createTag(this.newtag);
              }
            }
            this.$router.push({
              name: 'BlogDetail',
              params: { uid: this.config.uid, did: this.did },
            });
            // console.log(this.selected);
          } catch (e) {
            // console.log(e);
          }
        } catch (e) {
          // console.log(e);
          // console.log('실패');
        }
      }
    },
    mvUrl(url) {
      window.open(url, '_blank');
    },
  },
  watch: {
    selected() {
      bus.$emit('getCommits', this.selected);
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
.new-post-container {
  width: 100%;
}

.new-post-inner {
  padding-bottom: 70px;
  width: 1140px;
  margin: 0 auto;
}

.new-post-btn {
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

.new-post-btn-white {
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
.new-post-tag-container {
  width: 100%;
  overflow: hidden;
  margin: 32px 0;
  flex-wrap: wrap;
}

.new-post-tag {
  margin-bottom: 8px;
  margin-right: 8px;
  padding: 0 6px 0 12px;
  background: #fff;
  border: solid 1px #0051cb;
  border-radius: 20px;
  cursor: pointer;
  color: #0051cb;
  font-size: 14px;
}

.new-post-title {
  font-size: 18px;
  font-weight: 800;
  margin-bottom: 48px;
}

.new-post-subtitle {
  font-size: 14px;
  font-weight: 600;
}

.new-post-sidebar {
  width: 268px;
  margin-right: 32px;
  padding: 20px 8px 8px 8px;
  border: solid 1px #dde3eb;
}

.new-post-menu {
  font-size: 14px;
  padding: 16px;
  font-weight: 700;
}

.new-post-tmp-container {
  padding: 16px;
  border: solid 1px #dde3eb;
  border-radius: 6px;
}

.new-post-tmp-title {
  width: 80px;
  font-size: 12px;
}

.new-post-tmp-text {
  font-size: 12px;
  font-weight: 600;
}
</style>