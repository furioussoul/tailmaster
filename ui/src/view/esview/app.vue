<template>
  <div id="layout" class="layout" :class="{'layout-full-screen':!show}">
    <!--左侧菜单栏 start-->
    <transition name="slideleft">
      <div class="layout-left" v-show="show">
        <div class="layout-left-menu">
          <i-menu v-if="menu && menu.mtype!==-1"
                  v-cloak
                  :active-name="activedSecondMenu"
                  :open-names="openedMenu"
                  theme="light"
                  width="100%"
                  :accordion="true"
                  ref="secondMenu">
            <div class="layout-logo-left"><img @click="home" src="../../../static/img/logo.png"></img></div>
            <template v-for="group in menu">
              <Menu-item :name="group.id"
                         :key="group.id"
                         v-if="group.mtype!==-1 && !group.subMenuList || group.subMenuList.length===0">
                <router-link :to="group.url">
                  {{group.title}}
                </router-link>
              </Menu-item>
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
    <div style="z-index: 901" class="layout-header" :class="{'layout-header-full':false}" cloak>
      <i-menu mode="horizontal" v-cloak :active-name="activedFirstMenu" theme="dark" @on-select="selectMenu"
              ref="firstMenu">
        <Icon type="navicon-round" class="layout-round-icon" @click.native="toggleMenu"></Icon>
        <div class="layout-bread">
          <h1>
            <Breadcrumb ref="bread" v-if="path" separator=">">
              <Breadcrumb-item v-for="item in path" :key="item.id">{{item.title}}</Breadcrumb-item>
            </Breadcrumb>
          </h1>
        </div>
        <div class="layout-header-right" v-cloak>
          <Button @click="goGithub" size="small">
            <Icon type="social-github"></Icon>
            github
          </Button>
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
                <i-button @click.native="logout">退出</i-button>
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

        <!--路由视图 start-->
        <router-view></router-view>
        <!--路由视图 end-->

      </div>

    </div>
    <!--主视图 end-->

  </div>
</template>

<script>
import{ getQueryParam, getCookie} from '../../util/assist'
import store from '../../store/index'
import {mapGetters,mapMutations,mapActions} from 'vuex'
import {appApi} from '../../plugin/config'
import {initRouter, router, routerStore} from "../../plugin/router";
import{getBreadcrumb, getPages} from '../../util/assist'

export default{

    name: "app",
    data() {
      return {
        menu: null,
        routes: [],
        initPath: [],
        totalMenu: '',
        show: true,
        activedSecondMenu: '',
        openedMenu: [],
        activedFirstMenu: '',
        userInfo: ''
      };
    },
    computed: {
      title() {
        const path = routerStore.state.RouteModule.path,
          target = this.routes.find(function (e, i) {
            return e.url === path;
          });
        return target ? target.title : '';
      },
      path() {
        const path = routerStore.state.RouteModule.path;
        return getBreadcrumb(this.totalMenu, path);
      }
    },
    watch: {
      path() {
        const self = this;
        this.$nextTick(function () {
          self.$refs.bread.updateChildren();
        });
      }
    },
    methods: {
      ...mapActions('dragModule', ['getControlClazzes']),
      goGithub(){
        location.href='https://github.com/furioussoul/soul-esview'
      },
      home(){
        this.$router.push('/')
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
        if (fullPath === '/') {
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
          try{
            this.$refs.firstMenu.updateActiveName();
            this.$refs.secondMenu.updateActiveName();
            this.$refs.secondMenu.updateOpened();
          }catch (ex){

          }
        }.bind(this));
      },
      logout() {
        if (appApi.logout) {
          store.commit('userModule/changePage','login')
          this.$http.get(appApi.logout);
        }
      }
    },
    mounted() {
      let accessToken = getCookie("access_token");
      if(!accessToken) store.commit('userModule/changePage', 'login')

      this.getControlClazzes()

      let res = {
        "code": 10000,
        "msg": "ok",
        "data": [
          {
            "id": 1,
            "name": "esview",
            "title": "esview",
            "url": "/esview",
            "orderNo": 1,
            "mtype": 0,
            "hasPermisson": 1,
            "subMenuList": [
              {
                "id": 2,
                "name": "s",
                "title": "Assemble",
                "url": "/esview/assemble",
                "orderNo": 1,
                "mtype": 0,
                "icon": 'wrench',
                "hasPermisson": 1,
                "subMenuList": [
                  {
                    "id": 3,
                    "name": "managePage",
                    "title": "managePage",
                    "url": "/esview/assemble/manage_page",
                    "orderNo": 1,
                    "mtype": 1,
                    "hasPermisson": 1,
                    "subMenuList": null
                  },
                  {
                    "id": 4,
                    "name": "AssemblePage",
                    "title": "AssemblePage",
                    "url": "/esview/assemble/assemble_page",
                    "orderNo": 2,
                    "mtype": -1,
                    "hasPermisson": 1,
                    "subMenuList": null
                  }
                ]
              },
              {
                "id": 74842,
                "name": "develop",
                "title": "Develop",
                "url": "/esview/develop",
                "orderNo": 1,
                "mtype": 0,
                "icon": 'upload',
                "hasPermisson": 1,
                "subMenuList": [
                  {
                    "id": 74840,
                    "name": "index",
                    "title": "ManageControl",
                    "url": "/esview/develop/index",
                    "orderNo": 1,
                    "mtype": 1,
                    "hasPermisson": 1,
                    "subMenuList": null
                  },
                  {
                    "id": 74796,
                    "name": "add_control",
                    "title": "OperateControl",
                    "url": "/esview/develop/operate_control",
                    "orderNo": 1,
                    "mtype": -1,
                    "hasPermisson": 1,
                    "subMenuList": null
                  }
                ]
              }, {
                "id": 74842,
                "name": "test",
                "title": "test",
                "url": "/esview/test",
                "orderNo": 1,
                "mtype": 0,
                "icon": 'ios-game-controller-b',
                "hasPermisson": 1,
                "subMenuList": [
                  {
                    "id": 74840,
                    "name": "test",
                    "title": "test",
                    "url": "/esview/test/index",
                    "orderNo": 1,
                    "mtype": 1,
                    "hasPermisson": 1,
                    "subMenuList": null
                  }
                ]
              }
            ]
          }
        ]
      }

      this.totalMenu = res.data;
      this.routes = getPages(this.totalMenu);
      initRouter(this.routes);
      this.setLayout(this.$route.fullPath);

      let split = accessToken.split('@');

      res = {
        "code": 10000,
        "msg": "ok",
        "data": {
          "username": split[0]
        }
      }
      this.userInfo = res.data;
      store.commit('userModule/setMe',this.userInfo)
    }
  }
</script>
