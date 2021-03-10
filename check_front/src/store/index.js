import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    route: {},
    flag: 0,
    classes: [],
  },
  mutations: {
    initRoute(state, data) {
      state.route = data;
      state.flag = 1;
    },
    // initClass(state, data) {
    //   state.classes = data;
    // }
  },
  actions: {
  },
  modules: {
  }
})
