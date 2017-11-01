<p align="right">
  <a href="https://github.com/furioussoul/soul-esview/blob/master/ui/README-ch.md">中文</a>
</p>  
<p align="center"><a href="#">Esview</p>
  


<p align="center">
  <a href="https://www.npmjs.com/package/esview"><img src="https://img.shields.io/npm/l/esview.svg" alt="License"></a>
   <br>
</p>
  
  
# Introduction
Esview is a vue code generator by html5 drag/drop.  

You can get .vue file code by dnd components on esview.

![QQ图片20171027113639.png](https://user-gold-cdn.xitu.io/2017/10/31/4e39e42e4f101efe9bd9aee3a5bb73a7)
![QQ图片20171027113639.png](http://chuantu.biz/t6/121/1509463124x2890191685.png)
![QQ图片20171027113639.png](http://chuantu.biz/t6/121/1509463255x2890191685.gif)

# How to drag/drop?
Esview use grid system,  

for creating an app, you should drop 'Frame' into 'DropPanel',  

drop 'Row' into 'DropPanel',drop 'Col' into 'Row'.  

For creating a search form ,you should drop 'Form' into 'Col'.  

then drop 'FormItem' into 'Form',drop 'Input、Radio、Button etc.' into 'FormItem'.


# Online Demo

http://47.94.2.0:9090  


# Install  
frontend: Esview uses vue and iview，so the code generated must rely on vue and iview.  

backend: Java（springboot）,so you must install jdk firstly.

database:mysql,the sql file is on directory 'server',named 'soul-esview.sql'.

# Theory
How to implement dnd：I use html api，the code is in dnd.js

How to generate code：The data structure behind assembled page is a tree,I use Recursive downward parsing to get the final .vue code.  

# License
[MIT](https://opensource.org/licenses/MIT)

Copyright (c) 2017-present,  SunZhengJie(Furioussoulk)
