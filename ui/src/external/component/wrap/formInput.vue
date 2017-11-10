<template>
  <Form
    :model="soul.model.value"
    :label-width="120"
    :show-message="true"
    :rules="ruleValidate">
    <Form-item
      :required="soul.model.required.value"
      :prop="soul.model.prop.value"
      :label="soul.model.label.value">
      <Input
        v-model="soul.model.value.value">
      </Input>
    </Form-item>
  </Form>
</template>
<script>
  import{
    resetSoul
  } from '../../../core/lifecycle'
  export default{
    name: 'FormInput',
    props: {
      soul: [Object]
    },
    watch: {
      'soul.model.required.value'(n){
        this.soul.model.prop.value = n ?
          'value'
          : ''
      }
    },
    data: function () {
      return {
        model: 'model',
        ruleValidate: {
          value: [
            {required: true, message: 'cant be empty', trigger: 'blur'}
          ]
        }
      }
    }
  }
</script>
