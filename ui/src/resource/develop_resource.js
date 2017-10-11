import {
  getConfig
} from '../helper/code_helper'


function addControl() {

  let config = getConfig(this.opModel.code)

  this.$http.post('control/add', config).then(res => {
    if (res.data.code === 10000) {
      this.$Message.success('保存成功')
    } else {
      this.$Message.error('保存失败')
    }
  })
}

function delControl(id) {
  this.$http.get('control/del/' + id).then(res => {
    if (res.data.code === 10000) {
      getTableControlList.call(this)
      this.$Message.success('删除成功')
    } else {
      this.$Message.error('删除失败')
    }
  })
}

function updateControl() {

  let config = getConfig(this.opModel.code)
  config.id = this.opModel.id
  this.$http.post('control/update', config).then(res => {
    if (res.data.code === 10000) {
      this.$Message.success('保存成功')
    } else {
      this.$Message.error('保存失败')
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
      this.$Message.error('查询失败')
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
      this.$Message.error('查询失败')
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
      this.$Message.error('查询失败')
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
