<template>
  <Card>
    <template v-if="showHead" slot="title">
      <slot name="title">
      </slot>
    </template>

    <template v-if="showExtra" slot="extra">
      <slot name="extra">
      </slot>
    </template>

      <slot name="body">
      </slot>
  </Card>
</template>
<script>
  export default{
    name: 'WrapCard',
    props:{
      showHead:false,
      showExtra:false
    }
  }
</script>
