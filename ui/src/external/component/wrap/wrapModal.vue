<template>
  <Modal
    v-model="showModal"
    :title="title"
    @on-ok="ok"
    @on-cancel="cancel">
    <Button type="ghost" icon="ios-cloud-upload-outline">上传文件</Button>
  </Modal>
</template>
<script>
  export default {

  }
</script>
