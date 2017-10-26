<template>
  <Card>
    <div slot="title">App List</div>
    <div class="app-flow-container">
      <div class="app-flow"
           v-for="app in apps"
           key="app.sort">
        <Card>
          <a @click="openApp(app)">{{app.name}}</a>
        </Card>
      </div>
    </div>
  </Card>
</template>
<script>
  import{getAppList}from'../../../resource/assemble_resource'
  export default{
    name: 'Home',
    data(){
      return {
        apps: []
      }
    },
    methods: {
      openApp(app){
        if (app.url && app.url.indexOf('http')) {
          app.url = 'http://' + app.url
        }
        window.open(app.url)
      }
    },
    mounted(){
      getAppList.call(this, {}, (data) => {
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
    flex-basis: 20%;
    text-align: center;

    &:last-child{
      margin-right: 0;
    }
  }


</style>
