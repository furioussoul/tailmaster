import store from '../store/index'
import eventCenter from '../core/event'

export default {
  name: 'Render',
  props: {
    soul:[Object]
  },
  render(h){
    if(!this.soul) return
    h.vm = this
    h.store = store
    h.eventCenter = eventCenter
    return this.soul.render(h)
  }
}
