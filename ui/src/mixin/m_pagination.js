let paginationMixin = {
  mounted() {
    this.init()
  },
  methods: {
    init(){
      if (this.fn.initFns) {
        this.fn.initFns.forEach(fn => {
          fn.call(this)
        })
      }
    },
    search(pageNo){
      if (typeof pageNo === 'number') {
        this.searchInput.pageNo = pageNo
      } else {
        this.searchInput.pageNo = 1
      }
      this.fn.searchFns.forEach(fn => {
        fn.call(this)
      })
    },
    changePageSize(pageSize){
      this.searchInput.pageSize = pageSize
      this.fn.searchFns.forEach(fn => {
        fn.call(this)
      })
    }
  }
}

export {
  paginationMixin
}
