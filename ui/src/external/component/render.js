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
    h.store = store
    h.eventCenter = eventCenter
    this.soul.script(eventCenter,getConfig('store'))
    return this.soul.renderProd(h)
  }
}
