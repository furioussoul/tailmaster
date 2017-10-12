import Vue from 'vue';
import store from './store'
import render from './component/render.vue'

import {
  appFrame
} from './component'

const clientConfig = {
  router: '',
  store
}

function option(option) {
  clientConfig.router = option.router
}

Vue.component('AppFrame', appFrame);

// 在这查 appSoul,放到store

export default {
  option,
  clientConfig,
  render
}


