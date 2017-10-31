import {
  findSoulByUidDown
} from '../helper/soul_helper'
import {
  deepCopy
}from '../util/assist'

function resetSoul(soul) {
  for (let key in soul.model) {
    let copy
    Object.defineProperty(soul.model[key], 'value', {
      set: (n) => {
        copy = n
        reset(soul)
      },
      get: () => {
        return copy
      }
    })
  }
}

function reset(soul) {
  let pSoul = findSoulByUidDown(soul.pid);
  if (pSoul) {
    let soulCopy = deepCopy(soul)
    let index
    for (let i = 0; i < pSoul.children.length; i++) {
      if (pSoul.children[i].uid === soul.uid) {
        index = i
        break
      }
    }

    pSoul.children.splice(index, 1)
    setTimeout(() => {
      soulCopy.initScript = false
      pSoul.children.splice(index, 0, soulCopy)
    }, 1)
  }
}

function fire(stage, soul) {
  if (soul[stage]) soul[stage](soul)
}

export {
  fire,
  resetSoul
}
