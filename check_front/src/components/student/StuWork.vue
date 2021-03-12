<template>
  <div>
    <div style="width: 1121px; heigth: 700px; margin-left: 50px">
      <div>
        <el-table :data="stuWorkData" border stripe style="width: 100%">
          <el-table-column type="index" width="50"> </el-table-column>
          <el-table-column prop="className" label="班级" width="100">
          </el-table-column>
          <el-table-column prop="workTitle" label="作业标题" width="120">
          </el-table-column>
          <el-table-column prop="workDescribe" label="作业描述" width="120">
          </el-table-column>
          <el-table-column prop="workEndTime" label="截止时间" width="100">
          </el-table-column>
          <el-table-column prop="createTime" label="创建时间" width="100">
          </el-table-column>
          <el-table-column prop="workName" label="源代码" width="100">
          </el-table-column>
          <el-table-column prop="pdfName" label="PDF" width="100">
          </el-table-column>
          <el-table-column prop="isCommit" label="提交结果" width="100">
          </el-table-column>
          <el-table-column label="提交作业" fixed="right" width="225">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="primary"
                @click="commitStuWork(scope.$index, scope.row)"
                >源代码</el-button
              >
              <el-button
                size="mini"
                type="primary"
                @click="commitStuWorkPDF(scope.$index, scope.row)"
                >PDF</el-button
              >
              <el-button
                size="mini"
                type="danger"
                @click="stuHandleDelete(scope.$index, scope.row)"
                >删除</el-button
              >
            </template>
          </el-table-column>
        </el-table>
        <div class="block" style="width: auto">
          <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="currentPage"
            :page-sizes="sizes"
            :page-size="size"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total"
          >
          </el-pagination>
        </div>
      </div>
    </div>

    <el-dialog
      title="提交作业"
      :visible.sync="commitStuWorkVisible"
      width="400px"
    >
      <el-upload
        :action="commitStuWorkUrl"
        :show-file-list="false"
        :before-upload="beforeUpload"
        :on-success="onSuccess"
        :on-error="onError"
        :disabled="commitStuWorkDisabled"
        :headers="headers"
        name="workFile"
        :data="commitStuWorkData"
      >
        <el-button
          size="small"
          type="success"
          :icon="commitStuWorkBtnIcon"
          :disabled="commitStuWorkDisabled"
          >{{ commitStuworkBtnText }}</el-button
        >
        <div slot="tip" class="el-upload__tip">
          仅能上传 c/cpp/java/python 源代码 或 pdf 文件，且不超过2MB
        </div>
      </el-upload>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "StuWork",
  data() {
    return {
      // 提交作业模块
      commitStuWorkUrl: "",
      commitStuWorkDisabled: false,
      commitStuworkBtnText: "点击上传",
      commitStuWorkBtnIcon: "el-icon-upload2",
      commitStuWorkVisible: false,
      commitStuWorkData: {
        stuWorkId: "",
      },
      isCommitting: false,
      headers: {
        Authorization: window.sessionStorage.getItem("tokenStr"),
      },
      // 作业查询模块
      stuWorkData: [],
      currentPage: 1,
      sizes: [10, 20, 30, 40, 50],
      size: 10,
      total: 100,
    };
  },
  methods: {
    // 作业提交模块
    beforeUpload() {
      this.commitStuworkBtnText = "正在上传";
      this.commitStuWorkBtnIcon = "el-icon-loading";
      this.commitStuWorkDisabled = true;
    },
    onSuccess() {
      this.commitStuworkBtnText = "点击上传";
      this.commitStuWorkBtnIcon = "el-icon-upload2";
      this.commitStuWorkDisabled = false;
      this.isCommitting = true;
      this.initStuWork();

      this.commitStuWorkUrl = "";
      //this.commitStuWorkData.stuWorkId = "";
      this.commitStuWorkVisible = false;
    },
    onError() {
      this.commitStuworkBtnText = "点击上传";
      this.commitStuWorkBtnIcon = "el-icon-upload2";
      this.commitStuWorkUrl = "";
      this.commitStuWorkData.stuWorkId = "";
      this.commitStuWorkDisabled = false;
      this.commitStuWorkVisible = false;
    },
    // 作业查询模块
    initStuWork() {
      let url = "currentPage=" + this.currentPage + "&size=" + this.size;
      this.$get("/student/selectStuWorks?" + url).then((res) => {
        this.stuWorkData = res.data;
        this.total = res.total;
        if (this.isCommitting) {
          res.data.forEach((one) => {
            if (one.id == this.commitStuWorkData.stuWorkId) {
              if (one.workName == "未提交") {
                this.$message({
                  type: "error",
                  message: "提交失败，源码中含有中文符号或重复率较高",
                });
              } else {
                this.$message({ type: "success", message: "提交成功" });
              }
            }
          });
        }
        this.isCommitting = false;
        this.commitStuWorkData.stuWorkId = "";
      });
    },
    handleSizeChange(val) {
      this.size = val;
      this.initStuWork();
    },
    handleCurrentChange(val) {
      this.currentPage = val;
      this.initStuWork();
    },
    commitStuWork(index, row) {
      if (row.workName === "未提交") {
        this.$confirm(
          "仅能上传 c/cpp/java/python 源代码文件（除注释外，代码中不能含有中文符号），若有多个源代码文件请写到一个文件中并用注释标注，是否继续?",
          "提示",
          {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
          }
        )
          .then(() => {
            this.commitStuWorkUrl = "/student/uploadStuWork";
            this.commitStuWorkVisible = true;
            this.commitStuWorkData.stuWorkId = row.id;
          })
          .catch(() => {
            this.$message({
              type: "info",
              message: "已取消提交",
            });
          });
      } else {
        this.$message("删除后可重新提交");
      }
    },
    commitStuWorkPDF(index, row) {
      if (row.workName == "未提交") {
        this.$message({
          type: "warning",
          message: "上传源代码成功后才能上传PDF",
        });
      } else {
        if (row.pdfName === "未提交") {
          this.$confirm("仅能上传 pdf 文件, 是否继续?", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
          })
            .then(() => {
              this.commitStuWorkUrl = "/student/uploadStuWorkPDF";
              this.commitStuWorkVisible = true;
              this.commitStuWorkData.stuWorkId = row.id;
            })
            .catch(() => {
              this.$message({
                type: "info",
                message: "已取消提交",
              });
            });
        } else {
          this.$message("删除后可重新提交");
        }
      }
    },
    stuHandleDelete(index, row) {
      if (row.workName != "未提交" || row.pdfName != "未提交") {
        this.$confirm("此操作将永久删除源文件和PDF文件, 是否继续?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        })
          .then(() => {
            this.$delete("/student/deleteStudentWork/" + row.id).then((res) => {
              this.initStuWork();
            });
          })
          .catch(() => {
            this.$message({
              type: "info",
              message: "已取消删除",
            });
          });
      } else {
        this.$message("还没有交作业哦!!!");
      }
    },
  },
  computed: {},
  created() {
    this.initStuWork();
  },
};
</script>

<style scoped>
</style>
