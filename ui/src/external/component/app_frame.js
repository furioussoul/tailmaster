import renderVue from './render.vue'
import {
  getConfig
} from '../config'

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

// make vue router path
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

function initRouter(pages) {

  if (getConfig('type') === 'assemble') {
    //frame is used in the assemble_page.vue
    let routes = pages.map((page, index) => {
      page.url = '/esview/assemble/assemble_page?pageId='
        + encodeURIComponent(page.url)
    })
    return
  }

  let routes = pages.map((page) => {
      return {
        path: page.url,
        component: renderVue //all routerPath render components 'renderVue'
      }
    }
  )

  getConfig('router').addRoutes(routes);
}

export {
  getBreadcrumb,
  getPages,
  initRouter
}
