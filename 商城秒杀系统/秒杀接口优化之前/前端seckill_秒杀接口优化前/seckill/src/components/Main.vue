<template>
  <el-container>
    <el-header height="120px">
      <seckill-head></seckill-head>
    </el-header>
    <el-main>
      <el-table
        :data="tableData"
        style="width: 100%">
        <el-table-column
          prop="goods.name"
          label="商品名称"
          width="180">
        </el-table-column>
        <el-table-column
          prop="goods.image"
          label="商品图片"
          width="180">
          <template slot-scope="scope">
            <img :src="getSrcUrl(scope.row.goods.image)" width="60" height="80"/>
          </template>
        </el-table-column>
        <el-table-column
          width="180"
          prop="goods.price"
          label="商品原价(元)">
        </el-table-column>
        <el-table-column
          width="180"
          prop="price"
          label="秒杀价(元)">
        </el-table-column>
        <el-table-column
          width="180"
          prop="stock"
          label="库存数量">
        </el-table-column>
        <el-table-column
          label="操作">
          <template slot-scope="scope">
            <el-button @click="openDetail(scope.row.id)" type="text" size="small">详情</el-button>
          </template>
        </el-table-column>
      </el-table>
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
  name: 'Main',
  components:{
    SeckillHead, // 添加引入的组件 否则上边无法使用
    SeckillFooter
  },
  data(){
    return{
      tableData:[]
    }
  },
  methods:{
    // 点击商品详情时打开详情页面
    openDetail(id){
      // name值要与路由（index.js）指定的名称一致
      this.$router.push({name:"Detail",params:{id:id}})
    },
    // 获取商品图片 拼接图片地址
    getSrcUrl(t){
      return genServerUrl('image/'+t);
    },
    // 获取秒杀商品列表
    getMiaoShaGoods(){
      let url=genServerUrl("miaoShaGoods/findAll");
      let token=window.sessionStorage.getItem("token");
      axios.defaults.headers.common['token']=token;
      axios.get(url,{})
        .then(response=>{
          console.log(response.data.data);
          this.tableData=response.data.data;
        }).catch(error=>{
        alert(error+"-请联系管理员");
      })
    }
  },
  mounted() {
    this.getMiaoShaGoods();
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
</style>
