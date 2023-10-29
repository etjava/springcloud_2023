<template>
  <el-container>
    <el-header height="120px">
      <seckill-head></seckill-head>
    </el-header>
    <el-main>
      <el-form label-position="left" label-width="150px">
        <p class="goods_title">商品详情</p>
        <el-form-item label="商品名称">
            {{miaoShaGoods.goods.name}}
        </el-form-item>
        <el-form-item label="商品图片">
          <img :src="getSrcUrl(miaoShaGoods.goods.image)" style="width: 250px;">
        </el-form-item>
        <el-form-item label="商品原价">
          {{"￥"+miaoShaGoods.goods.price}}
        </el-form-item>
        <el-form-item label="抢购价">
          {{"￥"+miaoShaGoods.price}}
        </el-form-item>
        <el-form-item label="库存数量">
          {{miaoShaGoods.stock}}
        </el-form-item>
        <el-form-item label="抢购开始时间">
          {{miaoShaGoods.startTime}}
          <span v-show="miaoShaGoods.miaoShaStatus==0">抢购倒计时 {{miaoShaGoods.remainBeginSecond}} s</span>
          <span v-show="miaoShaGoods.miaoShaStatus==1">抢购进行中</span>
          <span v-show="miaoShaGoods.miaoShaStatus==2">抢购结束</span>
        </el-form-item>
        <el-form-item label="抢购结束时间">
          {{miaoShaGoods.endTime}}
        </el-form-item>
      </el-form>
      <img :src="verifyCodeImgSrc" id="verifyImg" width="80" height="32" @click="refreshVerifyCode" v-show="miaoShaGoods.miaoShaStatus==1" />
      <input v-show="miaoShaGoods.miaoShaStatus==1"
             style="width: 60px;height: 23px;padding: 4px;border: 1px solid gray;"
             type="text"  id="verifyCode" v-model="verifyCode">
      <el-button type="success" @click="exec_miaosha()"
                 v-show="miaoShaGoods.miaoShaStatus==1" size="small">立即抢购</el-button>
      <p style="color: red">{{miaoShaResult}}</p>
    </el-main>
    <el-footer class="elfooter">
      <seckill-footer></seckill-footer>
    </el-footer>
  </el-container>
</template>

<script>
import SeckillHead from './commons/Head'
import SeckillFooter from './commons/Footer'
import axios from "axios";
import {genServerUrl} from '../config/sys'

