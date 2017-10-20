import {
  appApi
} from "./config";
import {
  verifyToken
} from '../util/assist'
import store from '../store'

export default function install(Vue, app) {

  let lockMap = {}
  const lockTime = 1000 //ms
  Vue.http.interceptors.push(function (request, next) {
    verifyToken(appApi)
    if (request.url.indexOf('save') !== -1 ||
      request.url.indexOf('update') !== -1 ||
      request.url.indexOf('operate') !== -1 ||
      request.url.indexOf('add') !== -1 ||
      request.url.indexOf('modify') !== -1 ||
      request.url.indexOf('del') !== -1 ||
      request.url.indexOf('save') !== -1 ||
      request.url.indexOf('set') !== -1 ||
      request.url.indexOf('send') !== -1) {

      if (!lockMap[request.url]) {
        //没锁
        lockMap[request.url] = true
        setTimeout(() => {
          lockMap[request.url] = false
        }, lockTime)
      } else {
        // 锁了
        return {}
      }
    }
    next(function (response) {
      if (response.body.code === 405) {
        store.commit('userModule/changePage', 'login')
      } else if (response.body.code === 20000) {
        app.$Notice.warning({
          title: '出错了',
          desc: response.body.msg
        })
      }
      return response
    })
  })
}
