<template>
  <div>
      <h2>프로젝트 추가 페이지</h2>
      제목 : <input v-model="blogData.title" type = "text" placeholder="제목을 입력하세요">
      <br>
      대표 이미지 : <input v-model="blogData.img" type = "text" placeholder="설정한 이미지의 경로 출력"> <button> 찾아보기 </button>
      <br>
      시작 날짜 : <input v-model="blogData.s_date" type = "date">
      종료 날짜 : <input v-model="blogData.e_date" type = "date">
      <br>
      내용 : <input v-model="blogData.desc" type = "text" placeholder="상세 내용을 입력하세요">
      <br>
      <div style="float:right">
          <button> 취소 </button>
          <button> 작성완료 </button>
          <button> 임시저장 </button>
      </div>
      
  </div>
</template>

<script>
export default {
    name : 'BlogAdd',
    data(){
        return {
            blogData:{
                uid : null,// 회원 pk.
                title : null,
                desc : null,
                img : null, //얘는 이미지의 주소가 string 형태로 들어가는거겠지?
                git_url : null, //얘는 블로그에선 필요없는 요소. 맞지?
                is_project : 1, // 블로그는 프로젝트가 아니니까 무조건 0으로 해놓음.
                s_date : null,  //정확한 개념을 모르므로 일단 입력 폼에서 입력받도록 하였음.
                e_date : null,  //정확한 개념을 모르므로 일단 입력 폼에서 입력받도록 하였음.
            },
        }
    }
}
</script>

<style>

</style>