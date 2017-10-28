import store from '../store'

export default {
  bind (el, binding, vnode) {
    el.onmouseout = function (e) {
      e.preventDefault()
      e.stopPropagation()
      store.commit('dragModule/clear','layer')
    }
    el.onmouseover = function (e) {
      e.preventDefault()
      e.stopPropagation()
      store.commit('dragModule/setEditLayer', {
        el,
        binding
      })
    }
    el.oncontextmenu = function (e) {
      e.preventDefault()
      e.stopPropagation()
      store.commit('dragModule/setRightClickMenu', el)
      return false;
    }
  }
};
