<template>
  <Div>
    <Row type="null">
      <Col span="12">
      <Form labelWidth="100">
        <FormItem label="test">
          <Input disabled="false" style="width:200px">
          </Input>
        </FormItem>
        <FormItem label="test">
          <TimePicker>
          </TimePicker>
        </FormItem>
        <FormItem label="test">
          <DatePicker format="yyyy-MM-dd" type="date">
          </DatePicker>
        </FormItem>
        <FormItem label="test">
          <Button name="提交" type="primary"></Button>
        </FormItem>
      </Form>
      </Col>
    </Row>
    <Row type="null">
      <Col span="24">
      <Table :data="data" :columns="columns"></Table>
      </Col>
    </Row>
  </Div>
</template>
<script>
  export default {
    data() {
      return {
        "data": [{
          "name": "xm",
          "age": 20,
          "address": "sh"
        }],
        "columns": [{
          "title": "name",
          "key": "name"
        }, {
          "title": "age",
          "key": "age"
        }, {
          "title": "address",
          "key": "address"
        }]
      }
    }
  }
</script>
