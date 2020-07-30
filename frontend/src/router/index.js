import Vue from 'vue'
import Router from 'vue-router'



import tmp from '../pages/tmp.vue'

// user
import MainPage from '../MainPage.vue'
import AccountDetail from '../pages/user/AccountDetail.vue'
import SignUp from '../pages/user/SignUp.vue'
import Login from '../pages/user/Login.vue'
import Follow from '../pages/user/Follow.vue'
import Scrap from '../pages/user/Scrap.vue'
//Diary
import DiaryMain from '../pages/diary/DiaryMain.vue'


//project
import Project from '../pages/diary/Projects/Projects.vue'
import ProjectDetail from '../pages/diary/Blogs/BlogDetail.vue'
import ProjectAdd from '../pages/diary/DiaryAdd.vue'
import NewProjectPost from '../pages/diary/Blogs/NewBlogPost.vue'

// blog
import BlogDetail from '../pages/diary/Blogs/BlogDetail.vue'
import BlogAdd from '../pages/diary/DiaryAdd.vue'
import NewBlogPost from '../pages/diary/Blogs/NewBlogPost.vue'
import PostDetail from '../pages/diary/Blogs/PostDetail.vue'


//Group
import GroupMain from '../pages/Group/GroupMain.vue'
import MemberList from '../pages/Group/MemberList.vue'
import GroupDetail from '../pages/Group/GroupDetail.vue'

Vue.use(Router)

// import Empty from '@/pages/Empty'

const router = new Router({
  mode: 'history',
  routes: [
    {
      path: '/search/:key',
      name: 'tmp',
      component: tmp
    },
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
    
        //프로젝트
    {
      path: '/diary/projects',
      name:'Project',
      component: Project,
    },
    {
      path:'/diary/projects/add',
      name:'ProjectAdd',
      component: ProjectAdd,
      
    },
    {
      path:'/diary/projects/:did',
      name:'ProjectDetail',
      component : ProjectDetail
    },
    {
      path:'/diary/projects/:did/new',
      name:'NewProjectPost',
      component: NewProjectPost
    },
    //블로그
    {
      path: '/diary',
      name:'DiaryMain',
      component: DiaryMain,
    },
    {
      path : '/diary/blogs/add',
      name : 'BlogAdd',
      component : BlogAdd
    },
    {
      path:'/diary/blogs/:did',
      name:'BlogDetail',
      component:BlogDetail
    },
    {
      path:'/diary/:pid',
      name:'PostDetail',
      component:PostDetail
    },
    {
      path:'/diary/blog/:did/new',
      name:'NewBlogPost',
      component:NewBlogPost
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
      path:'/group/GroupDetail',
      name:'GroupDetail',
      component: GroupDetail
    }
  ]
})

router.beforeEach((to, from, next) => {
  window.scrollTo(0, 0)
  next()
})

export default router;
