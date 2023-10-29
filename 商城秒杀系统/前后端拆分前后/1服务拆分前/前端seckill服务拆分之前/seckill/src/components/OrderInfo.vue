<template>
  <el-container>
    <el-header height="120px">
      <seckill-head></seckill-head>
    </el-header>
    <el-main>
      <el-form label-position="left" label-width="150px">
        <p class="goods_title">订单详情</p>
        <el-form-item label="订单编号">
          {{this.order.id}}
        </el-form-item>
        <el-form-item label="商品名称">
          {{this.order.miaoShaGoods.goods.name}}
        </el-form-item>
        <el-form-item label="商品图片">
          <img :src="getSrcUrl(this.order.miaoShaGoods.goods.image)" style="width: 250px;">
        </el-form-item>
        <el-form-item label="订单价格">
          {{"￥"+this.order.totalPrice}}
        </el-form-item>
        <el-form-item label="订单状态">
          <span v-show="this.order.status==0">待支付</span>
          <span v-show="this.order.status==1">已支付</span>
          <span v-show="this.order.status==2">待发货</span>
          <span v-show="this.order.status==3">已收获</span>

        </el-form-item>
        <el-form-item label="收货人">
          {{this.order.user.name}}
        </el-form-item>
        <el-form-item label="联系电话">
          {{this.order.user.phoneNumber}}
        </el-form-item>
        <el-form-item label="收获地址">
          {{this.order.user.address}}
        </el-form-item>
      </el-form>
      <el-button type="success" @click="exec_miaosha()" size="small">立即支付</el-button>
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
  name: 'OrderInfo',
  components:{
    SeckillHead, // 添加引入的组件 否则上边无法使用
    SeckillFooter
  },
  data(){
    return{
      order:{
        miaoShaGoods:{
          goods: {
            name:'',
            image:'default.jpg'
          }
        },
        user:{
          name:'',
          phoneNumber:'',
          address:''
        }
      }

    }
  },
  methods:{
    getSrcUrl(t){
      return genServerUrl('image/'+t);
    },
    getInfo(){
      let url=genServerUrl("order/detail");
      let token=window.sessionStorage.getItem("token");
      axios.defaults.headers.common['token']=token;

      axios.get(url,{
        params:{
          id:this.$route.params.id
        }
      }).then(response=>{
          console.log(response.data.data);
          this.order=response.data.data;
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
