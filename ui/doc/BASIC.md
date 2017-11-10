# 基础

## 进入布局页面  

点击Assemble->managePage页面点击add进入布局页面，或者点layout修改布局。

## 菜单栏（code，layout，save，undo，redo，no board）
### code：显示当前布局的代码。
### layout：切换回布局。
### save：保存当前布局。
### undo：撤销。
### redo：反撤销。
### no board：隐藏布局块的边距和边框。

## 布局

### 栅格
Esview使用栅格布局，拖拽Row到中间区域，再拖拽Col到Row内，点击Col可以在右侧Model Config区域修改span来设置Col的宽度。

### 表单
拖拽Form到中间区域，再拖拽Row、Col到Form，再把FormInput、FormSelect等拖拽到Col中。
拖拽Button到From中。

## 编辑组件属性
点击布局中的组件，右侧Model Config区域会显示可编辑的属性，修改后会立刻呈现出来。

## 请求后台接口
Form会自动嗅探到其内部的元素。
1）修改Form的url参数为你要请求的接口。
2）表单元素包括FormInput、FormSelect等带有Form前缀的组件。表单元素的label字段代表传给后台接口的请求参数的key，
修改label来定制化请求参数。
3）拖拽Button到中间区域，点击Button即可提交表单到你指定的url。

## Table接入后台接口
Table必须和Form一起使用，Table会告诉Form让Form提交表单，并把请求结果传给Table。

