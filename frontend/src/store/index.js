import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    isLoggedIn:null,
    user:{}

  },
  mutations: {
    isLoggedIn(state,val){
      state.isLoggedIn = val
    },
    userData(state,val){
      state.user = val
    }
  },
  actions: {
  },
  modules: {
  }
})
