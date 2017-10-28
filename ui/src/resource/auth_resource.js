import store from '../store'
function register() {
  if(!this.userInfo.userName){
    return void this.$Message.error('userName can\'t be empty')
  }
  if(!this.userInfo.password){
    return void this.$Message.error('password can\'t be empty')
  }
  if(!this.userInfo.passwordConfirm || this.userInfo.password !== this.userInfo.passwordConfirm){
    return void this.$Message.error('confirm your password')
  }
  this.$http.post('./auth/register',this.userInfo).then(res=>{
    if(res.data.code === 10000){
      this.$router.push('./')
      store.commit('userModule/changePage','manage')
    }else {
      this.$Message.error(res.data.msg)
    }
  })
}

function login() {
  this.$http.post('./auth/login',this.userInfo).then(res=>{
    if(res.data.code === 10000){
      this.$router.push('/')
      store.commit('userModule/changePage','manage')
    }else {
      this.$Message.error(res.data.msg)
    }
  })
}

export {
  register,
  login
}
