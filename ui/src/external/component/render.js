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
    h.$store = getConfig('store')
    h.$util = getConfig('util')
    h.vm = this
    h.store = store
    h.eventCenter = eventCenter
    return this.soul.renderProd(h)
  }
}
