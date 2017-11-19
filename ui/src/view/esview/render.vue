<template>
  <Div>
    <Form labelWidth="100">
      <Row type="flex">
        <Col span="12">
        <FormItem label="label">
          <Input v-model="modelInput">
          </Input>
        </FormItem>
        <FormItem label="label">
          <Button type="primary">提交</Button>
        </FormItem>
        </Col>
      </Row>
    </Form>
    <Table></Table>
    <Page current="1" pageSize="10" total="100"></Page>
  </Div>
</template>
<script>
  export default {
    data() {
      return {
        "modelInput": "",
        "columns": [{
          "title": "Name",
          "key": "name"
        }, {
          "title": "Age",
          "key": "age"
        }, {
          "title": "Address",
          "key": "address"
        }],
        "data": [{
          "name": "John Brown",
          "age": 18,
          "address": "New York No. 1 Lake Park",
          "date": "2016-10-03"
        }, {
          "name": "Jim Green",
          "age": 24,
          "address": "London No. 1 Lake Park",
          "date": "2016-10-01"
        }, {
          "name": "Joe Black",
          "age": 30,
          "address": "Sydney No. 1 Lake Park",
          "date": "2016-10-02"
        }, {
          "name": "Jon Snow",
          "age": 26,
          "address": "Ottawa No. 2 Lake Park",
          "date": "2016-10-04"
        }]
      }
    }
  }
</script>
