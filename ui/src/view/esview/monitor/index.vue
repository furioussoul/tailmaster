<template>
  <div>
    <Card class="loginPanel">
      <Tabs value="tab.name">
        <TabPane label="扫码登陆" name="tab1">
          <div v-show="!core.uuid" class="img">
            <Button class="refresh"
                    type="ghost"
                    size="large"
                    shape="circle"
                    @click="refreshQrCode">
              刷新二维码
            </Button>
          </div>
          <div class="imgContainer" v-show="core.uuid">
            <img class="img" :src="'https://login.weixin.qq.com/qrcode/'+core.uuid"/>
            <Button class="refresh"
                    type="ghost"
                    size="large"
                    shape="circle"
                    @click="refreshQrCode">
              刷新二维码
            </Button>
          </div>
        </TabPane>
      </Tabs>
    </Card>


    <div class="attention">
      <p>注意点：</p>
      <p>
        1、在扫码、确认登陆及系统获取信息期间，请勿登陆网页版微信及微信的PC客户端，如已登陆请退出
      </p>
      <p>
        2、系统获取信息完毕后，方可再次登陆网页版微信或PC客户端
      </p>
    </div>

  </div>
</template>
<script>
  export default{
    data(){
      return {
        tab: {
          name: 'tab1'
        },
        core: {
          uuid: ''
        }
      }
    },
    methods:{
      refreshQrCode(){
        this.$http.get('./wx/generateUUID').then(res => {
          this.core.uuid = res.data.data
        })
      }
    },
    mounted(){
      this.refreshQrCode()
    }
  }
</script>
<style scoped>

  .attention {
    visibility: hidden;
  }

  .loginPanel {
    position: absolute;
    width: 350px;
    left: 35%;
    top: 35%;
    margin: 0 auto;
  }

  .loginPanel .img {
    width: 315px;
    height: 315px;
  }

  .loginPanel .img .refresh {
    position: absolute;
    top: 35%;
    left: 35%;
  }

  .loginPanel .refresh{
    position: absolute;
    left: 35%;
  }

  .imgContainer{
    height:360px;
  }
</style>
