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
    

    async userUpdate(userdata){
        return (await axios.put(`${baseURL}/users`,userdata))
    },
    async signupp(data,configs){
        return (await axios.post(`${baseURL}/users/signup`,data,configs))
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
    async googleLogin(){
        return (await axios.get(`${baseURL}/google`))
    },
    async deleteCommit(cid){
        return (await axios.delete(`${baseURL}/commits/${cid}`))
    }
}
