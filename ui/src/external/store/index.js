import Vue from 'vue';
import Vuex from 'vuex';
import routerModule from './router_module'
import soulModule from './soul_module'
import dragModule from './drag_module'
Vue.use(Vuex);

const store = new Vuex.Store({
  modules:{
    routerModule,
    soulModule,
    dragModule
  }
});

export default store
