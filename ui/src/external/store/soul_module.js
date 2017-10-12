import {
  deepCopy
} from '../../util/assist'

export default {
  namespaced: true,
  state: {
    soul: null,//展示用的组件树
    originSoul:null,//初始化soul
    pageSoul: {},//对应路由的soul
  },
  getters: {
    soul({soul}){
      return soul
    }
  },
  mutations: {
    changeSoul(state, pagePath){
      if(!pagePath){
        pagePath = ''
      }
      if (!state.pageSoul[pagePath]) {
        const soulCopy = deepCopy(state.originSoul)
        state.pageSoul[pagePath] = soulCopy
        state.soul = soulCopy
        return
      }
      state.soul = state.pageSoul[pagePath]
      this.$router.push(path)
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
