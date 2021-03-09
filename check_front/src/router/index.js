import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/Login'

Vue.use(VueRouter)

const routes = [
  // {
  //   path: '/',
  //   name: 'Home',
  //   component: Home
  // },
  // {
  //   path: '/about',
  //   name: 'About',
  //   // route level code-splitting
  //   // this generates a separate chunk (about.[hash].js) for this route
  //   // which is lazy-loaded when the route is visited.
  //   component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  // }
  {
    path: "/",
    name: 'Login',
    component: Login,
    routerId: 0
  },
  // {
  //   path: "/home",
  //   name: 'Home',
  //   component: Home,
  //   routerId: 0,
  //   children: [
  //     {
  //       path: "/admin",
  //       name: '管理员菜单',
  //       component: AdminHome,
  //       routerId: 1,
  //       children: [
  //         {
  //           path: "/admin/user",
  //           name: "用户管理",
  //           component: AdminUser,
  //         },
  //         {
  //           path: '/admin/teacher',
  //           name: '教师管理',
  //           component: AdminTeacher,
  //         }
  //       ]
  //     },
  //     {
  //       path: "/teacher",
  //       name: '教师菜单',
  //       component: TeacherHome,
  //       routerId: 2,
  //       children: [
  //         {
  //           path: "/teacher/class",
  //           name: "班级管理",
  //           component: TeacherClass,
  //         },
  //         {
  //           path: '/teacher/work',
  //           name: '作业管理',
  //           component: TeacherWork,
  //         },
  //         // {
  //         //   path: '/teacher/info',
  //         //   name: '教师个人信息',
  //         //   component: TeacherInfo,
  //         // }
  //       ]
  //     },
  //     {
  //       path: "/student",
  //       name: "学生菜单",
  //       component: StudentHome,
  //       routerId: 3,
  //       children: [
  //         {
  //           path: '/student/work',
  //           name: '作业查询',
  //           component: StudentWork,
  //         },
  //         {
  //           path: '/student/info',
  //           name: '个人信息',
  //           component: StudentInfo,
  //         }
  //       ]
  //     }
  //   ]

  // },

]

const router = new VueRouter({
  routes
})

export default router
