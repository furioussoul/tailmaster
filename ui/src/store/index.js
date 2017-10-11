import Vue from 'vue';
import Vuex from 'vuex';
import userModule from './user_module'
import dragModule from './drag_module'
Vue.use(Vuex);

const store = new Vuex.Store({
  modules:{
    userModule,
    dragModule
  }
});
export default store
