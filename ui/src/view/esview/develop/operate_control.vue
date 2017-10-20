<template>
    <CodeEditor style="height: 100%;width: 98%;  margin-left: 15px;position: relative;"
                :code="opModel.code"
                @save="saveCode">
    </CodeEditor>
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
        })
      }
    }
  }
</script>

