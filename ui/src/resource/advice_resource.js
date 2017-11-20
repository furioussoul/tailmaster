export function addAdvice() {
  this.$http.post('advice/add',this.data).then(res => {
    if (res.data.code === 10000) {
      this.$Message.success('success')
    }
  })
}
