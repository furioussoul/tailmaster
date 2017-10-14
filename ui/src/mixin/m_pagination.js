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
    search(pageNum){
      if (typeof pageNum === 'number') {
        this.searchInput.pageNum = pageNum
      } else {
        this.searchInput.pageNum = 1
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
