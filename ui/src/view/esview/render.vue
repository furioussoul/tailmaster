<template>
  <Div>
    <Form labelWidth="100">
      <Row type="flex">
        <Col span="10">
        <FormItem label="input">
          <Input v-model="modelInput">
          </Input>
        </FormItem>
        <FormItem label="number">
          <InputNumber v-model="modelInput" max="10" min="0">
          </InputNumber>
        </FormItem>
        <FormItem label="date">
          <DatePicker v-model="modelDatePicker" type="date" format="yyyy-MM-dd HH:mm:ss">
          </DatePicker>
        </FormItem>
        <FormItem>
          <Button type="primary">提交</Button>
        </FormItem>
        </Col>
      </Row>
    </Form>
    <Row type="flex">
      <Col span="24">
      <Tabs>
        <TabPane label="tab1">
          <Table></Table>
          <Row type="flex">
            <Col span="10"> </Col>
            <Col span="14">
            <Page current="1" pageSize="10" total="100"></Page>
            </Col>
          </Row>
        </TabPane>
        <TabPane label="tab2">
          <Table></Table>
        </TabPane>
      </Tabs>
      </Col>
    </Row>
  </Div>
</template>
<script>
  export default {
    data() {
      return {
        "modelInput": "",
        "modelDatePicker": "",
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
