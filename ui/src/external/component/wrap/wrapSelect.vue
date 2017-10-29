<template>
  <Select
    :disabled="disabled"
    :filterable="filterable"
    :clearable="clearable"
    @on-change="change"
    @on-query-change="onQueryChange"
    v-model="model">
    <Option v-for="item in items" :value="item.value" :key="item.value">{{ item.label }}</Option>
  </Select>
</template>
<script>
  export default{
    name: 'WrapSelect',
    props: {
      items: [Array],
      disabled:{
        type:Boolean,
        default:false
      },
      filterable:{
        type:Boolean,
        default:true
      },
      clearable:{
        type:Boolean,
        default:true
      }
    },
    methods: {
      change(n){
        this.$emit('on-change', n);
      },
      onQueryChange(n){
        this.$emit('on-query-change', n);
      }
    },
    data(){
      return {
        model: ''
      }
    }
  }
</script>
