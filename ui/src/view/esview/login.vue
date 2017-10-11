<template>
  <Card style="width:350px" class="loginPanel">
    <p slot="title">esview</p>
    <Form :model="formItem" :label-width="80">
      <FormItem label="username">
        <Input v-model="formItem.userName" placeholder="enter..."></Input>
      </FormItem>
      <FormItem label="password">
        <Input v-model="formItem.password" placeholder="enter..."></Input>
      </FormItem>
      <div v-if="register">
        <FormItem label="confirm password">
          <Input v-model="formItem.passwordConfirm" placeholder="enter..."></Input>
        </FormItem>
        <FormItem label="profession">
          <Input v-model="formItem.profession" placeholder="enter..."></Input>
        </FormItem>
        <FormItem>
          <a @click="toggle">go login?</a>
          <Button type="primary">register</Button>
        </FormItem>
      </div>
      <div v-else>
        <FormItem>
          <a @click="toggle">go register?</a>
          <Button type="primary">login</Button>
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
        register: false,
        formItem: {
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
          this.register = !this.register
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
