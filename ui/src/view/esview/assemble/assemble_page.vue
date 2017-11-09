<template>
  <div>
    <Menu class="action_bar" @on-select="action" mode="horizontal" theme="dark" active-name="1">
      <div class="index-layout-nav">
        <MenuItem name="4">
          <Icon type="code"></Icon>
          code
        </MenuItem>
        <MenuItem name="5">
          <Icon type="wrench"></Icon>
          layout
        </MenuItem>
        <MenuItem v-if="!opModel.createBy ||  me.username === opModel.createBy" name="6">
          <Icon type="document-text"></Icon>
          save
        </MenuItem>
        <MenuItem name="9">
          <Icon type="ios-undo"></Icon>
          undo
        </MenuItem>
        <MenuItem name="12">
          <Icon type="ios-redo"></Icon>
          redo
        </MenuItem>
        <MenuItem name="3">
          <Icon type="ios-eye"></Icon>
          no bord
        </MenuItem>
      </div>
    </Menu>

    <div class="index-layout-content">
      <Row>

        <i-col span="3">
          <transition name="index-soul-control-class-fade">
            <div>
              <Collapse v-model="open" :key="classIndex" v-for="(controlClass, classIndex) in controlClazzes">
                <Panel :name="classIndex+1+''">
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

        <i-col span="17" class="middle" :class="{'is-preview':isPreview}">
          <RenderDev v-if="!showCode" :soul="soul"></RenderDev>
          <pre contenteditable="true" v-else v-highlightjs="vueCode" class="code" id="code"><code ></code>
            </pre>
          <Button v-if="showCode"
                  @click="copyCode"
                  type="ghost"
                  size="small"
                  style="position: absolute;right: 0;top: 0;opacity:0.5">
            <Icon type="ios-copy-outline"></Icon>
            copy
          </Button>
        </i-col>

        <i-col span="4">
          <ModelEditor
            :pageName="opModel.name"
            :editSoul="editSoul">
          </ModelEditor>
        </i-col>


      </Row>
    </div>

    <Modal
      :mask-closable="false"
      v-model="showConfirmPageNameModal"
      title="confirmPageName"
      @on-ok="okPageName">
      <i-form :label-width="100">
        <FormItem label="PageName">
          <i-input v-model="opModel.name"  @keyup.13.native="okPageName"></i-input>
        </FormItem>
      </i-form>
    </Modal>

    <Modal
      style="width: 600px"
      :mask-closable="false"
      v-model="showEditScriptModal"
      title="script">
      <div slot="footer">
      </div>
      <CodeEditor
        :editorStyle="editorStyle"
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
    <textarea id="copy" style="width: 1px;height: 1px;border: 0;outline:0" />
  </div>
</template>

<script>
  import {mapGetters, mapMutations, mapActions} from 'vuex'
  import store from '../../../store'
  import {findSoulByCid, findSoulByUidDown, resetUid, generateUid, findSoulByCTypeUp} from '../../../helper/soul_helper'
  import {makeControl, addRenderFn} from '../../../helper/code_helper'
  import{undo, redo, clear, init, saveSoul, resetSnapShot}from '../../../core/assemble'
  import {copyProperties, stringify, parse, deepCopy,jsCopy}from '../../../util/assist'
  import {getControlList} from  '../../../resource/develop_resource'
  import {resetSoul,reset} from  '../../../core/lifecycle'
  import {
    addPage,
    delPage,
    updatePage,
    getPageList,
    getTablePageList,
    getRichPage,
    getAppList
  } from '../../../resource/assemble_resource'
  import {walkSoul} from '../../../helper/soul_helper'
  import{
    interceptDrop
  }from '../../../core/dnd'

  export default {
    name: 'AssemblePage',
    data(){
      return {
        open:'1',
        isPreview: true,
        showConfirmPageNameModal: false,
        showEditScriptModal: false,
        opModel: {},
        editControlSoul: {scriptString: ''},
        pageSoulId: '',
        appId: '',
        editorStyle:{height:'400px'}
      }
    },
    computed: {
      ...mapGetters('userModule', ['me']),
      ...mapGetters('dragModule', ['soul', 'editLayer', 'rightClickMenu',
        'draggableControls', 'editSoul', 'controlClazzes', 'vueCode', 'showCode'])
    },
    methods: {
      ...mapMutations('dragModule', ['setSoul', 'clear', 'setDraggableControls', 'setShowCode']),
      ...mapActions('dragModule', ['getControlClazzes']),
      copyCode(){
        jsCopy('copy',this.vueCode)
        this.$Message.success('copied')
      },
      deleteControl(){
        this.editControlSoul = findSoulByUidDown(this.rightClickMenu.uid, this.soul)
        let pSoul = findSoulByUidDown(this.editControlSoul.pid, this.soul);
        if (pSoul) {
          let index = pSoul.children.indexOf(this.editControlSoul);
          pSoul.children.splice(index, 1)
        }
        this.clear()
        saveSoul()
      },
      editControl(){
        this.editControlSoul = findSoulByUidDown(this.rightClickMenu.uid, this.soul)
        this.editControlSoul.scriptString = this.editControlSoul.script.toString()
        this.clear()
        this.showEditScriptModal = true
      },
      saveCode(code){
        this.editControlSoul.scriptString = code
        this.editControlSoul.script = eval('(function () { \r\n return ' + code + '})()')
        this.showEditScriptModal = false
        reset(this.editControlSoul)
      },
      okPageName(){
        addPage.call(this)
      },
      action(a){
        if (a === '3') {
          //toggle preview
          this.isPreview = !this.isPreview

        } else if (a === '4') {
          this.setShowCode(true)

        } else if (a === '5') {
          this.setShowCode(false)

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
      this.getControlClazzes()
      resetSnapShot()
      this.appId = this.$route.query.appId
      this.pageSoulId = this.$route.query.pageSoulId
      this.clear()

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
            let ancestorSoul = parse(data.pageSoul)
            addRenderFn(ancestorSoul)
            resetUid(ancestorSoul.maxUid)
            saveSoul()
            this.setSoul(ancestorSoul)
            walkSoul(ancestorSoul,(soul)=>{
              resetSoul(soul)
            })
          })
        }
        this.setShowCode(false)
      })
    }
  }
</script>

<style scoped>
  .middle {
    box-shadow: 0 1px 6px rgba(0, 0, 0, .117647), 0 1px 4px rgba(0, 0, 0, .117647);
    min-height: 948px;
    overflow: auto;
    position: relative;
    height: 1000px;
    z-index: 0;
    padding-top: 20px;
    padding-bottom: 500px;
  }

  .code {
    text-align: center;
    height: 1000px;
    margin-top: 20px;
    margin-bottom: 100px;
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
    width: 800px;
    margin: 0 auto;
  }

  .index-layout-content {
    min-height: 200px;
    overflow: hidden;
    background: #fff;
  }

  .action_bar {
    position: sticky;
    line-height: 3.5;
    top: 0;
    height: 50px;
    width: 100%;
  }

  .index-soul-control-class-fade-enter, .soul-control-class-fade-leave-active {
    opacity: 0;
  }
</style>
