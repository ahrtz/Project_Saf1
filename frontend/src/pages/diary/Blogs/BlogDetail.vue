<template>
  <div class="blog-detail-container">
    <div class="d-flex flex-grow-0 blog-detail-inner">
      <div class="d-flex flex-grow-0" style="width: 300px">
        <div class="blog-detail-contact">
          <s-contact />
        </div>
      </div>
      <div class="d-flex flex-column" style="width: 840px">
        <div class="d-flex align-center" style="width: 100%;margin-bottom:8px">
          <div class="blog-detail-title">{{diarydata.title}}</div>
          <div class="d-flex" />

          <v-dialog v-model="dialog" scrollable max-width="600px" v-if="diaryid.uid==mydata.id">
            <template v-slot:activator="{ on }">
              <v-btn
                color="info"
                dark
                v-on="on"
                @click="updateData()"
              >{{isProj ? "프로젝트" : "블로그"}} 수정</v-btn>
            </template>

            <v-card>
              <v-card-title>{{isProj ? "프로젝트" : "블로그"}} 수정</v-card-title>
              <v-divider></v-divider>
              <v-card-text>
                {{isProj ? "프로젝트" : "블로그"}} 타이틀 :
                <v-text-field v-model="updatediary.title" type="text" placeholder="다이어리 이름을 입력하세요"></v-text-field>간단 설명
                <v-textarea v-model="updatediary.intro" label="intro"></v-textarea>대표 이미지
                <input
                  @change="onFileSelected($event)"
                  ref="file"
                  type="file"
                  name="file"
                  accept="image/*"
                />

                <v-menu
                  v-model="menu1"
                  :close-on-content-click="false"
                  :nudge-right="40"
                  transition="scale-transition"
                  offset-y
                  min-width="290px"
                >
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      v-model="updatediary.sdate"
                      label="시작날짜"
                      prepend-icon="event"
                      readonly
                      v-bind="attrs"
                      v-on="on"
                    ></v-text-field>
                  </template>
                  <v-date-picker v-model="updatediary.sdate" @input="menu1 = false"></v-date-picker>
                </v-menu>
                <v-menu
                  v-model="menu2"
                  :close-on-content-click="false"
                  :nudge-right="40"
                  transition="scale-transition"
                  offset-y
                  min-width="290px"
                >
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      v-model="updatediary.edate"
                      label="종료날짜"
                      prepend-icon="event"
                      readonly
                      v-bind="attrs"
                      v-on="on"
                    ></v-text-field>
                  </template>
                  <v-date-picker v-model="updatediary.edate" @input="menu2 = false"></v-date-picker>
                </v-menu>
              </v-card-text>

              <v-divider></v-divider>
              <v-card-actions>
                <v-btn color="blue darken-1" text @click="dialog = false;diaryUpdate()">Save</v-btn>
                <v-btn color="blue darken-1" text @click="dialog = false">cancel</v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
          <div
            class="d-flex flex-grow-0 align-center justify-center blog-detail-red-btn"
            style="margin-left: 4px;"
            @click="diaryDelete()"
            v-if="diaryid.uid==mydata.id"
          >{{isProj ? "프로젝트" : "블로그"}} 삭제</div>
        </div>

        <div>
          <div class="d-flex" v-if="diarydata.isProj==1">
            <div class="blog-detail-info-title">Repository</div>
            <a
              class="blog-detail-info-content"
              :href="diarydata.gitUrl"
            >{{diarydata.gitName ? diarydata.gitName:""}}</a>
          </div>
          <div class="d-flex">
            <div class="blog-detail-info-title">Start date ~ End date</div>
            <p
              class="blog-detail-info-content"
            >{{diarydata.sdate ? diarydata.sdate.substr(0,10) : ""}} ~ {{diarydata.sdate ? diarydata.edate.substr(0,10) : ""}}</p>
          </div>
        </div>

        <Status
          v-if="!!diarydata.id"
          :uid="diaryid.uid"
          :did="diaryid.did"
          :repoId="diarydata.repoId"
          :isProj="diarydata.isProj"
        />

        <div class="d-flex align-center" style="margin-bottom: 30px">
          <v-text-field
            id="header-text"
            class="d-flex justify-center flex-grow-0"
            placeholder="Search by Title"
            outlined
            dense
            type="text"
            hide-details
            append-icon="search"
            style="width: 300px"
            v-model="keyw"
          ></v-text-field>
          <div class="d-flex" />
          <div
            v-if="!isProj && diaryid.uid==mydata.id"
            class="d-flex justify-center align-center flex-grow-0 blog-detail-btn"
            @click="$router.push({name:'NewBlogPost',params:{did:diaryid.did}})"
          >글 작성</div>

          <div
            v-if="isProj && diaryid.uid==mydata.id"
            class="d-flex justify-center align-center flex-grow-0 blog-detail-btn"
            @click="$router.push({name:'NewProjectPost',params:{did:diaryid.did}})"
          >글 작성</div>
        </div>
        <div>

          <div
            class="d-flex align-center"
            style="padding-bottom: 8px;margin-bottom:16px; border-bottom:solid 1px #dde3ea"
          >
            <div>
              <div class="blog-detail-section-title">{{isProj ? "Project " : "Blog "}} Post</div>
            </div>
          </div>
          <div
            class="d-flex flex-column blog-detail-card"
            v-for="(post,indexs) in postdata"
            :key="post.id"
            v-if="post.title.includes(keyw)"
            @click="$router.push({name:'PostDetail',params:{uid:diaryid.uid ,pid:post.id}})"
          >
            <!-- 프로필 이미지, 닉네임  -->
            <div class="d-flex align-center blog-card-header">
              <div class="d-flex blog-detail-content-title">{{post.title}}</div>
              <div class="d-flex flex-grow-0 blog-detail-cdate">{{post.c_date}}</div>
              <v-icon class="d-flex justify-end ">mdi-source-commit</v-icon>
              <span class="main-page-commitcnt">{{post.commitCnt}}</span>
            </div>
            <!-- 포스트 제목 / 컨텐츠 -->
            <div class="d-flex blog-card-article">
              <!-- <div class="blog-detail-content-text" >{{post.content}}</div> -->
              <div class="blog-detail-content-text" v-html="compiledMarkdown(post)"></div>
            </div>
            <!-- 태그 -->
            <div style="position:absolute;bottom:0 ">
              <div
                class="d-flex flex-grow-0 posts-detail-tag"
                v-for="(tags,index) in tagdata[indexs]"
                :key="'t-'+index"
              >
                  #{{tags.name}}
              </div>
            </div>
          
          
          </div>


             
          </div>

        </div>
      </div>
    </div>
  
