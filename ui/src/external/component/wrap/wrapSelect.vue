<template>
  <Select @on-change="change" v-model="model">
    <Option v-for="item in items" :value="item.value" :key="item.value">{{ item.label }}</Option>
  </Select>
</template>
<script>
  export default{
    name: 'WrapSelect',
    props:{
      items:[Array]
    },
    methods:{
      change(n){
        this.$emit('on-change', n);
      }
    },
    data(){
      return {
          model:''
      }
    }
  }
</script>
