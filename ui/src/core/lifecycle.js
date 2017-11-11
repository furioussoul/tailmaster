import {
  findSoulByUidDown
} from '../helper/soul_helper'
import {
  deepCopy,
  isPlain,
  isNumber
}from '../util/assist'

function resetSoul(soul) {
  if (isPlain(soul.model))return
  for (let key in soul.model) {
    if (soul.model[key].exclude) continue
    let copy = soul.model[key].value
    if (!soul.model[key]) continue
    Object.defineProperty(soul.model[key], 'value', {
      set: (n) => {
        if (copy !== n) {
          if (isNumber(n)) {
            n = Number(n)
          }
          copy = n
          reset(soul)
        }
      },
      get: () => {
        return copy
      }
    })
  }
}

export function reset(soul) {
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