</template>

<script>
import Status from '@/component/Status.vue';
import axios from 'axios';
import SContact from '@/component/s-contact.vue';
import marked from  'marked'

var renderer = new marked.Renderer();

axios.defaults.headers.common['Access-Control-Allow-Origin'] = '*';
export default {
  name: 'BlogDetail',
  components: {
    Status,
    SContact,
  },
  data() {
    return {
      keyw: '',
      diaryid: this.$route.params,
      config: {
        keyword: '',
        isTemp: 0,
      },
      dialog: false,
      postdata: [],
      diarydata: {},
      updatediary: {
        id: '',
        title: '',
        intro: '',
        img: null,
        sdate: new Date().toISOString().substr(0, 10),
        edate: new Date().toISOString().substr(0, 10),
      },
      menu2: false,
      menu1: false,
      mydata: {},
      tagdata:{}
    };
  },
  methods: {
    compiledMarkdown: function (posttmp) {
      let vm = posttmp
      console.log(vm)
      renderer.em = function(text) {
        // var indexNumber = text.indexOf('/');
        // if (indexNumber !== -1 && text.substr(indexNumber - 1, 1) !== "\\") {
      //     var idx = text.substr(indexNumber + 1)
      //     var commit = vm.commitList[idx-1];
      //     var res = '<div class="post-detail-commit-container" ><div class="contents-commit-box">'
      //             + `<div class="d-flex flex-column justify-center contents-commit" ><div class="contents-commit-title"> #`
      //             + idx +' '+ commit.msg
      //             + '</div><div class="d-flex"> <div class="contents-commit-author">'
      //             + commit.author + ' committed on ' + commit.date
      //             + '</div></div></div></div></div>'
      //     return res;
        // }
        return '<em>' + "" + '</em>';
      }



      var tmp1 = marked(posttmp.content, { renderer: renderer }); 
      
      return tmp1
    },

    onFileSelected(event) {
      var input = event.target;
      if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = (e) => {
          this.uploadImageFile = e.target.result;
        };
        reader.readAsDataURL(input.files[0]);
      }
    },
    diaryDelete() {
      if (confirm('정말 삭제 하시겠습니까?') == true){

      try {
        console.log('다이어리 삭제 완료');
        this.$api.deleteDiary(this.diaryid.did);
        this.$router.push({
          name: 'DiaryMain',
          params: { uid: this.diaryid.uid, test: 2 },
        });
      } catch (e) {
        console.log(e);
      }}
      else{
        return
      }
    },
    updateData() {
      this.updatediary.id = this.diarydata.id;
      this.updatediary.title = this.diarydata.title;
      this.updatediary.intro = this.diarydata.intro;
      this.updatediary.img = this.diarydata.img;
      this.updatediary.sdate = this.diarydata.sdate.substr(0, 10);
    },
    async diaryUpdate() {
      if (this.updatediary.title == '' || this.updatediary.title == null) {
        alert('타이틀은 빈칸이 올수 없습니다');
      } else {
        try {
          if (this.$refs.file != null) {
            this.updatediary.file = this.$refs.file.files[0];
          }
          const formData = new FormData();
          formData.append('id', this.updatediary.id);
          formData.append('title', this.updatediary.title);
          formData.append('intro', this.updatediary.intro);
          formData.append('img', this.updatediary.img);
          formData.append('sdate', this.updatediary.sdate);
          formData.append('edate', this.updatediary.edate);
          formData.append('file', this.updatediary.file);

          await this.$api.updateDiary(formData, {
            headers: {
              'Content-Type': 'multipart/form-data',
            },
          });
          let tmpspace1 = await this.$api.individualDiary(
            this.$route.params.did
          );
          this.diarydata = tmpspace1;
        } catch (e) {
          console.log(e);
        }
      }
    },
  },
  async created() {
    this.mydata = this.$store.state.user;
    try {
      let tmpspace = await this.$api.diarydetail(
        this.$route.params.did,
        this.config
      );
      console.log(tmpspace,'123');
      this.postdata = tmpspace;
      try{
        for( var i=0 ;i<tmpspace.length;i++){
          this.tagdata[i]= await this.$api.tagIndex(tmpspace[i].id)
        }
        console.log('ddddd')
      }catch(e){
        console.log(e)
        
      }


      console.log('성공');
    } catch (e) {
      console.log('실패');
    }
    try {
      let tmpspace1 = await this.$api.individualDiary(this.$route.params.did);
      this.diarydata = tmpspace1;
    } catch (e) {
      console.log('fdas');
    }
  },
  computed: {
    isProj() {
      if (this.diarydata.isProj == 1) {
        return true;
      } else {
        return false;
      }
    },
    
  },
};
</script>

