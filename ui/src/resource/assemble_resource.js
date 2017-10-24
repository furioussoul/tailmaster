import store from '../store'
import {
  isPlain,
  stringify
} from '../util/assist'
import {
  currentUid
} from '../helper/soul_helper'

function addPage() {
  let pageSoul = store.getters['dragModule/pageSoul']
  if(isPlain(pageSoul)){
    return void this.$Message.error('can\'t save empty page,please drop something into middle area');
  }

  pageSoul.maxUid = currentUid()

  this.opModel.pageSoul = stringify(pageSoul)
  this.$http.post('page/add', this.opModel).then(res => {
    if (res.data.code === 10000) {
      this.$Message.success('saved')
    } else {
      this.$Message.error('save failed')
    }
  })
}

function delPage(id) {
  this.$http.get('page/del/' + id).then(res => {
    if (res.data.code === 10000) {
      getTablePageList.call(this)
      this.$Message.success('deleted')
    } else {
      this.$Message.error('delete failed')
    }
  })
}

function updatePage() {
  let pageSoul = store.getters['dragModule/pageSoul']
  pageSoul.maxUid = currentUid()
  this.opModel.pageSoul = stringify(pageSoul)
  this.$http.post('page/update', this.opModel).then(res => {
    if (res.data.code === 10000) {
      this.$Message.success('saved')
    } else {
      this.$Message.error('save failed')
    }
  })
}

function getPageList({pageName,token},fn) {
  this.http.post('page/pageList',{name:pageName}).then(res => {
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

function getTablePageList() {
  this.$http.post('page/tablePageList', this.searchInput).then(res => {
    if (res.data.code === 10000) {
      let data = res.data.data
      this.searchInput.total = data.total
      this.tableData = data.list
    } else {
      this.$Message.error('query failed')
    }
  })
}

function getRichPage(id, fn) {
  this.$http.get('page/richPage/' + id).then(res => {
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
  addPage,
  delPage,
  updatePage,
  getPageList,
  getTablePageList,
  getRichPage
}
