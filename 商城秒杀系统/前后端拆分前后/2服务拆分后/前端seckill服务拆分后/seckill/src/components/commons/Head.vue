<template>
  <div>
    <img src="static/images/logo.png" width="300px" height="120px">
  </div>
</template>

<script>
import axios from 'axios'
import {genServerUrl} from '@/config/sys'

export default {
  name: 'Head',
  methods:{
    // 刷新token的接口
    refreshToken: function () {
      let url = genServerUrl('refreshToken',"token")
      // 获取前端保存的token
      let token = window.sessionStorage.getItem('token');
      // 设置到axios中
      if(token==null){
        token = "";
      }
      axios.defaults.headers.common['token'] = token;
      //
      axios.get(url,{})
        .then(response=>{
          if(response.data.code == 0){
            console.log("token刷新成功");
          }
        }).catch(error=>{
          alert(error+" - 刷新token发生异常");
      })
    }
  },
  mounted () {// 页面加载完成执行
    setInterval(this.refreshToken,1*60*1000*10);// 每隔10分钟调用一次刷新token接口
    // setInterval(this.refreshToken,5000);
  }
}
</script>

<style scoped>

</style>
