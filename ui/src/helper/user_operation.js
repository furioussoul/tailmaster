import store from '../../store/index'
import{
  deepCopy//为了soul和templateStore引用解耦
} from '../../util/assist'
import {
  generateUid,
  resetUid
} from '../../core/soul_helper'
let templateStore = {
  count: 0,
  dataSnapshot: []
};

function drop(saveInfo) {
  if(templateStore.count < templateStore.dataSnapshot.length){
    //撤销操作后新增元素则丢弃当前版本后的节点
    templateStore.dataSnapshot = templateStore.dataSnapshot.slice(0,templateStore.count)
  }
  saveInfo.drop.children.push(saveInfo.drag)//版本1
  let data = store.getters['dragModule/soul']
  templateStore.dataSnapshot[templateStore.count] = clone(data)//版本2
  templateStore.count++
  templateStore.currentId = saveInfo.drag.props.id + 1

  localStorage.setItem("templateStore", JSON.stringify(templateStore));
}

function undo() {
  if (templateStore.count < 1) {
    return false
  }
  let dataSnapshot = templateStore.dataSnapshot[templateStore.count - 2];
  dataSnapshot = dataSnapshot ? dataSnapshot : {}
  store.commit('dragModule/setSoul',clone(dataSnapshot))
  templateStore.count--;
  return true;
}

function redo() {
  if (templateStore.count === templateStore.dataSnapshot.length) {
    return false
  }
  store.commit('dragModule/setSoul',templateStore.dataSnapshot[templateStore.count++])
  return true;
}

function clear() {
  localStorage.setItem("templateStore", null)
  resetId()
  store.commit('dragModule/setSoul',{
    props: {
      id: generateId(),
      name: 'TDiv',
    },
    children: []
  })
}

function saveSoul() {
  let data = store.getters['dragModule/soul']
  templateStore.dataSnapshot[templateStore.count] = clone(data)//版本2
  templateStore.count++
  localStorage.setItem("templateStore", JSON.stringify(templateStore));
}

export {
  drop,
  undo,
  redo,
  clear,
  saveSoul
}
