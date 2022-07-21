<template>
  <div class="home">
    <el-button type="success" @click="aa">登录成功后访问list接口</el-button>
    <el-button type="success" @click="bb">登录成功后访问del接口</el-button>
    <el-button type="success" @click="cc">访问goods服务,演示负载均衡</el-button>
    <el-button type="success" @click="dd">演示文件预览</el-button>
    <br>

    <!--echarts-->

    <div class="box-pie" id="main1" style="height: 400px" ref="chart"></div>
    <br>



    <label>关键词：<input v-model="keyword"></label>
    <label>地区：<input v-model="location"></label>
    <baidu-map class="map" center="北京">

      <bm-view class="map"></bm-view>
      <bm-local-search :keyword="keyword" :auto-viewport="true" :location="location"></bm-local-search>
    </baidu-map>




  </div>
</template>

<script>
// @ is an alias to /src
import { Base64 } from 'js-base64';


export default {
  name: 'Home',

  data () {
    return {
      option:{
        xAxis: {
          type: 'category',
          data: [
            '0点',
            '1点',
            '2点',
            '3点',
            '4点',
            '5点',
            '6点',
            '7点',
            '8点',
            '9点',
            '10点',
            '11点',
            '12点',
            '13点','14点','15点','16点','17点','18点','19点','20点','21点','22点','23点','24点'
          ]
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            data: [],
            type: 'line',
            smooth: true
          }
        ]
      },
      location: '北京',
      keyword: '百度',
      myChart:null,
      wsUrl: 'ws://127.0.0.1:8888/test/oneToMany', // ws地址
      websock: null, // ws实例
    }
  },
  mounted() {
    //初始化表格
    this.initEcharts();
    this.initWebSocket();//初始化websocket
  },

  destroyed() {
    // 离开路由之后断开websocket连接
    this.websock.close()
  },
  methods:{
    // 初始化weosocket
    initWebSocket() {
      if (typeof WebSocket === 'undefined')
        return console.log('您的浏览器不支持websocket')
      this.websock = new WebSocket(this.wsUrl)
      this.websock.onmessage = this.websocketonmessage
      this.websock.onopen = this.websocketonopen
      this.websock.onerror = this.websocketonerror
      this.websock.onclose = this.websocketclose
    },
    websocketonopen() {
      // 连接建立之后执行send方法发送数据
      let actions = { test: 'test' }
      this.websocketsend(JSON.stringify(actions))
    },
    websocketonerror() {
      // 连接建立失败重连
      this.initWebSocket()
    },
    websocketonmessage(e) {
      // 数据接收
      const redata = e.data
      console.log('接收的数据', redata)//d
      if(this.option.series[0].data.length==24){
        this.option.series[0].data.shift() //删除第一个元素
      }
      this.option.series[0].data.push(redata) //往图形报表中的数组添加元素
      this.initEcharts();
    },
    websocketsend(Data) {
      // 数据发送
      this.websock.send(Data)
    },
    websocketclose(e) {
      // 关闭
      console.log('断开连接', e)
    },

    initEcharts(){
      var elementById = document.getElementById("main1");
      this.myChart = this.$echarts.init(elementById);
      this.myChart.setOption(this.option);
    },
    dd(){
      let url = 'http://82.156.45.132:9000/zhaoshixin/2022/07/01/2050577_20220701083820A004.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=OF94CJNBM89K1VIUX3K8%2F20220705%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20220705T004347Z&X-Amz-Expires=604800&X-Amz-Security-Token=eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJhY2Nlc3NLZXkiOiJPRjk0Q0pOQk04OUsxVklVWDNLOCIsImV4cCI6MTY1Njk4NTQwNCwicGFyZW50IjoibWluaW90ZXN0In0.ZFBNevAkX9VjQ83hJpvLi8HmCrDYRWi6JeD8Ccxa8tKw7VgAaxVSANNBEn0B2VyG5KC0cz2pDlZOrh6Y9U5plA&X-Amz-SignedHeaders=host&versionId=null&X-Amz-Signature=ebabb53d73f8a60e6a21b081ee281ac96a217c20bc293bbdce64365265a2e2c9'
      window.open('http://127.0.0.1:8012/onlinePreview?url='+Base64.encodeURL(url));
    },
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