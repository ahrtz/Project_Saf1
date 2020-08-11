<template>
  <div class="post-detail-container">
    <div class="d-flex post-detail">
      <div class="d-flex flex-grow-0 post-detail-contact">
        <s-contact />
      </div>
      <div class="d-flex">
        <div class="d-flex flex-column" style="width:100%">
          <div class="d-flex">
            <div
              class="d-flex flex-grow-0 align-center justify-center post-detail-blue-btn"
              @click="goback()"
            >목록</div>
            <div class="d-flex" />
            <div
              class="d-flex flex-grow-0 align-center justify-center post-detail-white-btn"
              style="margin-right: 4px;"
              v-show="checkWriter"
              @click="$router.push({name:'UpdatePost',params:{pid:tmp.id}})"
            >수정</div>
            <div
              class="d-flex flex-grow-0 align-center justify-center post-detail-red-btn"
              v-show="this.uid==this.tmp.uid"
              @click="deleteP(tmp.id)"
            >삭제</div>
          </div>

          <div class="d-flex justify-center post-detail-title">{{tmp.title}}</div>
          <div class="d-flex">
            <v-rating
              v-model="tmp.priority"
              background-color="orange lighten-3"
              color="orange"
              readonly
            ></v-rating>
            <div class="d-flex" />
            <div>{{tmp.cdate}}</div>
          </div>
          <div class="post-detail-commit-container" v-if="checkcommit">
            <div v-if="!commitcheck">
              <div
                class="post-detail-commit-box"
                v-for="commit in commitList.slice(0,5)"
                :key="commit.cid"
              >
                <div class="post-detail-commit-date">Commits on {{commit.date}}</div>
                <div class="d-flex flex-column justify-center post-detail-commit">
                  <div class="post-detail-commit-title">{{commit.msg}}</div>
                  <div class="d-flex">
                    <div class="post-detail-commit-author">{{commit.author}}</div>
                    <div class="d-flex" />
                    <div class="post-detail-commit-sha">{{commit.sha}}</div>
                  </div>
                </div>
              </div>
            </div>
            <div
              v-if="commitcheck==false & commitList.length>5"
              class="d-flex align-center justify-center post-detail-white-btn"
              @click="commitwide()"
            >펼치기</div>
            <div v-if="commitcheck">
              <div class="post-detail-commit-box" v-for="commit in commitList" :key="commit.cid">
                <div class="post-detail-commit-date">Commits on {{commit.date}}</div>
                <div class="d-flex flex-column justify-center post-detail-commit">
                  <div class="post-detail-commit-title">{{commit.msg}}</div>
                  <div class="d-flex">
                    <div class="post-detail-commit-author">{{commit.author}}</div>
                    <div class="d-flex" />
                    <div class="post-detail-commit-sha">{{commit.sha}}</div>
                  </div>
                </div>
              </div>
            </div>
            <div
              v-if="commitcheck"
              class="d-flex align-center justify-center post-detail-white-btn"
              @click="commitwide()"
            >접기</div>
          </div>
          <div class="post-detail-content">{{tmp.content}}</div>

          <div class="d-flex align-center flex-grow-0 post-detail-tag-container">
            <div
              class="d-flex flex-grow-0 post-detail-tag"
              v-for="tag in tags"
              :key="'t-'+tag.id"
              @click="searchTag(tag.name)"
            >#{{tag.name}}</div>
          </div>
          <div class="d-flex algin-center">
            <div v-if="likeData.likechecked" @click="like()" class="post-detail-icon">
              <v-icon color="#db4455" size="32">favorite</v-icon>
            </div>
            <div v-if="!likeData.likechecked" @click="like()" class="post-detail-icon">
              <v-icon color="#db4455" size="32">favorite_border</v-icon>
            </div>
            <div class="post-detail-like">{{tmp.cntLike}}</div>
            <div @click="scrap" class="post-detail-icon">
              <v-icon color="#e8a317" size="32">stars</v-icon>
            </div>
            <div @click="grapurl()" class="post-detail-icon">
              <v-icon color="#808080" size="32">share</v-icon>
            </div>
          </div>
          <div class="post-detail-comment-container">
            <div
              class="d-flex flex-column post-detail-comment-list-box"
              v-for="comment in comments"
              :key="comment.id"
            >
              <div class="d-flex align-center">
                <div class="d-flex flex-grow-0">
                  <img class="post-detail-comment-img" :src="comment.userinfo.img" />
                </div>
                <div class="post-detail-comment-id">{{comment.userinfo.nickname}}</div>
              </div>
              <div class="d-flex align-center">
                <div class="d-flex post-detail-comment-content">{{comment.content}}</div>
                <div
                  class="d-flex justify-center align-center flex-grow-0 post-detail-red-btn"
                  style="margin-left: 8px;"
                  v-if="isWritten(comment.uid)"
                  @click="commenterase(comment.id)"
                >삭제</div>
              </div>
              <div class="d-flex post-detail-comment-date">{{comment.cdate}}</div>
            </div>
            <div class="d-flex justify-center align-center post-detail-comment-box">
              <!-- v-model="commentData.content" -->
              <v-text-field id="post-comment-content" dense outlined hide-details />
              <div
                style="margin-left: 8px"
                class="d-flex flex-grow-0 align-center justify-center post-detail-blue-btn"
                @click="commentwrite()"
              >작성</div>
            </div>
          </div>
        </div>
      </div>
      <!-- <aside>
          <ContentSidebar/>
      </aside>-->
    </div>
  </div>
