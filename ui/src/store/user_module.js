

export default {
  namespaced: true,
  state: {
    me: 1,
    page: "test"
  },
  getters: {
    me: ({me}) => me,
    page: ({page}) => page
  },
  mutations: {
    changePage(state,page){
      state.page = page
    }
  },
  actions: {}
}
