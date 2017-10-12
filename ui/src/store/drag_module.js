import {
  findNode
} from '../helper/soul_helper'
import {
  findElUpward
} from '../helper/dom_helper'
import {
  isPlain
}from '../util/assist'
import {
  deepCopy
} from '../util/assist'
export default {
  namespaced: true,
  state: {
    dragElement: null,//当前被拖拽的元素
    soul: null,//展示用的组件树
    originSoul:null,//初始化soul
    pageSoul: {},//对应路由的soul
    showEditorPanel: false,
    editSoul: null
  },
  getters: {
    appSoul({appSoul}){
      return appSoul
    },
    editSoul({editSoul}){
      return editSoul
    },
    getDragElement({dragElement}){
      return dragElement
    },
    soul({soul}){
      return soul
    },
    showEditorPanel({showEditorPanel}){
      return showEditorPanel;
    }
  },
  mutations: {
    changeSoul(state, pagePath){
      if (!pagePath) {
        pagePath = decodeURIComponent('')
      }
      if (!state.pageSoul[pagePath]) {
        const soulCopy = deepCopy(state.originSoul)
        state.pageSoul[pagePath] = soulCopy
        state.soul = soulCopy
        return
      }
      state.soul = state.pageSoul[pagePath]
    },
    setAppSoul(state, appSoul){
      state.appSoul = appSoul
    },
    setDragElement(state, element){
      state.dragElement = element
    },
    setSoul(state, soul){
      state.originSoul = deepCopy(soul)
      state.soul = state.pageSoul[''] = soul
    },
    showEditorPanel(state, e){

      e.stopPropagation()
      const el = findElUpward(e.target);
      const soul = findNode(el.controlConfig.uid, state.soul);
      if (!soul || !isPlain(soul.model)) {
        state.showEditorPanel = true
        state.editSoul = soul
        return true
      }

      state.showEditorPanel = false
      state.editSoul = null
    },
    hideEditorPanel(state, e){
      e.stopPropagation()
      state.showEditorPanel = false
    }
  },
  actions: {
    savePageSoul({state}){
      localStorage.setItem('pageSoul', JSON.stringify(state.pageSoul))
    }
  }
}
