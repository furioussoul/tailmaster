import Vue from 'vue';
import {
  appFrame,
  wrapCard
} from './component'

import {
  addConfig
} from './config'

Vue.component('AppFrame', appFrame);
Vue.component('WrapCard', wrapCard);

import store from './store'
import renderVue from './component/render.vue'
import {addRenderFn} from '../helper/code_helper'

// 在这查 appSoul,放到store
function render(appName, token) {
  let pageSoul = JSON.parse(localStorage.getItem('pageSoul'));
  for (let key in pageSoul) {
    let soul = pageSoul[key];
    addRenderFn(pageSoul[key])
  }
  store.commit('soulModule/setPageSoul', pageSoul)
  store.commit('soulModule/setSoul', pageSoul['/index'])
  return renderVue
}



export default{
  addConfig,
  render
}   // eslint-disable-line no-undef

