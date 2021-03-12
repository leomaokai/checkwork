<template>
  <div>
    <el-container>
      <el-header class="homeHeader">
        <div class="title">CUMT作业管理系统</div>
        <el-dropdown @command="userCommand">
          <span class="el-dropdown-link">
            {{ username }}<i class="el-icon-arrow-down el-icon--right"></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item command="logout">退出登录</el-dropdown-item>
            <el-dropdown-item command="changePwd">修改密码</el-dropdown-item>

            <!-- <el-dropdown-item command="c">个人中心</el-dropdown-item> -->
            <!-- <el-dropdown-item disabled>设置</el-dropdown-item> -->
          </el-dropdown-menu>
        </el-dropdown>
      </el-header>
      <el-dialog title="修改密码" :visible.sync="changePwdFormVisible">
        <el-form
          :model="changePwdModel"
          :rules="changePwdRules"
          ref="changePwdModel"
        >
          <el-form-item
            label="旧密码"
            :label-width="formLabelWidth"
            prop="oldPassword"
          >
            <el-input
              type="password"
              v-model="changePwdModel.oldPassword"
              autocomplete="off"
            ></el-input>
          </el-form-item>
          <el-form-item
            label="新密码"
            :label-width="formLabelWidth"
            prop="newPassword"
          >
            <el-input
              type="password"
              v-model="changePwdModel.newPassword"
              autocomplete="off"
            ></el-input>
          </el-form-item>
          <el-form-item
            label="确认密码"
            :label-width="formLabelWidth"
            prop="checkNewPassword"
          >
            <el-input
              type="password"
              v-model="changePwdModel.checkNewPassword"
              autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="changePwdFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="changePwd('changePwdModel')"
            >确 定</el-button
          >
        </div>
      </el-dialog>
      <el-main>
        <router-view></router-view>
      </el-main>
      <!-- <el-footer>Footer</el-footer> -->
    </el-container>
  </div>
</template>

<script>
import { Encrypt } from "../utils/secret.js";
export default {
  name: "Home",
  data() {
    let checkPass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请再次输入密码"));
      } else if (value !== this.changePwdModel.newPassword) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };
    return {
      username: "",
      changePwdFormVisible: false,
      changePwdModel: {
        oldPassword: "",
        newPassword: "",
        checkNewPassword: "",
      },
      changePwdRules: {
        oldPassword: [
          { required: true, message: "请输入旧密码", trigger: "blur" },
          { min: 8, message: "旧密码长度不小于8位", trigger: "blur" },
        ],
        newPassword: [
          { required: true, message: "请输入新密码", trigger: "blur" },
          { min: 8, message: "新密码长度不小于8", trigger: "blur" },
        ],
        checkNewPassword: [{ validator: checkPass, trigger: "blur" }],
      },
      formLabelWidth: "120px",
    };
  },
  methods: {
    userCommand(command) {
      if (command == "logout") {
        this.$confirm("此操作将注销登录, 是否继续?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        })
          .then(() => {
            this.$post("/user/logout");
            window.sessionStorage.clear();
            this.$store.state.flag = 0;
            this.$router.replace("/");
          })
          .catch(() => {
            this.$message({
              type: "info",
              message: "已取消退出",
            });
          });
      }
      if (command == "changePwd") {
        this.changePwdFormVisible = true;
      }
    },
    changePwd(changePwdModel) {
      this.$refs[changePwdModel].validate((valid) => {
        if (valid) {
          let updatePwd = {
            // oldPassword:  Encrypt(this.changePwdModel.oldPassword),
            // newPassword:  Encrypt(this.changePwdModel.newPassword),
            oldPassword: this.changePwdModel.oldPassword,
            newPassword: this.changePwdModel.newPassword,
          };
          this.$put("/user/update", updatePwd).then((res) => {
            if (res != undefined && res.code == 200202) {
              this.changePwdFormVisible = false;
              window.sessionStorage.clear();
              this.$router.replace("/");
            } else {
              this.changePwdModel.oldPassword = "";
              this.changePwdModel.newPassword = "";
              this.changePwdModel.checkNewPassword = "";
            }
          });
        } else {
          this.changePwdFormVisible = true;
        }
      });
    },
  },

  mounted() {
    this.$get("/user/userInfo").then((res) => {
      this.username = res.username;
    });
  },
};
</script>

<style scoped>
.homeHeader {
  background: #409eff;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 15px;
  box-sizing: border-box;
}
.title {
  font-size: 30px;
  color: white;
  font-family: 楷体;
}
.el-dropdown-link {
  cursor: pointer;
  color: white;
  font-size: 18px;
}
.el-icon-arrow-down {
  font-size: 18px;
}
</style>