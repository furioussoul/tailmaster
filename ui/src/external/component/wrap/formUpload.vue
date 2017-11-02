<template>
  <Form
    ref="model"
    :model="soul.model.value"
    :label-width="120"
    :show-message="true"
    :rules="ruleValidate">
    <Form-item
      v-if="soul.model.required.value"
      :label="soul.model.label.value">
      :prop="soul.model.prop.value"
      <Upload
        :on-remove="removeFile"
        :on-success="uploadSuccess"
        :action="uploadUrl">
        <Button type="ghost" icon="ios-cloud-upload-outline">upload</Button>
      </Upload>
    </Form-item>

    <Form-item
      v-else
      :label="soul.model.label.value">
      <Upload
        :on-remove="removeFile"
        :on-success="uploadSuccess"
        :action="uploadUrl">
        <Button type="ghost" icon="ios-cloud-upload-outline">upload</Button>
      </Upload>
    </Form-item>
  </Form>
</template>
<script>
  import{
    resetSoul
  } from '../../../core/lifecycle'
  var commonValid = function (rule, value, callback) {
    //隐藏的不校验
    if (!this.control.isShow) {
      return callback();
    }

    if (!value || value.length == 0) {
      var verifyPrompt = this.control.verifyPrompt;
      return callback(new Error(verifyPrompt ? verifyPrompt : " "));
    } else {
      callback();
    }
  }


  export default{
    name: 'FormInput',
    props: {
      soul: [Object]
    },
    watch:{
      'soul.model.required.value'(n){
        //v-if will reset get/set of model value
        resetSoul(this.soul)
        this.soul.model.required.value = n
      }
    },data: function () {
      return {
        uploadUrl: this.soul.model.uploadUrl.value,
        urlContainer: [],
        ruleValidate: {
          value: [
            {required: true, message:'cant be empty', trigger: 'blur'}
          ]
        }
      }
    },
    methods:{
      removeFile: function (file) {
        var fileIndex = this.urlContainer.indexOf(file);
        this.urlContainer.splice(fileIndex, 1)
        this.soul.model.fileUrls.splice(fileIndex, 1)
      },
      uploadSuccess: function (res, file) {
        if (res.code === 10000) {
          this.soul.model.fileUrls.push(res.data)
          this.urlContainer.push(file)
        }
      },
      valid: commonValid
    }
  }
</script>
