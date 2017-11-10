<p align="center"><a href="#">Esview</p>

<p align="center">
  <a href="https://www.npmjs.com/package/esview"><img src="https://img.shields.io/npm/l/esview.svg" alt="License"></a>
   <br>
</p>
  

# 1 介绍
1 项目介绍
Esview是一款拖拽组件生成页面的工具，并且可以生成vue代码。

包含拖拽生成页面、页面管理、组件管理等功能。  

前端采用vue和iview，生成的代码必须安装vue和iview才能使用，  

后台采用java（springboot）作为持久层，保存生成的页面、创建的组件。   

![QQ图片20171027113639.png](http://chuantu.biz/t6/127/1509858385x1968319443.gif)
![QQ图片20171027113639.png](https://user-gold-cdn.xitu.io/2017/10/31/4e39e42e4f101efe9bd9aee3a5bb73a7)
![QQ图片20171027113639.png](http://chuantu.biz/t6/121/1509463124x2890191685.png)
![QQ图片20171027113639.png](http://chuantu.biz/t6/121/1509463255x2890191685.gif)

# 2 在线demo  
http://47.94.2.0:9090/#/esview/assemble/assemble_page?pageSoulId=235

# <a href="https://github.com/furioussoul/esview/blob/master/ui/doc/SUMMARY.md">Doc</a>

# 3 原理
如何实现拖拽：html源生api，代码在dnd.js。  

如何生成代码：拖拽生成的页面，背后是一个树形的结构，通过递归向下法解析语法树生成最终的.vue代码。  

组件的属性编辑影响组件样式：vue会监听这棵属性结构的所有属性，当属性被编辑过后，vue会更新整个页面。  

# 4 安装

前端: Esview使用webpack和vue、iview，所以必须安装他们和nodejs，下载本项目后npm install,npm run dev就能启动dev 模式。
dev模式不需要安装后端。

后端: 使用Java（springboot）,所以你必须先安装jdk.

数据库: mysql,数据库表文件在'server'目录下,名字叫 'soul-esview.sql'.

# License
[MIT](https://opensource.org/licenses/MIT)

Copyright (c) 2017-present,  SunZhengJie(Furioussoulk)
