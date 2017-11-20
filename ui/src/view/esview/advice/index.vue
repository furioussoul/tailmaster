<template>
  <Row>
    <i-col span="24">
      <Card>
          <Form :model="data" :label-width="100" >
            <Form-item label="advise(建议):">
              <Input type="textarea" :rows="10" style="width: 600px" v-model="data.content">
              </Input>
            </Form-item>
            <Form-item>
              <ButtonGroup>
                <Button style="float: right" @click="add()" type="ghost">submit</Button>
              </ButtonGroup>
            </Form-item>
          </Form>
      </Card>
    </i-col>
  </Row>
</template>
<script>
  import {
    addAdvice
  } from '../../../resource/index'

  export default{
    name: 'advice',
    data() {
      return {
        data: {
          content: ''
        }
      }
    },
    methods: {
      add(){
        addAdvice.call(this)
      }
    }
  }

</script>

<style scoped>

  .demo-upload-list img {
    width: 100%;
    height: 100%;
  }

  .member-container li {
    float: left;
    margin-right: 40px;
    margin-bottom: 10px;
  }
</style>
