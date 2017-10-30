<template>
  <div class="soul-drag-control">
    <div class="soul-drag-control__preview">
      <slot name="preview"></slot>
    </div>
  </div>
</template>
<script>
  import {
    onDragStart,
    onDrag,
    onDragEnd
  } from '../core/dnd'

  export default{
    name: 'Control',
    props: {
      controlConfig: [Object],
    },
    mounted(){
      this.$el.controlConfig = this.controlConfig
      this.$el.setAttribute('draggable', true)
      this.$el.ondragstart = onDragStart
      this.$el.ondrag = onDrag
      this.$el.ondragend = onDragEnd
    }
  }
</script>