export default {
  name: 'Detail',
  components:{
    SeckillHead, // 添加引入的组件 否则上边无法使用
    SeckillFooter
  },
  data(){
    return{
      miaoShaGoods:{
        goods:{
          name:'',
          price:'',
          image:'default.jpg'

        }
      },
      verifyCodeImgSrc: '',
      verifyCode: '',
      miaoShaResult: ''
    }
  },
  methods:{
    getSrcUrl(t){
      return genServerUrl('image/'+t);
    },
    // 刷新验证码
    refreshVerifyCode(){
      let imgUrl = genServerUrl("verifyCode/get");
      // 添加timestamp参数的目的是为了防止浏览器缓存
      this.verifyCodeImgSrc=imgUrl+"?miaoShaGoodsId="+this.$route.params.id+
        "&token="+window.sessionStorage.getItem("token")+"&timestamp="+new Date();
    },
    // 执行秒杀
    exec_miaosha(){
      if(this.verifyCode==''){
        alert("请填写验证码结果");
        return;
      }
      let url=genServerUrl("miaoSha/exec");
      let token=window.sessionStorage.getItem("token");
      axios.defaults.headers.common['token']=token;

      axios.get(url,{
        params:{
          // 注意 key值必须与服务端接收数据的参数名保持一致
          goodsId:this.$route.params.id,
          verifyCode: this.verifyCode
        }
      }).then(response=>{
        let data = response.data;
        this.miaoShaGoods.miaoShaStatus=4;// 隐藏验证码 按钮
        if(data.code!=0){
          alert(data.msg);
          this.miaoShaResult=data.msg;// 给出提示
        }else{// 下单成功
          //alert(data.orderId);
          this.miaoShaGoods.miaoShaStatus=4;
          this.miaoShaResult=data.msg;
          this.getMiaoShaResult(this.$route.params.id);// 轮询获取秒杀状态
        }
      }).catch(error=>{
        alert(error+"-请联系管理员");
      })
    },
    // 获取秒杀状态
    getMiaoShaResult(miaoShaGoodsId){
      let url=genServerUrl("miaoSha/result");
      let token=window.sessionStorage.getItem("token");
      axios.defaults.headers.common['token']=token;
      axios.get(url,{
        params:{
          // 注意 key值必须与服务端接收数据的参数名保持一致
          goodsId:this.$route.params.id
        }
      }).then(response=>{
        let data=response.data;
        //alert("res=>"+data.code);
        if(data.code!=0){
          alert(data.msg);
        }else{
          let result=data.result;
          // alert("res = >"+result);
          if(result=="0"){  // 排队中，继续轮询
            setTimeout(() =>{// 箭头符号为全局的 function() 局部的 只会执行一次
              this.getMiaoShaResult(miaoShaGoodsId)
            },50);
          }else if(result=="-1"){
            this.miaoShaResult="秒杀失败";
          }else{
            //alert("秒杀成功,订单号："+result);
            this.miaoShaGoods.miaoShaStatus=4;// 隐藏按钮和验证码
            // 跳转到订单信息页面
            //this.$router.push({name:"OrderInfo",params:{id:result}});
            this.$router.push({name:"OrderInfo2",params:{id:result}});
          }
        }
      }).catch(error=>{
        alert(error+"-请联系管理员");
      })
    },
    // 秒杀状态及动态倒计时显示
    countDown(){
      let timeout;
      let res = this.miaoShaGoods.remainBeginSecond;
      if(res==0){
        // 秒杀进行中
        this.miaoShaGoods.miaoShaStatus=1;
        // 销毁倒计时
        if(timeout){
          clearTimeout(timeout);
        }
        // 秒杀进行中的时候显示验证码
        let imgUrl = genServerUrl("verifyCode/get");
        this.verifyCodeImgSrc=imgUrl+"?miaoShaGoodsId="+this.$route.params.id+"&token="+window.sessionStorage.getItem("token");
      }else if(res>0){
        // 秒杀还没开始 显示倒计时
        timeout = setTimeout(()=>{
          this.miaoShaGoods.remainBeginSecond = this.miaoShaGoods.remainBeginSecond-1;
          this.countDown();// 每秒调用一次
        },1000)
      }else{
        // 秒杀结束 这里不会动态隐藏秒杀按钮
        this.miaoShaGoods.miaoShaStatus=2;
      }
    },
    getInfo(){
      let url=genServerUrl("miaoShaGoods/findById");
      let token=window.sessionStorage.getItem("token");
      axios.defaults.headers.common['token']=token;

      axios.get(url,{
        params:{
          id:this.$route.params.id
        }
      })
        .then(response=>{
          console.log(response.data.data);
          this.miaoShaGoods=response.data.data;
          this.countDown();// 显示秒杀状态
          // 动态隐藏秒杀按钮
          if(this.miaoShaGoods.remainEndSecond>0){
            // 秒杀还没结束 需要动态判断等到结束为止
            setTimeout(()=>{
              this.miaoShaGoods.miaoShaStatus=2;
            },this.miaoShaGoods.remainEndSecond*1000)// 按秒执行

          }
        }).catch(error=>{
         alert(error+"-请联系管理员");
      })
    }
  },
  mounted() {
    this.getInfo();// 页面加载完成后执行
  }
}
</script>

<style scoped>
  .elfooter{
    position: fixed;
    bottom: 0;
    left: 50%;
    transform: translateX(-50%);
  }
  .goods_title{
    font-size: 20px;
    font-weight: bold;
  }
</style>
