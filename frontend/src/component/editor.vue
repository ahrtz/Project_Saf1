<template>
  <div>
      <span class="toolbar" @click="notPreview()">Contents</span>
      <span class="toolbar" @click="doPreview()">Preview</span>
    
      <v-textarea v-show="!preview" v-model="input" required outlined rows="9" no-resize auto-grow style="margin-top: 5px;"></v-textarea>    
    
      <div id="preview" v-show="preview" v-html="compiledMarkdown" label="preview"></div>
    
  </div>
</template>

<script>
import marked from 'marked'
import bus from './EventBus'

var renderer = new marked.Renderer();

export default {
  name : 'editor',
  data(){
    return{
      preview: false,
      input:'',
      commitList:[]
    }
  },
  created(){

  },
  methods:{
		notPreview(){      
      this.preview = false;
    },
    doPreview(){
      this.preview = true;
    },
    getCommits(commitList){
      this.commitList = commitList;
      console.log(this.commitList);
    }
  },
  computed: {
    compiledMarkdown: function () {
      var tmp = marked(this.input, { renderer: renderer }); 
      bus.$emit('updateContent',tmp);     
      return tmp
    },
  },
  mounted(){
    let vm = this;
    renderer.em = function(text) {
      var indexNumber = text.indexOf('/');
      if (indexNumber !== -1 && text.substr(indexNumber - 1, 1) !== "\\") {
        var idx = text.substr(indexNumber + 1)
        var commit = vm.commitList[idx-1];
        var res = '<div class="contents-commit-box">'
                  + `<div class="d-flex flex-column justify-center contents-commit" @click="mvUrl(`
                  + commit.url + `)"><div class="contents-commit-title"> #`
                  + idx +' '+ commit.msg
                  + '</div><div class="d-flex"> <div class="contents-commit-author">'
                  + commit.author + ' committed on ' + commit.date
                  + '</div></div></div></div>'
        return res;
      }
      return '<em>' + text.replace('\\/', '/') + '</em>';
    },
    bus.$on('getCommits', this.getCommits)
  }
}


</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>

textarea, #editor div {
  display: inline-block;
  width: 49%;
  height: 100%;
  vertical-align: top;
  box-sizing: border-box;
  padding: 0 20px;
}

code {
  color: #f66;
}

.toolbar
{
  border:solid 1px #ddd;
  background:#f4f4f4;
  padding: 5px;
  border-radius:3px;
  margin-bottom: 30px;
  cursor:pointer;
  font-weight: bold;
}

#preview{
  border: 1px solid black;
  min-height: 250px;
  padding-left: 15px;
  margin-top: 5px;
  margin-bottom: 35px;
}


.contents-commit-box {
  border-left: solid 2px #dde3ea;
  padding-left: 16px;
  margin-bottom: 22px;
}

.contents-commit {
  border: solid 1px #dde3ea;
  padding: 8px;
  height: 60px;
  border-radius: 6px;
  width: 500px;
}

.contents-commit:hover {
  background: #0051cb11;
  cursor: pointer;
}

.contents-commit-title {
  font-size: 14px;
  font-weight: 800;
}

.contents-commit-date {
  margin-bottom: 12px;
  font-size: 12px;
  font-weight: normal;
}

.contents-commit-author {
  font-size: 12px;
  font-weight: 600;
  color: #24292e;
}

.contents-commit-sha {
  font-size: 10px;
  font-weight: normal;
}
</style>
