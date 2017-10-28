import Vue from 'vue'
export default {
  namespaced: true,
  state: {
    me: {},
    page: "test", //test
    controlClazzes: []
  },
  getters: {
    me: ({me}) => me,
    page: ({page}) => page,
    controlClazzes: ({controlClazzes}) => controlClazzes
  },
  mutations: {
    setMe(state,me){
      state.me = me
    },
    changePage(state, page){
      state.page = page
    },
    getControlClazzes(state){
      Vue.http.post('class/classList').then(res => {
        if (res.data.code === 10000) {
          state.controlClazzes = res.data.data
        } else {
          console.error('queried failed')
        }
      })
    }
  },
  actions: {
    getControlClazzes({commit}){
      commit('getControlClazzes')
    }
  }
}
