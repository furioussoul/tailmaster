<template>
  <Row class="model-config">
    <i-col span="24">
      <Card>
        <p slot="title">
          {{pageName}}: Model Config
        </p>
        <i-form label-position="left"
                :label-width="70"
                :model="model">

          <div v-for="(config,index) in configs"
               v-if="config.showConfig === undefined|| config.showConfig"
               :key="index">

            <Form-item v-if="config.type === 'text'"
                       :label="config.name">
              <i-input
                v-model="config.value">
              </i-input>
            </Form-item>

            <Form-item v-if="config.type === 'boolean'"
                       :label="config.name">
              <i-switch size="large" style="margin-left:20px" v-model="config.value">
                <span slot="open">true</span>
                <span slot="close">false</span>
              </i-switch>
            </Form-item>

            <!--<Form-item v-if="config.type === 'select'"-->
            <!--:label="config.name">-->
            <!--<i-Select-->
            <!--v-model="config.value">-->
            <!--<Option v-for="option in config.options"-->
            <!--:value="option"-->
            <!--:key="option">{{ option }}-->
            <!--</Option>-->
            <!--</i-Select>-->
            <!--</Form-item>-->

          </div>

        </i-form>

      <!--  <div style="width: 200px; margin: 15px auto 0;">
          <Button class="tool-button" type="info" @click="save">保存</Button>
        </div>-->
      </Card>
    </i-col>
  </Row>
</template>

<script>


  export default {
    name: 'Editor',
    props: {
      pageName:'',
      editSoul: [Object]
    },
    data(){
      return {
        model: {},
        configs: []
      }
    },
    methods: {
      save(){
        for (let i = 0; i < this.configs.length; i++) {
          let config = this.configs[i]
          this.model[config.name] = config.value
        }
      }
    },
    watch: {
      /**
       * 点击组件时显示编辑窗口
       * @param n
       */
      editSoul(n){
        /**
         * config是model里面的对象引用，所以不需要做双向绑定就能改变value：
         * label: {
              type: 'text',
              value: '测试',
              desc: 'x'
            }
         */
        this.editSoul = n
        let configs = []
        let model = {}
        for (let name in this.editSoul.model) {
          let config = this.editSoul.model[name]
          model[name] = ''
          config.name = name
          configs.push(config)
        }
        this.model = model
        this.configs = configs
      }
    }
  }
</script>
<style lang="less" scoped>
  .model-config {
  }
</style>
