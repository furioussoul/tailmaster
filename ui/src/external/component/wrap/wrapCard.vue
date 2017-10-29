<template>
  <Card :bordered="bordered"
        :dis-hover="disHover"
        :shadow="shadow"
        :padding="padding">
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
    props: {
      showHead: false,
      showExtra: false,
      bordered:{
        type:Boolean,
        default:true
      },
      disHover:{
        type:Boolean,
        default:false
      },
      shadow:{
        type:Boolean,
        default:false
      },
      padding:{
        type:Number,
        default:16
      }
    }
  }
</script>
