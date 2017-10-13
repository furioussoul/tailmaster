<template>
  <Card>
    <div slot="title">
      <slot name="drop-title">
      </slot>
    </div>
    <div slot="extra">
      <slot name="drop-extra">
      </slot>
    </div>
    <slot name="drop-body">
    </slot>
  </Card>
</template>
<script>
  export default{
    name:'WrapCard'
  }
</script>
