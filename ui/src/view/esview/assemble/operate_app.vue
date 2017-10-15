<template>
  <div>
    <Menu class="action_bar" @on-select="action" mode="horizontal" theme="dark" active-name="1">

      <div class="index-layout-nav">
        <MenuItem name="2">
          <Icon type="ios-eye"></Icon>
          preview
        </MenuItem>
        <MenuItem name="6">
          <Icon type="document-text"></Icon>
          save
        </MenuItem>
        <MenuItem name="9">
          <Icon type="android-arrow-back"></Icon>
          undo
        </MenuItem>
        <MenuItem name="13">
          <Icon type="android-arrow-forward"></Icon>
          redo
        </MenuItem>
      </div>
    </Menu>

    <div class="index-layout-content">
      <Row>

        <i-col span="3">
          <transition name="index-soul-control-class-fade">
            <div>
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

        <i-col v-if="showEditorPanel" span="18" :class="{'is-preview':isPreview}">
          <Render :soul="soul"></Render>
        </i-col>
        <i-col v-else span="21" :class="{'is-preview':isPreview}">
          <Render :soul="soul"></Render>
        </i-col>

        <i-col v-show="showEditorPanel" span="3">
          <Editor :editSoul="editSoul"></Editor>
        </i-col>

      </Row>
    </div>

    <Modal
      v-model="showConfirmAppNameModal"
      title="confirmAppName"
      @on-ok="okAppName">
      <i-input v-model="opModel.name"></i-input>
    </Modal>

    <Modal
      v-model="showEditScriptModal"
      title="script">
      <div slot="footer">
      </div>
      <CodeEditor
                  style="height: 280px"
                  :code="editControlSoul.scriptString"
                  @save="saveCode">
      </CodeEditor>
    </Modal>

    <div class="edit_layer" :style="editLayer.style">
      {{editLayer.name}}
    </div>

    <div class="rightClickMenu" :style="rightClickMenu.style">
      <Dropdown trigger="custom" visible>
        <DropdownMenu slot="list">
          <DropdownItem @click.native="editControl">editScript</DropdownItem>
        </DropdownMenu>
      </Dropdown>
    </div>

  </div>
</template>
<script>

  import {
    findSoul,
    findNode,
    resetUid,
    generateUid
  } from '../../../helper/soul_helper'
  import {
    getConfig,
    addRenderFn
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
    copyProperties,
    stringify,
    parse,
    deepCopy
  }from '../../../util/assist'

  import {
    addApp,
    delApp,
    updateApp,
    getRichApp
  } from '../../../resource/assemble_resource'

  let classes = [
    {
      name: 'controls',
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
        showConfirmAppNameModal: false,
        showEditScriptModal: false,
        editControlSoul:{
          scriptString:''
        },
        opModel: {},
        isPreview: true,
        collapseValue: "0",
        classes: classes
      }
    },
    computed: {
      ...mapGetters('dragModule', ['soul', 'editSoul', 'editLayer', 'rightClickMenu','showEditorPanel'])
    },
    methods: {
      ...mapMutations('dragModule',
        [
          'setSoul'
        ]),
      ...mapMutations('userModule',
        [
          'changePage'
        ]),
      editControl(){
        this.editControlSoul = findNode(this.rightClickMenu.uid)
        this.editControlSoul.scriptString = this.editControlSoul.script.toString()
        store.commit('dragModule/clear')
        this.showEditScriptModal= true
      },
      saveCode(code){
        this.editControlSoul.scriptString = code
        this.editControlSoul.script = eval('(function () { \r\n return ' + code + '})()')
        this.showEditScriptModal= false
        let soul = store.getters['dragModule/soul']
        store.commit('dragModule/syncSoul', soul)
      },
      okAppName(){
        addApp.call(this)
      },
      action(a){
        if (a === '2') {
          this.isPreview = !this.isPreview

        } else if (a === '6') {
          if (!this.opModel.id) {
            this.showConfirmAppNameModal = true
          } else {
            updateApp.call(this)
          }
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

        store.commit('dragModule/setControlConfigs', controlConfigs)

        let query = this.$route.query
        if (!query.appId) {
          reload(controlConfigs)
        } else {
          getRichApp.call(this, query.appId, (data) => {
            store.commit('dragModule/setAppId',query.appId)
            this.opModel = data
            let pageSoul = data.pageSoul
            pageSoul = parse(pageSoul)

            for (let key in pageSoul) {
                if(key === 'maxUid'){
                  resetUid(pageSoul[key])
                }else {
                  addRenderFn(pageSoul[key])
                }
            }

            store.commit('dragModule/setPageSoul', {
                pageSoul:pageSoul
            })
            store.commit('dragModule/setSoul', pageSoul['/index'])

            let frame = findSoul(105, store.getters['dragModule/controlConfigs'])
            let dropPanelSoul = findSoul(100, store.getters['dragModule/controlConfigs'])
            dropPanelSoul.uid = generateUid()
            frame.children.push(deepCopy(dropPanelSoul))
            store.commit('dragModule/setOriginSoul',frame)
          })
        }
      })
    }
  }
</script>

<style scoped>

  .edit_layer {
    display: none;
    opacity: 0.5;
    background: #eee;
    border: 1px dashed #999;
    pointer-events: none;
    z-index: 10000;
    position: fixed;
  }

  .rightClickMenu {
    display: none;
    z-index: 10001;
    position: fixed;
  }

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
    overflow: hidden;
    background: #fff;
  }

  .action_bar {
    position: fixed;
    line-height: 3.5;
    top: 50px;
    height: 50px;
    width: 100%;
  }

  .index-soul-control-class-fade-enter, .soul-control-class-fade-leave-active {
    opacity: 0;
  }
</style>
