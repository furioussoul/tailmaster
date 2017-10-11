import Render from '../core/render'
import Vue from "vue";
import Vuex from "vuex";
import Sync from "vuex-router-sync";
import {router} from "../plugin/router";
import {clientConfig} from '../external'

Vue.use(Vuex);
let routerStore = new Vuex.Store({
  state: {} // 把router的状态同步到state对象
});

Sync.sync(routerStore, router, {
  moduleName: 'RouteModule'
});

/**
 * 获取浏览器内的cookie
 * @param name cookie名称
 * @returns {*}
 */
function getCookie(name) {
  const reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
  let arr;
  if (arr = document.cookie.match(reg)) {
    return decodeURIComponent(arr[2]);
  }
  return null;
}

/**
 * 获取面包屑
 * @param menus 菜单
 * @param path 当前路径
 * @returns {*}
 */
function getBreadcrumb(menus, path) {
  let breadcrumb = [];
  if (menus && menus.length) {
    let flag = false;
    for (let i = 0; i < menus.length; i++) {

      if (flag) {
        return breadcrumb;
      }

      //mtype === 1 说明menu是个页面
      if ((menus[i].mtype === 1 || menus[i].mtype === -1) && path.indexOf(menus[i].url) !== -1 && menus[i].url.substr(-1, 1) !== '/') {
        flag = true;
        return [menus[i]];
      }

      breadcrumb = getBreadcrumb(menus[i].subMenuList, path);
      if (breadcrumb.length) {
        return [menus[i]].concat(breadcrumb);
      }
    }
  }
  return breadcrumb;
}

/**
 * 把菜单生成vue路由对象
 * @param menus 菜单
 * @returns {Array}
 */
function getPages(menus) {
  let pages = [];
  if (menus && menus.length > 0) {
    for (let i = 0; i < menus.length; i++) {
      if (!menus[i].subMenuList || menus[i].subMenuList.length === 0) {
        pages.push(menus[i]);
      } else {
        pages = pages.concat(getPages(menus[i].subMenuList));
      }
    }
  }
  return pages;
}
/**
 * pages {array} 菜单
 */

function initRouter(pages, assemblePage) {
  let router = clientConfig.router;

  if (assemblePage) {

    let routes = pages.map((page, index) => {
      return {
        path: '/test/child2',
        component: Render
      }
    });

    router.routers.forEach(router => {
      if (router.path.indexOf('assemble/index') > -1) {
        debugger
        router.children = [].concat(routes)
      }
    });

    return
  }


  //esview的路由
  let routes = pages.map((page, index) => {
      let routeComponent;
      try {
        routeComponent = () =>
          import ('../view' + page.url + '.vue');

        return {
          path: page.url,
          component: routeComponent
        }

      } catch (e) {
        return {
          path: page.url,
          component: Render
        }
      }
    }
  );
  router.addRoutes(routes);
  router.routers =routes
}

export {
  getCookie,
  getBreadcrumb,
  getPages,
  initRouter,
  routerStore
}
