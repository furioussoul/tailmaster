import store from '../store/index'
let uid = 1
const generateUid = (() => uid++)
const resetUid =function (newuid) {
  uid = newuid || 1
}
const currentUid = (() => uid)

function resetModel(pageSoul) {
  for (let key in pageSoul) {
    if (key !== 'maxUid') {
      reset(pageSoul[key])
    }
  }
}

function reset(soul) {
  if(!soul) return
  soul.initScript=false
  // if (soul.model && !soul.model.save) {
  //   soul.model.value = ''
  // }
  //
  for (let i = 0; i < soul.children.length; i++) {
    reset(soul.children[i])
  }
}

function findSoul(cid, controls) {
  if(!controls){
    controls = store.getters['dragModule/draggableControls']
  }
  for (let i = 0; i < controls.length; i++) {
    if (cid == controls[i].cid) {
      return controls[i]
    }
  }
}

function findNode(uid, soul) {
  if (!soul) {
    soul = store.getters['dragModule/soul']
  }

  if (uid === soul.uid) {
    return soul
  }

  let node;
  for (let i = 0; i < soul.children.length; i++) {
    node = findNode(uid, soul.children[i])
    if (node) {
      return node;
    }
  }
}

export {
  generateUid,
  findNode,
  resetUid,
  currentUid,
  findSoul,
  resetModel
}
