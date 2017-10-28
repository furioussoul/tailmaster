<style lang="less" scoped>
  .clearfix() {
    &:after {
      content: "";
      display: table;
      clear: both;
      visibility: hidden;
      font-size: 0;
      height: 0;
    }
  }

  html, body {
    height: 100%;
  }

  [v-cloak] {
    display: none !important;
  }

  body {
    /*min-width: 1366px;*/
    overflow: auto;
    background-color: #ecf0f5 !important;
  }

  body {
  }

  .page {
    margin: 15px;
  }

  .layout {
    height: 100%;
    width: 100%;
    position: relative;
    padding: 50px 0 0 200px;
    transition: all .3s
  }

  .slideleft-enter-active, .slideleft-leave-active {
    transition: transform .3s;
  }

  .slideleft-enter, .slideleft-leave-active {
    transform: translateX(-100%);
  }

  .slideView-enter-active {
    transition: all .5s ease-in;
  }

  .slideView-enter {
    /*transform: translate3d(0,100px,0);*/
    opacity: .3;
  }

  .layout-full-screen {
    padding-left: 0;
  }

  .layout-left {
    position: absolute;
    left: 0;
    top: 0;
    height: 100%;
    width: 200px;
    z-index:901
  }

  .layout-left .navigator {
    color: #4b646f;
    background: #1a2226;
    padding: 10px 25px 10px 15px;
    font-size: 12px;
  }

  .layout-left .ivu-menu-item {
    padding: 0 !important;
    line-height: 41px;
    overflow: hidden;
  }

  .layout-left .ivu-menu-item a {
    padding: 0 24px 0 40px;
  }

  .layout-left .ivu-menu-light {
    /*modified*/
    background: #222d32;
  }

  .layout-left .ivu-menu-submenu-title {
    font-weight: bold;
    padding-left: 15px;
  }

  .layout-left-menu .ivu-menu-light.ivu-menu-vertical .ivu-menu-opened {
    /*modified*/
    background: #2c3b41;
  }

  .layout-left-menu .ivu-menu-light.ivu-menu-vertical .ivu-menu-opened .ivu-menu-submenu-title {
    /*modified*/
    color: #fff;
    background: #1e282c;
  }

  .layout-left-menu .ivu-menu-light.ivu-menu-vertical .ivu-menu-item:hover {
    /*modified*/
    color: #fff !important;
    background: transparent !important;
  }

  .layout-left-menu .ivu-menu-light.ivu-menu-vertical .ivu-menu-submenu-title:hover {
    /*modified*/
    color: #fff;
    background: #1e282c
  }

  .layout-left-menu {
    height: 100%;
    background: #222d32;
  }

  .layout-left-menu .ivu-menu-submenu > .ivu-menu {
    display: block !important;
    color: #a2acbd;
  }

  .layout-left-menu > .ivu-menu {
    height: 100%;
    overflow: auto;
    color: #a2acbd;
  }

  .layout-left-menu a {
    display: block;
    height: 100%;
    width: 100%;
    color: inherit;
  }

  .layout-left-menu .ivu-menu-submenu > .ivu-menu .ivu-menu-item {
    color: black;
    height: 0;
    transition: height .4s ease;
  }

  .layout-left-menu .ivu-menu-submenu.ivu-menu-opened > .ivu-menu .ivu-menu-item {
    height: 41px;
    color: #8aa4af;
  }

  .layout-left-menu .ivu-menu-submenu.ivu-menu-opened > .ivu-menu .ivu-menu-item:hover {
    color: #fff;
  }

  .ivu-menu-light.ivu-menu-vertical .ivu-menu-item-active:not(.ivu-menu-submenu) {
    color: #fff !important;
    border-right: 4px solid #1ab394;
    z-index: 2;
  }

  .layout-header {
    position: absolute;
    left: 0;
    top: 0;
    padding-left: 200px;
    width: 100%;
    height: 50px;
    box-shadow: 0 1px 1px rgba(0, 0, 0, .1);
    background: #fff;
    transition: left .4s ease;
    z-index: 900;
    color: black;
  }

  .layout-header .layout-title {
    float: left;
    line-height: 50px;
    width: 200px;
    color: #fff;
    font-size: 24px;
    font-weight: bold;
    background-color: #2d8cf0;
    text-align: center;
    border-right: 1px solid rgba(255, 255, 255, 0.7);
    transition: all .2s ease-in-out;
    cursor: pointer;
    overflow: hidden;
  }

  .layout-header .layout-title:hover{
    color: #fff !important;
    background-color: rgb(64, 165, 226);
  }
  .layout-logo-left{
    width: 150px;
    height: 30px;
    background: #5b6270;
    border-radius: 3px;
    margin: 15px auto;
  }

  .layout-logo-left img{
    width: 100%;
    height: 100%;
    border-radius: 4px;
    cursor: pointer;
  }

  .layout-left .layout-logo {
    height: 50px;
    width: 220px;
    background: url('//file.40017.cn/tcwlcrm/common/img/logo.png') no-repeat;
    background-size: 140px;
    background-position: center;
  }

  .layout-header .layout-round-icon {
    float: left;
    line-height: 50px;
    width: 50px;
    font-size: 20px;
    text-align: center;
    cursor: pointer;
    transition: all .2s ease-in-out;
  }

  .layout-header .layout-round-icon:hover {
    color: #fff !important;
    background-color: rgb(64, 165, 226);
  }

  .layout-header.layout-header-full {
    left: 0;
  }

  .layout-header .layout-nav-right {
    float: right;
    font-size: 16px;
  }

  .layout-header .ivu-menu > .ivu-menu-item {
    font-weight: bold;
    color: black;
  }

  .layout-header .ivu-menu-item-selected, .layout-header .ivu-menu-item-selected:hover, .layout-header .ivu-menu > .ivu-menu-item:hover {
    color: #fff !important;
    background-color: rgb(64, 165, 226);
  }

  .layout-header-left {
    float: left;
    height: 100%;
    display: flex;
    align-items: center;
  }

  .layout-header .ivu-menu-dark {
    background: white;
    height: 50px;
    line-height: 50px;
    font-size: 14px;

  }

  .layout-header-right {
    position: relative;
    right: 0;
    top:0;
    float: right;
    height: 50px;
    line-height: 50px;
    display: flex;
    align-items: center;
  }


  .layout-header-right .ivu-menu-submenu, .layout-header-right .ivu-menu-item {
    padding: 0 12px;
    height: 50px;
    color:black !important ;
  }

  .layout-header-right .ivu-menu-submenu:hover, .layout-header-right .ivu-menu-item:hover {
    color: #fff !important;
    background-color: rgb(64, 165, 226);
  }

  .layout-content {
    position: relative;
    width: 100%;
    height: 100%;
    min-height: 300px;

    &__container {
      border: 1px solid #d7dde4;
      background: #f5f7f9;
      margin-left: 15px;
    }
  }

  .layout-scroll {
    overflow: auto;
    width: 100%;
    height: 100%;
  }

  .layout-bread {
    position: relative;
    float: left;
    height: 50px;
    line-height: 50px;
    display: flex;
    align-items: center;
    .clearfix();
  }

  .layout-bread h1 {
    font-size: 24px;
    font-weight: 400;
    line-height: 1.1;
  }

  .layout-footer {
    position: absolute;
    left: 0;
    right: 0;
    bottom: -55px;
    text-align: center;
  }

  .layout-userinfo {
    /*background: #1ab394;*/
    padding: 10px 0;
    line-height: 25px;
    text-align: center;
  }

  .layout-userinfo-depart {
    font-size: 17px;

  }

  .layout-userinfo-name {
    font-size: 13px;
    /*line-height: 25px;*/
  }

  .layout-userinfo-bar {
    text-align: right;
    padding-bottom: 5px;
  }

  .soul_form {
    margin-bottom: 10px;

    &__item {
      &_inner {
        width: 200px;
      }
    }
  }