</template>

<script>
import SContact from '../../../component/s-contact.vue';
import ContentSidebar from '../../../component/ContentSidebar.vue';
import axios from 'axios';

export default {
  name: 'PostDetail',
  components: { SContact, ContentSidebar },
  data() {
    return {
      uid: null,
      id: this.$route.params, //pid임
      commitcheck: false,
      commentData: {
        uid: '',
        pid: '',
        cDate: '',
        content: '',
      },
      tmp: {}, //post detail
      likeData: {
        likechecked: false,
      },
      likedummy: '',
      tags: {},
      commitList: [],
      comments: [],
    };
  },
  async created() {
    this.uid = this.$store.state.user.id;
    //comment 데이터 가져오기
    this.getComment();

    //post 데이터 가져오기
    try {
      let tmpspace = await this.$api.postdetail(this.id.pid);
      this.tmp = tmpspace;
      // console.log('성공')
    } catch (e) {
      console.log(e);
    }
    //좋아요 데이터 가져오기
    try {
      let tmpspace1 = await this.$api.likedatas(this.id.pid);
      this.likedummy = tmpspace1;
      if (tmpspace1.length != 0) {
        if (tmpspace1.status == 1) {
          this.likeData.likechecked = true;
        } else {
          this.likeData.likechecked = false;
        }
      } else {
        this.likeData.likechecked = false;
      }
    } catch (e) {
      console.log(e);
    }
    //commit data가져오기
    try {
      let tmpspace2 = await this.$api.getPostCommit(this.id.pid);
      this.commitList = tmpspace2;
      console.log('커밋 부르기 성공');
    } catch (e) {
      console.log(e);
    }

    //tag 데이터 가져오기
    try {
      let tmpspace3 = await this.$api.tagIndex(this.id.pid);
      this.tags = tmpspace3;
    } catch (e) {
      console.log(e);
    }

    console.log('***' + this.commitList[0]);
  },
  methods: {
    async getComment() {
      try {
        let temp = await this.$api.getCommentlist(this.id.pid);
        this.comments = temp;
        // console.log('Comment 목록 가져오기 성공')
      } catch (e) {
        console.log(e);
      }
    },
    getcDate() {
      var d = new Date();

      var ymd = d.toISOString().substr(0, 10);
      var timestamp =
        ('00' + d.getHours()).slice(-2) +
        ':' +
        ('00' + d.getMinutes()).slice(-2) +
        ':' +
        ('00' + d.getSeconds()).slice(-2);

      this.commentData.cDate = ymd + ' ' + timestamp;
      //console.log(this.commentData.cDate)
    },
    goback() {
      this.$router.go(-1);
    },
    async like() {
      if (this.likeData.likechecked == true) {
        this.likeData.likechecked = false;
        this.$api.likeDislike({ pid: this.id.pid, status: 0 });
        console.log('좋아요 취소');
      } else {
        console.log('좋아요');
        this.likeData.likechecked = true;
        this.$api.likeDislike({ pid: this.id.pid, status: 1 });
      }

      try {
        let tmpspace = await this.$api.postdetail(this.id.pid);
        this.tmp = tmpspace;
        // console.log('성공')
      } catch (e) {
        console.log(e);
      }
      this.$forceUpdate();
    },
    searchTag(tagName) {
      document.getElementById('header-text').value = tagName;
      this.$router.push({ name: 'tmp', params: { key: tagName, type: 'tag' } });
    },
    grapurl() {
      alert(this.$route.path);
    },
    commitwide() {
      if (this.commitcheck == false) {
        this.commitcheck = true;
      } else {
        this.commitcheck = false;
      }
    },
    scrap() {
      this.$api.makeScrap({ pid: this.id.pid, status: 1 });
      alert('스크랩 되었습니다');
    },
    async commentwrite() {
      this.commentData.content = document.getElementById(
        'post-comment-content'
      ).value;
      //alert('준비중입니다.')
      this.commentData.uid = this.$store.state.user.id;
      // this.getcDate()
      this.commentData.pid = this.id.pid;
      await this.$api.createComment(this.commentData);
      this.getComment();
    },
    async commenterase(commentid) {
      // console.log("CKCK commentid")
      await this.$api.deleteComment(commentid);
      this.getComment();
    },
    isWritten(authorid) {
      this.uid = this.$store.state.user.id;

      if (authorid == this.uid) {
        // console.log("ckck 같다.")
        return true;
      } else {
        // console.log("ckck 다르다.")
        return false;
      }
    },
    async deleteP(postid) {
      try {
        this.$api.deletePost(postid);
        // console.log('성공')
        this.$router.go(-1);
      } catch (e) {
        console.log(e);
      }
    },
  },
  computed: {
    limited: function () {
      return this.commitList;
    },
    checkcommit() {
      if (this.commitList.length == 0) {
        return false;
      } else {
        return true;
      }
    },
    likechecking() {
      if (this.likedummy.status == 1) {
        this.likeData.likechecked == true;
      } else if ((this.likedummy, length == 0)) {
        this.likeData.likechecked == false;
      } else {
        this.likeData.likechecked == false;
      }
    },
    putpid() {
      return (this.commentData.pid = this.id.pid);
    },
    checkWriter() {
      // console.log(123);
      this.uid = this.$store.state.user.id;
      // console.log(this.uid);
      // console.log(this.tmp.uid);
      return this.uid == this.tmp.uid;
    },
  },
};
</script>

