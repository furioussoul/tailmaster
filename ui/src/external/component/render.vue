<template>
  <Render :soul="soul"></Render>
</template>
<script>
  import Render from './render'
  import store from '../store'

  export default{
    name: 'renderVue',
    components: {
      Render
    },
    data() {
        return {
          soul:store.getters['soulModule/soul']
        }
    },
    mounted(){
      console.log(this.soul)
    }
  }
</script>
