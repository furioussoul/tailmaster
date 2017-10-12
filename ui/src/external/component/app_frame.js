import Render from '../../core/render'
import esview from '../index'

let clientConfig = esview.clientConfig

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

function initRouter(pages, assemblePage) {
  let router = clientConfig.router;

  if (assemblePage) {


    return
  }

  //router configuration of client app
  let routes = pages.map((page, index) => {

    //.vue file has higher priority
      try {
        let routeComponent = () => import ('../view' + page.url + '.vue');

        return {
          path: page.url,
          component: routeComponent
        }

      } catch (e) {
        //use esview page if cant load .vue file by router url
        return {
          path: page.url,
          component: Render
        }
      }

    }
  )

  router.addRoutes(routes);
}

export {
  getBreadcrumb,
  getPages,
  initRouter
}
