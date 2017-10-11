//核心数据,树结构
import store from '../store/index'
let uid = 1
const generateUid = (() => uid++)
const resetUid = (newuid => uid = newuid || 1)
const currentUid = (() => uid)

function findSoul(cid, controls) {
  for(let i =0;i<controls.length;i++){
    if(cid == controls[i].cid){
      return controls[i]
    }
  }
}

function findNode(uid, soul) {
  if(!soul){
    soul = store.getters['dragModule/soul']
  }
  if (uid === soul.uid) {
    return soul
  }

  let nodes = soul.children.filter(child => child.uid === uid);
  if (nodes.length > 0) {
    return nodes[0]
  }

  let node;
  if (nodes.length === 0) {
    for (let i = 0; i < soul.children.length; i++) {
      node = findNode(uid, soul.children[i])
      if (node) {
        return node;
      }
    }
  }
}

export {
  generateUid,
  findNode,
  resetUid,
  currentUid,
  findSoul
}
