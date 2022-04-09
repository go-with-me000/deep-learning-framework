// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
// import 'interceptors/interceptor'
import axios from 'axios'
import Print from 'vue-print-nb'
// import 'interceptors/Timetables.min'
import GeminiScrollbar from 'vue-gemini-scrollbar'
Vue.use(Print)
Vue.use(GeminiScrollbar)

Vue.prototype.$axios = axios


Vue.config.productionTip = false

const FastClick = require('fastclick')
FastClick.attach(document.body)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
