<template>
  <div id="building">
    <div class="login-container">
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>秒杀商城-用户登录</span>
        </div>
        <el-form label-width="80px">
          <el-form-item label="用户名">
            <el-input v-model="username" placeholder="用户名" style="width: 252px;"/>
          </el-form-item>
          <el-form-item label="密码">
            <el-input type="password" v-model="password" placeholder="密码" style="width: 252px;"/>
          </el-form-item>
          <el-form-item >
            <el-button type="primary" style="width: 252px;" @click="submit()">登录</el-button>
          </el-form-item>
          <p class="errorInfo">{{errorInfo}}</p>
        </el-form>
        <div style="text-align: center;">
          <a href="#">版权信息</a> @Copyright
        </div>
      </el-card>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
// @表示src目录 在webpack.base.comf.js中定义了'@': resolve('src'),
import { hex_md5 } from '@/util/md5'

import {genServerUrl} from '@/config/sys'

export default {
  name: 'Login',
  data () { // 注意 默认都是有空格的
    return {
      username: '',
      password: '',
      errorInfo: ''
    }
  },
  methods: {
    submit () {
      if (this.username.trim() == null || this.username === '') {
        this.errorInfo = '用户名不能为空'
        return
      } else {
        this.errorInfo = ''
      }
      if (this.password.trim() == null || this.password === '') {
        this.errorInfo = '密码不能为空'
        return
      } else {
        this.errorInfo = ''
      }
      let salt = "3dfsty";// 要与服务端设置的前端salt保持一致
      let 啊 = genServerUrl("login","user");
      // 发送登录请求
      axios.post(
        啊,
        {
          'username': this.username,
          'password': hex_md5(salt+this.password)
        })
        .then(response => {
          let data = response.data
          if (data.code === 0) {
            console.log(data)
            // 保存token信息
            // 保存在本地 永久有效
            //window.localStorage.setItem("token",data.msg);
            // 关闭浏览器失效
            window.sessionStorage.setItem("token",data.msg);
            // 跳转到main主页面
            this.$router.replace("/main");
          } else if (data.code === 500) {
            this.errorInfo = data.msg
          }
        }).catch(error => {
          this.errorInfo = error
        })
    }
  }
}
</script>

<style scoped>
.login-container{
  display: flex;/*flex布局*/
  justify-content: center;/*居中*/
  margin-top: 152px;
  opacity: 0.9;/*透明度*/
}
.errorInfo{
  font-weight: bold;
  text-align: center;
  color: red;
}

#building{
  background:url("~@/assets/2.jpg");
  width:100%;
  height:100%;
  position:fixed;
  background-size:100% 100%;
}
</style>
