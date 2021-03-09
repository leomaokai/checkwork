import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import { initMenu } from './utils/menus'

// 插件形式使用请求
import { postRequest } from "./utils/api";
import { getRequest } from "./utils/api";
import { putRequest } from "./utils/api";
import { deleteRequest } from "./utils/api";
Vue.prototype.$post = postRequest;
Vue.prototype.$put = putRequest;
Vue.prototype.$get = getRequest;
Vue.prototype.$delete = deleteRequest;

Vue.config.productionTip = false

// import { initMenu } from './utils/menus'
router.beforeEach((to, from, next) => {
  if (window.sessionStorage.getItem('tokenStr')) {
    initMenu(router, store);
    //存入用户信息到session
    // if (!window.sessionStorage.getItem("user")) {
    //   return getRequest('/user/userInfo').then(res => {
    //     if (res) {
    //       window.sessionStorage.setItem("user", JSON.stringify(res));
    //       next();
    //     }
    //   })
    // }
    next();
  } else {
    if (to.path == '/') {
      next();
    } else {
      next('/?redirect=' + to.path);
    }
  }
  // if (window.sessionStorage.getItem('loginTimes') == '0') {
  //   window.sessionStorage.setItem('loginTimes', "1");
  //   next({ name: "Home" });
  // }
  // if (to.routerId && window.sessionStorage.getItem('roleId') != to.routerId) {
  //   next();
  // }
})

Vue.use(ElementUI)

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
