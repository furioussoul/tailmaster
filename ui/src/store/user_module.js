import Vue from 'vue'
export default {
  namespaced: true,
  state: {
    me: 1,
    page: "manage",
    controlClazzes: []
  },
  getters: {
    me: ({me}) => me,
    page: ({page}) => page,
    controlClazzes: ({controlClazzes}) => controlClazzes
  },
  mutations: {
    changePage(state, page){
      state.page = page
    },
    getControlClazzes(state){
      Vue.http.post('class/classList').then(res => {
        if (res.data.code === 10000) {
          state.controlClazzes = res.data.data
        } else {
          this.$Message.error('queried failed')
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
