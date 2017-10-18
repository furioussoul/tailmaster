<template>
  <div>
    <render :soul="soul"></render>
  </div>
</template>
<script>

  import {
    findSoul
  } from '../helper/soul_helper'
  import {
    getConfig
  } from '../helper/code_helper'

  import Render from '../core/render'

  import {
    mapGetters,
    mapMutations
  } from 'vuex'
  import dropDirective from '../directive/droppable'
  import store from '../store'
  import Editor from '../component/editor.vue'
  import {
    getControlList
  } from  '../resource/develop_resource'
  import {
    copyProperties
  }from '../util/assist'

  export default{
    name: 'Test',
    components:{
      render
    },
    mounted(){
      getControlList.call(this, (data) => {
        let controlConfigs = []
        data.forEach(control => {
          let controlConfig = getConfig(control.code);
          controlConfigs.push(controlConfig)
        })

        let dropPanelSoul = findSoul(100, controlConfigs)
        let appFrame = findSoul(105, controlConfigs)
        appFrame.children.push(dropPanelSoul)

        this.soul = appFrame
      })
    },
    data(){
      return {
        soul:null
      }
    }
  }
</script>
