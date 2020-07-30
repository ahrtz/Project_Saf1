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
        })).data
    }
}
