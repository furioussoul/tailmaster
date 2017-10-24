import renderVue from './render.vue'
import {
  getConfig
} from '../config'
import{getBreadcrumb, getPages} from '../../util/assist'

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