<style>
.blog-detail-container {
  width: 100%;
}

.blog-detail-inner {
  padding-bottom: 70px;
  width: 1140px;
  margin: 0 auto;
}

.blog-detail-btn {
  padding: 0 16px;
  font-size: 14px;
  background: #0051cb;
  font-weight: 600;
  color: #fff;
  border-radius: 6px;
  height: 40px;
  cursor: pointer;
}

.blog-detail-title {
  font-size: 32px;
  font-weight: 800;
}

.blog-detail-info-title {
  font-size: 12px;
  color: rgb(0, 0, 0, 0.57);
  width: 150px;
  font-weight: 600;
}

.blog-detail-info-content {
  font-size: 12px;
}

.blog-card-header {
  background-color: white;
  /* background:#ffffffde; */
  font-size: 16px;
  color: black;
  height: 55px;
}
.blog-card-header-nick_date {
  display: flex;
  flex-direction: column;
}
.blog-card-article {
  margin-top: 2px;
  padding-top: 8px;
  padding: 16px;
  overflow: hidden;
  border-top: solid 1px #dde3ea;
  background-color: white;
  float: unset;
  width: 100%;
  height: 100px;
}

.blog-detail-contact {
  width: 268px;
}

.blog-detail-red-btn {
  font-size: 14px;
  background: #db4455;
  font-weight: 600;
  color: #fff;
  border-radius: 6px;
  padding: 0 16px;
  height: 40px;
  cursor: pointer;
}

.blog-detail-section-title {
  font-size: 18px;
  font-weight: 700;
}

.blog-detail-card {
  position: relative;
  padding: 6px 10px 16px 10px;
  margin-top: 15px;
  height: 150px;
  border: 1px solid #dde3ea;
  border-radius: 8px;
  margin: 15px 5px;
  text-decoration: none;
  color: #21262e;
  -webkit-transition: -webkit-box-shadow 0.2s;
  transition: -webkit-box-shadow 0.2s;
  transition: box-shadow 0.2s;
  transition: box-shadow 0.2s, -webkit-box-shadow 0.2s;
  cursor: pointer;
}

.blog-detail-card:hover {
  box-shadow: 1px 1px 10px 4px #dde3ea;
}

.blog-detail-nickname {
  font-size: 14px;
  font-weight: 700;
}

.blog-detail-cdate {
  font-size: 12px;
  color: #21262e;
}

.blog-detail-content-title {
  font-size: 18px;
  font-weight: 700;
  margin-bottom: 8px;
}

.blog-detail-content-text {
  font-size: 12px;
}

.posts-detail-tag-container {
  width: 100%;
  overflow: hidden;
  margin: 32px 0;
  flex-wrap: wrap;
}
.posts-detail-tag {
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
</style>
