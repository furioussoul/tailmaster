import store from '../store/index'
import{
  deepCopy//为了soul和templateStore引用解耦
} from '../util/assist'
import {
  generateUid,
  resetUid,
  findSoulByCid,
  walkSoul
} from '../helper/soul_helper'
import {
  stringify,
  parse
}from '../util/assist'
import {
  resetSoul
} from '../core/lifecycle'

let templateStore = {
  count: 0,//version number
  dataSnapshot: []//version array
};

function resetSnapShot() {
  templateStore = {
    count: 0,//version number
    dataSnapshot: []//version array
  };
}

function drop(saveInfo) {
  if (templateStore.count < templateStore.dataSnapshot.length) {
    //撤销操作后新增元素则丢弃当前版本后的节点
    templateStore.dataSnapshot = templateStore.dataSnapshot.slice(0, templateStore.count)
  }

  saveInfo.drop.children.push(saveInfo.drag)
  let soul = store.getters['dragModule/soul']
  templateStore.dataSnapshot[templateStore.count] = deepCopy(soul)
  templateStore.count++

  localStorage.setItem("templateStore", stringify(templateStore));
}

function undo() {
  if (templateStore.count < 1) {
    return false
  }
  let dataSnapshot = templateStore.dataSnapshot[templateStore.count - 2];
  if(!dataSnapshot) {
    let dropPanelSoul = findSoulByCid(100, store.getters['dragModule/draggableControls'])
    dropPanelSoul.uid = generateUid()
    dataSnapshot = dropPanelSoul
  }
  let soulCopy = deepCopy(dataSnapshot)

  store.commit('dragModule/setSoul', soulCopy)
  walkSoul(soulCopy,(soul)=>{
    resetSoul(soul)
  })

  templateStore.count--;
  return true;
}

function redo() {
  if (templateStore.count === templateStore.dataSnapshot.length) {
    return false
  }
  let soulSnap = templateStore.dataSnapshot[templateStore.count++]
  let soulCopy = deepCopy(soulSnap)

  store.commit('dragModule/setSoul', soulCopy)
  walkSoul(soulCopy,(soul)=>{
    resetSoul(soul)
  })
  return true;
}

function clear() {
  localStorage.setItem("templateStore", null)
  resetUid()
  store.commit('dragModule/setSoul', {
    props: {
      id: generateUid(),
      name: 'TDiv',
    },
    children: []
  })
}

function init(draggableControls) {
  let dropPanelSoul = findSoulByCid(100, draggableControls),
  copy = deepCopy(dropPanelSoul);//before drop ,must copy drag control
  copy.uid = generateUid() //dropped control has unique uid
  store.commit('dragModule/setSoul', copy)
}

function saveSoul() {
  let data = store.getters['dragModule/soul']
  templateStore.dataSnapshot[templateStore.count] = deepCopy(data)//版本2
  templateStore.count++
  localStorage.setItem("templateStore", stringify(templateStore));
}

export {
  drop,
  undo,
  redo,
  clear,
  saveSoul,
  init,
  resetSnapShot
}
