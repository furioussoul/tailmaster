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
  import {mapGetters, mapMutations} from 'vuex'
  import {
    addPage,
    delPage,
    updatePage,
    getPageList,
    getTablePageList,
    getRichPage
  } from '../../../resource/assemble_resource'
  import{
    paginationMixin
  } from '../../../mixin/m_pagination'
  export default{
    name: 'ManagePage',
    mixins: [paginationMixin],
    props: {
      refresh: [String, Number]
    },
    watch: {
      refresh(n){
        this.searchInput.appId = localStorage.getItem('appId')
          getTablePageList.call(this)
      }
    },
    data() {
      return {
        fn: {
          initFns: [],
          searchFns: [getTablePageList]
        },
        columns: [
          {
            title: 'PageId',
            key: 'id'
          },
          {
            title: 'PageName',
            key: 'name',
            width:150
          },
          {
            title: 'action',
            key: 'action',
            width: 200,
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
        loading:false,
        tableData: [],
        searchInput: {
          pageNum: 1,
          pageSize: 20,
          total: 0,
          name: ''
        }
      }
    },
    methods: {
      ...mapMutations('dragModule', ['setSoul']),
      reset(name){
        this.$refs[name].resetFields();
      },
      hrefAdd(){
        localStorage.setItem('pageSoulId','')
        this.$router.push({path: './assemble_page', query: {appId: this.$route.query.appId}})
      },
      edit(param){
        this.setSoul(null)
        localStorage.setItem('pageSoulId', param.row.id)
        this.$router.push({path: './assemble_page', query: {pageSoulId: param.row.id}})
      },
      del(param){
        delPage.call(this,param.row.id)
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
