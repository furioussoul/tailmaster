import store from '../store/index'
import{
  deepCopy//为了soul和templateStore引用解耦
} from '../util/assist'
import {
  generateUid,
  resetUid,
  findSoul
} from './soul_helper'
import {
  addRenderFn
} from './code_helper'

let templateStore = {
  count: 0,//version number
  dataSnapshot: []//version array
};

function drop(saveInfo) {
  if (templateStore.count < templateStore.dataSnapshot.length) {
    //撤销操作后新增元素则丢弃当前版本后的节点
    templateStore.dataSnapshot = templateStore.dataSnapshot.slice(0, templateStore.count)
  }
  saveInfo.drop.children.push(saveInfo.drag)
  let data = store.getters['dragModule/soul']
  templateStore.dataSnapshot[templateStore.count] = deepCopy(data)
  templateStore.count++

  localStorage.setItem("templateStore", JSON.stringify(templateStore));
}

function undo() {
  if (templateStore.count < 1) {
    return false
  }
  let dataSnapshot = templateStore.dataSnapshot[templateStore.count - 2];
  dataSnapshot = dataSnapshot ? dataSnapshot : deepCopy(findSoul(100, store.getters['dragModule/controlConfigs']))
  let soulCopy = deepCopy(dataSnapshot)
  addRenderFn(soulCopy)
  store.commit('dragModule/setSoul', soulCopy)
  let pageSoul = store.getters['dragModule/pageSoul'];
  pageSoul[store.getters['dragModule/currentRouterPath']] = soulCopy
  templateStore.count--;
  return true;
}

function redo() {
  if (templateStore.count === templateStore.dataSnapshot.length) {
    return false
  }
  let soulSnap = templateStore.dataSnapshot[templateStore.count++]
  let soulCopy = deepCopy(soulSnap)
  addRenderFn(soulCopy)
  store.commit('dragModule/setSoul', soulCopy)
  let pageSoul = store.getters['dragModule/pageSoul'];
  pageSoul[store.getters['dragModule/currentRouterPath']] = soulCopy
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

function reload(controlConfigs) {
  let dropPanelSoul = findSoul(100, controlConfigs),
  copy = deepCopy(dropPanelSoul);
  copy.uid = generateUid()
  store.commit('dragModule/setSoul', copy)
  store.commit('dragModule/setPageSoul', {})
}

function saveSoul() {
  let data = store.getters['dragModule/soul']
  templateStore.dataSnapshot[templateStore.count] = deepCopy(data)//版本2
  templateStore.count++
  localStorage.setItem("templateStore", JSON.stringify(templateStore));
}

export {
  drop,
  undo,
  redo,
  clear,
  saveSoul,
  reload
}
