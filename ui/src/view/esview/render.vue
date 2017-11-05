<template>
  <div class="render-container">
    <RenderDev :soul="soul"></RenderDev>
  </div>

</template>
<script>
  import {
    getRichPage
  } from '../../resource/assemble_resource'
  import{
      parse
  }from'../../util/assist'
  import {mapGetters, mapMutations, mapActions} from 'vuex'
  export default{
    computed: {
      ...mapGetters('dragModule', ['soul']),
    },
    methods:{
      ...mapMutations('dragModule', ['setSoul']),
    },
    mounted(){
      getRichPage.call(this, this.$route.query.pageSoulId, (data) => {
        let ancestorSoul = parse(data.pageSoul)
        this.setSoul(ancestorSoul)
      })
    }
  }
</script>
<style scoped>
  .render-container{
    padding: 20px;
  }
</style>
