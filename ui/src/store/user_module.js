export default {
  namespaced: true,
  state: {
    me: {},
    page: "manage" //test
  },
  getters: {
    me: ({me}) => me,
    page: ({page}) => page
  },
  mutations: {
    setMe(state,me){
      state.me = me
    },
    changePage(state, page){
      state.page = page
    }
  },
  actions: {
    getControlClazzes({commit}){
      commit('getControlClazzes')
    }
  }
}
