<template>
  <div class="login-background">
    <Card class="noticePanel">
      <span slot="title" style="color: red;">提示：</span>
      用户名: 33，密码 : 1,</br>
      Username : 33 Password : 1
      </br>
    </Card>
    <Card class="loginPanel">
      <p slot="title">esview</p>
      <Form ref="auth" :model="userInfo" :label-width="80">
        <FormItem label="username">
          <Input v-model="userInfo.userName" @keyup.13.native="login"></Input>
        </FormItem>
        <FormItem label="password">
          <Input type="password" v-model="userInfo.password" @keyup.13.native="login"></Input>
        </FormItem>
        <div v-if="isRegister">
          <FormItem label="confirm password">
            <Input type="password" v-model="userInfo.passwordConfirm" @keyup.13.native="register"></Input>
          </FormItem>
          <FormItem label="profession">
            <Input v-model="userInfo.profession" @keyup.13.native="register"></Input>
          </FormItem>
          <FormItem>
            <Button class="authButton" type="primary" @click="register">register</Button>
          </FormItem>
          <FormItem>
            <a @click="toggle">go login?</a>
          </FormItem>
        </div>
        <div v-else>
          <FormItem>
            <Button class="authButton" type="primary" @click="login">login</Button>
          </FormItem>
          <FormItem>
            <a @click="toggle">go register?</a>
          </FormItem>
        </div>
      </Form>
    </Card>
  </div>
</template>
<script>
  import {
    register,
    login
  }from '../../resource'
  export default {
    name: 'Login',
    data () {
      return {
        isRegister: false,
        userInfo: {
          userName: '',
          password: '',
          passwordConfirm: '',
          profession: ''
        }
      }
    },
    methods: {
      register(){
        register.call(this)
      },
      login(){
        if (this.isRegister) {
          register.call(this)
        } else {
          login.call(this)
        }
      },
      toggle(){
        this.$refs['auth'].resetFields();
        this.isRegister = !this.isRegister
      }
    }
  }
</script>
<style scoped>
  .loginPanel {
    width: 350px;
    top: 15%;
    margin: 0 auto;
  }

  .login-background {
    width: 100%;
    height: 100%;
  }

  .authButton {
    width: 100%;
  }
</style>
