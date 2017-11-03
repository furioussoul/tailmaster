<template>
  <Form
    :ref="model"
    :model="soul.model.value"
    :label-width="120"
    :show-message="true"
    :rules="ruleValidate">
    <Form-item
      v-if="soul.model.required.value"
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
    <Form-item
      v-else
      :label="soul.model.label.value">
      <CheckboxGroup v-model="soul.model.value.value">
        <Checkbox
          v-for="item in soul.model.items.value"
          :key="item.id"
          :label="item.value">
          {{item.label}}
        </Checkbox>
      </CheckboxGroup>
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
        //v-if will reset get/set of model value
        resetSoul(this.soul)
        this.soul.model.required.value = n
      }
    },
    data: function () {
      return {
        model:'model',
        ruleValidate: {
          value: [
            { required: true, type: 'array', min: 1, message: '至少选择一个爱好', trigger: 'change' },
            { type: 'array', max: 2, message: '最多选择两个爱好', trigger: 'change' }
          ]
        }
      }
    },
    methods:{

    }
  }
</script>
