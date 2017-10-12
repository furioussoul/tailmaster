import Vue from 'vue';
import {
  appFrame
} from './component'

import {
  addConfig
} from './config'

Vue.component('AppFrame', appFrame);

import store from './store'
import renderVue from './component/render.vue'
import {getConfig} from '../helper/code_helper'

// 在这查 appSoul,放到store
function render(appName, token) {
  let pageSoul = JSON.parse(localStorage.getItem('pageSoul'));
  for (let key in pageSoul) {
    let soul = pageSoul[key];
    addRenderFn(pageSoul[key])
  }
  store.commit('soulModule/setPageSoul', pageSoul)
  store.commit('soulModule/setSoul', pageSoul[''])
  return renderVue
}

function addRenderFn(soul) {
  let config = getConfig(soul.code);
  soul.render = config.render
  soul.children.forEach(child => {
    addRenderFn(child)
  })
}

export default{
  addConfig,
  render
}   // eslint-disable-line no-undef

