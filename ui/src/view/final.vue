<template>
  <div class="final-panel">
    <RenderFinalControl
      :soul="soul">
    </RenderFinalControl>
  </div>
</template>
<script>
  import {
    mapGetters,
    mapActions,
    mapMutations
  } from 'vuex'
  export default{
    computed: {
      ...mapGetters('dragModule',
        [
          'soul'
        ])
    }
  }
</script>
<style lang="less" scoped>
  .final-panel{
    margin: 100px auto;
    width:600px;
    height: 600px;
  }
</style>
