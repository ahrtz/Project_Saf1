import Vue from 'vue'
import Router from 'vue-router'




// user
import AccountDetail from '../pages/user/AccountDetail.vue'
import MainPage from '../MainPage.vue'
import Project from '../pages/diary/Projects/Projects.vue'
import Blog from '../pages/diary/Blogs/Blogs.vue'

//Diary
import Diary from '../pages/diary/DiaryMain.vue'

//Group
import GroupMain from '../pages/Group/GroupMain.vue'
import MemberList from '../pages/Group/MemberList.vue'

Vue.use(Router)

// import Empty from '@/pages/Empty'

const router = new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name:'MainPage',
      component: MainPage,
    },
    {
      path: '/account/detail',
      name:'AccountDetail',
      component: AccountDetail,
    },
    {
      path: '/diary',
      name:'Diary',
      component: Diary
    },
    {
      path: '/diary/projects',
      name:'Project',
      component: Project,
    },
    {
      path: '/diary/blogs',
      name:'Blog',
      component: Blog,
    },
    {
      path:'/group',
      name:'GroupMain',
      component: GroupMain
    },
    {
      path:'/group/MemberList',//이거 나중에 그룹 GID 받아와서 그거로 동적 라우팅 해야댐
      name:'MemberList',
      component : MemberList
    }
  ]
})

router.beforeEach((to, from, next) => {
  window.scrollTo(0, 0)
  next()
})

export default router;
