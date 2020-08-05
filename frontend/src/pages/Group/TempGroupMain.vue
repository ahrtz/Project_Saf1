<template>
  <v-card>
    <v-card-title>
      <v-text-field
        v-model="search"
        append-icon="search"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="group_list"
      :search="search"
    ></v-data-table>
  </v-card>
</template>

<script>
  export default {
    data () {
      return {
        uid : null, 
        search: '',
        headers: [
          { text: 'Name', align: 'start', value: 'name',},
          { text: 'Members', value: 'members' },
          { text: 'Leader', value: 'leader',  filterable: false },
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
          this.group_list.push(obj);
        }
      }
    },
  }
</script>