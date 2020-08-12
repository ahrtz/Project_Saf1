<template>
  <div class="group-main-container">
    <div class="d-flex group-main-inner">
      <div class="d-flex justify-center">
        <div class="d-flex flex-column" style="width: 100%;padding-right:32px">
          <div class="group-main-title">그룹</div>
          <v-card flat>
            <v-card-title>
              <v-row>
                <v-col cols="8">
                  <v-text-field
                    v-model="search"
                    append-icon="search"
                    label="Search"
                    single-line
                    hide-details
                  ></v-text-field>
                </v-col>
                <v-spacer></v-spacer>
                <v-col cols="auto">
                  <v-dialog v-model="dialog" max-width="500px">
                    <template v-slot:activator="{on}">
                      <v-btn v-on="on" color="primary" dark class="mb-2">그룹 추가</v-btn>
                    </template>
                    <v-card>
                      <v-card-title>
                        <span class="headline">New Group</span>
                      </v-card-title>

                      <v-card-text>
                        <v-container grid-list-md>
                          <v-layout column>
                            <v-flex xs12 sm6 md4>
                              <v-text-field
                                v-model="addItem.name"
                                label="Group name"
                                class="group-text-box"
                              ></v-text-field>
                            </v-flex>
                            <v-flex xs12 sm6 md4>
                              <v-textarea
                                v-model="addItem.intro"
                                label="Description"
                                class="group-text-box"
                              ></v-textarea>
                            </v-flex>
                          </v-layout>
                        </v-container>
                      </v-card-text>

                      <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn class="ma-2" color="blue darken-1" dark @click="close()">Cancel</v-btn>
                        <v-btn class="ma-2" color="blue darken-1" dark @click="add()">Add</v-btn>
                      </v-card-actions>
                    </v-card>
                  </v-dialog>
                </v-col>
              </v-row>
            </v-card-title>
            <v-data-table
              :headers="headers"
              :items="group_list"
              :search="search"
              @click:row="goGroup"
              style="cursor:pointer"
            ></v-data-table>
          </v-card>
        </div>
      </div>
      <user-sidebar />
    </div>
  </div>
</template>

<script>
import userSidebar from '../../component/user_sidebar';

export default {
  components: {
    userSidebar
  },
  data() {
    return {
      uid: null,
      search: '',
      headers: [
        { text: 'Group', align: 'start', value: 'name' },
        { text: 'Leader', value: 'leader', filterable: false },
        { text: 'Members', value: 'members', filterable: false },
      ],
      groups: [], // api에 대응하는 group
      group_list: [], //Table에 띄워줄 data 포멧.

      // Add Group Form
      dialog: false,
      addItem: {
        // 새로운 그룹 데이터
        name: '',
        intro: '',
      },
    };
  },
  async created() {
    this.uid = this.$store.state.user.id;
    console.log('CKCK' + this.uid);
    this.getGroup();
  },

  watch: {
    dialog(val) {
      val || this.close();
    },
  },
  methods: {
    async getGroup() {
      try {
        let temp = await this.$api.getGroupList();
        this.groups = temp;

        this.setDataTable();
        console.log('CKCK:: Group 가져오기 성공');
      } catch (e) {
        console.log(e);
      }
    },
    setDataTable() {
      console.log('CKCK:: Group 정보' + this.groups.length);
      var templist = [];
      for (var i = 0; i < this.groups.length; i++) {
        var obj = {};
        obj['name'] = this.groups[i].name;
        obj['members'] = this.groups[i].mCnt;
        obj['leader'] = this.groups[i].lName;
        obj['id'] = this.groups[i].id;
        templist.push(obj);
      }
      this.group_list = templist;
    },
    goGroup(param) {
      console.log('CKCK :: goGroup()!!! ' + param.id);
      this.$router.push({ name: 'GroupDetail', params: { gid: param.id } });
    },
    close() {
      this.dialog = false;
    },
    add() {
      //console.log(this.addItem.name + "//" + this.addItem.intro)
      //this.desserts.push(this.editedItem)
      this.$api.addGroup(this.addItem);
      this.getGroup();
      //여기 어떻게 해야 깔끔해지지..? 새로고침 싫은데...
      //location.reload()
      this.close();
    },
  },
};
</script>

<style>
.group-text-box {
  background-color: rgb(245, 245, 245);
  border-radius: 10px;
}

.hr-css {
  border-color: grey;
  opacity: 0.2;
}

.group-main-container {
  width: 100%;
}

.group-main-inner {
  padding-bottom: 70px;
  min-height: 80vh;
  width: 1140px;
  margin: 0 auto;
}

.group-main-title {
  font-weight: 600;
  margin-bottom: 8px;
}

.group-main-diary {
  margin-bottom: 16px;
  padding: 20px;
  width: 100%;
  border: solid 1px #ccc;
  border-radius: 6px;
}

.group-main-date {
  font-size: 12px;
}

.group-main-tmp-title {
  width: 100px;
  font-size: 12px;
}

.group-main-tmp-content {
  font-weight: bold;
  font-size: 14px;
}

.group-main-btn {
  margin-right: 8px;
  padding: 0 16px;
  font-size: 12px;
  background: #0051cb;
  color: #fff;
  border-radius: 6px;
  height: 30px;
  cursor: pointer;
}

.group-main-btn-white {
  margin-right: 8px;
  padding: 0 16px;
  font-size: 12px;
  background: #fff;
  color: #0051cb;
  border: solid 1px #0051cb;
  border-radius: 6px;
  height: 30px;
  cursor: pointer;
}

.group-main-btn-red {
  padding: 0 16px;
  font-size: 12px;
  background: #db4455;
  color: #fff;
  border: solid 1px #db4455;
  border-radius: 6px;
  height: 30px;
  cursor: pointer;
}
</style>