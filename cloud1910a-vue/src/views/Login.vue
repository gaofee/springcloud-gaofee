<template>
    <div>
        <el-row>
            <el-col :span="8"><div class="grid-content"></div></el-col>
            <el-col :span="8"><div class="grid-content">
                <el-form :model="ruleForm" status-icon  ref="ruleForm" label-width="100px" class="demo-ruleForm">

                    <el-form-item label="用户名" prop="username">
                        <el-input v-model.number="ruleForm.username"></el-input>
                    </el-form-item>
                    <el-form-item label="密码" prop="password">
                        <el-input type="password" v-model="ruleForm.password" autocomplete="off"></el-input>
                    </el-form-item>
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
                    password: 'admin',
                    username: 'admin'
                }
            }
        },
        methods:{
            submitForm(){
                this.axios.get("http://localhost:8200/api/user/login?username="+this.ruleForm.username+"&password="+this.ruleForm.password).then(resp=>{
                    console.log(resp.data)
                    if(resp.data=="error"){
                        //提示错误
                        //不进行跳转,刷新页面
                    }
                    localStorage.setItem("tokenId", resp.data);
                    this.$router.push("/")
                })
            }
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
