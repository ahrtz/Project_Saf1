<template>
<div>
  <v-card>
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
              <v-btn v-on="on" color="primary" dark class="mb-2">Add Group</v-btn>
            </template>     
            <v-card>
              <v-card-title>
                <span class="headline">New Group </span>
              </v-card-title>

              <v-card-text>
                <v-container grid-list-md>
                  <v-layout column>
                    <v-flex xs12 sm6 md4>
                      <v-text-field v-model="addItem.name" label="Group name" class="group-text-box"></v-text-field>
                    </v-flex>
                    <v-flex xs12 sm6 md4>
                      <v-textarea v-model="addItem.intro" label="Description" class="group-text-box"></v-textarea>
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
    >
    </v-data-table>
  </v-card>
</div>
</template>

<script>
  export default {
    data () {
      return {
        uid : null, 
        search: '',
        headers: [
          { text: 'Group', align: 'start', value: 'name',},
          { text: 'Leader', value: 'leader',  filterable: false },
           { text: 'Members', value: 'members', filterable:false },
        ],
        groups : [],  // api에 대응하는 group
        group_list : [], //Table에 띄워줄 data 포멧.

        // Add Group Form
        dialog : false,
        addItem: {  // 새로운 그룹 데이터
        name: '',
        intro : '',
        },

      }
    },
    async created(){
      this.uid=this.$store.state.user.id
      console.log('CKCK' + this.uid)
      this.getGroup();
    },

    watch : {
      dialog (val) {
        val || this.close()
      }
    },
    methods:{
      async getGroup(){
        try{
          let temp = await this.$api.getGroupList()
          this.groups = temp

          this.setDataTable()
          console.log('CKCK:: Group 가져오기 성공')
        }
        catch(e)
        {
          console.log(e)
        }
      },
      setDataTable(){
        console.log('CKCK:: Group 정보' + this.groups.length)
        for(var i =0; i<this.groups.length; i++)
        {
          var obj = {}
          obj['name'] = this.groups[i].name;
          obj['members'] = this.groups[i].mCnt;
          obj['leader'] = this.groups[i].lName;
          obj['id'] = this.groups[i].id;
          this.group_list.push(obj);
        }
      },
      goGroup(param){
        console.log("CKCK :: goGroup()!!! " + param.id)
        this.$router.push({name:'GroupDetail', params:{gid:param.id}})
      },
      close () {
        this.dialog = false
      },
      add() {
        //console.log(this.addItem.name + "//" + this.addItem.intro)
        //this.desserts.push(this.editedItem)
        this.$api.addGroup(this.addItem)
        this.getGroup()
        //여기 어떻게 해야 깔끔해지지..? 새로고침 싫은데...
        //location.reload()
        this.close()
      },
    },
  }
</script>

<style>
.group-text-box {
  background-color : rgb(245, 245, 245);
  border-radius: 10px;
}
</style>