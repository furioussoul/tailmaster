<template>
  <div class="soul-drop-panel" :class="wrapClasses">
    <Render :dev="true" :soul="soul"></Render>
  </div>
</template>
<script>

  import Render from '../core/render'

  import {
    initDropEvents
  } from '../core/dnd'

  import {
    mapGetters,
    mapMutations
  } from 'vuex'

  export default{
    name: 'DropPanel',
    components: {
      Render
    },
    props: {
      isPreview: {
        type: Boolean,
        default: false
      }
    },
    computed: {
      wrapClasses(){
        return [{'is-preview': this.isPreview}]
      },
      ...mapGetters('dragModule', ['soul'])
    },
    methods:{
      ...mapMutations('dragModule', ['setSoul'])
    },
    mounted(){
      this.$el.control = {
        id: 1,
        name: 'DropPanel',
        nickname: '初始面板',
        desc: '描述',
        allow: [],
        children: [],
        model: {
          type: 'Div',
          className: {
            type: 'text',
            value: '123',
            desc: 'class'
          }
        },
        script: function (eventCenter) {

        },
        render: function (createElement) {
          const context = this
          return createElement(context.type, {
            props: {
              'class-name': context.model.className.value
            }
          }, context.children.map(function (child) {
            return child.render(createElement)
          }))
        }
      }
      this.setSoul(this.$el.control)
      initDropEvents(this.$el)
    }
  }
</script>
