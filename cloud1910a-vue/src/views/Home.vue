<template>
  <div class="home">
    <el-button type="success" @click="aa">登录成功后访问list接口</el-button>
    <el-button type="success" @click="bb">登录成功后访问del接口</el-button>
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
  methods:{
    aa(){
      this.axios.get("http://localhost:8200/api/user/list").then(resp=>{
        alert(resp.data)
      })
    },
    bb(){
      this.axios.get("http://localhost:8200/api/user/del").then(resp=>{
        alert(resp.data)
      })
    }
  }
}
</script>
