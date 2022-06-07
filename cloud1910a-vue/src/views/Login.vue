<template>
    <div>
        <el-row>
            <el-col :span="8"><div class="grid-content"></div></el-col>
            <el-col :span="8"><div class="grid-content">
                <el-form :model="ruleForm" status-icon  ref="ruleForm" label-width="100px" class="demo-ruleForm">

                    <el-form-item label="用户名" prop="userName">
                        <el-input v-model.number="ruleForm.userName"></el-input>
                    </el-form-item>
                    <el-form-item label="密码" prop="password">
                        <el-input type="password" v-model="ruleForm.password" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="验证码" prop="password">
                        <el-input type="text" v-model="ruleForm.code" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-image :src="codeImgUrl" @click="captcha"></el-image>
                    <el-form-item>
                        <el-button type="primary" @click="submitForm">提交</el-button>
                    </el-form-item>
                </el-form>

            </div></el-col>
            <el-col :span="8"><div class="grid-content"></div></el-col>

        </el-row>
    </div>
</template>

<script>
    export default {
        name: "Login",
        data(){
            return{
                ruleForm: {
                    password: '1234',
                    userName: 'admin',
                    code:''
                },
                codeImgUrl:'http://127.0.0.1:8081/upload/line.png'
            }
        },
        methods:{
            captcha(){
                this.axios.get("http://localhost:8200/api/user/captcha").then(resp=>{
                    console.log(resp.data)
                    this.codeImgUrl=resp.data/*+"?time="+new Date().getMilliseconds()*/;
                });
            },
            submitForm(){
                this.axios.get("http://localhost:8200/api/user/login?userName="+this.ruleForm.userName+"&password="+this.ruleForm.password+"&code="+this.ruleForm.code).then(resp=>{
                    console.log(resp.data)
                    if(resp.data=="error"){
                        //提示错误
                        //不进行跳转,刷新页面
                        alert(resp.data)
                    }else if(resp.data==="codeerror"){
                        alert(resp.data)
                    } else if(resp.data==="codeexpire"){
                        alert(resp.data)
                    }
                    else {
                        localStorage.setItem("tokenId", resp.data);
                        this.$router.push("/")
                    }
                })
            }

        },
        created() {
            this.captcha();
        }
    }
</script>

<style scoped>
    .el-row {
        margin-bottom: 20px;
    &:last-child {
         margin-bottom: 0;
     }
    }
    .el-col {
        border-radius: 4px;
    }
    .bg-purple-dark {
        background: #99a9bf;
    }
    .bg-purple {
        background: #d3dce6;
    }
    .bg-purple-light {
        background: #e5e9f2;
    }
    .grid-content {
        border-radius: 4px;
        min-height: 36px;
    }
    .row-bg {
        padding: 10px 0;
        background-color: #f9fafc;
    }
</style>
