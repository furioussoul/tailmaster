import store from '../store'
import eventCenter from '../../core/event'
import {
  getConfig
} from '../config'
export default {
  name: 'Render',
  props: {
    soul:[Object]
  },
  render(h){
    if(!this.soul) return
    h.$router = getConfig('router')
    h.$util = getConfig('util')
    h.store = getConfig('store')
    h.vm = this
    h.store = store
    h.eventCenter = eventCenter
    eventCenter.createElement = h
    return this.soul.render(h)
  }
}
