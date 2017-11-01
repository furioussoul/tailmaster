<template>
  <Div>
    <Row>
      <Col>
      <Form>
        <FormItem>
          <Input disabled="false" style="width:200px">
          </Input>
        </FormItem>
        <FormItem>
          <TimePicker>
          </TimePicker>
        </FormItem>
        <FormItem>
          <DatePicker format="yyyy-MM-dd" type="date">
          </DatePicker>
        </FormItem>
        <FormItem>
          <Button type="primary"></Button>
        </FormItem>
      </Form>
      </Col>
    </Row>
    <Row>
      <Col>
      <Table></Table>
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
