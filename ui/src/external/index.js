import Vue from 'vue';
import {
  appFrame,
  WrapCard,
  WrapUpload,
  WrapModal,
  WrapSelect
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

Vue.component('AppFrame', appFrame);
Vue.component('WrapCard', WrapCard);
Vue.component('WrapUpload', WrapUpload);
Vue.component('WrapModal', WrapModal);
Vue.component('WrapSelect', WrapSelect);

function getPageList({appName,pageName,token},fn) {
  this.http.post('page/pageList',{
    appName,
    name:pageName,
    token
  }).then(res => {
    if (res.data.code === 10000) {
      this.controls = res.data.data
      if(fn){
        fn.call(this,res.data.data)
      }
    } else {
      this.$Message.error('query failed')
    }
  })
}

// 在这查 appSoul,放到store
function render({appName,pageName}, token) {
  getPageList.call(Vue,{
    appName,
    pageName,
    token
  },data=>{
    let pageSoul = parse(data[0].pageSoul)
    let soulType = pageSoul.soulType
    for (let key in pageSoul) {
      if(key !=='maxUid' && key !=='soulType'){
        if(!soulType || soulType==='multiple'){
          addRenderFn(pageSoul[key])
        }else {
          addRenderFn(pageSoul)
        }
      }
    }
    store.commit('soulModule/setPageSoul', pageSoul)
    store.commit('soulModule/setSoul', pageSoul['/index'])
  })
  return renderVue
}

export default{
  addConfig,
  getConfig,
  render
}

