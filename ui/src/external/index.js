import Vue from 'vue';
import store from './store'
import eventCenter from '../core/event'

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


function render(h, {
  token,
  appName,
  totalMenu,
  userInfo
}) {
  h.store = store
  h.eventCenter = eventCenter
  //get appSoul,menu by token and appName
  let appSoul
  return appSoul.render(h, {
    totalMenu,
    userInfo
  })
}

export default {
  option,
  clientConfig,
  render
}


