import Sync from "vuex-router-sync";
import store from './store'

let configs = {}
export function addConfig(key ,value) {
  if(key === 'router'){
    Sync.sync(store, value, {
      moduleName: 'routerModule'
    })
  }

  configs[key] = value
}

export function getConfig(key){
  return configs[key]
}

