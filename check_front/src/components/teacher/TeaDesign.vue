<template>
  <div>
    <div style="width: 1000px; heigth: 500px; margin-left: 100px">
      <div>
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="small"
          @click="createDesign"
          style="float: right"
          >布置课程设计</el-button
        >
      </div>
      <br />
      <div style="margin-top: 15px">
        <el-table :data="designData" stripe style="width: 100%" border>
          <el-table-column type="index" width="50"></el-table-column>
          <el-table-column
            prop="className"
            label="班级名称"
            width="180"
          ></el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="primary"
                @click="selectClassDesigns(scope.$index, scope.row)"
                >查看布置的课程设计</el-button
              >
              <el-button
                size="mini"
                type="primary"
                @click="selectGroup(scope.$index, scope.row)"
                >查看分组情况</el-button
              >
              <el-button
                size="mini"
                type="primary"
                @click="selectDesignResult(scope.$index, scope.row)"
                >查看设计结果</el-button
              >
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>

    <!-- 布置课程设计模块 -->
    <div>
      <el-dialog title="布置课程设计" :visible.sync="createDesignFormVisible">
        <el-form
          :model="createDesignFormModel"
          :rules="createDesignFormRules"
          ref="createDesignFormModel"
          label-width="100px"
        >
          <el-form-item label="课程设计" prop="designTitles">
            <el-select
              v-model="createDesignFormModel.designTitles"
              multiple
              collapse-tags
              placeholder="请选择设计标题"
            >
              <el-option
                v-for="item in designTitleOptions"
                :key="item.id"
                :label="item.designTitle"
                :value="item.id"
              >
                <span style="float: left">{{ item.designTitle }}</span>
                <el-button
                  class="el-icon-delete"
                  style="margin: 5px 0 0 20px"
                  circle
                  size="mini"
                  @click="deleteDesignTitle(item.id)"
                ></el-button>
              </el-option>
            </el-select>
            <el-button
              icon="el-icon-plus"
              @click="createDesignTitle"
              style="margin-left: 5px"
              size="medium"
              >添加设计</el-button
            >
          </el-form-item>
          <el-form-item label="选择班级" prop="designClasses">
            <el-checkbox-group v-model="createDesignFormModel.designClasses">
              <el-checkbox
                v-for="(classOne, index) in classes"
                :key="index"
                :label="classOne.className"
                name="designClasses"
              ></el-checkbox>
            </el-checkbox-group>
          </el-form-item>
          <el-form-item label="截至日期" required prop="designTime">
            <div class="block">
              <el-date-picker
                v-model="createDesignFormModel.designTime"
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
              @click="submitCreateDesignForm('createDesignFormModel')"
              >布置</el-button
            >
            <el-button @click="resetCreateDesignForm('createDesignFormModel')"
              >取消</el-button
            >
          </el-form-item>
        </el-form>
      </el-dialog>
    </div>

    <!-- 添加设计 -->
    <div>
      <el-dialog title="添加课程设计" :visible.sync="insertDesignFormVisible">
        <el-form
          :model="insertDesignFormModel"
          :rules="insertDesignFormRules"
          ref="insertDesignFormModel"
        >
          <el-form-item
            label="课程设计标题"
            :label-width="formLabelWidth"
            prop="designTitle"
          >
            <el-input
              v-model="insertDesignFormModel.designTitle"
              autocomplete="off"
            ></el-input>
          </el-form-item>
          <el-form-item
            label="选择人数限制"
            :label-width="formLabelWidth"
            prop="designLimit"
          >
            <el-input
              v-model="insertDesignFormModel.designLimit"
              autocomplete="off"
            ></el-input>
          </el-form-item>
          <el-upload
            class="upload-demo"
            ref="upload"
            :action="insertDesignPdfUrl"
            :data="insertDesignFormModel"
            :headers="headers"
            name="designPdf"
            :auto-upload="false"
            style="margin-left: 100px"
            :limit="1"
            @submit="submitUpload"
          >
            <el-button slot="trigger" size="small" type="primary"
              >选取文件</el-button
            >
            <!-- <el-button
              style="margin-left: 10px"
              size="small"
              type="success"
              @click="submitUpload"
              hidden
              >上传到服务器</el-button
            > -->
            <div slot="tip" class="el-upload__tip">
              请选择一个具体描述文件供学生下载,只能上传 pdf 文件
            </div>
          </el-upload>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="undoInsertDesign('insertDesignFormModel')"
            >取 消</el-button
          >
          <el-button
            type="primary"
            @click="submitInsertDesignForm('insertDesignFormModel')"
            >确 定</el-button
          >
        </div>
      </el-dialog>
    </div>
    <!-- 查看分组情况 -->
    <div>
      <el-dialog title="分组情况" :visible.sync="selectStudentsGroupVisible">
        <el-table :data="studentsGroupData">
          <el-table-column type="index" width="50"> </el-table-column>
          <el-table-column
            property="designTitle"
            label="设计标题"
            width="100"
          ></el-table-column>
          <el-table-column
            property="id"
            label="组ID"
            width="100"
          ></el-table-column>
          <el-table-column
            property="stuId1"
            label="组长"
            width="100"
          ></el-table-column>
          <el-table-column
            property="stuId2"
            label="组员1"
            width="100"
          ></el-table-column>
          <el-table-column
            property="stuId3"
            label="组员2"
            width="100"
          ></el-table-column>
          <el-table-column
            property="stuId4"
            label="组员3"
            width="100"
          ></el-table-column>
        </el-table>
      </el-dialog>
    </div>

    <!-- 查看布置的课程设计 -->
    <div>
      <el-dialog
        title="已布置的课程设计"
        :visible.sync="selectClassDesignsVisible"
        width="300px"
      >
        <div v-for="(design, index) in classDesignData" :key="index">
          <span>{{ design }}</span>
          <el-divider></el-divider>
        </div>
      </el-dialog>
    </div>

    <!-- 查看课程设计结果 -->
    <div>
      <el-dialog title="小组设计结果" :visible.sync="selectDesignResultVisible">
        <el-table :data="selectDesignResultData">
          <el-table-column type="index" width="50"> </el-table-column>
          <el-table-column
            property="groupId"
            label="组ID"
            width="80"
          ></el-table-column>
          <el-table-column
            property="designTitle"
            label="设计标题"
            width="100"
          ></el-table-column>
          <el-table-column property="codeName" label="源代码"></el-table-column>
          <el-table-column property="isChecked" width="50"></el-table-column>
          <el-table-column property="pdfName" label="PDF"></el-table-column>
          <el-table-column label="操作" width="90">
            <template slot-scope="scope">
              <el-button @click="downPdf(scope.row)" type="text" size="small"
                >下载</el-button
              >
              <!-- <el-button
                @click="inlinePdf(scope.row)"
                type="text"
                size="small"
                disabled
                >查看</el-button -->
            </template>
          </el-table-column>
          <el-table-column
            property="isCommit"
            label="完成情况"
          ></el-table-column>
        </el-table>
      </el-dialog>
    </div>

    <!-- 查看查重结果 -->
    <div></div>
  </div>
