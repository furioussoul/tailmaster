<template>
  <Div>
    <Card showHead="true" showExtra="true">
      <Div>
        <p text="1231231" style="padding:10px;minHeight:30px"></p>
      </Div>
      <Div>
        <Icon type="refresh"></Icon>
      </Div>
      <Div>
        <Table :data="data" :columns="columns" style=""></Table>
      </Div>
    </Card>
    <Row>
      <Col span="12">
      <Form labelWidth="100" style="">
        <FormItem label="name" style="" prop="name">
          <Input style="width:200px">
          </Input>
        </FormItem>
        <FormItem label="gender" style="">
          <RadioGroup value="-1" style="">
            <Radio label="apple">
            </Radio>
            <Radio label="apple">
            </Radio>
          </RadioGroup>
        </FormItem>
        <FormItem label="interest" style="" prop="name">
          <CheckboxGroup :value="value" style="">
            <Checkbox label="facebook">
            </Checkbox>
            <Checkbox label="facebook">
            </Checkbox>
          </CheckboxGroup>
        </FormItem>
        <FormItem label="date" style="" prop="name">
          <DatePicker format="yyyy-MM-dd" type="date">
          </DatePicker>
          <DatePicker format="yyyy-MM-dd" type="date">
          </DatePicker>
        </FormItem>
        <FormItem label="time" style="" prop="name">
          <TimePicker>
          </TimePicker>
        </FormItem>
        <FormItem style="" prop="name">
          <Button name="search" type="primary"></Button>
        </FormItem>
      </Form>
      </Col>
    </Row>
    <Row>
      <Col span="24">
      <Table :data="data" :columns="columns" style=""></Table>
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
        }],
        "value": []
      }
    }
  }
</script>
