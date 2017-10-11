<template>
  <div>
    <slot></slot>
  </div>
</template>
<script>

  import {
    initDropEvents
  } from '../core/dnd'

  export default{
    name: 'Drop',
    mounted(){
      initDropEvents(this.$el)
    }
  }
</script>
