<template>
  <Form
    :model="soul.model.value"
    :label-width="120"
    :show-message="true"
    :rules="ruleValidate">
    <Form-item
      :prop="soul.model.prop.value"
      :required="soul.model.required.value"
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
        this.soul.model.prop.value = n ?
          'value'
          : ''
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
