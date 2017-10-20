<template>
  <Row>
    <i-col span="24">
      <Card>
        <div>
          <Form ref="searchForm" :model="searchInput" :label-width="85" inline>
            <Form-item prop="controlName" label="controlName:">
              <Input v-model="searchInput.controlName">
              </Input>
            </Form-item>
            <Form-item prop="controlClass" label="controlClass:">
              <Input v-model="searchInput.controlClass">
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

    <Modal
      v-model="showEditModal"
      title="edit"
      @on-ok="okEdit">
      <i-form :label-width="100">
        <FormItem  label="control class" >
          <Select filterable v-model="opModel.clazzId" size="small" style="width:100px">
            <Option v-for="item in controlClazzes" :value="item.id" :key="item.id">{{ item.name }}</Option>
          </Select>
        </FormItem >
        <FormItem  label="sort">
          <InputNumber v-model="opModel.sort" style="width:100px"></InputNumber>
        </FormItem >
      </i-form>
    </Modal>
  </Row>
</template>
<script>
  import{
      mapGetters
  }from 'vuex'
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
        showEditModal: false,
        opModel: {
          id: '',
          clazzId: '',
          sort:-1
        },
        fn: {
          initFns: [getTableControlList],
          searchFns: [getTableControlList]
        },
        columns: [
          {
            title: 'name',
            key: 'name'
          },
          {
            title: 'class',
            key: 'clazzName'
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
                      this.code(params)
                    }
                  }
                }, 'code'),
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
          pageSize: 10,
          total: 0,
          controlName: '',
          controlClass: ''
        }
      }
    },
    computed:{
      ...mapGetters('userModule',['controlClazzes'])
    },
    methods: {
      reset(name){
        this.$refs[name].resetFields();
      },
      hrefAdd(){
        this.$router.push('./operate_control')
      },
      code(param){
        this.$router.push({path: './operate_control', query: {id: param.row.id}})
      },
      edit(param){
        this.opModel.id = param.row.id
        if(param.row.clazzId !== null){
          this.opModel.clazzId = param.row.clazzId
        }else {
          this.opModel.clazzId = ''
        }

        if(param.row.sort !== null){
          this.opModel.sort = param.row.sort
        }else {
          this.opModel.sort = ''
        }
        this.showEditModal = true
      },
      okEdit(){
        updateControl.call(this)
        this.showEditModal = false
      },
      del(param){
//        delControl.call(this,param.row.id)
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
