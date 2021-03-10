<template>
  <div>
    <el-form
      :rules="rulesLogin"
      ref="loginForm"
      :model="loginForm"
      class="loginContainer"
      v-loading="loading"
    >
      <h3 class="loginTitle">登录</h3>
      <el-form-item prop="username">
        <el-input
          type="text"
          v-model="loginForm.username"
          autocomplete="false"
          placeholder="请输入用户名"
        >
        </el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input
          type="password"
          v-model="loginForm.password"
          autocomplete="false"
          placeholder="请输入密码"
        ></el-input>
      </el-form-item>
      <el-form-item prop="code">
        <el-input
          v-model="loginForm.code"
          placeholder="点击图片更换验证码"
          type="text"
          autocomplete="false"
          style="width: 250px; margin-right: 5px"
        >
        </el-input>
        <img :src="captchaUrl" @click="updateCaptcha" />
      </el-form-item>
      <el-button
        type="primary"
        style="width: 100%"
        @click="submitLogin('loginForm')"
        >登录</el-button
      >
    </el-form>
  </div>
</template>
<script>
export default {
  name: "Login",
  data() {
    return {
      captchaUrl: "/captcha?time=" + new Date(),
      loginForm: {
        username: "",
        password: "",
        code: "",
      },
      rulesLogin: {
        username: [
          { required: true, message: "请输入用户名", trigger: "blur" },
          { min: 8, max: 8, message: "用户名为学号或工号", trigger: "blur" },
        ],
        password: [
          { required: true, message: "请输入密码", trigger: "blur" },
          { min: 8, message: "密码不少于8位", trigger: "blur" },
        ],
        code: [
          { required: true, message: "请输入验证码", trigger: "blur" },
          { min: 4, max: 4, message: "验证码有误", trigger: "blur" },
        ],
      },
      loading: false,
    };
  },
  methods: {
    updateCaptcha() {
      this.captchaUrl = "/captcha?time=" + new Date();
    },
    submitLogin(loginForm) {
      this.loading = true;
      this.$refs[loginForm].validate((valid) => {
        if (valid) {
          // this.$message.success({
          //   message: "登录成功",
          //   type: "success",
          // });
          // this.$http.post("/user/login", this.loginForm).then((resp) => {
          //   alert(resp.data);
          // });
          this.$post("/user/login", this.loginForm).then((resp) => {
            this.loading = false;
            //alert(resp.obj.token);
            if (resp) {
              // 存储用户token
              const tokenStr = resp.obj.tokenHead + resp.obj.token;
              window.sessionStorage.setItem("tokenStr", tokenStr);
              // if (
              //   window.sessionStorage.getItem("tokenStr") &&
              //   window.sessionStorage.getItem("tokenStr") == tokenStr
              // ) {
              //   window.sessionStorage.setItem("isSame", "1");
              // } else {
              //   window.sessionStorage.setItem("tokenStr", tokenStr);
              //   window.sessionStorage.setItem("isSame", "0");
              // }

              window.sessionStorage.setItem("loginTimes", "0");
              window.sessionStorage.setItem("roleId", resp.obj.roleId + "");
              // 根据用户角色转发不同页面
              this.$router.replace({ name: "Home" });
              console.log("login....");

              let path = this.$route.query.redirect;
              if (path == "/" || path == undefined)
                switch (window.sessionStorage.getItem("roleId")) {
                  case "3":
                    this.$router.replace("/student");
                    break;
                  case "2":
                    this.$router.replace("/teacher/class");
                    break;
                  case "1":
                    this.$router.replace("/admin");
                    break;
                  default:
                    this.$router.replace("/");
                }

              // if (this.$router.path != "/home") {
              //   this.$router.replace("/home");
              // }
            }
          });
        } else {
          this.$message.error("登录失败");
          this.loading = false;
        }
      });
    },
  },
};
</script>
<style>
.loginContainer {
  border-radius: 15px;
  background-clip: padding-box;
  margin: 180px auto;
  width: 350px;
  padding: 15px 35px 15px 35px;
  background: white;
  border: 1px solid grey;
  box-shadow: 0 0 25px #cac6c6;
}
.loginTitle {
  margin: 10px auto 25px auto;
  text-align: center;
}
.el-form-item__content {
  display: flex;
  align-items: center;
}
</style>