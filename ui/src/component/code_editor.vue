<template>
  <div :style="editorStyle">
    <ButtonGroup :style="buttonStyle">
      <Button type="primary" @click="save">save</Button>
    </ButtonGroup>
    <div id="editor"></div>
  </div>
</template>
<script>
  import beautify from 'js-beautify'
  export default{
    name: 'CodeEditor',
    props: {
      code: [String],
      buttonStyle:[Object],
      editorStyle:[Object]
    },
    data(){
      return {
        editor: null//ace editor
      }
    },
    methods: {
      save(){
        this.$emit('save', this.editor.getValue())
      }
    },
    watch:{
      code(n){
        if(n===undefined ) n = ''
        this.editor.setValue(beautify(n))
      }
    },
    mounted(){
      const editor = ace.edit("editor");
      this.editor = editor
      editor.$blockScrolling = Infinity;
      editor.setFontSize(16);
      editor.setTheme("ace/theme/tomorrow");
      editor.session.setMode("ace/mode/sql");
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
