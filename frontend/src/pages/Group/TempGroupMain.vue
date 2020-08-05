<template>
  <v-card>
    <v-card-title>
      <v-row>
        <v-col cols="11">
          <v-text-field
            v-model="search"
            append-icon="search"
            label="Search"
            single-line
            hide-details
          ></v-text-field>
        </v-col>
        
        <v-col  cols="1">
          <v-btn>dds</v-btn>
        </v-col>
      </v-row>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="group_list"
      :search="search"
      @click:row="goGroup"  
    >
      <!-- <template slot="items" slot-scope="props" @click="goGroup(props.item.id)">
          <tr>
            <td>{{props.item.name}}</td>
            <td>{{props.item.leader}}</td>
            <td>{{props.item.members}}</td>
          </tr>
      </template> -->
    </v-data-table>
  </v-card>
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
        groups : [],  //api에 대응하는 group
        group_list : [] //Table에 띄워줄 data 포멧.
      }
    },
    async created(){
      this.uid=this.$store.state.user.id
      console.log('CKCK' + this.uid)
      this.getGroup();
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
      }
    },
  }
</script>