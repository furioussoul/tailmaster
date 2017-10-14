import {
  deepCopy
} from '../../util/assist'

export default {
  namespaced: true,
  state: {
    soul: null,//展示用的组件树
    pageSoul: {},//对应路由的soul
  },
  getters: {
    soul({soul}){
      return soul
    }
  },
  mutations: {
    changeSoul(state, pagePath){
      let soul = state.pageSoul[pagePath]
      if (soul) state.soul = soul
      console.log(soul)
    },
    setSoul(state, soul){
      state.originSoul = deepCopy(soul)
      state.soul = soul
    },
    setPageSoul(state, pageSoul){
      state.pageSoul = pageSoul
    }
  },
  actions: {}
}
