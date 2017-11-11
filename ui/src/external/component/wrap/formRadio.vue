<template>
  <Form
    :model="soul.model.value"
    :label-width="120"
    :show-message="true"
    :rules="ruleValidate">
    <Form-item
      v-show="soul.model.required.value"
      :prop="prop"
      :label="soul.model.label.value">
      <Radio-group v-model="soul.model.value.value"
                   @on-change="radioChange">
        <Radio
          v-for="item in soul.model.items.value"
          :key="item.id"
          :label="item.value">
          {{item.label}}
        </Radio>
      </Radio-group>
    </Form-item>
    <Form-item
      v-show="!soul.model.required.value"
      :label="soul.model.label.value">
      <Radio-group v-model="soul.model.value.value"
                   @on-change="radioChange">
        <Radio
          v-for="item in soul.model.items.value"
          :key="item.id"
          :label="item.value">
          {{item.label}}
        </Radio>
      </Radio-group>
    </Form-item>
  </Form>
</template>
<script>
  import{
    resetSoul
  } from '../../../core/lifecycle'
  export default{
    name: 'FormRadio',
    props: {
      soul: [Object]
    },
    computed: {
      prop(){
        return this.soul.model.required.value
          ? 'value'
          : ''
      }
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
            {required: true, message:'cant be empty', trigger: 'change'}
          ]
        }
      }
    },
    methods:{
      radioChange(){

      }
    }
  }
</script>
