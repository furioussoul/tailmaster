<template>
    <Row>
      <i-col span="24">
        <Card>
          <div>
            <Form ref="searchForm" :model="searchInput" :label-width="80" inline>
              <Form-item prop="controlName" label="appName:">
                <Input v-model="searchInput.controlName">
                </Input>
              </Form-item>
              <ButtonGroup>
                <Button @click="search" type="primary">search</Button>
                <Button @click="reset('searchForm')">reset</Button>
                <Button style="float: right" @click="hrefAdd()" type="ghost">add</Button>
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
    addApp,
    delApp,
    updateApp,
    getAppList,
    getTableAppList,
    getRichApp
  } from '../../../resource/assemble_resource'
  import{
    paginationMixin
  } from '../../../mixin/m_pagination'
  export default{
    name: 'develop',
    mixins: [paginationMixin],
    data() {
      return {
        fn: {
          initFns: [getTableAppList],
          searchFns: [getTableAppList]
        },
        columns: [
          {
            title: 'name',
            key: 'name'
          },
          {
            title: 'action',
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
                }, 'edit'),
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
                }, 'del')
              ]);
            }
          }
        ],
        tableData: [],
        searchInput: {
          pageNum: 1,
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
        this.$router.push('./operate_app')
      },
      edit(param){
        this.$router.push({path: './operate_app', query: {id: param.row.id}})
      },
      del(param){
        delApp.call(this,param.row.id)
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
