<template>
  <div class="update-post-container">
    <div class="d-flex update-post-inner justify-center">
      <div class="d-flex flex-shrink-0 flex-grow-0 update-post-sidebar">
        <div class="d-flex flex-column" style="width: 100%;">
          <div class="update-post-menu">임시저장 글 보기</div>
          <div>
            <div class="update-post-tmp-container" v-for="temp in tmppost" :key="temp.id">
              <div class="d-flex" style="margin-bottom: 4px;">
                <div class="d-flex flex-grow-0 update-post-tmp-title">날짜</div>
                <div class="update-post-tmp-text">{{temp.cdate}}</div>
              </div>
              <div class="d-flex" style="margin-bottom: 4px;">
                <div class="d-flex flex-grow-0 update-post-tmp-title">다이어리</div>
                <div class="update-post-tmp-text">{{temp.dName}}</div>
              </div>
              <div class="d-flex">
                <div class="d-flex flex-grow-0 update-post-tmp-title">글 제목</div>
                <div class="update-post-tmp-text">{{temp.title}}</div>
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
              class="d-flex justify-center align-center flex-grow-0 update-post-btn-white"
              @click="goback()"
            >뒤로가기</div>
          </div>
          <div class="update-post-title">포스트 수정</div>
          <div class="update-post-subtitle">제목</div>
          <v-text-field v-model="post.title" required outlined></v-text-field>
          <div class="update-post-subtitle">중요도</div>
          <v-rating v-model="post.priority" background-color="orange lighten-3" color="orange" style="margin-bottom:32px;"></v-rating>

          <div v-if="selectedCommits.length!=0">
            <div
              class="update-post-commit-box"
              v-for="(scommit, index) in selectedCommits"
              :key="index"
            >
              <div class="update-post-commit-date" style="margin-top:4px;">#{{index+1}} Commits on {{scommit.date}}</div>
              <div class="d-flex align-center">
              <div class="d-flex flex-column justify-center flex-grow-1 update-post-commit">
                <div class="update-post-commit-title">{{scommit.msg}}</div>
                <div class="d-flex">
                  <div class="update-post-commit-author">{{scommit.author}}</div>
                  <div class="d-flex" />
                  <div class="update-post-commit-sha">{{scommit.sha1}}</div>
                </div>
              </div>
              <div
                class="d-flex justify-center align-center flex-grow-0 s-button-red"
                style="margin-left: 4px;height: 60px;font-size:12px;font-weight:500;padding: 12px"
                @click="commitDelete(scommit.id),index"
              >삭제</div>
              </div>
              
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
            <div class="update-post-commit-box" v-for="(commit, i) in selected" :key="i">
              <div
                class="update-post-commit-date"
              >#{{i+1+selectedCommits.length}} Commits on {{commit.date}}</div>
              <div class="d-flex flex-column justify-center update-post-commit">
                <div class="update-post-commit-title">{{commit.msg}}</div>
                <div class="d-flex">
                  <div class="update-post-commit-author">{{commit.author}}</div>
                  <div class="d-flex" />
                  <div class="update-post-commit-sha">{{commit.sha1}}</div>
                </div>
              </div>
            </div>
          </div>
          <div class="update-post-subtitle" style="margin-bottom:16px">내용</div>
          <editor />
          <!-- <v-textarea v-model="post.content" label="content" required outlined></v-textarea> -->

          <div class="update-post-subtitle">태그</div>
          <div class="d-flex">
            <v-text-field
              label="엔터, 스페이스 바를 눌러 태그를 추가하세요."
              v-model="tag"
              @keyup.enter="addtag()"
              @keyup.space="addtag()"
            ></v-text-field>
            <div
              class="d-flex justify-center align-center flex-grow-0 update-post-btn"
              style="margin-left: 32px"
              @click="addtag()"
            >태그추가</div>
          </div>
          <br />
          <!-- tags -->

          <div class="d-flex align-center flex-grow-0 update-post-tag-container">
            <div
              class="d-flex flex-grow-0 update-post-tag"
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
              class="d-flex flex-grow-0 update-post-tag"
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
  name: 'UpdatePost',
  components: {
    editor,
  },
  data() {
    return {
      drawer: null,
      dialog: false,
      tag: '',
      newtag: {
        pid: '',
        name: '',
      },
      tags: [],
      originaltag: {},
      pid: this.$route.params.pid,
      selected: [],
      selectedCommits: [],
      page: 1,
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
      config: {
        uid: '',
        isProj: 2,
        keyword: '',
        isTemp: 1,
        limit: 10,
      },
      isProj: false,
      tmppost: [],
    };
  },
  props: ['value'],
  async created() {
    bus.$on('updateContent', this.updateContent);
    this.post.uid = this.$store.state.user.id;
    this.config.uid = this.$store.state.user.id;
    try {
      let tmpspace = await this.$api.postdetail(this.pid);
      this.post = tmpspace;
      // console.log('성공');
      bus.$emit('getContent', this.post.content);

      try {
        let tempspace1 = await this.$api.individualDiary(this.post.did);
        if (tempspace1.gitName.length > 0) {
          // console.log(tempspace1, 'vdsa');
          this.isProj = true;
          try {
            let listCommit = await this.$api.getCommitList({
              repoId: tempspace1.repoId,
            });
            this.commitList = listCommit;
            bus.$emit('getCommits', this.commitList);
          } catch (e) {
            // console.log('커밋 받아오기  에러');
          }
        }
      } catch (e) {
        // console.log('깃네임 받아오기 에러');
      }
    } catch (e) {
      // console.log(e);
    }
    try {
      let selectedCommit = await this.$api.getPostCommit(this.pid);
      this.selectedCommits = selectedCommit;
      // console.log('선택한 커밋 불러오기');
    } catch (e) {}
    //tags 가져오기
    try {
      let tmpspace3 = await this.$api.tagIndex(this.pid);
      this.originaltag = tmpspace3;
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
    // console.log(this.tags);
  },
  methods: {
    updateContent(content) {
      this.post.content = content;
    },

    clear() {
      this.$refs.form.reset();
    },
    searchTag(tagName) {
      document.getElementById('header-text').value = tagName;
      this.$router.push({ name: 'tmp', params: { key: tagName } });
    },
    goback() {
      this.$router.go(-1);
    },
    removetag(data) {
      let index = this.tags.indexOf(data);
      this.tags.splice(index, 1);
    },
    async tagdelete(id) {
      this.$api.deleteTag(id);
      let tmpspace3 = await this.$api.tagIndex(this.pid);
      this.originaltag = tmpspace3;
    },
    addtag() {
      if (this.tag == '' || this.tag == null) {
        alert('값이 입력되지 않았습니다');
      } else if (this.tags.length != 0) {
        for (var i = 0; i < this.tags.length; i++) {
          if (this.tags[i].toLowerCase() == this.tag.toLowerCase()) {
            alert('중복된 태그 입니다');
            this.tag = '';
            return;
          }
        }
      } else if (this.originaltag.length != 0) {
        for (var i = 0; i < this.originaltag.length; i++) {
          if (
            this.originaltag[i].name.toLowerCase() == this.tag.toLowerCase()
          ) {
            alert('중복된 태그 입니다');
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
        alert('제목은 빈칸이 올 수 없습니다');
      } else {
        try {
          this.post.isTemp = 0;
          await this.$api.updatePost(this.post);
          // console.log('성공11');
          try {
            if (this.selected.length != 0) {
              for (var i = 0; i < this.selected.length; i++) {
                // this.selected[i].uid = this.post.id
                this.selected[i].pid = this.pid;
                this.selected[i].sha = this.selected[i].sha1;
                this.selected[i].date = this.selected[i].date;
                delete this.selected[i].sha1;
                await this.$api.addCommit(this.selected[i]);
                // console.log(this.selected[i], '부악');
                // console.log('성공', i);
              }
            }
            if (this.tags.length != 0) {
              for (var i = 0; i < this.tags.length; i++) {
                this.newtag.pid = this.pid;
                this.newtag.name = this.tags[i];
                await this.$api.createTag(this.newtag);
              }
            }
            this.$router.push({
              name: 'PostDetail',
              params: { uid: this.config.uid, pid: this.pid },
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
        alert('제목은 빈칸이 올 수 없습니다');
      } else {
        this.post.isTemp = 1;

        try {
          await this.$api.updatePost(this.post);
          try {
            if (this.selected.length != 0) {
              for (var i = 0; i < this.selected.length; i++) {
                // this.selected[i].uid = this.post.id
                this.selected[i].pid = this.pid;
                this.selected[i].sha = this.selected[i].sha1;
                this.selected[i].date = this.selected[i].date.substr(0, 10);
                delete this.selected[i].sha1;
                await this.$api.addCommit(this.selected[i]);
                // console.log('성공', i);
              }
            }
            this.$router.push({
              name: 'PostDetail',
              params: { uid: this.config.uid, pid: this.pid },
            });
            // this.$router.go(-1);
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
    commitDelete(id, index) {
      this.$api.deleteCommit(id);
      this.selectedCommits.splice(index, 1);
    },
  },
  computed: {
    downcontent() {
      return this.post.content;
    },
  },
};
</script>

<style>
.update-post-container {
  width: 100%;
}

.update-post-inner {
  padding-bottom: 70px;
  width: 1140px;
  margin: 0 auto;
}

.update-post-btn {
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

.update-post-btn-white {
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
.update-post-tag-container {
  width: 100%;
  overflow: hidden;
  margin: 32px 0;
  flex-wrap: wrap;
}

.update-post-tag {
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

.update-post-title {
  font-size: 18px;
  font-weight: 800;
  margin-bottom: 48px;
}

.update-post-subtitle {
  font-size: 14px;
  font-weight: 600;
}

.update-post-sidebar {
  width: 268px;
  margin-right: 32px;
  padding: 20px 8px 8px 8px;
  border: solid 1px #dde3eb;
}

.update-post-menu {
  font-size: 14px;
  padding: 16px;
  font-weight: 700;
}

.update-post-tmp-container {
  padding: 16px;
  border: solid 1px #dde3eb;
  border-radius: 6px;
}

.update-post-tmp-title {
  width: 80px;
  font-size: 12px;
}

.update-post-tmp-text {
  font-size: 12px;
  font-weight: 600;
}

.update-post-commit-box {
  border-left: solid 2px #dde3ea;
  padding-left: 16px;
  margin-bottom: 22px;
}

.update-post-commit {
  border: solid 1px #dde3ea;
  padding: 8px;
  height: 60px;
  border-radius: 6px;
}

.update-post-commit:hover {
  background: #0051cb11;
  cursor: pointer;
}

.update-post-commit-title {
  font-size: 14px;
  font-weight: 800;
}

.update-post-commit-date {
  margin-bottom: 12px;
  font-size: 12px;
  font-weight: normal;
}

.update-post-commit-author {
  font-size: 12px;
  font-weight: 600;
  color: #24292e;
}

.update-post-commit-sha {
  font-size: 10px;
  font-weight: normal;
}
</style>