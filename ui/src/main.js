import Vue from 'vue';
import VueResource from "vue-resource";
import VueRouter from 'vue-router';
import Iview from 'iview';
import {appApi, appContext} from "./plugin/config";
import {initRouter, router, routerStore} from "./plugin/router";
import SoulUi from "./plugin/install_component"
import Store from './store/index'
import initAppConstructor from './plugin/app_constuctor'
import 'iview/dist/styles/iview.css'
import './style/index.less'
import directive from './directive/d_drop'
import Login from './view/esview/login.vue'
import RenderApp from './view/esview/render_app.vue'

Vue.dev = true

Vue.directive('drop',directive)
Vue.use(VueResource)
Vue.use(VueRouter)
Vue.use(SoulUi)
Vue.use(Iview)

Vue.http.options.root = appContext.apiUrl;

let config = {
  appApi: appApi,
  initRouter: initRouter,
  routerStore: routerStore
}

const manage = initAppConstructor(config);

const app = new Vue({
  store:Store,
  router: router,
  render (h) {
    if(!Store.getters['userModule/me']){
      return h(Login)
    }

    switch (Store.getters['userModule/page']){
      case 'manage':
        return h(manage)
      case 'app':
        return h(RenderApp)
    }
  }
})

app.$mount('#app');
