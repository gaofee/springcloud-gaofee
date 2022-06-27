import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import axios from 'axios'
import VueAxios from 'vue-axios'


import VueCookies from 'vue-cookies'

Vue.use(VueCookies)
//配置全局路径
axios.defaults.baseURL="http://192.168.21.1:8200/api";

import BaiduMap from 'vue-baidu-map'

Vue.use(BaiduMap, {
  // ak 是在百度地图开发者平台申请的密钥 详见 http://lbsyun.baidu.com/apiconsole/key */
  ak: 'YxsOEEELLGrNtA3cwz4oK3k95NcST551'
})
Vue.use(VueAxios, axios)
Vue.config.productionTip = false
Vue.use(ElementUI);
new Vue({
  router,
  render: h => h(App)
}).$mount('#app')

//请求拦截器和相应拦截器
// 添加请求拦截器
axios.interceptors.request.use(function (config) {
  // 在发送请求之前做些什么
  //从localStorage中取出token
  var token = localStorage.getItem("tokenId");
  if(token){
    //然后拼接到请求头  ctrl shift  r
    //如果localStorage中有token,就把token放入请求头
    config.headers.token = token
  }

  return config;
}, function (error) {
  // 对请求错误做些什么

  return Promise.reject(error);
});

// 添加响应拦截器
axios.interceptors.response.use(function (response) {
  // 对响应数据做点什么
  if(response.data.msg){
    alert(response.data.msg)
  }
  return response;
}, function (error) {
  // 对响应错误做点什么
  return Promise.reject(error);
});
