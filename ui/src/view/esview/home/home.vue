<style lang="less">
    @import "./home.less";
</style>
<template>
    <div class="home-main">
        <Row>
            <Col span="12">
                <Row>
                    <Card>
                        <Row type="flex" class="user-infor">
                            <Col span="8">
                                <Row class-name="made-child-con-middle" type="flex" align="middle">
                                    <img class="avator-img" :src="avatorPath" />
                                </Row>
                            </Col>
                            <Col span="16" style="padding-left:6px;">
                                <Row class-name="made-child-con-middle" type="flex" align="middle">
                                    <div>
                                        <b class="card-user-infor-name">Admin</b>
                                        <p>super admin</p>
                                    </div>
                                </Row>
                            </Col>
                        </Row>
                        <div class="line-gray"></div>
                        <Row class="margin-top-8">
                            <Col span="8"><p class="notwrap">last publish time:</p></Col>
                            <Col span="16" class="padding-left-8">2017.10-21 13:32:20</Col>
                        </Row>
                    </Card>
                </Row>

            </Col>
            <Col span="12" class-name="padding-left-5">
                <Row>
                    <Col span="8">
                        <infor-card
                            id-name="user_created_count"
                            :end-val="count.createUser"
                            iconType="android-person-add"
                            color="#2d8cf0"
                            intro-text="new user join today"
                        ></infor-card>
                    </Col>
                    <Col span="8" class-name="padding-left-5">
                        <infor-card
                            id-name="visit_count"
                            :end-val="count.visit"
                            iconType="ios-eye"
                            color="#64d572"
                            :iconSize="50"
                            intro-text="pv today"
                        ></infor-card>
                    </Col>
                    <Col span="8" class-name="padding-left-5">
                        <infor-card
                            id-name="transfer_count"
                            :end-val="count.transfer"
                            iconType="shuffle"
                            color="#f25e43"
                            intro-text="service count"
                        ></infor-card>
                    </Col>
                </Row>
            </Col>
        </Row>

        <Row class="margin-top-10">
            <Col span="12">
                <Card>
                    <p slot="title" class="card-title">
                        <Icon type="android-map"></Icon>
                      last week pv
                    </p>
                    <div class="data-source-row">
                        <visite-volume></visite-volume>
                    </div>
                </Card>
            </Col>
          <Col span="12">
          <Row class="margin-top-10">
            <i-col span="24">
              <Card>
                <p slot="title" class="card-title">
                  <Icon type="android-checkbox-outline"></Icon>
                  todo list
                </p>
                <div class="to-do-list-con">
                  <div v-for="(item, index) in toDoList" :key="index" class="to-do-item">
                    <to-do-list-item :content="item.title"></to-do-list-item>
                  </div>
                </div>
              </Card>
            </i-col>

          </Row>
          </Col>

        </Row>

    </div>
</template>

<script>
import cityData from './map-data/get-city-value.js';
import homeMap from './components/map.vue';
import dataSourcePie from './components/dataSourcePie.vue';
import visiteVolume from './components/visiteVolume.vue';
import serviceRequests from './components/serviceRequests.vue';
import userFlow from './components/userFlow.vue';
import countUp from './components/countUp.vue';
import inforCard from './components/inforCard.vue';
import mapDataTable from './components/mapDataTable.vue';
import toDoListItem from './components/toDoListItem.vue';

export default {
    components: {
        homeMap,
        dataSourcePie,
        visiteVolume,
        serviceRequests,
        userFlow,
        countUp,
        inforCard,
        mapDataTable,
        toDoListItem
    },
    data () {
        return {
            toDoList: [
                {
                    title: 'authority'
                },
              {
                  title:'optimize component code'
              },
              {
                  title:'add more component'
              },{
                title:'write doc'
              }
            ],
            count: {
                createUser: 496,
                visit: 3264,
                collection: 24389305,
                transfer: 39503498
            },
            cityData: cityData,
            showAddNewTodo: false,
            newToDoItemValue: ''
        };
    },
    computed: {
        avatorPath () {
            return '../../../static/img/avatar.jpg';
        }
    },
    methods: {
        addNewToDoItem () {
            this.showAddNewTodo = true;
        },
        addNew () {
            if (this.newToDoItemValue.length !== 0) {
                this.toDoList.unshift({
                    title: this.newToDoItemValue
                });
                setTimeout(function () {
                    this.newToDoItemValue = '';
                }, 200);
                this.showAddNewTodo = false;
            } else {
                this.$Message.error('请输入待办事项内容');
            }
        },
        cancelAdd () {
            this.showAddNewTodo = false;
            this.newToDoItemValue = '';
        }
    }
};
</script>
