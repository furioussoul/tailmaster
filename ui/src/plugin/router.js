import Vue from "vue";
import Router from "vue-router";
import Vuex from "vuex";
import Sync from "vuex-router-sync";
import Render from '../core/render'
import store from '../store'

Vue.use(Vuex)
let rs = new Vuex.Store({
  state: {} // 把router的状态同步到state对象
});

let routerCache

export const router = new Router({
  base: '/',
  routes: [
    {
      path: '/login',
      component: () =>
        import ('../view/esview/login.vue')
    }
  ]
})

Sync.sync(rs, router, {
  moduleName: 'RouteModule'
})

export const routerStore = rs

/**
 * pages {array} 菜单
 */
export function initRouter(pages, assemblePage) {
  if (assemblePage) {
    let routes = pages.map((page, index) => {
      return {
        path: page.url,
        component: Render
      }
    })

    routes.push({
      path: store.getters['dragModule/soul'].model.appName.value,
      component: Render
    })

    //子应用路由加到'assemble/index'下，作为嵌套路由
    routerCache.forEach(router => {
      if (router.path.indexOf('assemble/index') > -1) {
        router.children = [].concat(routes)
      }
    })
    router.addRoutes(routerCache)
    router.routers =routes
    return
  }

  //esview的路由
  let routes = pages.map((page, index) => {
      const routeComponent = () =>
        import ('../view' + page.url + '.vue')
      return {
        path: page.url,
        component: routeComponent
      }
    }
  )
  routerCache = routes
  router.addRoutes(routes)
  router.routers =routes
}