</style>
<template>
  <div id="layout" class="layout" :class="{'layout-full-screen':!show}">

    <!--左侧菜单栏 start-->
    <transition name="slideleft">
      <div class="layout-left" v-show="show">
        <div class="layout-left-menu">
          <i-menu v-if="menu"
                  v-cloak
                  :active-name="activedSecondMenu"
                  :open-names="openedMenu"
                  theme="light"
                  width="200px"
                  :accordion="true"
                  ref="secondMenu">
            <div class="layout-logo-left"><img @click="home" src="../../../static/img/logo.png"></img></div>
            <template v-for="group in menu">
              <Menu-item :name="group.id"
                         :key="group.id"
                         v-if="group.mtype!==-1 && (!group.subMenuList || group.subMenuList.length===0)">
                <router-link :to="group.url">
                  {{group.title}}
                </router-link>
              </Menu-item>
              <div v-if="group.mtype === -1" style="display: none"></div>
              <Submenu :name="group.id" v-else :key="group.id">
                <template slot="title">
                  <Icon :type="group.icon"></Icon>
                  {{group.title}}
                </template>
                <Menu-item :name="child.id"
                           v-for="child in group.subMenuList"
                           :key="child.id"
                           v-if="child.mtype!==-1">
                  <router-link :to="child.url">
                    {{child.title}}
                  </router-link>
                </Menu-item>
              </Submenu>
            </template>
          </i-menu>
        </div>
      </div>
    </transition>
    <!--左侧菜单栏 end-->

    <!--顶部菜单栏 start-->
    <div class="layout-header" :class="{'layout-header-full':false}" cloak>
      <i-menu mode="horizontal" v-cloak :active-name="activedFirstMenu" theme="dark" @on-select="selectMenu"
              ref="firstMenu">

        <Icon type="navicon-round" class="layout-round-icon" @click.native="toggleMenu"></Icon>
        <div class="layout-bread">
          <h1>
            <Breadcrumb ref="bread" v-if="path" separator=">" >
              <Breadcrumb-item v-for="item in path" :key="item.id">{{item.title}}</Breadcrumb-item>
            </Breadcrumb>
          </h1>
        </div>
        <div class="layout-header-right" v-cloak>
          <Poptip style="height: 50px;" trigger="hover" title="user" content="" placement="bottom-end"
                  v-if="userInfo">
            <div class="ivu-menu-item">
              <Icon type="person"></Icon>
              {{userInfo.username}}
            </div>
            <div class="" slot="content">
              <div class="layout-userinfo">
                <p class="layout-userinfo-depart">{{userInfo.department}}</p>
                <p class="layout-userinfo-name">{{userInfo.username}} - {{userInfo.workId}}</p>
              </div>
              <div class="layout-userinfo-bar">
                <i-button @click="logout">退出</i-button>
              </div>
            </div>
          </Poptip>
        </div>
      </i-menu>
    </div>
    <!--顶部菜单栏 end-->


    <!--主视图 start-->
    <div class="layout-content" id="layout-content" v-cloak>
      <div class="layout-scroll">

        <slot name="drop-panel">
        </slot>

      </div>

    </div>
    <!--主视图 end-->

  </div>
