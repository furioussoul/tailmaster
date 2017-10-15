import Vue from 'vue';
import {
  appFrame,
  wrapCard
} from './component'
import {
  addConfig
} from './config'
import {
  parse
}from '../util/assist'
import {
  getAppList
}from '../resource/assemble_resource'

Vue.component('AppFrame', appFrame);
Vue.component('WrapCard', wrapCard);

import store from './store'
import renderVue from './component/render.vue'
import {addRenderFn} from '../helper/code_helper'

// 在这查 appSoul,放到store
function render(appName, token) {
  getAppList.call(Vue,{
    appName,
    token
  },data=>{
    let pageSoul = parse(data.pageSoul)
    for (let key in pageSoul) {
      if(key !=='maxUid'){
        let soul = pageSoul[key];
        addRenderFn(pageSoul[key])
      }
    }
    store.commit('soulModule/setPageSoul', pageSoul)
    store.commit('soulModule/setSoul', pageSoul['/index'])
  })
  return renderVue
}

export default{
  addConfig,
  render
}   // eslint-disable-line no-undef

