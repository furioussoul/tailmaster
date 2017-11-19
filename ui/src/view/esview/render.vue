<template>
  <div  class="render-container">
    <h3 slot="title">{{pageName}}</h3>
    <RenderDev style="    margin-top: 50px;" :soul="soul"></RenderDev>
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
    data(){
        return {
          pageName:''
        }
    },
    mounted(){
      getRichPage.call(this, this.$route.query.pageSoulId, (data) => {
        this.pageName = data.name
        let ancestorSoul = parse(data.pageSoul)
        this.setSoul(ancestorSoul)
      })
    }
  }
</script>
<style lang="less" scoped>
  .render-container{
    padding: 20px;
  }

</style>
