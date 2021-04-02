<template>
  <div>
    <el-dialog title="选择课程设计和组员" :visible.sync="selectDesignVisible">
      <div>
        <el-form
          :model="selectDesignModel"
          :rules="selectDesignRules"
          ref="selectDesignModel"
          label-width="100px"
        >
          <el-form-item label="课程设计" prop="designId">
            <el-select
              v-model="selectDesignModel.designId"
              clearable
              placeholder="请选择课程设计"
            >
              <el-option
                v-for="item in designOptions"
                :key="item.teaDesign.id"
                :label="item.teaDesign.designTitle"
                :value="item.teaDesign.id"
              >
                <span style="float: left">{{
                  item.teaDesign.designTitle
                }}</span>
                <el-button
                  class="el-icon-download"
                  style="margin: 4px 0 0 50px"
                  circle
                  size="mini"
                  @click="downDesignPdf(item.teaDesign.id)"
                ></el-button>
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="选择组员" prop="studentIds">
            <el-select
              v-model="selectDesignModel.studentIds"
              multiple
              :multiple-limit="3"
              placeholder="请选择组员"
            >
              <el-option
                v-for="item in studentsOptions"
                :key="item"
                :label="item"
                :value="item"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button
              type="primary"
              @click="submitSelectDesigntForm('selectDesignModel')"
              >立即创建</el-button
            >
            <el-button @click="resetSelectDesignForm('selectDesignModel')"
              >重置</el-button
            >
          </el-form-item>
        </el-form>
      </div>
    </el-dialog>

    <div>
      <el-form
        ref="groupDesignInfo"
        :model="groupDesignInfo"
        label-width="80px"
      >
        <el-form-item label="设计名称">
          <el-input v-model="groupDesignInfo.designTitle" disabled></el-input>
        </el-form-item>
        <el-form-item label="源代码">
          <el-input v-model="groupDesignInfo.codeName" disabled></el-input>
        </el-form-item>
        <el-form-item label="PDF">
          <el-input v-model="groupDesignInfo.pdfName" disabled></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="selectGroupMembers"
            >查看小组成员</el-button
          >
          <el-button
            type="success"
            @click="downDesignPdf(groupDesignInfo.designId)"
            >下载具体要求</el-button
          >
          <el-button type="primary" @click="submitDesignCode"
            >提交源代码</el-button
          >
          <el-button type="success" @click="submitDesignPdf">提交PDF</el-button>
        </el-form-item>
      </el-form>
    </div>

    <div>
      <el-dialog title="小组成员" :visible.sync="groupMembersVisible">
        <ul>
          组长
          <li>{{ groupMembers.stuId1 }}</li>
        </ul>
        <ul>
          组员
          <li>{{ groupMembers.stuId2 }}</li>
          <li>{{ groupMembers.stuId3 }}</li>
          <li>{{ groupMembers.stuId4 }}</li>
        </ul>
      </el-dialog>
    </div>
    <div></div>
  </div>
</template>

<script>
export default {
  name: "StuDesign",
  data() {
    return {
      // 查看小组成员
      groupMembersVisible: false,
      // 查询设计模块
      groupDesignInfo: {},
      groupMembers: {},
      // 选择设计模块
      selectDesignVisible: false,
      studentsOptions: [],
      designOptions: [],
      selectDesignModel: {
        designId: "",
        studentIds: [],
      },
      selectDesignRules: {
        designId: [
          {
            required: true,
            message: "请选择你要完成的课程设计",
            trigger: "change",
          },
        ],
        studentIds: [
          { required: false, message: "请选择组员", trigger: "change" },
        ],
      },
    };
  },
  methods: {
    // 查询设计模块
    selectGroupMembers() {
      this.$get("/student/selectGroupMembers").then((res) => {
        this.groupMembers = res;
        this.groupMembersVisible = true;
      });
    },
    submitDesignCode() {},
    submitDesignPdf() {},
    initGroupDesign() {
      this.$get("/student/getGroupDesignInfo").then((res) => {
        console.log(res);
        if (res) {
          this.groupDesignInfo = res;
          this.selectDesignVisible = false;
        } else {
          this.selectDesignVisible = true;
        }
      });
    },
    // 选择设计模块
    listClassStudents() {
      this.$get("/student/listClassStudents").then((res) => {
        this.studentsOptions = res;
      });
    },
    listDesignOptions() {
      this.$get("/student/listClassDesigns").then((res) => {
        this.designOptions = res;
      });
    },
    downDesignPdf(designId) {
      this.$confirm("确定下载该课程设计的PDF?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.$download(
            "/student/downDesignPdf/" + designId
          ).then((res) => {});
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消下载",
          });
        });
    },
    submitSelectDesigntForm(selectDesignModel) {
      this.$refs[selectDesignModel].validate((valid) => {
        if (valid) {
          let url = "?designId=" + this.selectDesignModel.designId;
          this.selectDesignModel.studentIds.forEach((studentId) => {
            url += "&studentIds=" + studentId;
          });
          this.$post("/student/createGroup" + url);
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    resetSelectDesignForm(selectDesignModel) {
      this.$refs[selectDesignModel].resetFields();
    },
  },
  created() {
    this.initGroupDesign();
    this.listDesignOptions();
    this.listClassStudents();
  },
};
</script>

<style scoped>
</style>