</template>

<script>

  import {
    getBreadcrumb,
    getPages,
    initRouter
  } from "./app_frame";
  import {
    getConfig
  } from '../config'
  import store from '../store'

  import {
    mapGetters,
    mapMutations
  } from 'vuex'

  export default {
    name: "AppFrame",
    props: {
      fullPath: [String],
      totalMenu: [Array],
      userInfo: [Object]
    },
    data() {
      return {
        menu: null,
        routes: [],
        initPath: [],
        show: true,
        activedSecondMenu: '',
        openedMenu: [],
        activedFirstMenu: ''
      };
    },
    computed: {
      title() {
        const path = store.state.routerModule.path,
          target = this.pages.find(function (e, i) {
            return e.url === path;
          });
        return target ? target.title : '';
      },
      path() {
        const path = store.state.routerModule.path;

        if(!getConfig('type')){
          store.commit('soulModule/changeSoul', path)//router of client app
        }
        return getBreadcrumb(this.totalMenu, path);
      }
    },
    watch: {
      path(n) {
        const self = this;
        this.$nextTick(function () {
          self.$refs.bread.updateChildren();
        });
      }
    },
    methods: {
      home(){
          if(getConfig('type') === 'assemble'){
            // router of the frame inside assemble factory
            this.$router.push('/esview/assemble/assemble_page?pageId=%2Findex')
          }else {
            // router of client app
            this.$router.push('/index')
          }
      },
      toggleMenu() {
        this.show = !this.show;
        window.setTimeout(function () {
          window.dispatchEvent(new Event('resize'));
        }, 350);
      },
      selectMenu(id) {
        this.totalMenu.forEach(function (e) {
          if (e.id === id) {
            this.menu = e.subMenuList;
          }
        }.bind(this));
      },
      setLayout(fullPath) {
        if (fullPath === this.fullPath) {
          this.activedFirstMenu = this.totalMenu[0].id;
          this.menu = this.totalMenu[0].subMenuList;
        } else {
          this.initPath = getBreadcrumb(this.totalMenu, fullPath);
          if (this.initPath.length === 0) return;
          this.activedFirstMenu = this.initPath[0].id;
          this.menu = this.initPath[0].subMenuList;
          if (this.initPath[1].mtype === 0) {
            this.openedMenu = [this.initPath[1].id];
            this.activedSecondMenu = this.initPath[2].id;
          } else {
            this.activedSecondMenu = this.initPath[1].id;
          }
        }
        this.$nextTick(function () {
          this.$refs.firstMenu.updateActiveName();
          this.$refs.secondMenu.updateActiveName();
          this.$refs.secondMenu.updateOpened();
        }.bind(this));
      },
      logout() {
//        if (appApi.clearAuth) {
//          this.$http.get(appApi.clearAuth);
//        }
//        location.href = appApi.logout;
      }
    },
    created() {
      this.pages = getPages(this.totalMenu);
      initRouter(this.pages);
      this.setLayout(this.fullPath);
    }
  }
</script>
