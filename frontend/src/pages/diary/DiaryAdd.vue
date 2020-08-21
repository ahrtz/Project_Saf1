<template>
  <div class="diary-add-container">
    <div class="diary-add">
      <div class="diary-add-title">다이어리 추가</div>
      <div class="diary-add-subtitle">다이어리 이름</div>
      <v-text-field v-model="blogData.title" type="text" dense placeholder="다이어리 이름을 입력하세요"></v-text-field>

      <v-layout v-if="this.$route.path[7] =='p'">
        <v-dialog v-model="dialog" scrollable max-width="300px">
          <template v-slot:activator="{ on }">
            <div
              class="d-flex align-center justify-center flex-grow-0 s-button-blue"
              style="margin-top: 32px;"
              dark
              v-on="on"
            >git repository 선택하기</div>
          </template>
          <v-card color="primary" dark v-if="repoData.length==0">
            <v-card-text>
              repository 정보 받아 오는중
              <v-progress-linear indeterminate color="white" class="mb-0" buffer-value="0" stream></v-progress-linear>
            </v-card-text>
          </v-card>

          <v-card v-if="repoData.length!=0">
            <v-card-title>레포 선택</v-card-title>
            <v-divider></v-divider>

            <v-card-text style="height: 220px;">
              <v-radio-group v-model="repoChecked" column>
                <v-radio
                  v-for="repo in repoData.slice((this.page-1)*5,(this.page)*5)"
                  :v-if="repoData!=null && repo!=null"
                  :key="repo.url"
                  :label="repo.repoName"
                  :value="repo"
                ></v-radio>
              </v-radio-group>
            </v-card-text>
            <div class="text-center">
              <v-pagination
                v-model="page"
                :length="Math.ceil(repoData.length / 5)"
                :total-visible="7"
              ></v-pagination>
            </div>
            <v-divider></v-divider>
            <v-card-actions>
              <div class="d-flex" />
              <div
                class="d-flex align-center justify-center flex-grow-0 s-button-blue"
                @click="dialog = false"
              >저장</div>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-layout>

      <v-text-field v-if="repoData.length!=0" readonly v-model="repoChecked.repoName"></v-text-field>
      <div class="d-flex flex-column">
        <div class="diary-add-subtitle">대표 이미지</div>
        <input @change="onFileSelected($event)" ref="file" type="file" name="file" accept="image/*" />
      </div>
      <div class="d-flex flex-column">
        <v-menu
          v-model="blogData.menu2"
          :close-on-content-click="false"
          :nudge-right="40"
          transition="scale-transition"
          offset-y
          min-width="290px"
        >
          <template v-slot:activator="{ on, attrs }">
            <div class="diary-add-subtitle" style="margin-top: 32px">시작 날짜</div>
            <v-text-field
              v-model="blogData.sdate"
              readonly
              v-bind="attrs"
              v-on="on"
              dense
              style="width: 150px"
            ></v-text-field>
          </template>
          <v-date-picker v-model="blogData.sdate" @input="blogData.menu2 = false"></v-date-picker>
        </v-menu>
      </div>

      <div class="d-flex flex-column">
        <div class="diary-add-subtitle">설명</div>
        <v-textarea v-model="blogData.intro" placeholder="다이어리의 간략한 설명을 적어주세요."></v-textarea>
      </div>
      <div class="d-flex" style="margin-top: 32px;">
        <div class="d-flex" />
        <div
          class="d-flex align-center justify-center flex-grow-0 s-button-white"
          style="margin-right: 4px;"
          @click="goback()"
        >취소</div>
        <div
          class="d-flex align-center justify-center flex-grow-0 s-button-blue"
          @click="AddProj()"
        >추가</div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
axios.defaults.headers.common['Access-Control-Allow-Origin'] = '*';
export default {
  name: 'BlogAdd',
  data() {
    return {
      config: {},
      blogData: {
        uid: '',
        title: '',
        intro: '',
        img: '', //얘는 이미지의 주소가 string 형태로 들어가는거겠지?
        gitUrl: '', //얘는 블로그에선 필요없는 요소. 맞지?
        gitName: '',
        isProj: 0, // 블로그는 프로젝트가 아니니까 무조건 0으로 해놓음.
        sdate: new Date().toISOString().substr(0, 10),
        edate: new Date().toISOString().substr(0, 10),
        modal: false,
        menu2: false,
        languages: [],
        repoId: '',
      },
      repoData: [],
      repoChecked: {},
      page: 1,
      dialog: false,
      uploadImageFile: '',
    };
  },
  watch: {
    repoChecked() {
      this.blogData.gitUrl = this.repoChecked.url;
      this.blogData.gitName = this.repoChecked.repoName;
      this.blogData.repoId = this.repoChecked.repoId;
      this.blogData.languages = this.repoChecked.languages;
    },
  },
  async created() {
    this.blogData.uid = this.$store.state.user.id;

    if (this.$route.path[7] == 'p') {
      this.blogData.isProj = 1;

      try {
        let tmpspace = await this.$api.getRepoData();
        // console.log(tmpspace);
        this.repoData = tmpspace;
      } catch (e) {
        // console.log(e);
      }

      return true;
    } else {
      this.blogData.isProj = 0;
      return false;
    }
  },
  computed: {
    isProject() {},
  },
  methods: {
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

    goback() {
      this.$router.go(-1);
    },
    async AddProj() {
      if (this.blogData.title == '' || this.blogData.title == null) {
        alert('title은 필수 값입니다.');
      } else {
        try {
          if (this.$refs.file != null) {
            this.blogData.file = this.$refs.file.files[0];
          }
          const formData = new FormData();
          formData.append('uid', this.blogData.uid);
          formData.append('title', this.blogData.title);
          formData.append('intro', this.blogData.intro);
          formData.append('img', this.blogData.img);
          formData.append('gitUrl', this.blogData.gitUrl);
          formData.append('gitName', this.blogData.gitName);
          formData.append('isProj', this.blogData.isProj);
          formData.append('sdate', this.blogData.sdate);
          formData.append('edate', this.blogData.edate);
          formData.append('file', this.blogData.file);
          formData.append('languages', this.blogData.languages);
          formData.append('repoId', this.blogData.repoId);

          await this.$api.AddProject(formData, {
            headers: {
              'Content-Type': 'multipart/form-data',
            },
          });
          console.log('성공');
          this.$router.push({
            name: 'DiaryMain',
            params: { uid: this.blogData.uid, test: 2 },
          });
        } catch (e) {
          console.log('실패');
          console.log(e);
        }
      }
    },
  },
};
</script>

<style >
.diary-add-container {
  width: 100%;
}

.diary-add {
  width: 600px;
  margin: 0 auto;
}

.diary-add-title {
  margin-bottom: 40px;
  font-size: 18px;
  font-weight: 800;
}

.diary-add-subtitle {
  margin-top: 16px;
  margin-bottom: 16px;
  font-size: 14px;
  font-weight: 600;
}
</style>

