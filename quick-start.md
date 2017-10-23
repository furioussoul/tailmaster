# 安装 {#安装}

推荐使用vue-cli项目做为脚手架。

# NPM {#安装}

```shell
npm install esview
```

安装完成后，需要给esview配置额外的属性。

```js
import esview from 'esview'
import VueRouter from 'vue-router';

esview.addConfig('router',router)

const app = new Vue({
  router: router,
  render (h) {
    return h(esview.render('MyApp','token'))
  }
})
```



