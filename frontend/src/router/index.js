import Vue from 'vue'
import Router from 'vue-router'
import Login from 'pages/login/login'
import control from '@/pages/directory/control.vue'

Vue.use(Router)

var router = new Router({
  mode: 'history',
  routes: [{
      path: '/',
      name: 'root',
      redirect: {
        name: 'login'
      }
    }, {
      path: '/login',
      name: 'login',
      component: resolve => require(['@/pages/login/login'], resolve),
    }, {
      path: '/logout',
      name: 'logout',
      beforeEnter(to, from, next) {
        localStorage.clear();
        next({
          name: 'login'
        })
      }
    }, {
      path: '/select',
      name: 'select',
      component: resolve => require(['@/pages/selectBranch/selectBranchcontrol'], resolve),
      children: [
        {
          path: "dataset",
          name: 'dataset',
          component: resolve => require(['@/pages/dataset/dataset.vue'], resolve),

        },{
          path: "onemodel",
          name: 'onemodel',
          component: resolve => require(['@/pages/model/one-model.vue'], resolve),
        },{
          path: "test",
          name: 'test',
          component: resolve => require(['@/pages/test2/test.vue'], resolve),
        },
        {
          path: "model",
          name: 'model',
          component: resolve => require(['@/pages/model/model.vue'], resolve),

        },
        {
          path: "message",
          name: 'message',
          component: resolve => require(['@/pages/student/message/message.vue'], resolve),

        }, {
          path: "grade",
          name: 'grade',
          component: resolve => require(['@/pages/student/grade/grade.vue'], resolve),

        }, {
          path: "achievement",
          name: 'achievement',
          component: resolve => require(['@/pages/student/achievement/achievement.vue'], resolve),

        }, {
          path: "selection",
          name: 'selection',
          component: resolve => require(['@/pages/student/selection/selection.vue'], resolve),

        },



      ],
      meta: {
        requiresAuth: true
      }
    }, {
      path: '/control',
      name: 'control',
      component: resolve => require(['@/pages/selectBranch/selectControl'], resolve),
      children: [

        {
          path: "message",
          name: 'message',
          component: resolve => require(['@/pages/teacher/message/message.vue'], resolve),

        }, {
          path: "courses",
          name: 'courses',
          component: resolve => require(['@/pages/teacher/basic/courses.vue'], resolve),

        },  {
          path: "scourse",
          name: 'scourse',
          component: resolve => require(['@/pages/teacher/scourse/scourse.vue'], resolve),

        },{
          path: "teachers",
          name: 'teachers',
          component: resolve => require(['@/pages/teacher/basic/teachers.vue'], resolve),

        },{
          path: "students",
          name: 'students',
          component: resolve => require(['@/pages/teacher/basic/students.vue'], resolve),

        },{
          path: "choosecourses",
          name: 'choosecourses',
          component: resolve => require(['@/pages/teacher/courses/courses.vue'], resolve),

        }, {
          path: "scourse",
          name: 'scourse',
          component: resolve => require(['@/pages/teacher/scourse/scourse.vue'], resolve),

        },
      ],
      meta: {
        requiresAuth: true
      }
    },


  ]
})



export default router
