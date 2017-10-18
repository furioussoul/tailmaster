import {
  initDropEvents,
} from '../core/dnd'
import store from '../store'

export default {
  bind (el, binding, vnode) {
    initDropEvents(el)
  },
  update () {

  },
  unbind (el, binding) {

  }
};
