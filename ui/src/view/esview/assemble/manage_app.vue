<template>
  <Row>
    <i-col span="24">
      <Card>
        <div>
          <Form ref="searchForm" :model="searchInput" :label-width="80" inline>
            <Form-item prop="name" label="AppName:">
              <Input v-model="searchInput.name">
              </Input>
            </Form-item>
            <ButtonGroup>
              <Button @click="search" type="primary">search</Button>
              <Button @click="reset('searchForm')">reset</Button>
              <Button style="float: right" @click="add()" type="ghost">add</Button>
            </ButtonGroup>
          </Form>
        </div>

        <Table border
               :loading="loading"
               :columns="columns"
               :data="tableData"
               :stripe="true"
               size="small">
        </Table>
        <Row style="margin-top:15px;">
          <Page style="float:right;"
                :current="searchInput.pageNum"
                :page-size="searchInput.pageSize"
                show-total
                :total="searchInput.total"
                show-elevator
                @on-change="search">
          </Page>
        </Row>
      </Card>
    </i-col>

    <Modal
      v-model="showConfirmAppModal"
      title="confirmApp"
      @on-ok="ok">
      <i-form ref="confirmApp"
              :model="opModel"
              :label-width="80">
        <form-item prop="name" label="name">
          <i-input v-model="opModel.name"></i-input>
        </form-item>
        <form-item prop="sort" label="sort">
          <i-input v-model="opModel.sort"></i-input>
        </form-item>
        <form-item prop="url" label="url">
          <i-input v-model="opModel.url"></i-input>
        </form-item>
      </i-form>
    </Modal>

    <Modal
      v-model="showManagePageModal"
      title="managePage">
      <ManagePage :refresh="random"></ManagePage>
    </Modal>
  </Row>
</template>
<script>
  import {addApp,delApp, updateApp, getAppList, getTableAppList,getRichApp } from '../../../resource/assemble_resource'
  import{ paginationMixin} from '../../../mixin/m_pagination'
  import ManagePage from '../assemble/manage_page.vue'
  export default{
    name: 'ManageApp',
    mixins: [paginationMixin],
    components:{
      ManagePage
    },
    data() {
      return {
        fn: {
          initFns: [getTableAppList],
          searchFns: [getTableAppList]
        },
        columns: [
          {
            title: 'AppId',
            key: 'id'
          },
          {
            title: 'AppName',
            key: 'name'
          },
          {
            title: 'sort',
            key: 'sort'
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
                    type: 'ghost',
                    size: 'small'
                  },
                  on: {
                    click: () => {
                      this.page(params)
                    }
                  }
                }, 'page'),
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
          name: ''
        },
        opModel:{
          sort:'',
          name:'',
          url:''
        },
        showManagePageModal:false,
        showConfirmAppModal:false,
        loading:false,
        random:-1
      }
    },
    methods: {
      reset(name){
        this.$refs[name].resetFields();
      },
      page(param){
        this.random = Math.random()
        this.$route.query.appId = param.row.id
        this.showManagePageModal = true
      },
      add(){
        this.$refs['confirmApp'].resetFields();
        this.showConfirmAppModal = true
      },
      edit(param){
        getRichApp.call(this,param.row.id,(data)=>{
          this.opModel = data
          this.$refs['confirmApp'].resetFields();
          this.showConfirmAppModal = true
        })
      },
      ok(){
        if(this.opModel.id){
            updateApp.call(this)
        }else {
            addApp.call(this)
        }
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
