<template>
  <Card>
    <div slot="title">App List</div>
    <div class="app-flow-container">
      <div
        @click="openApp(app)"
        class="app-flow"
        v-for="app in apps"
        key="app.id">
        <Card>
          <a>{{app.name}}</a>
        </Card>
      </div>
    </div>
  </Card>
</template>
<script>
  import{getPageList}from'../../../resource/assemble_resource'
  export default{
    name: 'Home',
    data(){
      return {
        apps: []
      }
    },
    methods: {
      openApp(app){
        this.$router.push({path: 'render', query: {pageSoulId: app.id}})
      }
    },
    mounted(){
      getPageList.call(this, {all:true}, (data) => {
        this.apps = data
      })
    }
  }
</script>
<style scoped lang="less">
  .app-flow-container {
    display: flex;
    flex-wrap: wrap;
    justify-content: flex-start;
    align-content: flex-start;
  }

  .app-flow {
    margin-right: 50px;
    flex-basis: 10%;
    text-align: center;
    cursor: pointer;
    &:last-child {
      margin-right: 0;
    }
  }
</style>
