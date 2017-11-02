import store from '../store/index'
let uid = 1
const generateUid = (() => uid++)
const resetUid =function (newuid) {
  uid = newuid+1 || 1
}
const currentUid = (() => uid)

function walkSoul(soul,fn) {
  fn.call(soul,soul)
  soul.children.forEach(child=>{
    walkSoul(child,fn)
  })
}

function refreshInitScript(soul) {
  if(!soul) return
  soul.initScript=false
  for (let i = 0; i < soul.children.length; i++) {
    refreshInitScript(soul.children[i])
  }
}

function findSoulByCTypeUp(type, drag, soul) {
  if (drag.type === type)return drag
  let dragParent = findSoulByUidDown(drag.pid,soul)
  return findSoulByCTypeUp(type,dragParent,soul)
}

function findSoulByCid(cid, controls) {
  if(!controls){
    controls = store.getters['dragModule/draggableControls']
  }
  for (let i = 0; i < controls.length; i++) {
    if (cid == controls[i].cid) {
      return controls[i]
    }
  }
}

function findSoulByUidDown(uid, soul) {
  if (!soul) {
    soul = store.getters['dragModule/soul']
  }

  if (uid === soul.uid) {
    return soul
  }

  let node;
  for (let i = 0; i < soul.children.length; i++) {
    node = findSoulByUidDown(uid, soul.children[i])
    if (node) {
      return node;
    }
  }
}

export {
  generateUid,
  findSoulByUidDown,
  resetUid,
  currentUid,
  findSoulByCid,
  refreshInitScript,
  findSoulByCTypeUp,
  walkSoul
}