</template>

<script>
export default {
  name: "TeaDesign",
  data() {
    return {
      // 查看课程设计结果
      selectDesignResultVisible: false,
      selectDesignResultData: [],
      // 查看布置的课程设计
      selectClassDesignsVisible: false,
      classDesignData: [],
      // 分组情况
      selectStudentsGroupVisible: false,
      studentsGroupData: [],
      // 添加课程设计
      insertDesignFormRules: {
        designTitle: [
          { required: true, message: "请输入课程设计标题", trigger: "blur" },
          { min: 3, message: "长度不少于 3 个字符", trigger: "blur" },
        ],
      },
      insertDesignPdfUrl: "",
      designPdfLimit: 1,
      headers: {
        Authorization: window.sessionStorage.getItem("tokenStr"),
      },
      insertDesignFormVisible: false,
      insertDesignFormModel: {
        designTitle: "",
      },
      formLabelWidth: "120px",
      // 查看课程设计表格
      designData: [],
      // 布置课程设计模块
      classes: [],
      designTitleOptions: [],
      createDesignFormVisible: false,
      createDesignFormModel: {
        designTitles: [],
        designTime: "",
        designClasses: [],
      },
      createDesignFormRules: {
        designTitles: [
          {
            required: true,
            type: "array",
            message: "请至少选择一个设计",
            trigger: "change",
          },
        ],
        designTime: [
          {
            type: "date",
            required: true,
            message: "请选择截止时间",
            trigger: "change",
          },
        ],
        designClasses: [
          {
            type: "array",
            required: true,
            message: "请至少选择一个班级",
            trigger: "change",
          },
        ],
      },
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
    downPdf(row) {
      let flag = 2;
      this.downStuWork(row.id, flag);
    },
    downStuWork(groupDesignId, flag) {
      let url = "groupDesignId=" + groupDesignId + "&flag=" + flag;
      this.$download("/teacher/downloadDesign?" + url).then((res) => {});
    },
    // 查看小组设计结果
    selectDesignResult(index, row) {
      this.$get("/teacher/listClassDesigns/" + row.classId).then((res) => {
        this.selectDesignResultData = res;
        this.selectDesignResultVisible = true;
      });
    },
    // 查看布置的课程设计
    selectClassDesigns(index, row) {
      this.$get("/teacher/listClassOfDesigns/" + row.classId).then((res) => {
        this.classDesignData = res;
        this.selectClassDesignsVisible = true;
      });
    },
    // 分组情况
    selectGroup(index, row) {
      this.$get("/teacher/listStudentsGroups/" + row.classId).then((res) => {
        this.studentsGroupData = res;
        this.selectStudentsGroupVisible = true;
      });
    },
    // 添加设计模块
    submitUpload() {
      this.$refs.upload.submit();
      this.$refs.upload.clearFiles();
    },
    undoInsertDesign(insertDesignFormModel) {
      this.$refs[insertDesignFormModel].resetFields();
      this.$refs.upload.clearFiles();
      this.insertDesignFormVisible = false;
    },
    submitInsertDesignForm(insertDesignFormModel) {
      this.$refs[insertDesignFormModel].validate((valid) => {
        if (valid) {
          this.submitUpload();
          this.insertDesignFormModel.designTitle = "";
          this.insertDesignFormVisible = false;
          this.listDesignTitles();
        } else {
          console.log("error submit!!");
          return false;
        }
      });
      this.listDesignTitles();
    },
    createDesignTitle() {
      this.insertDesignFormVisible = true;
    },
    // 布置课程模块
    listDesignTitles() {
      this.$get("/teacher/listDesigns").then((res) => {
        this.designTitleOptions = res;
      });
    },
    listClasses() {
      this.$get("/teacher/getClasses").then((res) => {
        this.classes = res;
      });
    },
    createDesign() {
      this.insertDesignPdfUrl = "/teacher/createDesign";
      this.createDesignFormVisible = true;
      this.listDesignTitles();
      this.listClasses();
    },
    deleteDesignTitle(id) {
      this.$confirm("确定删除，是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.$delete("/teacher/deleteTheDesign/" + id).then((res) => {
            this.listDesignTitles();
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
    submitCreateDesignForm(createDesignFormModel) {
      this.$refs[createDesignFormModel].validate((valid) => {
        if (valid) {
          this.checkSubmitCreateDesign(createDesignFormModel);
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    checkSubmitCreateDesign(createDesignFormModel) {
      this.$confirm("确定布置课程设计?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          let date = this.createDesignFormModel.designTime;
          function p(s) {
            return s < 10 ? "0" + s : s;
          }
          let endTime =
            date.getFullYear() +
            "-" +
            p(date.getMonth() + 1) +
            "-" +
            p(date.getDate()) +
            " " +
            p(date.getHours()) +
            ":" +
            p(date.getMinutes()) +
            ":" +
            p(date.getSeconds());
          let url = "?endTime=" + endTime;
          this.createDesignFormModel.designTitles.forEach((designId) => {
            url += "&designIds=" + designId;
          });
          this.createDesignFormModel.designClasses.forEach((className) => {
            this.classes.forEach((classOne) => {
              if (classOne.className === className) {
                url += "&classIds=" + classOne.id;
              }
            });
          });
          this.$post("/teacher/disposeDesignToClasses" + url).then((res) => {
            this.initDesignClasses();
            this.createDesignFormVisible = false;
            this.$refs[createDesignFormModel].resetFields();
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消布置",
          });
        });
    },
    resetCreateDesignForm(createDesignFormModel) {
      this.createDesignFormVisible = false;
      this.$refs[createDesignFormModel].resetFields();
    },

    // 查看课程设计表格
    initDesignClasses() {
      this.$get("/teacher/listClassToDesign").then((res) => {
        this.designData = res;
      });
    },
  },
  created() {
    this.initDesignClasses();
  },
};
</script>

<style scoped>
</style>