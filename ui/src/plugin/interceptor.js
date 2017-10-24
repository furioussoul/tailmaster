import {getCookie} from '../util/assist'
import store from '../store'

export default function install(Vue, app) {

  let lockMap = {}
  const lockTime = 1000 //ms
  Vue.http.interceptors.push(function (request, next) {

    let accessToken = getCookie("access_token");
    if(!accessToken) store.commit('userModule/changePage', 'login')

    if (request.url.indexOf('save') !== -1 ||
      request.url.indexOf('update') !== -1 ||
      request.url.indexOf('operate') !== -1 ||
      request.url.indexOf('add') !== -1 ||
      request.url.indexOf('modify') !== -1 ||
      request.url.indexOf('del') !== -1 ||
      request.url.indexOf('save') !== -1 ||
      request.url.indexOf('set') !== -1 ||
      request.url.indexOf('send') !== -1 ||
      request.url.indexOf('add') !== -1) {

      if (!lockMap[request.url]) {
        //not locked
        lockMap[request.url] = true
        setTimeout(() => {
          lockMap[request.url] = false
        }, lockTime)
      } else {
        // locked
        return {}
      }
    }
    next(function (response) {

      if (response.body.code === 405) {
        store.commit('userModule/changePage', 'login')

      } else if (response.body.code === 20000) {
        app.$Message.error(response.body.msg)
      }
      return response
    })
  })
}
