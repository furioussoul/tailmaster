import Vue from 'vue';
import Vuex from 'vuex';
import userModule from './user_module'
import dragModule from './drag_module'
import routerModule from './router_module'
import Sync from "vuex-router-sync";
import {
  router
} from '../plugin/router'
Vue.use(Vuex);

const store = new Vuex.Store({
  modules:{
    userModule,
    dragModule,
    routerModule
  }
});

Sync.sync(store, router, {
  moduleName: 'routerModule'
})

export default store
