import Vue from 'vue';
import {
  appFrame,
  WrapCard
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

function getPageList({appId,pageName,token},fn) {
  this.http.post('page/pageList',{name:pageName,appId}).then(res => {
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
function render({appId,pageName}, token) {
  getPageList.call(Vue,{
    appId,
    pageName,
    token
  },data=>{
    let pageSoul = parse(data.pageSoul)
    for (let key in pageSoul) {
      if(key !=='maxUid' && key !=='soulType'){
        let soul = pageSoul[key];
        addRenderFn(pageSoul[key])
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

