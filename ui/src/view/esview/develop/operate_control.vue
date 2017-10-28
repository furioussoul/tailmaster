<template>
    <CodeEditor :buttonStyle="codeButtonStyle"
                class="code-editor"
                :code="opModel.code"
                @save="saveCode">
    </CodeEditor>
</template>
<script>
  import{
    mapGetters
  }from 'vuex'
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
        opModel: {},
        codeButtonStyle:{
          position: 'fixed',
          top: '50px',
          height: '50px',
          zIndex:1000000,
          display:'none'
        }
      }
    },
    computed: {
      ...mapGetters('userModule', ['me'])
    },
    methods: {
      saveCode(code){
        this.opModel.code = code
        if (this.opModel.id) {
          updateControl.call(this,()=>{
            this.$router.push('./index')
          })
        }
        else {
          addControl.call(this,()=>{
            this.$router.push('./index')
          })
        }
      }
    },
    mounted(){
      let query = this.$route.query
      if (query.id) {
        getRichControl.call(this, query.id, (data) => {
          this.opModel = data
          if(data.createBy === this.me.username){
              this.codeButtonStyle.display = 'block'
          }
        })
      }else {
        this.codeButtonStyle.display = 'block'
      }
    }
  }
</script>
<style scoped>
  .code-editor{
    height: 100%;
    width: 98%;
    margin-left: 15px;
    margin-top: 35px;
    position: relative;
  }
</style>
