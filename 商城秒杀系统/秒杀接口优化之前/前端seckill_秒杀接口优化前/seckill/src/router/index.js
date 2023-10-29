import Vue from 'vue'
import Router from 'vue-router'
// 导入Login组件
import Login from '@/components/Login'

import Main from '@/components/Main'
import Detail from '@/components/Detail'
Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Login',
      component: Login
    },
    // 登录成功后路由到主页面
    {
      path: '/main',
      name: 'Main',
      component: Main
    }
    ,
    // 点击详情时路由到详情页
    {
      path: '/detail/:id',// 跳转页面时传递点击的id值过去
      name: 'Detail',
      component: Detail
    }
  ]
})
