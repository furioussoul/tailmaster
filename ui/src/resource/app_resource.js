function getAppSoulList() {
  this.$http.post('appSoul/getAppSoulList', this.searchInput).then(res=>{

  })
}

function getPaginationAppSoul() {
  this.$http.post('appSoul/getAppSoulPagination', this.searchInput).then(res=>{

  })

}


export {
  getAppSoulList,
  getPaginationAppSoul
}
