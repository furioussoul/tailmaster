<template>
  <Upload :action="actionUrl"
          :show-upload-list='true'
          :on-success="onSuccess"
          :on-remove="onRemove"
          multiple
          type="drag">
    <div style="padding: 20px 0">
      <Icon type="ios-cloud-upload" size="52" style="color: #3399ff"></Icon>
      <p>{{name}}</p>
    </div>
  </Upload>
</template>
<script>
  export default {
    name:'WrapUpload',
    props:{
      actionUrl:[String],
      name:{
        type:String,
        default:'点击或将文件拖拽到这里上传'
      }
    },
    methods:{
      onSuccess(response, file, fileList){
        this.$emit('on-success',response,file,fileList)
      },
      onRemove(response, file, fileList){
        this.$emit('on-remove',response,file,fileList)
      }
    }
  }
</script>
