<template>
  <div class="home">
    <el-button type="success" @click="aa">登录成功后访问list接口</el-button>
    <el-button type="success" @click="bb">登录成功后访问del接口</el-button>
    <el-button type="success" @click="cc">访问goods服务,演示负载均衡</el-button>
    <br>
    <label>关键词：<input v-model="keyword"></label>
    <label>地区：<input v-model="location"></label>
    <baidu-map class="map" center="北京">

      <bm-view class="map"></bm-view>
      <bm-local-search :keyword="keyword" :auto-viewport="true" :location="location"></bm-local-search>
    </baidu-map>


    <HelloWorld msg="Welcome to Your Vue.js App"/>


  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'

export default {
  name: 'Home',
  components: {
    HelloWorld
  },
  data () {
    return {
      location: '北京',
      keyword: '百度'
    }
  },
  methods:{
    aa(){
      this.axios.get("/user/list").then(resp=>{
        alert(resp.data)
      })
    },
    bb(){
      this.axios.get("/user/del").then(resp=>{
        alert(resp.data)
      })
    },
    cc(){
      this.axios.get("/goods/list").then(resp=>{
        alert(resp.data)
      })
    }
  }
}
</script>
<style>
.map {
  width: 100%;
  height: 400px;
}
</style>