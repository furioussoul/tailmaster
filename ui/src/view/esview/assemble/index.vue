<template>
  <div class="layout-content__container">

    <Menu class="action_bar" @on-select="action" mode="horizontal" theme="dark" active-name="1">

      <div class="index-layout-nav">
        <MenuItem name="2">
          <Icon type="ios-keypad"></Icon>
          预览
        </MenuItem>
        <MenuItem name="6">
          <Icon type="ios-keypad"></Icon>
          保存
        </MenuItem>
        <MenuItem name="9">
          <Icon type="ios-keypad"></Icon>
          撤销
        </MenuItem>
        <MenuItem name="13">
          <Icon type="ios-keypad"></Icon>
          恢复
        </MenuItem>
      </div>
    </Menu>

    <div class="index-layout-content">
      <Row>

        <i-col span="3">
          <transition name="index-soul-control-class-fade">
            <div v-show="showEditorPanel">
              <Collapse v-model="collapseValue" :key="classIndex" v-for="(controlClass, classIndex) in classes">
                <Panel :name="classIndex+''">
                  {{controlClass.name}}
                  <p slot="content" class="index-layout-content__class">
                    <Control :controlConfig="control" :key="controlIndex"
                             v-for="(control,controlIndex) in controlClass.controls">
                      <div slot="preview">
                        <MenuItem :name="classIndex + '-' + controlIndex">{{control.name}}
                        </MenuItem>
                      </div>
                    </Control>
                  </p>
                </Panel>
              </Collapse>
            </div>
          </transition>
        </i-col>

        <i-col span="18" :class="{'is-preview':isPreview}">
          <Render :soul="soul"></Render>
        </i-col>

        <i-col span="3">
          <Editor :editSoul="editSoul"></Editor>
        </i-col>

      </Row>
    </div>

  </div>
</template>
<script>

  import {
    findSoul
  } from '../../../helper/soul_helper'
  import {
    getConfig
  } from '../../../helper/code_helper'
  import{
    undo,
    redo,
    clear,
    reload
  }from '../../../helper/user_operation'
  import Render from '../../../core/render'

  import {
    mapGetters,
    mapMutations
  } from 'vuex'
  import dropDirective from '../../../directive/d_drop'
  import store from '../../../store'
  import Editor from '../../../component/editor.vue'
  import {
    getControlList
  } from  '../../../resource/develop_resource'
  import {
    copyProperties
  }from '../../../util/assist'
  let classes = [
    {
      name: '测试组件',
      controls: []
    }
  ]
  export default {
    components: {
      Render,
      Editor
    },
    data(){
      return {
        isPreview: true,
        collapseValue: "0",
        classes: classes
      }
    },
    computed: {
      ...mapGetters('dragModule', ['soul', 'editSoul'])
    },
    methods: {
      ...mapMutations('dragModule',
        [
          'setSoul',
          'showEditorPanel'
        ]),
      ...mapMutations('userModule',
        [
          'changePage'
        ]),
      action(a){
        if (a === '2') {
          this.isPreview = !this.isPreview

        } else if (a === '6') {

          store.dispatch('dragModule/savePageSoul')

        } else if (a === '9') {
          undo()
        } else if (a === '13') {
          redo()
        } else if (a === '14') {
          clear()
        }
      },
      final(){
        this.$router.push('./final')
      }
    },
    mounted(){
      getControlList.call(this, (data) => {
        let controlConfigs = []
        this.classes[0].controls = []
        data.forEach(control => {
          let controlConfig = getConfig(control.code);
          controlConfigs.push(controlConfig)
        })

        for (let i = 0; i < controlConfigs.length; i++) {
          this.classes[0].controls.push(controlConfigs[i])
        }

        store.commit('dragModule/setControlConfigs',controlConfigs)

        reload(null ,controlConfigs)
      })
    }
  }
</script>

<style scoped>

  .index-layout-logo {
    width: 100px;
    height: 30px;
    background: #5b6270;
    border-radius: 3px;
    float: left;
    position: relative;
    top: 15px;
    left: 20px;
  }

  .index-layout-nav {
    width: 600px;
    margin: 0 auto;
  }

  .index-layout-content {
    min-height: 200px;
    margin: 5px;
    overflow: hidden;
    background: #fff;
    border-radius: 4px;
  }

  .action_bar {
    position: fixed;
    line-height: 3.5;
    top: 50px;
    height: 50px;
    width: 60%;
  }

  .index-soul-control-class-fade-enter, .soul-control-class-fade-leave-active {
    opacity: 0;
  }
</style>
