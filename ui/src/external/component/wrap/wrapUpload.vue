<template>
  <Upload :action="actionUrl">
    <Button type="ghost" icon="ios-cloud-upload-outline">upload</Button>
  </Upload>
</template>
<script>
  export default {
    name:'WrapUpload',
    props:{
      actionUrl:[String]
    }
  }
</script>
