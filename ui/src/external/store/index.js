import Vue from 'vue';
import Vuex from 'vuex';
import routerModule from './router_module'
import soulModule from './soul_module'
Vue.use(Vuex);

const store = new Vuex.Store({
  modules:{
    routerModule,
    soulModule
  }
});

export default store
