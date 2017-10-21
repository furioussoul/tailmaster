<p align="center"><a href="#">Esview</p>
  
<p align="center">
  <a href="https://www.npmjs.com/package/esview"><img src="https://img.shields.io/npm/dm/esview.svg" alt="Downloads"></a>
  <a href="https://www.npmjs.com/package/esview"><img src="https://img.shields.io/npm/v/esview.svg" alt="Version"></a>
  <a href="https://www.npmjs.com/package/esview"><img src="https://img.shields.io/npm/l/esview.svg" alt="License"></a>
   <br>
 
</p>
  
  
# Introduction
Esview is a web frontend platform for building vue spa through html5 drag/drop api.  

Recommand use webpack constructor spa to inject esview.


# How to drag/drop?
Esview use grid system,  

for creating an app, you should drop 'Frame' into 'DropPanel',  

drop 'Row' into 'DropPanel',drop 'Col' into 'Row'.  

For creating a search form ,you should drop 'Form' into 'Col'.  

then drop 'FormItem' into 'Form',drop 'Input、Radio、Button etc.' into 'FormItem'.

right click to edit script to use eventCenter to comminute between components,  

also you can use ajax in script in the same domain.  

# Quick Start

To build vue spa,you only need 3 steps, after then run script

First step : download vue-spa demo(esview-demo) https://github.com/furioussoul/esview-demo   

Second step : assemble pages online on manager-site(this is online demo : http://47.94.2.0:9090/)  

Third step: esview-demo install esview npm package and npm run dev to deploy


# Demo

Assemble vue spa on manager-site,then you can see spa changed on client-site.

manager-site: http://47.94.2.0:9090/#/esview/assemble/operate_app?appId=36  

usage: https://github.com/furioussoul/esview-demo


![pic](http://chuantu.biz/t6/98/1508081223x1968430843.png "esview")
![pic](http://chuantu.biz/t6/99/1508130896x1114690581.png "esview")


# License
[MIT](https://opensource.org/licenses/MIT)

Copyright (c) 2017-present,  SunZhengJie(Furioussoulk)
