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
              v-model="selectDesignModel.design"
              clearable
              placeholder="请选择课程设计"
            >
              <el-option
                v-for="item in designOptions"
                :key="item.teaDesign.id"
                :label="item.teaDesign.designTitle"
                :value="[item.teaDesign.id, item.teaDesign.designLimit]"
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
                <span style="float: right">{{
                  item.teaDesign.designLimit
                }}</span>
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="选择组员" prop="studentIds">
            <el-select
              v-model="selectDesignModel.studentIds"
              multiple
              :multiple-limit="selectDesignModel.design[1] - 1"
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

    <!-- 小组设计信息 -->
    <div>
      <el-form
        ref="groupDesignInfo"
        :model="groupDesignInfo"
        label-width="80px"
      >
        <el-form-item label="组ID">
          <el-input v-model="groupDesignInfo.groupId" disabled></el-input>
        </el-form-item>
        <el-form-item label="设计名称">
          <el-input v-model="groupDesignInfo.designTitle" disabled></el-input>
        </el-form-item>
        <el-form-item label="截止日期">
          <el-input v-model="endTime" disabled></el-input>
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

    <!-- 查看小组 -->
    <div>
      <el-dialog
        :title="`第` + groupDesignInfo.groupId + `组`"
        :visible.sync="groupMembersVisible"
      >
        <div>
          <span>组长</span>
          <el-divider direction="vertical"></el-divider>
          <span>{{ groupMembers.stuId1 }}</span>
          <el-divider direction="vertical"></el-divider>
          <el-divider></el-divider>
          <span>组员:</span>
          <el-divider direction="vertical"></el-divider>
          <span>{{ groupMembers.stuId2 }}</span>
          <el-divider direction="vertical"></el-divider>
          <span>{{ groupMembers.stuId3 }}</span>
          <el-divider direction="vertical"></el-divider>
          <span>{{ groupMembers.stuId4 }}</span>
          <el-divider direction="vertical"></el-divider>
        </div>
      </el-dialog>
    </div>

    <!-- 提交设计 -->
    <el-dialog
      title="提交设计"
      :visible.sync="commitGroupDesignVisible"
      width="400px"
    >
      <el-upload
        :action="commitGroupDesignUrl"
        :show-file-list="false"
        :before-upload="beforeUpload"
        :on-success="onSuccess"
        :on-error="onError"
        :disabled="commitGroupDesignDisabled"
        :headers="headers"
        name="workFile"
        :data="commitGroupDesignData"
      >
        <el-button
          size="small"
          type="success"
          :icon="commitGroupDesignBtnIcon"
          :disabled="commitGroupDesignDisabled"
          >{{ commitGroupDesignBtnText }}</el-button
        >
        <div slot="tip" class="el-upload__tip">
          仅能上传 c/cpp/java/py 源代码 或 pdf 文件，且不超过5MB
        </div>
      </el-upload>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "StuDesign",
  data() {
    return {
      endTime: "2022-01-09 00:00:00",
      // 提交设计(与提交作业代码类似)
      commitGroupDesignUrl: "",
      commitGroupDesignDisabled: false,
      commitGroupDesignBtnText: "点击上传",
      commitGroupDesignBtnIcon: "el-icon-upload2",
      commitGroupDesignVisible: false,
      commitGroupDesignData: {
        groupDesignId: "",
      },
      isCommitting: false,
      isCommittingPdf: false,
      headers: {
        Authorization: window.sessionStorage.getItem("tokenStr"),
      },
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
        design: [],
      },
      selectDesignRules: {
        // designId: [
        //   {
        //     required: true,
        //     message: "请选择你要完成的课程设计",
        //     trigger: "change",
        //   },
        // ],
        studentIds: [
          { required: false, message: "请选择组员", trigger: "change" },
        ],
        design: [{ required: true }],
      },
    };
  },
  methods: {
    submitDesignCode() {
      if (this.groupDesignInfo.codeName === "未提交") {
        this.$confirm(
          "仅能上传 c/cpp/java/python 源代码文件（除注释外，代码中不能含有中文符号），若有多个源代码文件请写到一个文件中并用注释标注，且提交成功后无法删除，是否继续?",
          "提示",
          {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
          }
        )
          .then(() => {
            this.commitGroupDesignUrl = "/student/uploadStuDesignCode";
            this.commitGroupDesignVisible = true;
            this.commitGroupDesignData.groupDesignId = this.groupDesignInfo.id;
          })
          .catch(() => {
            this.$message({
              type: "info",
              message: "已取消提交",
            });
          });
      } else {
        this.$message("提交成功后无法删除");
      }
    },
    submitDesignPdf() {
      if (this.groupDesignInfo.codeName === "未提交") {
        this.$message({
          type: "warning",
          message: "上传源代码成功后才能上传PDF",
        });
      } else {
        if (this.groupDesignInfo.pdfName === "未提交") {
          this.$confirm(
            "仅能上传 pdf 文件, 提交成功后无法删除，是否继续?",
            "提示",
            {
              confirmButtonText: "确定",
              cancelButtonText: "取消",
              type: "warning",
            }
          )
            .then(() => {
              this.commitGroupDesignUrl = "/student/uploadStuDesignPDF";
              this.commitGroupDesignVisible = true;
              this.commitGroupDesignData.groupDesignId =
                this.groupDesignInfo.id;
              this.isCommittingPdf = true;
            })
            .catch(() => {
              this.$message({
                type: "info",
                message: "已取消提交",
              });
            });
        } else {
          this.$message("提交成功后无法删除");
        }
      }
    },
    // 设计提交模块
    beforeUpload() {
      this.commitGroupDesignBtnText = "正在上传";
      this.commitGroupDesignBtnIcon = "el-icon-loading";
      this.commitGroupDesignDisabled = true;
    },
    onSuccess() {
      this.commitGroupDesignBtnText = "点击上传";
      this.commitGroupDesignBtnIcon = "el-icon-upload2";
      this.commitGroupDesignDisabled = false;
      this.isCommitting = true;
      this.initGroupDesign();

      this.commitGroupDesignUrl = "";

      this.commitGroupDesignVisible = false;
    },
    onError() {
      this.commitGroupDesignBtnText = "点击上传";
      this.commitGroupDesignBtnIcon = "el-icon-upload2";
      this.commitGroupDesignUrl = "";
      this.commitGroupDesignData.groupDesignId = "";
      this.commitGroupDesignDisabled = false;
      this.commitGroupDesignVisible = false;
      this.isCommitting = false;
      this.isCommittingPdf = false;
    },
    //
    initGroupMembers() {
      this.$get("/student/selectGroupMembers").then((res) => {
        this.groupMembers = res;
      });
    },
    selectGroupMembers() {
      this.groupMembersVisible = true;
    },
    initGroupDesign() {
      this.$get("/student/getGroupDesignInfo").then((res) => {
        if (res) {
          this.groupDesignInfo = res;
          this.selectDesignVisible = false;
        } else {
          this.selectDesignVisible = true;
        }
        if (this.isCommitting) {
          if (res.codeName == "未提交") {
            this.$message({
              type: "error",
              message:
                "提交失败，源码文件类型错误或源码中含有中文符号，请检查输入输出语句是否含有中文！！！",
            });
          } else if (this.isCommittingPdf && res.pdfName == "未提交") {
            this.$message({
              type: "error",
              message: "提交失败，文件类型错误！！！",
            });
          } else {
            this.$message({ type: "success", message: "提交成功" });
          }
          this.isCommitting = false;
          this.isCommittingPdf = false;
          this.commitGroupDesignData.groupDesignId = "";
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
          this.$download("/student/downDesignPdf/" + designId).then(
            (res) => {}
          );
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
          let url = "?designId=" + this.selectDesignModel.design[0];
          this.selectDesignModel.studentIds.forEach((studentId) => {
            url += "&studentIds=" + studentId;
          });
          if (this.selectDesignModel.studentIds.length == 0) {
            url += "&studentIds=" + [];
          }
          this.$post("/student/createGroup" + url).then((res) => {
            this.selectDesignVisible = false;
            this.initGroupDesign();
            this.initGroupMembers();
          });
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
    this.initGroupMembers();
    this.initGroupDesign();
    this.listDesignOptions();
    this.listClassStudents();
  },
};
</script>

<style scoped>
</style>