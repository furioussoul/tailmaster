<template>
  <Div>
    <Row style="margin:20px">
      <Col span="24">
      <Form labelWidth="120">
        <FormItem label="input">
          <Input disabled="false" style="width:200px">
          </Input>
        </FormItem>
        <FormItem label="time">
          <TimePicker>
          </TimePicker>
        </FormItem>
        <FormItem label="date">
          <DatePicker format="yyyy-MM-dd" type="date">
          </DatePicker>
        </FormItem>
        <FormItem label="test">
          <CheckboxGroup :value="value">
            <Checkbox label="facebook">
            </Checkbox>
            <Checkbox label="facebook">
            </Checkbox>
          </CheckboxGroup>
        </FormItem>
        <FormItem label="test">
          <RadioGroup value="-1">
            <Radio label="apple">
            </Radio>
            <Radio label="apple">
            </Radio>
          </RadioGroup>
        </FormItem>
        <FormItem label="file">
          <Upload style="width:200px"></Upload>
        </FormItem>
        <FormItem label="select">
          <Select style="width:200px" filterable="false" disabled="false" clearable="false">
            <Option value="beijing" label="北京市">
              <span>北京</span>
              <span style="float:rightcolor:#ccc">Beiing</span>
            </Option>
            <Option value="shanghai" label="上海市">
              <span>上海</span>
              <span style="float:right;color:#ccc">ShangHai</span>
            </Option>
            <Option value="shenzhen" label="深圳市">
              <span>深圳</span>
              <span style="float:right;color:#ccc">ShenZhen</span>
            </Option>
          </Select>
        </FormItem>
        <FormItem>
          <Button type="primary">提交</Button>
        </FormItem>
      </Form>
      </Col>
    </Row>
    <Row>
      <Col span="24">
      <Tabs>
        <TabPane label="tab1">
          <Table :data="data" :columns="columns"></Table>
        </TabPane>
        <TabPane label="tab2">
          <Table :data="data" :columns="columns"></Table>
        </TabPane>
        <TabPane label="tab3">
          <Table :data="data" :columns="columns"></Table>
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
        "value": [],
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
