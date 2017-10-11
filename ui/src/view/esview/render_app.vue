<template>
    <Render :soul="soul"></Render>
</template>
<script>
  import {
    mapGetters,
    mapMutations
  } from 'vuex'

  import Render from '../../core/render'

  export default{
    name: 'RenderApp',
    props: {
      appId: [String]
    },
    components: {
      Render
    },
    computed: {
      ...mapGetters('dragModule', ['soul'])
    },
    mounted(){

    }
  }
</script>
