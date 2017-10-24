<template>
    <Render :soul="soul"></Render>
</template>
<script>
  import {
    mapGetters,
    mapMutations
  } from 'vuex'

  import Render from '../../component/render_dev'

  export default{
    name: 'RenderApp',
    props: {
      pageId: [String]
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
