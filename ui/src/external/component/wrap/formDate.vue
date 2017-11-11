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
      <Date-picker
        @on-change="dateChange"
        :format="soul.model.format.value"
        :type="soul.model.type.value"
        :value="soul.model.value.value">
      </Date-picker>
    </Form-item>
    <Form-item
      v-show="!soul.model.required.value"
      :label="soul.model.label.value">
      <Date-picker
        @on-change="dateChange"
        :format="soul.model.format.value"
        :type="soul.model.type.value"
        :value="soul.model.value.value">
      </Date-picker>
    </Form-item>
  </Form>
</template>
<script>
  import{
    resetSoul
  } from '../../../core/lifecycle'
  export default{
    name: 'FormDate',
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
    data() {
      return {
        ruleValidate: {
          value: [
            {required: true, message:'cant be empty', trigger: 'change'}
          ]
        }
      }
    },
    methods:{
      dateChange(date) {
        this.soul.model.value.value = date
      }
    }
  }
</script>
