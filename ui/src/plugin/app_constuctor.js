import{
  getQueryParam
} from '../util/assist'
import store from '../store'

import {
  mapGetters,
  mapMutations,
  mapActions
} from 'vuex'
export default function initAppConstructor(config) {

  const appTemplate = `
  
              <div id="layout" class="layout" :class="{\'layout-full-screen\':!show}">
              
                <!--左侧菜单栏 start-->
                <transition name="slideleft">
                  <div class="layout-left" v-show="show">
                    <div class="layout-left-menu">
                      <i-menu v-if="menu && menu.mtype!==-1"
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
                <div class="layout-header" :class="{\'layout-header-full\':false}" cloak>
                  <i-menu mode="horizontal" v-cloak :active-name="activedFirstMenu" theme="dark" @on-select="selectMenu"
                          ref="firstMenu">
                    <div class="layout-title" @click='home'><img src="../../static/img/logo.png"></img></div>
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
                    <div class="layout-bread">
                      <h1>
                        <Breadcrumb ref="bread" v-if="path" separator=">" style="float:right;padding:5px 5px 0 0;">
                          <Breadcrumb-item v-for="item in path" :key="item.id">{{item.title}}</Breadcrumb-item>
                        </Breadcrumb>
                      </h1>
                    </div>
                    
                    
                    <!--路由视图 start-->
                    <router-view></router-view>
                    <!--路由视图 end-->
                    
                    
                  </div>
                  
                  
                  <!--footer start-->
                  <Card class="layout-footer">
                      Copyright© 2017-2020 版权所有 soul-esview
                      <a href="https://github.com/furioussoul/soul-esview.git">
                        <img style="width:30px;margin-left:10px;vertical-align:top" src="../../static/img/git.jpg">
                        </img>
                      </a>
                  </Card>  
                  <!--footer end-->
                  
                  
                </div>
                <!--主视图 end-->
                  
                  
              </div>
             
             `


  const appApi = config.appApi;
  const initRouter = config.initRouter;
  const routerStore = config.routerStore;

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


  return {
    name: "app",
    template: appTemplate,
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
        store.commit('dragModule/changeSoul', getQueryParam('pageId'))
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
      ...mapActions('userModule', ['getControlClazzes']),
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
          this.$refs.firstMenu.updateActiveName();
          this.$refs.secondMenu.updateActiveName();
          this.$refs.secondMenu.updateOpened();
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
            "id": 74784,
            "name": "gongdan",
            "title": "esview",
            "url": "/esview",
            "orderNo": 1,
            "mtype": 0,
            "hasPermisson": 1,
            "subMenuList": [
              {
                "id": 74785,
                "name": "s",
                "title": "Assemble",
                "url": "/esview/assemble",
                "orderNo": 1,
                "mtype": 0,
                "icon":'wrench',
                "hasPermisson": 1,
                "subMenuList": [
                  {
                    "id": 74796,
                    "name": "index",
                    "title": "ManageApp",
                    "url": "/esview/assemble/index",
                    "orderNo": 1,
                    "mtype": 1,
                    "hasPermisson": 1,
                    "subMenuList": null
                  },
                  {
                    "id": 74796,
                    "name": "index",
                    "title": "OperateApp",
                    "url": "/esview/assemble/operate_app",
                    "orderNo": 1,
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
                "icon":'upload',
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
              }
            ]
          }
        ]
      }
      this.totalMenu = res.data;
      this.routes = getPages(this.totalMenu);
      initRouter(this.routes);
      this.setLayout(this.$route.fullPath);

      res = {
        "code": 10000,
        "msg": "ok",
        "data": {
          "username": "furioussoul"
        }
      }
      this.userInfo = res.data;


      console.log()
    }
  }
}
