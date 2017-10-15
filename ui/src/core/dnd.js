/**
 *  drag and drop based on html5
 *  @author SunZhengJie
 *  @email 200765821@qq.com
 */

import store from '../store/index'

import {
  deepCopy
} from '../util/assist'
import {
  generateUid,
  findSoul
} from '../helper/soul_helper'
import {
  drop
} from '../helper/user_operation'

function onDragStart(e) {
  store.commit('dragModule/setDragElement', e.target)
  return true;
}

function onDrag(e) {
  return true
}

function onDragEnd(e) {
  e.preventDefault();
  store.commit('dragModule/setDragElement', null)
  return true
}

function onDragEnter(e) {

  e.preventDefault()
  e.stopPropagation()
  return true
}

function onDragOver(e) {
  e.preventDefault()
  e.stopPropagation()

  const drag = store.getters['dragModule/getDragElement'];

  if (validateDrop(drag, this)) {
    markDrop(this, true)
  }

  return true;
}

function onDragLeave(e) {
  e.preventDefault()
  e.stopPropagation()
  markDrop(this, false)
  return true;
}

function validateDrop(drag, drop) {

  if (!drag) {
    return false
  }

  if(drag.controlConfig.allowPlace){
    //优先处理allow_place: FormItem只能放在Form里面
    return drag.controlConfig.allowPlace.indexOf(drop.controlConfig.cid) > -1;

  }else {
    if (!drop || !drop.controlConfig.allow) {
      return false

    } else if(drop.controlConfig.allow && drop.controlConfig.allow.length === 0){
      return true

    }else if (drop.controlConfig.allow.indexOf(drag.controlConfig.cid) > -1) {
      return true
    }
  }
}

function markDrop(drop, mark) {
  if (mark) {
    if (!drop.classList.contains('drop')) {
      drop.classList.add('drop')
    }
  } else {
    drop.classList.remove('drop')
  }
}

function interceptDrop(saveInfo) {
  if(saveInfo.drag.type === 'AppFrame'){
    let dropPanelSoul = findSoul(100, store.getters['dragModule/controlConfigs'])
    saveInfo.drag.children.push(deepCopy(dropPanelSoul))
    store.commit('dragModule/setOriginSoul',saveInfo.drag)
    store.commit('dragModule/setPageSoul',{
      path:'/index',
      pageSoul:saveInfo.drag
    })
  }else if(saveInfo.drag.type === 'WrapCard'){
    let dropPanelSoul = findSoul(100, store.getters['dragModule/controlConfigs'])
    saveInfo.drag.children.push(deepCopy(dropPanelSoul))
    saveInfo.drag.children.push(deepCopy(dropPanelSoul))
    saveInfo.drag.children.push(deepCopy(dropPanelSoul))
  }
}

function onDrop(e) {
  e.stopPropagation();
  e.preventDefault();

  const drag = store.getters['dragModule/getDragElement'];

  if (!validateDrop(drag,this)) {
    return false;
  }

  let uid = generateUid()
  let copy = deepCopy(drag.controlConfig)
  copy.uid = uid

  const saveInfo = {
    drag:copy,
    drop:this.controlConfig
  }

  interceptDrop(saveInfo)

  drop(saveInfo)

  markDrop(this,false)
  return true;
}

function initDropEvents(drag) {
  drag.ondragenter = onDragEnter
  drag.ondragover = onDragOver
  drag.ondrop = onDrop
  drag.ondragleave = onDragLeave
}

export {
  onDragStart,
  onDrag,
  onDragEnd,
  onDragEnter,
  onDragOver,
  onDrop,
  onDragLeave,
  initDropEvents
}
