import Vue from 'vue';
import {walkSoul} from '../helper/soul_helper'
import {
  WrapCard,
  WrapModal,
} from './component'
import {
  addConfig,
  getConfig
} from './config'
import {
  parse
}from '../util/assist'
import store from './store'
import renderVue from './component/render.vue'
import {addRenderFn} from '../helper/code_helper'

Vue.component('WrapCard', WrapCard);
Vue.component('WrapUpload', WrapUpload);
Vue.component('WrapModal', WrapModal);
Vue.component('WrapSelect', WrapSelect);

const emptyDirective = {
  bind (el, binding, vnode) {
    el.oncontextmenu = function (e) {
    }
  }
}

function getPageList({appName, pageName, token}, fn) {
  this.http.post('page/pageList', {
    appName,
    name: pageName,
    token
  }).then(res => {
    if (res.data.code === 10000) {
      this.controls = res.data.data
      if (fn) {
        fn.call(this, res.data.data)
      }
    } else {
      this.$Message.error('query failed')
    }
  })
}

// 在这查 appSoul,放到store
function render({appName, pageName}, token) {
  getPageList.call(Vue, {
    appName,
    pageName,
    token
  }, data => {
    let ancestorSoul = parse(data[0].pageSoul)
    addRenderFn(ancestorSoul)
    store.commit('soulModule/setSoul', ancestorSoul)
    if(!getConfig('type')){
      Vue.directive('droppable',emptyDirective)
      Vue.directive('editable',emptyDirective)
    }
  })
  return renderVue
}

export default{
  addConfig,
  getConfig,
  render
}

