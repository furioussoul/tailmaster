import Vue from "vue";
import Router from "vue-router";
import Vuex from "vuex";
import Sync from "vuex-router-sync";
import Render from '../core/render'
import RenderApp from '../view/esview/render_app.vue'

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
export function initRouter(pages,assemblePage) {
  if(assemblePage){
    let routes = pages.map((page, index) => {
      return {
        path: page.url,
        component: Render
      }
    })
    routerCache.forEach(router=>{
      if(router.path.indexOf('assemble/index') > -1){
        router.children = [].concat(routes)
      }
    })
    router.addRoutes(routerCache)
    return
  }

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
}
