import Vue from 'vue';
import Vuex from 'vuex';
import soulModule from './soul_module'
Vue.use(Vuex);

const store = new Vuex.Store({
  modules:{
    soulModule
  }
});

export default store
