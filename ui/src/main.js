import Vue from 'vue';
import VueResource from "vue-resource";
import VueRouter from 'vue-router';
import Iview from 'iview';
import {appApi, appContext} from "./plugin/config";
import {initRouter, router, routerStore} from "./plugin/router";
import SoulUi from "./plugin/install_component"
import installDirective from "./plugin/install_directive"
import Store from './store/index'
import initAppConstructor from './plugin/app_constuctor'
import 'iview/dist/styles/iview.css'
import './style/index.less'
import Login from './view/esview/login.vue'
import RenderApp from './view/esview/render_app.vue'
import esview from './external'
import locale  from 'iview/dist/locale/en-US';

esview.addConfig('router',router)
esview.addConfig('type','assemble')

Vue.dev = true

Vue.use(VueResource)
Vue.use(VueRouter)
Vue.use(SoulUi)
Vue.use(Iview, { locale })

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
      case 'test':
        return h(esview.render('MyApp','token'))
    }
  }
})

app.$mount('#app');
