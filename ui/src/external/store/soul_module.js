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
      debugger
      let soul
      if (pagePath === '/' && !state.pageSoul[pagePath]) {
        soul = state.pageSoul['']
      }
      soul = state.pageSoul[pagePath]
      if (soul) state.soul = soul
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
