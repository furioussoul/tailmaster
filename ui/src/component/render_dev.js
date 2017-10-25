import store from '../store/index'
import eventCenter from '../core/event'
import {
  findSoul
} from '../helper/soul_helper'
import {
  deepCopy
} from '../util/assist'

const util={deepCopy}

export default {
  name: 'Render',
  props: {
    soul:[Object]
  },
  render(h){
    if(!this.soul) return
    let dropPanel = findSoul(100, store.getters['dragModule/draggableControls'])

    h.vm = this
    h.util = util
    h.controls ={
      dropPanel
    }
    h.store = store
    h.eventCenter = eventCenter
    return this.soul.render(h)
  }
}
