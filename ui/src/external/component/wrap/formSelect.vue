<template>
  <Form
    ref="model"
    :model="soul.model.value"
    :label-width="120"
    :show-message="true"
    :rules="ruleValidate">
    <Form-item
      v-if="soul.model.required.value"
      :prop="soul.model.prop.value"
      :label="soul.model.label.value">
      <Select
        v-model="soul.model.value.value">
        <Option
          v-for="item in soul.model.items.value"
          :value="item.value"
          :key="item.id">
          {{ item.label }}
        </Option>
      </Select>
    </Form-item>
    <Form-item
      v-else
      :label="soul.model.label.value">
      <Select
        v-model="soul.model.value.value">
        <Option
          v-for="item in soul.model.items.value"
          :value="item.value"
          :key="item.id">
          {{ item.label }}
        </Option>
      </Select>
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
    watch:{
      'soul.model.value.value'(n){
        //v-if will reset get/set of model value
        resetSoul(this.soul)
        this.soul.model.value.value = n
      },
      'soul.model.required.value'(n){
        //v-if will reset get/set of model value
        resetSoul(this.soul)
        this.soul.model.required.value = n
      }
    },data: function () {
      return {
        ruleValidate: {
          value: [
            {required: true, message:'cant be empty', trigger: 'change'}
          ]
        }
      }
    }
  }
</script>
