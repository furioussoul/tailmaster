import {
  initDropEvents,
} from '../core/dnd'
import store from '../store'

export default {
  bind (el, binding, vnode) {
    initDropEvents(el)
    el.onmouseout = function (e) {
      store.commit('dragModule/clearEditLayer')
    }
    el.onmouseover = function (e) {
      e.preventDefault()
      e.stopPropagation()
      store.commit('dragModule/setEditLayer', {
        el,
        binding
      })
    }

  },
  update () {

  },
  unbind (el, binding) {

  }
};
