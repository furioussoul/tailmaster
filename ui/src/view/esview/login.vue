<template>
  <Card style="width:350px" class="loginPanel">
    <p slot="title">esview</p>
    <Form ref="auth" :model="userInfo" :label-width="80">
      <FormItem label="username">
        <Input v-model="userInfo.userName"></Input>
      </FormItem>
      <FormItem label="password">
        <Input v-model="userInfo.password"></Input>
      </FormItem>
      <div v-if="isRegister">
        <FormItem label="confirm password">
          <Input v-model="userInfo.passwordConfirm"></Input>
        </FormItem>
        <FormItem label="profession">
          <Input v-model="userInfo.profession"></Input>
        </FormItem>
        <FormItem>
          <a @click="toggle">go login?</a>
          <Button type="primary" @click="register">register</Button>
        </FormItem>
      </div>
      <div v-else>
        <FormItem>
          <a @click="toggle">go register?</a>
          <Button type="primary" @click="login">login</Button>
        </FormItem>
      </div>
    </Form>
  </Card>
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
        login.call(this)
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
    margin: 0 auto;
    margin-top: 200px;
  }
</style>
