import Vue from 'vue'
import Router from 'vue-router'





// user 
import MainPage from '../Mainpage.vue'
import AccountDetail from '../pages/user/AccountDetail.vue'
import SignUp from '../pages/user/SignUp.vue'
import Login from '../pages/user/Login.vue'
import Project from '../pages/diary/Projects/Projects.vue'
import Blog from '../pages/diary/Blogs/Blogs.vue'
import Follow from '../pages/user/Follow.vue'
import Scrap from '../pages/user/Scrap.vue'
//Diary
import Diary from '../pages/diary/DiaryMain.vue'

//Group
import GroupMain from '../pages/Group/GroupMain.vue'
import MemberList from '../pages/Group/MemberList.vue'
import GroupList from '../pages/Group/GroupList.vue'

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
    // 회원 관리
    {
      path: '/account/detail',
      name:'AccountDetail',
      component: AccountDetail,
    },
    {
      path: '/account/follow',
      name :'Follow',
      component: Follow
    },
    {
      path: '/account/scrap',
      name : 'Scrap',
      component: Scrap
    },
    {
      path: '/login',
      name:'Login',
      component: Login,
    },
    {
      path: '/signup',
      name:'SignUp',
      component: SignUp,
    },
    // 다이어리
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
    // 그룹
    {
      path:'/group',
      name:'GroupMain',
      component: GroupMain
    },
    {
      path:'/group/MemberList',//이거 나중에 그룹 GID 받아와서 그거로 동적 라우팅 해야댐
      name:'MemberList',
      component : MemberList
    },
    {
      path:'/group/GroupList',
      name:'GroupList',
      component: GroupList
    }
  ]
})

router.beforeEach((to, from, next) => {
  window.scrollTo(0, 0)
  next()
})

export default router;
