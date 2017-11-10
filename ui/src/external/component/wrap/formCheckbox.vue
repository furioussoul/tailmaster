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
      <CheckboxGroup  v-model="soul.model.value.value">
        <Checkbox
          v-for="item in soul.model.items.value"
          :key="item.id"
          :label="item.value">
          {{item.label}}
        </Checkbox>
      </CheckboxGroup >
    </Form-item>
  </Form>
</template>
<script>
  import{
    resetSoul
  } from '../../../core/lifecycle'
  export default{
    name: 'FormCheckbox',
    props: {
      soul: [Object]
    },
    watch:{
      'soul.model.required.value'(n){
        this.soul.model.prop.value = n ?
          'value'
          : ''
      }
    },
    data: function () {
      return {
        model:'model',
        ruleValidate: {
          value: [
            { required: true, type: 'array', min: 1, message: 'at least 1', trigger: 'change' },
            { type: 'array', max: 2, message: 'at most 2', trigger: 'change' }
          ]
        }
      }
    },
    methods:{

    }
  }
</script>
