<template>
  <div>
    <Table border
           :loading="loading"
           :columns="columns"
           :data="data"
           :stripe="true"
           size="small">
    </Table>
    <Row style="margin-top:15px;">
      <Page style="float:right;"
            :current="current"
            :page-size="pageSize"
            :total="total"
            show-total
            @on-change="change">
      </Page>
    </Row>
  </div>

</template>
<script>
  export default{
    name: 'WrapTable',
    props:{
      columns:[Array],
      data:[Array],
      current:[Number],
      'pageSize':[Number],
      total:[Number],
      loading: {
        type: Boolean,
        default: false
      }
    },
    data(){
        return {}
    },
    methods:{
        'change'(e){
            this.$emit('on-change',e)
        }
    }
  }
</script>
