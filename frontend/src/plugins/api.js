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
    async postdetail(pid){
        return (await axios.get(`${baseURL}/posts/${pid}`)).data
    },
    async likedata(pid){
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
        return  (await axios.post(`${baseURL}/posts/all`,data)).data
    },
    async getPost(data) {
        return (await axios.post(`${baseURL}/posts/0`,data)).data
    }
}
