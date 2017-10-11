<template>
</template>
<script>
  export default{
    name: 'Test',
    data(){
      return {
        props: {
          name: 'Row',
          nickname: '横',
          desc: '描述',
          leaf: [2],
          model: {
            ex: {
              name: 'width',
              type: 'text',
              def: 'primary',
              desc: '宽度'
            }
          },
          script: function(eventCenter){

          },
          render: function (createElement) {

            return ``;
          }
        }
      }
    }
  }
</script>
