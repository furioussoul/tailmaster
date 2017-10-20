import {
  getConfig
} from '../helper/code_helper'

function addControl(fn) {

  let config = getConfig(this.opModel.code)

  this.$http.post('control/add', config).then(res => {
    if (res.data.code === 10000) {
      if(fn){
        fn.call(this)
      }
      this.$Message.success('saved')
    } else {
      this.$Message.error('saved failed')
    }
  })
}

function delControl(id) {
  this.$http.get('control/del/' + id).then(res => {
    if (res.data.code === 10000) {
      getTableControlList.call(this)
      this.$Message.success('deleted')
    } else {
      this.$Message.error('delete failed')
    }
  })
}

function updateControl(fn) {

  this.$http.post('control/update', this.opModel).then(res => {
    if (res.data.code === 10000) {
      if(fn){
        fn.call(this)
      }
      getTableControlList.call(this)
      this.$Message.success('saved')
    } else {
      this.$Message.error('save failed')
    }
  })
}

function getControlList(fn) {
  this.$http.post('control/controlList').then(res => {
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

function getTableControlList() {
  this.$http.post('control/tableControlList', this.searchInput).then(res => {
    if (res.data.code === 10000) {
      let data = res.data.data
      this.searchInput.total = data.total
      this.tableData = data.list
    } else {
      this.$Message.error('query failed')
    }
  })
}

function getRichControl(id, fn) {
  this.$http.get('control/richControl/' + id).then(res => {
    if (res.data.code === 10000) {
      if (fn) {
        fn.call(this, res.data.data)
      }
    } else {
      this.$Message.error('query failed')
    }
  })
}

export {
  addControl,
  delControl,
  updateControl,
  getControlList,
  getTableControlList,
  getRichControl
}
