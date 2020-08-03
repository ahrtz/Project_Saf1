// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'

// Library
import axios from 'axios'
import VueAxios from 'vue-axios'
import vuetify from '@/plugins/vuetify'
import 'material-design-icons-iconfont/dist/material-design-icons.css'
import moment from 'moment'
import VueAwesomeSwiper from 'vue-awesome-swiper'
import Swiper from 'swiper';

// import Swiper styles
import 'swiper/swiper-bundle.css';

// API
import api from './plugins/api'
Vue.prototype.$api = api

import store from './store'

Vue.use(VueAxios, axios)
Vue.prototype.$moment = moment
Vue.use(VueAwesomeSwiper, /* { default options with global component } */)

// Layout
// import SMainLayout from './layouts/s-main-layout'

// Vue.component('s-main-layout', SMainLayout)

// Component
// import SHeader from './components/common/s-header'

// Vue.component('s-header', SHeader)


Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>',
  store,
  vuetify
})
