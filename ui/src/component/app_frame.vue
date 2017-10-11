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
                  width="220px"
                  :accordion="true"
                  ref="secondMenu">
            <template v-for="group in menu">
              <Menu-item :name="group.id"
                         :key="group.id"
                         v-if="!group.subMenuList || group.subMenuList.length===0">
                <router-link :to="group.url">
                  {{group.title}}
                </router-link>
              </Menu-item>
              <Submenu :name="group.id" v-else :key="group.id">
                <template slot="title">
                  <Icon type="android-apps"></Icon>
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
        <div class="layout-title">发布完成的app</div>
        <Icon type="navicon-round" class="layout-round-icon" @click.native="toggleMenu"></Icon>

        <div class="layout-header-right" v-cloak>
          <Poptip style="height: 50px;" trigger="hover" title="提示标题" content="提示内容" placement="bottom-end"
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
        <div class="layout-bread">
          <h1>
            <Breadcrumb ref="bread" v-if="path" separator=">" style="float:right;padding:5px 5px 0 0;">
              <Breadcrumb-item v-for="item in path" :key="item.id">{{item.title}}</Breadcrumb-item>
            </Breadcrumb>
          </h1>
        </div>

        <slot name="router-view">
        </slot>

      </div>


      <!--footer start-->
      <Card class="layout-footer">
        Copyright© 2017-2020 版权所有 soul-esview
      </Card>
      <!--footer end-->


    </div>
    <!--主视图 end-->

  </div>
</template>

<script>

  /**
   * 获取浏览器内的cookie
   * @param name cookie名称
   * @returns {*}
   */
  function getCookie(name) {
    const reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
    let arr;
    if (arr = document.cookie.match(reg)) {
      return decodeURIComponent(arr[2]);
    }
    return null;
  }


  /**
   * 获取面包屑
   * @param menus 菜单
   * @param path 当前路径
   * @returns {*}
   */
  function getBreadcrumb(menus, path) {
    let breadcrumb = [];
    if (menus && menus.length) {
      let flag = false;
      for (let i = 0; i < menus.length; i++) {

        if (flag) {
          return breadcrumb;
        }

        //mtype === 1 说明menu是个页面
        if ((menus[i].mtype === 1 || menus[i].mtype === -1) && path.indexOf(menus[i].url) !== -1 && menus[i].url.substr(-1, 1) !== '/') {
          flag = true;
          return [menus[i]];
        }

        breadcrumb = getBreadcrumb(menus[i].subMenuList, path);
        if (breadcrumb.length) {
          return [menus[i]].concat(breadcrumb);
        }
      }
    }
    return breadcrumb;
  }

  /**
   * 把菜单生成vue路由对象
   * @param menus 菜单
   * @returns {Array}
   */
  function getPages(menus) {
    let pages = [];
    if (menus && menus.length > 0) {
      for (let i = 0; i < menus.length; i++) {
        if (!menus[i].subMenuList || menus[i].subMenuList.length === 0) {
          pages.push(menus[i]);
        } else {
          pages = pages.concat(getPages(menus[i].subMenuList));
        }
      }
    }
    return pages;
  }

  import {appApi, appContext} from "../plugin/config";
  import {initRouter, router, routerStore} from "../plugin/router";
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
      ...mapGetters('dragModule', ['soul']),
      title() {
        const path = routerStore.state.RouteModule.path,
          target = this.routes.find(function (e, i) {
            return e.url === path;
          });
        return target ? target.title : '';
      },
      path() {
        const path = routerStore.state.RouteModule.path;
        this.changeSoul(path)
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
      ...mapMutations('dragModule', ['changeSoul']),
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
        if (appApi.clearAuth) {
          this.$http.get(appApi.clearAuth);
        }
        location.href = appApi.logout;
      }
    },
    created() {
// verifyToken(appApi);

      /*    //get menus from server
       this.$http.get(appApi.menu).then(res=> {
       res = res.body;
       if (res.code === 10000) {
       this.totalMenu = res.data;
       this.routes = getPages(this.totalMenu);
       initRouter(this.routes);
       this.setLayout(this.$route.fullPath);
       }
       })

       //get userInfo from server
       this.$http.get(appApi.info).then(res=> {
       res = res.body;
       if (res.code === 10000) {
       this.userInfo = res.data;
       }
       })
       }*/
      this.totalMenu.forEach(menu=>{
          menu.url = '/esview/assemble/index' + menu.url
      })
      this.routes = getPages(this.totalMenu);
      initRouter(this.routes, true);
      this.setLayout(this.fullPath);
    }
  }
</script>
