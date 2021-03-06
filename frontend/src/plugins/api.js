import axios from 'axios'

const baseURL = '/api'

export default {
    async getHealth() {
        return (await axios.get(`${baseURL}/health`)).data
    },
    async getMe() {
        return (await axios.get(`${baseURL}/users/me`)).data
    },
    async isLoggedIn() {
        return (await axios.get(`${baseURL}/users/is-logged-in`)).data
    },
    async isthere(id){
        return (await axios.get(`${baseURL}/users/${id}`)).data
    },
    async findUserByUid(id){
        return (await axios.get(`${baseURL}/users?id=${id}`)).data
    }
    ,
    async login(loginData) {
        return (await axios.post(`${baseURL}/users/login`, loginData, {
            headers: {
                'Content-Type': 'application/json'
            }
        }))
    },
    async logout(){
        return (await axios.post(`${baseURL}/users/logout`))
    },
    async diarydetail(did,configdata){
        return (await axios.post(`${baseURL}/posts/${did}`,configdata)).data

        },
    async individualDiary(id){
        return (await axios.get(`${baseURL}/diaries/${id}`)).data
    }
    
    ,

    async postdetail(pid){
        return (await axios.get(`${baseURL}/posts/${pid}`)).data
    },
    async likedatas(pid){
        return(await axios.get(`${baseURL}/likes/${pid}`)).data
    },
    async AddProject(blogData){
        return(await axios.post(`${baseURL}/diaries`,blogData,))
    },
    async getDiaries(uid,params){
        return(await axios.post(`${baseURL}/diaries/${uid}`,params)).data
    },
    

    async userUpdate(userdata,configs){
        return (await axios.put(`${baseURL}/users`,userdata,configs))
    },
    async signupp(data,configs){
        return (await axios.post(`${baseURL}/users/signup`,data,configs))
    },
    async getProfileImage(fileName){
        return (await axios.get(`${baseURL}/users/image/${fileName}`)).data
    },

    async savePost(data){
        return (await axios.post(`${baseURL}/posts`,data))
    },
    async deletePost(pid){
        return (await axios.delete(`${baseURL}/posts/${pid}`))
    },
    async updatePost(data){
        return (await axios.put(`${baseURL}/posts`,data))
    },
    async searchTemp(data){
        return  (await axios.post(`${baseURL}/posts/all`,data))
    },
    async getPost(data) {
        return (await axios.post(`${baseURL}/posts/0`,data)).data
    }, // 이거 누가 어디서 쓴거지
    // 여기서부턴 깃 관련 api
    async certgitToken(data){
        return (await axios.post(`${baseURL}/gits`,data))
    },//깃 토큰 검증
    async getCommitList(data){
        return (await axios.post(`${baseURL}/gits/commits`,data)).data
    },
    //포스트 디테일 깔때 쓰는거
    async likeDislike(data){
        return (await axios.put(`${baseURL}/likes`,data))
    },
    //태그 생성
    async createTag(datas){
        return (await axios.post(`${baseURL}/tags/add`,datas))
    },
    //포스트 별 태그 조회
    // 이거 여러개가 와야 할텐데 한번해보고 이상하면 백에 요청
    async tagIndex(pid){
        return (await axios.get(`${baseURL}/tags/${pid}`)).data
    },
    // 태그 검색
    async tagSearch(data){
        return (await axios.post(`${baseURL}/tags`,data)).data
    },
    // 회원 별 태그 상위 num개 조회
    // 이건 해보고 안되면 params 에 담아서 날리는 걸로 해야댐
    async userTagIndex(uid,num){
        return (await axios.get(`${baseURL}/tags?uid=${uid}&num=${num}`)).data
    },
    // 삭제
    async deleteTag(id){
        return (await axios.delete(`${baseURL}/tags/${id}`))
    },
    //댓글 조회하기
    async getCommentlist(pid){
        return (await axios.get(`${baseURL}/comments/${pid}`)).data
    },
    //댓글 작성
    async createComment(commentData){
        return (await axios.post(`${baseURL}/comments`,commentData)).data
    },
    //댓글 삭제
    // id 는 코멘트의 id 임
    async deleteComment(id){
        return (await axios.delete(`${baseURL}/comments/${id}`))
    },
    //다이어리 삭제
    async deleteDiary(did){
        return (await axios.delete(`${baseURL}/diaries/${did}`))
    }   ,
    async getRepoData(){
        return (await axios.get(`${baseURL}/gits/repositories`)).data
    },

    async addCommit(data){
        return (await axios.post(`${baseURL}/commits`,data))
    },
    async getPostCommit(pid){
        return (await axios.get(`${baseURL}/commits/${pid}`)).data
    },
    async socialLogin(){
        return (await axios.get(`${baseURL}/social`))
    },
    async deleteCommit(cid){
        return (await axios.delete(`${baseURL}/commits/${cid}`))
    },
    // 스크랩 정보 조회
    async getScrapInfo(){
        return (await axios.get(`${baseURL}/scraps`)).data
    },
    // 스크랩 생성 및 수정
    async makeScrap(data){
        return (await axios.post(`${baseURL}/scraps`,data))
    } ,
    // 스크랩 삭제
    async deleteScrap(id){
        return (await axios.delete(`${baseURL}/scraps/${id}`))
    },

    // Group 관련
    async addGroup(data){
        return (await axios.post(`${baseURL}/groups`,data))
    },
    async deleteGroup(id)
    {
        return (await axios.delete(`${baseURL}/groups/${id}`))
    },
    async getGroupList(){
        return (await axios.get(`${baseURL}/groups`)).data
    },
    async groupDetail(gid) {
        return (await axios.get(`${baseURL}/groups/${gid}`)).data
    },
    async groupRelationAdd(data){
        return (await axios.post(`${baseURL}/groups/user`,data))
    },
    async groupRelationDelete(data){
        return (await axios.put(`${baseURL}/groups/user`,data))
    },
    async groupDetailUpdate(datas){
        return (await axios.put(`${baseURL}/groups`,datas)).data
    },
    //그룹 별 상위회원 조회
    async getTopMembers(data){
        return (await axios.post(`${baseURL}/groups/top`,data)).data
    },
    
    //팔로우 관련
    // 팔로우, 팔로잉 유저 목록 확인
    async searchFollow(data){
        return (await axios.post(`${baseURL}/follows`,data)).data //이게 내가 만든 api임.
    },
    //팔로우 생성 및 수정
    async makeFollow(data){
        return (await axios.put(`${baseURL}/follows`,data))     //이건 일단 나중에 쓸거고! 
    },
    //단일 팔로우 확인
    async confirmFollow(pid){
        return (await axios.get(`${baseURL}/follows/${pid}`))
    }
    ,
    async gitCancel(){
        return (await axios.get(`${baseURL}/users/cancel`))
    },
    async contactBar(userpk){
        return (await axios.get(`${baseURL}/users?id=${userpk}`))
    },
    async updateDiary(data){
        return (await axios.put(`${baseURL}/diaries`,data))
    },

    // status 관련 ~
    async getCommitStatus(data) {
        return (await axios.post(`${baseURL}/gits/commits/cnt`, data)).data
    },
    async getPostStatus(data) {
        return (await axios.post(`${baseURL}/posts/cnt`, data)).data
    },
    async forTest(data){
        return (await axios.post(`${baseURL}/test`,data)).data
    },
    async tagRank(datas){
        return (await axios.post(`${baseURL}/tags/rank`,datas)).data
    }
}   
