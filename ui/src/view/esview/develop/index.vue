<template>
    <Row>
      <i-col span="24">
        <Card>
          <div>
            <Form ref="searchForm" :model="searchInput" :label-width="80" inline>
              <Form-item prop="controlName" label="组件名称">
                <Input v-model="searchInput.controlName">
                </Input>
              </Form-item>
              <Form-item prop="controlClass" label="组件分类">
                <Input v-model="searchInput.controlClass">
                </Input>
              </Form-item>
              <ButtonGroup>
                <Button @click="search" type="primary">筛选</Button>
                <Button @click="reset('searchForm')">重置</Button>
                <Button style="float: right" @click="hrefAdd()" type="ghost">添加</Button>
              </ButtonGroup>
            </Form>
          </div>

          <Table border
                 :columns="columns"
                 :data="tableData"
                 :stripe="true"
                 size="small">
          </Table>
          <Row style="margin-top:15px;">
            <Page style="float:right;"
                  :current="searchInput.pageNo"
                  :page-size="searchInput.pageSize"
                  show-total
                  :total="searchInput.total"
                  show-elevator
                  @on-change="search">
            </Page>
          </Row>
        </Card>
      </i-col>
    </Row>
</template>
<script>
  import {
    updateControl,
    delControl,
    getRichControl,
    getTableControlList
  } from '../../../resource/develop_resource'
  import{
    paginationMixin
  } from '../../../mixin/m_pagination'
  export default{
    name: 'develop',
    mixins: [paginationMixin],
    data() {
      return {
        fn: {
          initFns: [getTableControlList],
          searchFns: [getTableControlList]
        },
        columns: [
          {
            title: '名称',
            key: 'name'
          },
          {
            title: '别名',
            key: 'nickname'
          }, {
            title: '分类',
            key: 'class'
          },
          {
            title: '操作',
            key: 'action',
            width: 300,
            align: 'center',
            render: (h, params) => {
              return h('ButtonGroup', [
                h('Button', {
                  props: {
                    type: 'primary',
                    size: 'small'
                  },
                  on: {
                    click: () => {
                      this.edit(params)
                    }
                  }
                }, '编辑'),
                h('Button', {
                  props: {
                    type: 'error',
                    size: 'small'
                  },
                  on: {
                    click: () => {
                      this.del(params)
                    }
                  }
                }, '删除')
              ]);
            }
          }
        ],
        tableData: [],
        searchInput: {
          pageNo: 1,
          pageSize: 20,
          total: 0,
          controlName: '',
          controlClass: ''
        }
      }
    },
    methods: {
      reset(name){
        this.$refs[name].resetFields();
      },
      hrefAdd(){
        this.$router.push('./operate_control')
      },
      edit(param){
        this.$router.push({path: './operate_control', query: {id: param.row.id}})
      },
      del(param){
        delControl.call(this,param.row.id)
      }
    }
  }

</script>

<style scoped>

  .demo-upload-list img {
    width: 100%;
    height: 100%;
  }

  .member-container li {
    float: left;
    margin-right: 40px;
    margin-bottom: 10px;
  }
</style>
