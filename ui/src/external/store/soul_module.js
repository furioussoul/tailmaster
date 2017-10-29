export default {
  namespaced: true,
  state: {
    soul: null
  },
  getters: {
    soul({soul}){
      return soul
    }
  },
  mutations: {
    setSoul(state, soul){
      state.soul = soul
    }
  },
  actions: {}
}
