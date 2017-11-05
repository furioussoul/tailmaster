<template>
  <Form
    :model="soul.model.value"
    :label-width="120"
    :show-message="true"
    :rules="ruleValidate">
    <Form-item
      v-if="soul.model.required.value"
      :prop="soul.model.prop.value"
      :label="soul.model.label.value">
      <InputNumber
        :max="soul.model.max.value"
        :min="soul.model.min.value"
        v-model="soul.model.value.value">
      </InputNumber >
    </Form-item>
    <Form-item
      v-else
      :label="soul.model.label.value">
      <InputNumber
        :max="soul.model.max.value"
        :min="soul.model.min.value"
        v-model="soul.model.value.value">
      </InputNumber >
    </Form-item>
  </Form>
</template>
<script>
  import{
    resetSoul
  } from '../../../core/lifecycle'
  export default{
    name: 'FormNumber',
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
        ruleValidate: {
          value: [
            {required: true, message:'cant be empty', trigger: 'blur'}
          ]
        }
      }
    }
  }
</script>
