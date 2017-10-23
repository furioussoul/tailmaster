import store from '../store'
import eventCenter from './event'

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
