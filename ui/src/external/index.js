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

export default{
  addConfig
}   // eslint-disable-line no-undef