<style>
.v-rating .v-icon {
  padding: 0;
}

.post-detail-container {
  width: 100%;
}

.post-detail {
  width: 1140px;
  margin: 0 auto;
}

.post-detail-contact {
  width: 300px !important;
}

.post-detail-blue-btn {
  font-size: 14px;
  background: #0051cb;
  font-weight: 600;
  color: #fff;
  border-radius: 6px;
  padding: 0 16px;
  height: 40px;
  cursor: pointer;
}

.post-detail-white-btn {
  font-size: 14px;
  background: #fff;
  font-weight: 600;
  color: #0051cb;
  border: solid 1px #0051cb;
  border-radius: 6px;
  padding: 0 16px;
  height: 40px;
  cursor: pointer;
}

.post-detail-red-btn {
  font-size: 14px;
  background: #db4455;
  font-weight: 600;
  color: #fff;
  border-radius: 6px;
  padding: 0 16px;
  height: 40px;
  cursor: pointer;
}

.post-detail-title {
  margin-top: 16px;
  min-height: 100px;
  font-size: 36px;
}

.post-detail-icon {
  margin-right: 4px;
  cursor: pointer;
}

.post-detail-tag-container {
  width: 100%;
  overflow: hidden;
  margin: 32px 0;
  flex-wrap: wrap;
}

.post-detail-tag {
  margin-bottom: 8px;
  margin-right: 8px;
  padding: 0 12px;
  background: #fff;
  border: solid 1px #0051cb;
  border-radius: 20px;
  cursor: pointer;
  color: #0051cb;
  font-size: 14px;
}

.post-detail-like {
    color: #db4455;
    font-weight: bold;
    font-size: 20px;
    margin-right: 8px;
}

.post-detail-commit-container {
  margin-top: 32px;
  padding: 16px;
  background: #fff;
  border-radius: 8px;
  border: solid 1px #dde3ea;
}

.post-detail-commit-box {
  border-left: solid 2px #dde3ea;
  padding-left: 16px;
  margin-bottom: 22px;
}

.post-detail-commit {
  border: solid 1px #dde3ea;
  padding: 8px;
  height: 60px;
  border-radius: 6px;
}

.post-detail-commit:hover {
  background: #0051cb11;
}

.post-detail-commit-title {
  font-size: 14px;
  font-weight: 800;
}

.post-detail-commit-date {
  margin-bottom: 12px;
  font-size: 12px;
  font-weight: normal;
}

.post-detail-commit-author {
  font-size: 12px;
  font-weight: 600;
  color: #24292e;
}

.post-detail-commit-sha {
  font-size: 10px;
  font-weight: normal;
}

.post-detail-content {
  margin: 48px 0;
  min-height: 150px;
}

.post-detail-comment-container {
  margin-top: 32px;
  border-radius: 6px;
  background: #0051cb11;
  padding: 24px 32px;
}

.post-detail-comment-box {
  background: #fff;
  border: solid 1px #dde3ea;
  border-radius: 6px;
  padding: 32px 16px;
}

.post-detail-comment-list-box {
  margin-bottom: 24px;
  padding-bottom: 8px;
  border-bottom: solid 1px #dde3ea;
}

.post-detail-comment-img {
  margin-right: 8px;
  width: 30px;
  height: 30px;
  border-radius: 50%;
  border: solid 1px #dde3ea;
}

.post-detail-comment-date {
  color: rgb(102, 102, 102);
  font-size: 12px;
}

.post-detail-comment-id {
  font-size: 12px;
  font-weight: 600;
}

.post-detail-comment-content {
  font-size: 14px;
}
</style>
