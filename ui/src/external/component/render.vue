<template>
  <Render :soul="soul"></Render>
</template>
<script>
  import render from './render'
  import {
    mapGetters,
    mapMutations
  } from 'vuex'

  export default{
    name: 'renderVue',
    components: {
      render
    },
    computed: {
      ...mapGetters('soulModule', ['soul'])
    },
  }
</script>
