<template>
  <Div>
    <Row>
      <Col span="24">
      <Switch>
      </Switch>
      <Switch>
      </Switch>
      <Switch>
      </Switch>
      </Col>
    </Row>
    <Row>
      <Col span="24">
      <AutoComplete style="width:200px">
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
      </AutoComplete>
      </Col>
    </Row>
    <Row>
      <Col span="24">
      <Slider>
      </Slider>
      </Col>
    </Row>
    <Row>
      <Col span="24">
      <Transfer :data="data" :targetKeys="targetKeys">
      </Transfer>
      </Col>
    </Row>
    <Row>
      <Col span="12">
      <Cascader :data="data">
      </Cascader>
      </Col>
    </Row>
    <Row>
      <Col span="12">
      <Rate>
      </Rate>
      </Col>
    </Row>
  </Div>
</template>
<script>
  export default {
    data() {
      return {
        "data": [{
          "value": "beijing",
          "label": "北京",
          "children": [{
            "value": "gugong",
            "label": "故宫",
            "__label": "北京 / 故宫",
            "__value": "beijing,gugong"
          }, {
            "value": "tiantan",
            "label": "天坛",
            "__label": "北京 / 天坛",
            "__value": "beijing,tiantan"
          }, {
            "value": "wangfujing",
            "label": "王府井",
            "__label": "北京 / 王府井",
            "__value": "beijing,wangfujing"
          }]
        }, {
          "value": "jiangsu",
          "label": "江苏",
          "children": [{
            "value": "nanjing",
            "label": "南京",
            "children": [{
              "value": "fuzimiao",
              "label": "夫子庙",
              "__label": "江苏 / 南京 / 夫子庙",
              "__value": "jiangsu,nanjing,fuzimiao"
            }]
          }, {
            "value": "suzhou",
            "label": "苏州",
            "children": [{
              "value": "zhuozhengyuan",
              "label": "拙政园",
              "__label": "江苏 / 苏州 / 拙政园",
              "__value": "jiangsu,suzhou,zhuozhengyuan"
            }, {
              "value": "shizilin",
              "label": "狮子林",
              "__label": "江苏 / 苏州 / 狮子林",
              "__value": "jiangsu,suzhou,shizilin"
            }]
          }]
        }],
        "targetKeys": ["1", "2"]
      }
    }
  }
</script>
