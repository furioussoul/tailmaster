<template>
  <div style="margin-top: 50px">
    <Menu class="action_bar" @on-select="action" mode="horizontal" theme="dark" active-name="1">

      <div class="index-layout-nav">
        <MenuItem name="3">
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
        <MenuItem name="12">
          <Icon type="android-arrow-forward"></Icon>
          redo
        </MenuItem>
      </div>
    </Menu>

    <div class="index-layout-content">
      <Row>

        <i-col class="controls-container" span="3">
          <transition name="index-soul-control-class-fade">
            <div>
              <Collapse :key="classIndex" v-for="(controlClass, classIndex) in controlClazzes">
                <Panel :name="classIndex+''">
                  {{controlClass.name}}
                  <p slot="content" class="index-layout-content__class">
                    <Control
                      v-for="(control,controlIndex) in controlClass.controls"
                      :controlConfig="control"
                      :key="controlIndex">
                      <div slot="preview">
                        <MenuItem
                          :name="classIndex + '-' + controlIndex">{{control.name}}
                        </MenuItem>
                      </div>
                    </Control>
                  </p>
                </Panel>
              </Collapse>
            </div>
          </transition>
        </i-col>

        <i-col style="margin-left: 200px" span="21" :class="{'is-preview':isPreview}">
          <RenderDev :soul="soul"></RenderDev>
        </i-col>

      </Row>
    </div>

    <Modal
      v-model="showConfirmPageNameModal"
      title="confirmPageName"
      @on-ok="okPageName">
      <i-form :label-width="100">
        <FormItem label="PageName">
          <i-input v-model="opModel.name"></i-input>
        </FormItem>
      </i-form>
    </Modal>

    <Modal
      v-model="showEditScriptModal"
      title="script">
      <div slot="footer">
      </div>
      <CodeEditor
        style="height: 500px"
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
          <DropdownItem @click.native="deleteControl">delete</DropdownItem>
        </DropdownMenu>
      </Dropdown>
    </div>

  </div>
</template>
<script>
  import {mapGetters, mapMutations} from 'vuex'
  import store from '../../../store'
  import {findSoul, findNode, resetUid,generateUid} from '../../../helper/soul_helper'
  import {makeControl, addRenderFn} from '../../../helper/code_helper'
  import{undo, redo, clear, init, saveSoul, resetSnapShot}from '../../../helper/user_operation'
  import {copyProperties, stringify, parse, deepCopy}from '../../../util/assist'
  import {getControlList} from  '../../../resource/develop_resource'
  import {
    addPage,
    delPage,
    updatePage,
    getPageList,
    getTablePageList,
    getRichPage,
    getAppList
  } from '../../../resource/assemble_resource'
  import{
    interceptDrop
  }from '../../../core/dnd'
  export default {
    name: 'AssemblePage',
    data(){
      return {
        isPreview: true,
        showConfirmPageNameModal: false,
        showEditScriptModal: false,
        opModel: {},
        editControlSoul: {scriptString: ''},
        pageSoulId:'',
        appId:''
      }
    },
    computed: {
      ...mapGetters('userModule', ['controlClazzes']),
      ...mapGetters('dragModule', ['soul', 'editSoul', 'editLayer', 'rightClickMenu', 'showEditorPanel', 'draggableControls'])
    },
    methods: {
      ...mapMutations('dragModule', ['setSoul', 'clear', 'syncSoul', 'setDraggableControls', 'setPageSoul', 'setOriginSoul']),
      ...mapMutations('userModule', ['changePage']),
      deleteControl(){
        this.editControlSoul = findNode(this.rightClickMenu.uid)
        let pSoul = findNode(this.editControlSoul.pid);
        if(pSoul){
          let index = pSoul.children.indexOf(this.editControlSoul);
          pSoul.children.splice(index,1)
        }else {
          init(this.draggableControls)
        }
        this.clear()
        saveSoul()
      },
      editControl(){
        this.editControlSoul = findNode(this.rightClickMenu.uid)
        this.editControlSoul.scriptString = this.editControlSoul.script.toString()
        this.clear()
        this.showEditScriptModal = true
      },
      saveCode(code){
        this.editControlSoul.scriptString = code
        this.editControlSoul.script = eval('(function () { \r\n return ' + code + '})()')
        this.showEditScriptModal = false
        let pSoul = findNode(this.editControlSoul.pid);
        if(pSoul){
          let editSoulCopy = deepCopy(this.editControlSoul)
          let index = pSoul.children.indexOf(this.editControlSoul);
          pSoul.children.splice(index,1)
          setTimeout(()=>{
            editSoulCopy.initScript = false
            pSoul.children.splice(index,0,editSoulCopy)
          },1)
        }
        this.syncSoul(this.soul)//edited soul, must synchronize pageSoul for saving changes
      },

      okPageName(){
        addPage.call(this)
      },
      action(a){
        if (a === '3') {
          //toggle preview
          this.isPreview = !this.isPreview

        } else if (a === '6') {
          //save changes of page
          if (!this.opModel.id) {
            this.showConfirmPageNameModal = true
          } else {
            updatePage.call(this)
          }

        } else if (a === '9') {
          undo()

        } else if (a === '12') {
          redo()
        }
      }
    },
    mounted(){
      resetSnapShot()
      this.appId = localStorage.getItem('appId')
      this.pageSoulId = localStorage.getItem('pageSoulId')

      getControlList.call(this, (data) => {

        let draggableControls = []

        data.forEach(origin => {
          let control = makeControl(origin.code);
          control.clazzId = origin.clazzId
          draggableControls.push(control)
        })

        //classify draggableControls
        let map = {}
        draggableControls.forEach(item => {
          if (!map[item.clazzId]) {
            map[item.clazzId] = []
          }
          map[item.clazzId].push(item)
        })
        this.controlClazzes.forEach(clazz => {
          let controls = map[clazz.id]
          if (controls) {
            clazz.controls = controls
          }
        })
        //store draggableControls
        this.setDraggableControls(draggableControls)

        if (!this.pageSoulId) {
          //when add new page
          init(draggableControls)
          saveSoul()

        } else {

          //when update page

          getRichPage.call(this, this.pageSoulId, (data) => {
            this.opModel = data
            let pageSoul = data.pageSoul
            pageSoul = parse(pageSoul)//deserialize functions from json
            resetUid(pageSoul['maxUid'])
            for (let key in pageSoul) {
              if (key !== 'maxUid') {
                addRenderFn(pageSoul[key])
              }
            }
            this.setPageSoul({pageSoul})
            saveSoul()
          })
        }
        let frame = findSoul(105, this.draggableControls)
        frame.uid = generateUid()
        let dropPanelSoul = findSoul(100, this.draggableControls)
        dropPanelSoul.uid = generateUid()
        frame.children.push(deepCopy(dropPanelSoul))
        this.setOriginSoul(frame)
      })
    }
  }
</script>

<style scoped>

  .controls-container{
    position: fixed;
    top: 100px;
    left: 200px;
    width: 200px
  }

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
