import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

// import Empty from '@/pages/Empty'

const router = new Router({
  mode: 'history',
  routes: [
    // {
    //   path: '/',
    //   component: Empty,
    // },
  ]
})

router.beforeEach((to, from, next) => {
  window.scrollTo(0, 0)
  next()
})

export default router;