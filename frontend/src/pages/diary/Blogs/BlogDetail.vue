<template>
  <div class="blog-detail-container">
    <div class="d-flex flex-grow-0 blog-detail-inner">
      <div class="d-flex flex-grow-0" style="width: 300px">
        <div class="blog-detail-contact">
          <s-contact />
        </div>
      </div>
      <div class="d-flex flex-column">
        <div class="d-flex">
          <div class="d-flex align-center">
            <div class="d-flex start">
              <h1 style="margin-right: 30px">{{diarydata.title}}</h1>
            </div>
            <div class="d-flex end align-self-end" style="text-align: center">
              <p>{{diarydata.sdate.substr(0,10)}} ~ {{diarydata.edate.substr(0,10)}}</p>
            </div>            
          </div>
          <div
            class="d-flex flex-grow-0 align-center justify-center blog-detail-red-btn"
            style="margin-bottom: 32px;"
            @click="diaryDelete()"
          >다이어리 삭제</div>
          

          <v-dialog v-model="dialog" scrollable max-width="600px">
            <template v-slot:activator="{ on }">
                <v-btn color="primary" dark v-on="on" @click="updateData()">다이어리 수정</v-btn>
            </template>

            <v-card >
                <v-card-title>다이어리 수정</v-card-title>
                <v-divider></v-divider>
                <v-card-text>
                  
                  다이어리 타이틀 : <v-text-field v-model="updatediary.title" type = "text" placeholder="다이어리 이름을 입력하세요"></v-text-field>
                  간단 설명
                  <v-textarea v-model="updatediary.intro" label="intro"></v-textarea>
                  대표 이미지
                  <input @change="onFileSelected($event)" ref="file" type="file" name="file" accept="image/*"/>
                  
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
                          v-model="updatediary.sdate"
                          label="시작날짜"
                          prepend-icon="event"
                          readonly
                          v-bind="attrs"
                          v-on="on"
                      ></v-text-field>
                      </template>
                      <v-date-picker v-model="updatediary.sdate" @input="menu2 = false"></v-date-picker>
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
            



        </div>
        <Status />
        <div class="d-flex">
          <div class="d-flex" />
          <v-text-field
          id="header-text"
          class="d-flex justify-center flex-grow-0"
          placeholder="Search by Title"
          outlined
          dense
          type="text"
          hide-details
          v-model="keyw"
          
        ></v-text-field>
          <div
            v-if="!isProj"
            class="d-flex justify-center align-center flex-grow-0 blog-detail-btn"
            @click="$router.push({name:'NewBlogPost',params:{did:diaryid.did}})"
          >글 작성</div>

          <div
            v-if="isProj"
            class="d-flex justify-center align-center flex-grow-0 blog-detail-btn"
            @click="$router.push({name:'NewProjectPost',params:{did:diaryid.did}})"
          >글 작성</div>
        </div>
        <div>
          <v-container fluid>
            <v-row>
              <v-col cols="12">
                <div
                  class="text-center d-flex justify-space-between"
                  style="margin-bottom:16px; border-bottom:solid 1px grey;"
                >
                  <div>
                    <h2>{{isProj ? "프로젝트 글" : "블로그 글"}}</h2>
                  </div>
                </div>
                <v-card
                  class="ma-2"
                  flat
                  v-for="post in postdata"
                  :key="post.id"
                  style="margin-top:10px; border-bottom:dashed 1px grey"
                  v-show="post.title.includes(keyw)"
                >
                  <router-link :to="{name:'PostDetail',params:{pid:post.id}}">
                    <!-- {{post}} -->
                    <!-- card layout -->
                    <div>
                      <!-- 프로필 이미지, 닉네임  -->
                      <header class="blog-card-header">
                        <!-- <img :src="post.userinfo.img" alt="" class="search-card-header-img"> -->
                        <div class="blog-card-header-nick_date">
                          <h3>
                            제목 :
                            {{post.title}}
                          </h3>
                          <span>
                            작성일 :
                            {{post.c_date}}
                          </span>
                        </div>
                      </header>
                      <!-- 포스트 제목 / 컨텐츠 -->
                      <article
                        class="blog-card-article"
                        @click="$router.push({name:'PostDetail',params:{uid:diaryid.uid ,pid:post.id}})"
                        style="cursor:pointer"
                      >
                        <h3 style="margin-left:10px;">{{post.title}}</h3>
                        <p style="margin-left:10px; margin-top:5px;">{{post.content}}</p>
                      </article>
                      <footer>
                        <!-- TODO: tags -->
                      </footer>
                    </div>
                  </router-link>
                </v-card>
              </v-col>
            </v-row>
          </v-container>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Status from '@/component/Status.vue';
import axios from 'axios';
import SContact from '@/component/s-contact.vue';

axios.defaults.headers.common['Access-Control-Allow-Origin'] = '*';
export default {
  name: 'BlogDetail',
  components: {
    Status,
    SContact,
  },
  data() {
    return {
      keyw:'',
      diaryid: this.$route.params,
      config: {
        keyword: '',
        isTemp: 0,
      },
      dialog:false,
      postdata: [],
      diarydata:{},
      updatediary:{
        id:"",
        title:"",
        intro:"",
        img:null,
        sdate:new Date().toISOString().substr(0, 10),
        edate:new Date().toISOString().substr(0, 10),

      },
      menu2:false
    };
  },
  methods: {
    onFileSelected(event) {
      var input = event.target;
      if (input.files && input.files[0]) { 
        var reader = new FileReader();
        reader.onload = (e) => { 
          this.uploadImageFile = e.target.result;
        }
        reader.readAsDataURL(input.files[0]);
      }
    },
    diaryDelete() {
      try {
        console.log('다이어리 삭제 완료');
        this.$api.deleteDiary(this.diaryid.did);
        this.$router.go(-1);
      } catch (e) {
        console.log(e);
      }
    },
    updateData(){
      this.updatediary.id=this.diarydata.id
      this.updatediary.title = this.diarydata.title
      this.updatediary.intro = this.diarydata.intro
      this.updatediary.img = this.diarydata.img
      this.updatediary.sdate =this.diarydata.sdate.substr(0,10)
      
    },
    async diaryUpdate(){
      try{
        if (this.$refs.file != null){
          this.updatediary.file=this.$refs.file.files[0]
        }
        const formData = new FormData()
        formData.append('id',this.updatediary.id)
        formData.append('title',this.updatediary.title)
        formData.append('intro',this.updatediary.intro)
        formData.append('img',this.updatediary.img)
        formData.append('sdate',this.updatediary.sdate)
        formData.append('edate',this.updatediary.edate)
        formData.append('file',this.updatediary.file)
      
      
        await this.$api.updateDiary(formData,{
        headers:{
                    'Content-Type':'multipart/form-data'
                }
      
      })
    } catch(e){
      console.log(e)
    }

  },
    
  },
  async created() {
    try {
      let tmpspace = await this.$api.diarydetail(
        this.$route.params.did,
        this.config
      );
      console.log(tmpspace);
      this.postdata = tmpspace;
      console.log('성공');
    } catch (e) {
      console.log('실패');
    }
    try{
      let tmpspace1 = await this.$api.individualDiary(this.$route.params.did)
      this.diarydata = tmpspace1
    }catch(e){
      console.log('fdas')
    }
  },
  computed: {
    isProj() {
      if (this.diarydata.isProj==1) {
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
  margin-top: 32px;
  padding: 0 16px;
  font-size: 14px;
  background: #0051cb;
  font-weight: 600;
  color: #fff;
  border-radius: 6px;
  height: 40px;
  cursor: pointer;
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
</style>
