<p align="center"><a href="#">Esview</p>

<p align="center">
  <a href="https://www.npmjs.com/package/esview"><img src="https://img.shields.io/npm/dm/esview.svg" alt="Downloads"></a>
  <a href="https://www.npmjs.com/package/esview"><img src="https://img.shields.io/npm/v/esview.svg" alt="Version"></a>
  <a href="https://www.npmjs.com/package/esview"><img src="https://img.shields.io/npm/l/esview.svg" alt="License"></a>
   <br>
</p>
  

# 介绍
Esview是一个Vue拖拽可视化开发平台，在接入esview的站点渲染拖拽出来的页面。


Vue可视化：![QQ图片20171027113639.png](http://chuantu.biz/t6/115/1509167213x1926933951.gif)

# 文档
https://cnodejs.org/topic/59f406d1085668ad5e6984a7  

# 快速上手
1） 在manager-site: http://47.94.2.0:9090, 创建一个单页面应用,

 进入Assemble->ManageApp页面,创建（add）一个app，然后点击page->add创建一个页面，

 拖拽 'Frame'组件到中间区域来生成一个单页面应用。

 推荐使用Row、Col来布局。


2） 下载demo ： https://github.com/furioussoul/esview-demo， npm install,安装依赖


3） 修改main.js的render方法，填写创建了的应用的名称和页面名称， npm run dev，就能显示创建的应用了。

![QQ图片20171027113639.png](http://chuantu.biz/t6/114/1509075922x986935075.png)


# 安装
下载demo ： https://github.com/furioussoul/esview-demo， npm install 依赖， npm run dev就能启动前端了。

如果要部署后端，请安装jdk1.80,并且启动server包下面的Application.java(springboot 项目)。

数据库表也在server包下，名soul-esview.sql。



# 组装工厂
Esview平台分为三个部分：面向用户的组装工厂、面向开发者的开发工厂以及面向后端服务化的暴露接口。

页面组装如图一所示，主要包含五部分：

1） 组件列表
组件列表展示可拖拽的组件（①所示区域部分），可以拖拽到中间的布局块中（②区域）。

2） 布局块
页面初始化时就会生成一个布局块，组件可以被拖拽到布局块中。

3） 组件编辑
右键布局块中的组件可以编辑组件脚本、组件参数，删除组件（③区域）

每个组件，都有可配置的属性、脚本，用户可以重它们，

如，配置按钮组件的颜色、大小，change、input事件（利用eventCenter（事件中心）和它附带的一些对象实现组件间通信）等，

都取决于组件开发者对该组件的预留项。

4） 顶部页面操作
（④区域）用户可以预览，保存，撤销，反撤销。

5） 左侧导航栏
（⑤区域）
第一个是组件的组装工厂，包括app管理，页面管理，组装页面。

第二个是组件的开发工厂，用户可以编辑组件的全部脚本。

![QQ图片20171027083158.png](http://chuantu.biz/t6/114/1509075952x986935075.png)


# 视图脚本
this.model封装了Iview组件对外暴露的接口，修改model属性来控制组件的显示。

利用eventCenter（事件中心）实现组件间通信。

![QQ图片20171027091954.jpg](http://chuantu.biz/t6/114/1509075967x986935075.jpg)

# License
[MIT](https://opensource.org/licenses/MIT)

Copyright (c) 2017-present,  SunZhengJie(Furioussoulk)
