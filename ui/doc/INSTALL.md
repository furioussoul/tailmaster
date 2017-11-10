# 安装（Esview是一个前后端分离的项目）

## 前端部署: Esview依赖npm包，所以必须安装他们和nodejs和npm，clone本项目后，切到ui目录下。

第一步：安装npm包：

```shell
npm install
```
第二步：启动dev模式（dev模式不需要启动后端）：
```shell
npm run dev
```
第二步（可选）：启动生产模式：
```shell
npm run build
```
再启动ui/src目录下的server.js部署前端。

## 后端部署: 后端使用Java（springboot）,所以你必须先安装jdk，启动src目录下的Application.java类。

数据库: mysql,数据库表文件在'server'目录下,名字叫 'soul-esview.sql'.




