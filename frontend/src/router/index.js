import Vue from 'vue'
import Router from 'vue-router'

import tmp from '../pages/tmp.vue'
import tmpPost from '../pages/tmpPost.vue'

// user
import MainPage from '../MainPage.vue'
import MainPagefor from '../MainPage.vue'
import AccountDetail from '../pages/user/AccountDetail.vue'
import SignUp from '../pages/user/SignUp.vue'
import Login from '@/pages/user/Login.vue'
import Follow from '../pages/user/FollownScrap.vue' //
import Scrap from '../pages/user/Scrap.vue'
//Diary
import DiaryMain from '../pages/diary/DiaryMain.vue'


//project
import Project from '../pages/diary/Projects/Projects.vue'
import ProjectAdd from '../pages/diary/DiaryAdd.vue'
import NewProjectPost from '../pages/diary/Blogs/NewBlogPost.vue'

// blog
import BlogDetail from '../pages/diary/Blogs/BlogDetail.vue'
import BlogAdd from '../pages/diary/DiaryAdd.vue'
import NewBlogPost from '../pages/diary/Blogs/NewBlogPost.vue'
import PostDetail from '../pages/diary/Blogs/PostDetail.vue'
import UpdatePost from '../pages/diary/Blogs/UpdatePost.vue'


//Group
import GroupMain from '../pages/Group/TempGroupMain.vue' //
import MemberList from '../pages/Group/MemberList.vue'
import GroupDetail from '../pages/Group/TempGroupDetail.vue'

//social login
import socialSuccess from '../pages/user/socialSuccess.vue'

Vue.use(Router)

// import Empty from '@/pages/Empty'

const router = new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name:'Login',
      component: Login,
    },
    
    {
      path: '/:uid',
      name:'MainPagefor',
      component: MainPagefor,
    },
    {
      path: '/search/:key',
      name: 'tmp',
      component: tmp
    },
    {
      path:'/:uid/tmppost',
      name:'tmpPost',
      component:tmpPost
    },
    // {
    //   path: '/',
    //   name:'MainPage',
    //   component: MainPage,
    // },
    // 회원 관리
    {
      path: '/:uid/account/detail',
      name:'AccountDetail',
      component: AccountDetail,
    },
    {
      path: '/:uid/account/follow',
      name :'Follow',
      component: Follow
    },
    {
      path: '/:uid/account/scrap',
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
      path:'/diary/projects/:did/new',
      name:'NewProjectPost',
      component: NewProjectPost
    },
    //블로그
    {
      path: '/:uid/diary',
      name:'DiaryMain',
      component: DiaryMain,
    },
    {
      path : '/diary/blogs/add',
      name : 'BlogAdd',
      component : BlogAdd
    },
    {
      path:'/:uid/diary/blogs/:did',
      name:'BlogDetail',
      component:BlogDetail
    },
    {
      path:'/:uid/diary/:pid',
      name:'PostDetail',
      component:PostDetail
    },
    {
      path:'/diary/blog/:did/new',
      name:'NewBlogPost',
      component:NewBlogPost
    },
    {
      path:'/diary/:pid/update',
      name:'UpdatePost',
      component:UpdatePost
    },
    // 그룹
    {
      path:'/:uid/group',
      name:'GroupMain',
      component: GroupMain
    },
    {
      path:'/group/:gid/memberList',//이거 나중에 그룹 GID 받아와서 그거로 동적 라우팅 해야댐
      name:'MemberList',
      component : MemberList
    },
    {
      path:'/group/:gid/groupdetail',
      name:'GroupDetail',
      component: GroupDetail
    },
    //소셜로그인용
    {
      path:'/social/success-callback',
      name:'socialSuccess',
      component: socialSuccess,
      // redirect: { name: 'DairyMain' }

    },


  ]
})

router.beforeEach((to, from, next) => {
  window.scrollTo(0, 0)
  next()
})

export default router;
