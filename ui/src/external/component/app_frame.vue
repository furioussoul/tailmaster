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

        <slot v-if="routerPath.indexOf('esview') > -1" name="drop-panel">
        </slot>

        <router-view v-else>
        </router-view>

      </div>

    </div>
    <!--主视图 end-->

  </div>
</template>
<style lang="less">
 @import '../../style/component/frame.less';

</style>
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
      routerPath(){
          return store.state.routerModule.path;
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
