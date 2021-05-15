<template>
  <div>
    <div style="width: 400px; margin-left: 20px">
      <el-form
        :model="sutdentInfoModel"
        status-icon
        ref="ruleForm"
        label-width="100px"
        class="demo-ruleForm"
      >
        <el-form-item label="学号">
          <el-input
            type="text"
            v-model="sutdentInfoModel.stuId"
            autocomplete="off"
            disabled
          ></el-input>
        </el-form-item>
        <el-form-item label="姓名" required>
          <el-input
            type="text"
            v-model="sutdentInfoModel.stuName"
            autocomplete="off"
            placeholder="请填写姓名"
          ></el-input>
        </el-form-item>
        <el-form-item label="手机">
          <el-input
            type="text"
            v-model="sutdentInfoModel.stuPhone"
            autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item label="QQ">
          <el-input
            type="text"
            v-model="sutdentInfoModel.stuQq"
            autocomplete="off"
          ></el-input>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="submitForm">立即修改</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: "StuInfo",
  data() {
    return {
      sutdentInfoModel: {
        stuId: "",
        stuName: "",
        stuQq: "",
        stuMail: "",
      },
    };
  },
  methods: {
    getStudentInfo() {
      this.$get("/student/info").then((res) => {
        this.sutdentInfoModel = res;
        window.sessionStorage.setItem("stuId", this.sutdentInfoModel.stuId);
      });
    },
    submitForm() {
      this.$put("/student/updateInfo", this.sutdentInfoModel).then((res) => {
        this.getStudentInfo();
      });
    },
  },
  created() {
    this.getStudentInfo();
  },
};
</script>

<style scpoed>
</style>