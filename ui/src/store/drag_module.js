import {generateUid, findSoul} from '../helper/soul_helper'
import {deepCopy, getQueryParam,isPlain} from '../util/assist'

export default {
  namespaced: true,
  state: {
    soul: null,//the view data of current routerPath
    dragElement: null,//current dragging element
    draggableControls: null,//draggableControls are the draggable items on the left side of dropPanel
    editLayer: {},//the cover appears when hover the dropped element
    rightClickMenu: {}//right click menu when right click the dropped element
  },
  getters: {
    soul: ({soul}) => soul,
    dragElement: ({dragElement}) => dragElement,
    draggableControls: ({draggableControls}) => draggableControls,
    editLayer: ({editLayer}) => editLayer,
    rightClickMenu: ({rightClickMenu}) => rightClickMenu
  },
  mutations: {
    setSoul: (state, soul) => {
      state.soul = soul
    },
    setDraggableControls(state, draggableControls){
      state.draggableControls = draggableControls
    },
    setDragElement(state, element){
      state.dragElement = element
    },
    clear(state,type){
      if(type==='layer'){
        state.editLayer = {
          style: {
            display: 'none'
          }
        }
      }else {
        state.editSoul = {}
        state.rightClickMenu = {}
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
    }
  },
  actions: {}
}
