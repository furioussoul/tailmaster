import {findNode} from '../helper/soul_helper'
import {findElUpward} from '../helper/dom_helper'
import {isPlain}from '../util/assist'
import {deepCopy, getQueryParam} from '../util/assist'

export default {
  namespaced: true,
  state: {
    originSoul: null,//flatten soul of frame with dropPanel
    soul: null,//the view data of current routerPath
    pageSoul: {},//view data of all routerPaths
    editSoul: {},//editable part of soul which you can edit on the right side of drop area
    dragElement: null,//current dragging element
    draggableControls: null,//draggableControls are the draggable items on the left side of dropPanel
    currentRouterPath: '',//current path of window's path, not hash
    showEditorPanel: false,//show the panel of 'editSoul'
    editLayer: {},//the cover appears when hover the dropped element
    rightClickMenu: {}//right click menu when right click the dropped element
  },
  getters: {
    soul: ({soul}) => soul,
    pageSoul: ({pageSoul}) => pageSoul,
    editSoul: ({editSoul}) => editSoul,
    dragElement: ({dragElement}) => dragElement,
    draggableControls: ({draggableControls}) => draggableControls,
    currentRouterPath: ({currentRouterPath}) => currentRouterPath,
    showEditorPanel: ({editSoul}) => !isPlain(editSoul),
    editLayer: ({editLayer}) => editLayer,
    rightClickMenu: ({rightClickMenu}) => rightClickMenu
  },
  mutations: {
    setOriginSoul(state, soul){
      state.originSoul = deepCopy(soul)
    },
    setSoul: (state, soul) => {
      state.soul = soul
    },
    setPageSoul(state, {path, pageSoul}){
      if (path) {
        state.pageSoul[path] = pageSoul
      } else {
        state.pageSoul = pageSoul
      }
    },
    syncSoul(state, soul){
      //sync changes of soul to pageSoul by routerPath
      state.pageSoul[state.currentRouterPath] = soul
    },
    setDraggableControls(state, draggableControls){
      state.draggableControls = draggableControls
    },
    setDragElement(state, element){
      state.dragElement = element
    },
    clear(state){
      state.editSoul = {}
      state.rightClickMenu = {}
    },
    clearEditLayer(state){
      state.editLayer = {
        style: {
          display: 'none'
        }
      }
    },
    setEditLayer(state, bind){
      let rect = bind.el.getBoundingClientRect();
      state.editLayer = {
        style: {
          left: rect.left + 'px',
          top: rect.top + 'px',
          width: rect.width + 'px',
          height: rect.height + 'px',
          display: 'block'
        },
        name: bind.binding.value
      }
    },
    setRightClickMenu(state, el){
      let e = e || window.event;
      //x,y of mouse
      let oX = e.clientX;
      let oY = e.clientY - 20;
      //x,y of menu appears
      state.rightClickMenu = {
        style: {
          display: "block",
          left: oX + "px",
          top: oY + "px"
        },
        uid: el.controlConfig.uid
      }
    },
    changeSoul(state){
      let path = decodeURIComponent(getQueryParam('pageId'))
      if (!path) return
      state.currentRouterPath = path

      if (!state.pageSoul[path]) {

        if (!state.originSoul) return

        const soulCopy = deepCopy(state.originSoul)
        state.pageSoul[path] = soulCopy
        state.soul = soulCopy
        return
      }
      state.soul = state.pageSoul[path]
    },
    showEditorPanel(state, e){
      e.stopPropagation()
      state.rightClickMenu = {}
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
  actions: {}
}
