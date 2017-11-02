import {
  makeControl
} from '../helper/code_helper'
import {
  dataFormat
}from '../util/assist'

function addControl(fn) {

  let config = makeControl(this.opModel.code)

  this.$http.post('control/add', config).then(res => {
    if (res.data.code === 10000) {
      if(fn){
        fn.call(this)
      }
      this.$Message.success('saved')
    }
  })
}

function delControl(id) {
  this.$http.get('control/del/' + id).then(res => {
    if (res.data.code === 10000) {
      getTableControlList.call(this)
      this.$Message.success('deleted')
    }
  })
}

function updateControl(fn) {

  this.$http.post('control/update', this.opModel).then(res => {
    if (res.data.code === 10000) {
      if(fn){
        fn.call(this)
      }
      this.$Message.success('saved')
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
    }
  })
}

function getTableControlList() {
  this.loading = true
  this.$http.post('control/tableControlList', this.searchInput).then(res => {
    if (res.data.code === 10000) {
      this.loading = false
      let data = res.data.data
      data.list.forEach(item=>{
        item.updateDt = dataFormat(item.updateDt)
      })
      this.searchInput.total = data.total
      this.tableData = data.list
    }
  })
}

function getRichControl(id, fn) {
  this.$http.get('control/richControl/' + id).then(res => {
    if (res.data.code === 10000) {
      if (fn) {
        fn.call(this, res.data.data)
      }
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
