<template>
  <div style="height: 100%;width: 98%;  margin-left: 15px;position: relative;">
    <ButtonGroup>
      <Button @click="save">save</Button>
    </ButtonGroup>
    <Modal></Modal>
    <div id="editor"></div>
  </div>
</template>
<script>

  import {
    addControl,
    delControl,
    updateControl,
    getTableControlList,
    getRichControl
  } from '../../../resource'

  export default{
    data(){
      return {
        editor: null,//ace editor
        opModel: {}
      }
    },
    methods: {
      save(){
        let code = this.editor.getValue();
        this.opModel.code = code
        if (this.opModel.id) {
          updateControl.call(this)
        }
        else {
          addControl.call(this)
        }
      }
    },
    mounted(){
      const editor = ace.edit("editor");
      this.editor = editor

      let query = this.$route.query
      if (query.id) {
        getRichControl.call(this, query.id, (data) => {
          this.opModel = data
          this.editor.setValue(data.code)
        })
      }

      editor.$blockScrolling = Infinity;
      editor.setFontSize(16);
      editor.setTheme("ace/theme/tomorrow");
      editor.session.setMode("ace/mode/javascript");
      editor.setOptions({
        enableBasicAutocompletion: true,
        enableSnippets: true,
        enableLiveAutocompletion: true
      });
    }
  }
</script>
<style scoped>
  #editor {
    position: relative;
    width: 100%;
    height: 100%;

  }
</style>
