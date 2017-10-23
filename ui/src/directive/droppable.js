import {
  initDropEvents,
} from '../core/dnd'

export default {
  bind (el, binding, vnode) {
    initDropEvents(el)
  }
};
