<template>
  <Row>
    <i-col span="24">
      <Card>
        <div>
          <Form ref="searchForm" :model="searchInput" :label-width="80" inline>
            <Form-item prop="name" label="PageName:">
              <Input v-model="searchInput.name">
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
  </Row>
</template>
<script>
  import {mapGetters, mapMutations} from 'vuex'
  import {
    addPage,
    delPage,
    updatePage,
    getPageList,
    getTablePageList,
    getRichPage,
    getTableAppList
  } from '../../../resource/assemble_resource'
  import{
    paginationMixin
  } from '../../../mixin/m_pagination'
  export default{
    name: 'ManagePage',
    mixins: [paginationMixin],
    data() {
      return {
        fn: {
          initFns: [getTablePageList],
          searchFns: [getTablePageList]
        },
        columns: [
          {
            title: 'PageId',
            key: 'id',
            width: 150
          },
          {
            title: 'PageName',
            key: 'name',
          },
          {
            title: 'updateBy',
            key: 'updateBy',
          },
          {
            title: 'updateDt',
            key: 'updateDt',
          },
          {
            title: 'action',
            key: 'action',
            width: 200,
            align: 'center',
            render: (h, params) => {
              let layout = h('Button', {
                  props: {
                    type: 'primary',
                    size: 'small'
                  },
                  on: {
                    click: () => {
                      this.edit(params)
                    }
                  }
                }, 'layout'),
                del = this.me.username === params.row.createBy && h('Button', {
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
              return h('ButtonGroup', [layout,del ]);
            }
          }
        ],
        loading: false,
        tableData: [],
        searchInput: {
          pageNum: 1,
          pageSize: 10,
          total: 0,
          name: ''
        }
      }
    },
    computed:{
      ...mapGetters('userModule', ['me']),
    },
    methods: {
      ...mapMutations('dragModule', ['setSoul']),
      reset(name){
        this.$refs[name].resetFields();
      },
      hrefAdd(){
        this.$router.push({path: './assemble_page'})
      },
      edit(param){
        this.$router.push({path: './assemble_page', query: {pageSoulId: param.row.id}})
      },
      del(param){
        delPage.call(this, param.row.id)
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
