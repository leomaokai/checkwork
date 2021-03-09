<template>
  <div>
    <div style="width: 1000px; heigth: 500px; margin-left: 100px">
      <div>
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="small"
          @click="createWork"
          style="float: right"
          >布置作业</el-button
        >
      </div>
      <br />
      <div></div>
    </div>

    <!-- 布置作业模块 -->
    <div>
      <el-dialog title="布置作业" :visible.sync="createWorkFormVisible">
        <el-form
          :model="createWorkFormModel"
          :rules="createWorkFormRules"
          ref="createWorkFormModel"
          label-width="100px"
        >
          <el-form-item label="作业标题" prop="workTitle">
            <el-input
              v-model="createWorkFormModel.workTitle"
              placeholder="数据结构第一次作业"
            ></el-input>
          </el-form-item>
          <el-form-item label="作业描述" prop="workDescribe">
            <el-input
              v-model="createWorkFormModel.workDescribe"
              placeholder="请输入作业描述"
            ></el-input>
          </el-form-item>
          <el-form-item label="选择班级" prop="workClasses">
            <el-checkbox-group v-model="createWorkFormModel.workClasses">
              <el-checkbox
                v-for="(workClass, index) in workClass"
                :key="index"
                :label="workClass.className"
                name="workClasses"
              ></el-checkbox>
            </el-checkbox-group>
          </el-form-item>
          <el-form-item label="截至日期" required prop="workDateTime">
            <div class="block">
              <el-date-picker
                v-model="createWorkFormModel.workDateTime"
                type="datetime"
                placeholder="选择日期时间"
                align="right"
                :picker-options="pickerOptions"
              >
              </el-date-picker>
            </div>
          </el-form-item>
          <el-form-item>
            <el-button
              type="primary"
              @click="submitCreateWorkForm('createWorkFormModel')"
              >布置</el-button
            >
            <el-button @click="resetCreateWorkForm('createWorkFormModel')"
              >取消</el-button
            >
          </el-form-item>
        </el-form>
      </el-dialog>
    </div>
  </div>
</template>

<script>
export default {
  name: "TeaWork",
  data() {
    return {
      // 布置作业模块
      workClass: [],
      createWorkFormRules: {
        workTitle: [
          { required: true, message: "请输入作业标题", trigger: "blur" },
          {
            min: 3,
            max: 15,
            message: "长度在 3 到 15 个字符",
            trigger: "blur",
          },
        ],
        workDescribe: [],
        workDateTime: [
          {
            type: "date",
            required: true,
            message: "请选择截止时间",
            trigger: "change",
          },
        ],
        workClasses: [
          {
            type: "array",
            required: true,
            message: "请至少选择一个班级",
            trigger: "change",
          },
        ],
      },
      createWorkFormModel: {
        workTitle: "",
        workDescribe: "",
        workDateTime: "",
        workClasses: [],
      },
      createWorkFormVisible: false,
      pickerOptions: {
        shortcuts: [
          {
            text: "今天",
            onClick(picker) {
              picker.$emit("pick", new Date());
            },
          },
          {
            text: "明天",
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() + 3600 * 1000 * 24);
              picker.$emit("pick", date);
            },
          },
          {
            text: "一周后",
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() + 3600 * 1000 * 24 * 7);
              picker.$emit("pick", date);
            },
          },
        ],
      },
    };
  },
  methods: {
    // 布置作业模块
    submitCreateWorkForm(createWorkFormModel) {
      this.$confirm("此操作将永久删除该文件, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.$message({
            type: "success",
            message: "删除成功!",
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
      this.createWorkFormVisible = false;
      this.$refs[createWorkFormModel].resetFields();
    },
    resetCreateWorkForm(createWorkFormModel) {
      this.createWorkFormVisible = false;
      this.$refs[createWorkFormModel].resetFields();
    },
    createWork() {
      this.createWorkFormVisible = true;
    },
  },
  computed: {},
  created() {
    this.workClass = this.$store.state.classes;
  },
};
</script>

<style scoped>
</style>